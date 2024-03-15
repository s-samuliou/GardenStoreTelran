package org.garden.com.service;

import org.garden.com.entity.Order;
import org.garden.com.exceptions.OrderNotFoundException;
import org.garden.com.repository.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderJpaRepository repository;

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public Order findById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Order deleteById(long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        repository.delete(order);
        return order;
    }
}