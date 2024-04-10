package org.garden.com.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.garden.com.converter.OrderItemMapper;
import org.garden.com.dto.OrderItemDto;
import org.garden.com.entity.OrderItem;
import org.garden.com.exceptions.OrderItemInvalidArgumentException;
import org.garden.com.exceptions.OrderItemNotFoundException;
import org.garden.com.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "OrderItem Controller", description = "Handles operations related to orderItems")
@RestController
@RequestMapping("/v1/orders/items")
public class OrderItemController {

    @Autowired
    private OrderItemService service;

    @Autowired
    private OrderItemMapper mapper;

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
    public ResponseEntity <List<OrderItemDto>> getAll() {
        log.debug("Received request to get all orderItems");

        List<OrderItem> orderItems = service.getAll();
        List<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(mapper::orderItemToOrderItemDto)
                .collect(Collectors.toList());

        log.info("OrderItemController found {} of OrderItems", orderItemDtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(orderItemDtos);
    }

    @Operation(
            summary = "Get orderItem by id",
            description = "Receive the orderItem by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OrderItems list received successfully"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity <OrderItemDto> getById(@PathVariable(name = "id") long id) {
        log.debug("Received request to get the OrderItem with ID: {}", id);

        OrderItem orderItem = service.findById(id);
        if (orderItem == null) {
            throw new OrderItemNotFoundException("OrderItem not found with id: " + id);
        }

        OrderItemDto orderItemDto = mapper.orderItemToOrderItemDto(orderItem);

        return ResponseEntity.status(HttpStatus.OK).body(orderItemDto);
    }

    @ExceptionHandler({OrderItemInvalidArgumentException.class, OrderItemNotFoundException.class})

    public ResponseEntity<Object> handleOrderItemException(Exception exception) {
        HttpStatus status = (exception instanceof OrderItemInvalidArgumentException) ?
                HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        log.debug("Error occurred: {}", exception.getMessage());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", exception.getMessage());
        return ResponseEntity.status(status).body(responseBody);
    }
}
