package org.garden.com.service;

import org.aspectj.weaver.ast.Or;
import org.garden.com.entity.Order;
import org.garden.com.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    List<Order> getAll();
}
