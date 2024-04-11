package org.garden.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.garden.com.converter.OrderMapper;
import org.garden.com.dto.OrderCreateDto;
import org.garden.com.dto.OrderDto;
import org.garden.com.entity.Order;
import org.garden.com.exceptions.OrderInvalidArgumentException;
import org.garden.com.exceptions.OrderNotFoundException;
import org.garden.com.exceptions.UserNotFoundException;
import org.garden.com.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Order Controller", description = "Handles operations related to orders")
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService service, OrderMapper orderMapperMock) {
        this.service = service;
    }

    @Operation(
            summary = "Get all orders",
            description = "Retrieves a list of all orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Orders list received successfully"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        log.debug("Received request to get all orders");

        List<Order> orders = service.getAll();
        List<OrderDto> orderDto = orders.stream()
                .map(order -> mapper.orderToOrderDto(order))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order based on the provided data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created order"),
                    @ApiResponse(responseCode = "404", description = "Not found")
            }
    )

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity <OrderCreateDto> create(@RequestBody OrderCreateDto dto) {
        log.debug("Received request to create order: {}", dto);

        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());

        Order order = mapper.createOrderDtoToOrder(dto);
        service.create(order);
        OrderCreateDto createdOrderDto = mapper.orderToOrderCreateDto(order);
        log.info("Order created: {}", dto);
        return ResponseEntity.status(HttpStatus.OK).body(createdOrderDto);
    }

    @Operation(
            summary = "Get the order by ID",
            description = "Receive the order by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully received order"),
                    @ApiResponse(responseCode = "404", description = "Order not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity <OrderDto> getById(@PathVariable(name = "id") long id) {
        log.debug("Received request to get the order with ID: {} ", id);

        Order order = service.findById(id);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        OrderDto orderDto = mapper.orderToOrderDto(order);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @Operation(
            summary = "Delete the order by ID",
            description = "Deletes the order with the provided ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Order not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") long id) {
        log.debug("Received request to delete product with ID: {} ", id);
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Get order history by user ID",
            description = "Retrieves the order history of a user by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order history received successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            }
    )
    @GetMapping("/history/{userId}")
    public ResponseEntity <List<OrderDto>> getHistoryByUserId(@PathVariable(name = "userId") long userId) {
        log.debug("Received request to get order history for user with ID: {}", userId);

        List<Order> orderHistory = service.getOrderHistoryByUserId(userId);
        if (orderHistory.isEmpty()) {
            throw new OrderNotFoundException("User with ID " + userId + " has no order history");
        }

        List<OrderDto> orderHistoryDto = orderHistory.stream()
                .map(order -> mapper.orderToOrderDto(order))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(orderHistoryDto);
    }

    @ExceptionHandler({OrderInvalidArgumentException.class, OrderNotFoundException.class})
    public ResponseEntity<Object> handleOrderException(Exception exception) {
        HttpStatus status = (exception instanceof OrderInvalidArgumentException) ?
                HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        log.debug("Error occurred: {}", exception.getMessage());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", exception.getMessage());
        return ResponseEntity.status(status).body(responseBody);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.debug("User not found: {}", exception.getMessage());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", exception.getMessage());
        return ResponseEntity.status(status).body(responseBody);
    }
}