package org.garden.com.service;

import org.garden.com.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> getAll();

    OrderItem findById(long id);
}
