package org.garden.com.controller;

import org.garden.com.converter.OrderMapper;
import org.garden.com.dto.OrderCreateDto;
import org.garden.com.dto.OrderDto;
import org.garden.com.entity.Order;
import org.garden.com.exceptions.OrderInvalidArgumentException;
import org.garden.com.exceptions.OrderNotFoundException;
import org.garden.com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public OrderDto getAll() {

        List<Order> orders = orderService.getAll();
        List<OrderDto> orderDto = orders.stream()
                .map(order -> orderMapper.orderToOrderDto(order))
                .collect(Collectors.toList());
        return (OrderDto) orderDto;
    }

    @PostMapping
    public OrderCreateDto createOrder(@RequestBody OrderCreateDto dto) {

        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        Order order = orderMapper.createOrderDtoToOrder(dto);
        orderService.create(order);
        return orderMapper.orderToOrderCreateDto(order);
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable(name = "id") int id) {

        Order order = orderService.findById(id);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        OrderDto orderDto = orderMapper.orderToOrderDto(order);
        return orderDto;
    }

    @DeleteMapping("/{id}")
    public Order deleteById(@PathVariable(name = "id") int id) {
        return orderService.deleteById(id);
    }

    @ExceptionHandler({OrderInvalidArgumentException.class, OrderNotFoundException.class})
    public ResponseEntity<String> handleProductException(Exception exception) {
        HttpStatus status = (exception instanceof OrderInvalidArgumentException) ?
                HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(exception.getMessage());
    }
}