package org.garden.com.service;

import org.garden.com.entity.Order;
import org.garden.com.exceptions.OrderNotFoundException;
import org.garden.com.repository.OrderJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderJpaRepository repository;

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Order create(Order order) {
        log.info("Creating order: {}", order);
        return repository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public Order findById(long id) {
        log.info("Find the order by ID : {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public Order deleteById(long id) {
        log.info("Delete the order by ID : {}", id);
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        repository.delete(order);
        log.info("Order with ID : {} deleted", id);
        return order;
    }
}