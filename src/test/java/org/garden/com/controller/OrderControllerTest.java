package org.garden.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.converter.OrderMapper;
import org.garden.com.dto.OrderDto;
import org.garden.com.entity.Order;
import org.garden.com.enums.DeliveryType;
import org.garden.com.enums.OrderStatus;
import org.garden.com.exceptions.OrderNotFoundException;
import org.garden.com.exceptions.UserNotFoundException;
import org.garden.com.security.JwtService;
import org.garden.com.service.OrderServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private OrderMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderController orderController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": \"4\"," +
                        "\"deliveryAddress\": \"Turkey\"," +
                        "\"contactPhone\": \"222330912\"," +
                        "\"deliveryMethod\": \"PICKUP\"," +
                        "\"status\": \"CREATED\"}"
                );
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        when(orderService.getAll()).thenReturn(orders);
        mockMvc.perform(get("/v1/orders"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOrderById() throws Exception {
        when(orderService.deleteById(anyLong())).thenReturn(null);
        mockMvc.perform(delete("/v1/orders/{id}", 4))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetOrderById() throws Exception {
        // Given
        int orderId = 4;
        Order order = new Order(); // create a mock Order object
        OrderDto orderDto = new OrderDto(1L, 1, LocalDateTime.now(), "Address_1", "123456789", DeliveryType.PICKUP, OrderStatus.PAID, LocalDateTime.now()); // create a mock OrderDto object
        orderDto.setId(orderId); // set the ID in the OrderDto

        // Mocking orderService.findById to return the mock Order object
        when(orderService.findById(orderId)).thenReturn(order);

        // Mocking orderMapper.orderToOrderDto to return the mock OrderDto object
        when(orderService.findById(orderId)).thenReturn(order);

        // Mocking orderMapper.orderToOrderDto to return the mock OrderDto object
        when(mapper.orderToOrderDto(order)).thenReturn(orderDto);

        // When performing a GET request to /v1/orders/{id}
        mockMvc.perform(get("/v1/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderId));  // Expects the returned JSON to have the correct ID
    }
    @Test
    public void testGetOrderByIdNotFound() throws Exception {
        // Given
        int orderId = 4;

        // Mocking orderService.findById to return null, simulating OrderNotFoundException
        when(orderService.findById(orderId)).thenReturn(null);

        // When performing a GET request to /v1/orders/{id}
        mockMvc.perform(get("/v1/orders/{id}", orderId))
                .andExpect(status().isNotFound()) // Expects HTTP 404 Not Found
                .andExpect(jsonPath("$.message").value("Order not found with id: " + orderId)); // Expects the correct error message
    }
    @Test
    public void shouldReturnOrderHistoryByUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User with ID 1 has no order history"));
    }


    @Test
    public void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() throws Exception {
        doThrow(new UserNotFoundException("User with ID " + 1L + " not found"))
                .when(orderService).getOrderHistoryByUserId(1L);

        mockMvc.perform(get("/v1/orders/history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(UserNotFoundException.class))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("User with ID 1 not found"));
    }

    @Test
    public void shouldThrowOrderNotFoundExceptionWhenNoOrderHistoryForUser() throws Exception {
        doThrow(new OrderNotFoundException("User with ID " + 1L + " has no order history"))
                .when(orderService).getOrderHistoryByUserId(1L);

        mockMvc.perform(get("/v1/orders/history/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(OrderNotFoundException.class))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("User with ID 1 has no order history"));
    }
}