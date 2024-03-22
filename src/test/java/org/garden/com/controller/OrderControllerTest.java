package org.garden.com.controller;

import org.garden.com.dto.OrderCreateDto;
import org.garden.com.dto.OrderDto;
import org.garden.com.entity.Order;
import org.garden.com.exceptions.OrderInvalidArgumentException;
import org.garden.com.exceptions.OrderNotFoundException;
import org.garden.com.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @Test
    public void testGetAllOrders() throws Exception {
        OrderDto orderDto1 = new OrderDto();
        OrderDto orderDto2 = new OrderDto();
        List<OrderDto> orderDtos = Arrays.asList(orderDto1, orderDto2);

        when(orderService.getAll()).thenReturn(Arrays.asList(new Order(), new Order()));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(orderDtos.size()));
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setCreatedAt(LocalDateTime.now());
        orderCreateDto.setUpdatedAt(LocalDateTime.now());

        Order order = new Order();
        order.setId(1L);

        when(orderService.create(any())).thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetOrderById() throws Exception {
        OrderDto orderDto = new OrderDto();

        when(orderService.findById(1)).thenReturn(new Order());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteOrderById() throws Exception {
        Order order = new Order();

        when(orderService.deleteById(1)).thenReturn(order);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/orders/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}