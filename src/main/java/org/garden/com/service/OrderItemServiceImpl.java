package org.garden.com.service;

import org.garden.com.entity.OrderItem;
import org.garden.com.exceptions.OrderItemNotFoundException;
import org.garden.com.repository.OrderItemJpaRepository;
import org.garden.com.repository.OrderJpaRepository;
import org.garden.com.repository.ProductJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemJpaRepository repository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    OrderJpaRepository orderJpaRepository;

    private static final Logger log = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    @Override
    public List<OrderItem> getAll() {
        log.info("Fetching the list of OrderItem");
        List<OrderItem> orderItemList = repository.findAll();
        return orderItemList;
    }


    @Override
    public OrderItem findById(long id) {
        log.info("Find the orderItem by ID : {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem with id " + id + " not found"));
    }
}
