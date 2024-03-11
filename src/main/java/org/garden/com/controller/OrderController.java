package org.garden.com.controller;

import org.garden.com.converter.OrderMapper;
import org.garden.com.dto.OrderCreateDto;
import org.garden.com.dto.OrderDto;
import org.garden.com.entity.Order;
import org.garden.com.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    OrderMapper orderMapper;

    @GetMapping
    public List<OrderDto> getAll() {
        return orderService.getAll().stream().map(orderMapper::orderToOrderDto).collect(Collectors.toList());
    }

    @PostMapping
    public OrderCreateDto createOrder(@RequestBody OrderCreateDto dto) {
        Order order = orderMapper.createOrderDtoToOrder(dto);
        orderService.create(order);

        return orderMapper.orderToOrderCreateDto(order);
    }
}
