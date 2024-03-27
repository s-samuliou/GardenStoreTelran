package org.garden.com.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.garden.com.converter.OrderItemMapper;
import org.garden.com.dto.OrderItemDto;
import org.garden.com.entity.OrderItem;
import org.garden.com.exceptions.OrderItemNotFoundException;
import org.garden.com.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "OrderItem Controller", description = "Handles operations related to orderItems")
@RestController
@RequestMapping("/v1/orders/items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    private static final Logger log = LoggerFactory.getLogger(OrderItemController.class);

    @Operation(
            summary = "Get all orderItems",
            description = "Retrieves a list of all orderItems",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Orders list received successfully"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )
    @GetMapping
    public List<OrderItemDto> getAll() {
        log.info("Received request to get all orderItems");

        List<OrderItem> orderItems = orderItemService.getAll();
        List<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(orderItemMapper::orderItemToOrderItemDto)
                .collect(Collectors.toList());

        log.info("OrderItemController found {} of OrderItems", orderItemDtos.size());
        return orderItemDtos;
    }

    @GetMapping("/{id}")
    public OrderItemDto getById(@PathVariable(name = "id") long id) {
        log.info("Received request to get the OrderItem with ID: {} ", id);

        OrderItem orderItem = orderItemService.findById(id);
        if (orderItem == null) {
            throw new OrderItemNotFoundException("OrderItem not found with id: " + id);
        }
        return orderItemMapper.orderItemToOrderItemDto(orderItem);
    }
}
