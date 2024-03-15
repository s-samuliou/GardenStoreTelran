package org.garden.com.service;

import org.garden.com.entity.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    List<Order> getAll();

    Order findById(long id);

    Order deleteById(long id);
}