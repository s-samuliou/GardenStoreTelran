package org.garden.com.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.converter.OrderItemMapper;
import org.garden.com.dto.OrderItemDto;
import org.garden.com.entity.OrderItem;
import org.garden.com.exceptions.OrderItemNotFoundException;
import org.garden.com.security.JwtService;
import org.garden.com.service.OrderItemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderItemController.class)
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class OrderItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private OrderItemServiceImpl orderItemService;

    @MockBean
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderItemController orderItemController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllOrderItems() throws Exception {
        List<OrderItem> orderItems = new ArrayList<>();
        when(orderItemService.getAll()).thenReturn(orderItems);
        mockMvc.perform(get("/v1/orders/items"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAll_thenReturnAllOrderItems() {
        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();

        List<OrderItem> allOrderItems = Arrays.asList(orderItem1, orderItem2);

        when(orderItemService.getAll()).thenReturn(allOrderItems);

        List<OrderItem> actualOrderItems = orderItemService.getAll();

        assertEquals(2, actualOrderItems.size());
        assertEquals(allOrderItems, actualOrderItems);
    }

    @Test
    public void whenGetAll_thenReturnEmptyList() {

        when(orderItemService.getAll()).thenReturn(Arrays.asList());

        List<OrderItem> actualOrderItems = orderItemService.getAll();

        assertEquals(0, actualOrderItems.size());
    }

    @Test
    public void testGetOrderItemById() throws Exception {
        // Given
        int orderItemId = 4;
        Long OrderItemDtoId = 4L;
        OrderItem orderItem = new OrderItem();
        OrderItemDto orderItemDto = new OrderItemDto(1L, 1L, 2L, 2, 100);


        orderItemDto.setId(OrderItemDtoId);

        when(orderItemService.findById(orderItemId)).thenReturn(orderItem);

        when(orderItemService.findById(orderItemId)).thenReturn(orderItem);

        when(orderItemMapper.orderItemToOrderItemDto(orderItem)).thenReturn(orderItemDto);

        mockMvc.perform(get("/v1/orders/items/{id}", orderItemId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(orderItemId));
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        // Given
        Mockito.when(orderItemService.findById(1L)).thenThrow(new OrderItemNotFoundException("OrderItem not found with id: 1"));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/items/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}