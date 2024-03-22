package org.garden.com.entity;

import org.garden.com.enums.OrderStatus;
import org.garden.com.enums.Role;
import org.garden.com.repository.OrderJpaRepository;
import org.garden.com.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderUserConnectionTest {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private OrderJpaRepository orderRepository;

    @Test
    public void testUserOrderRelationship() {
        // Create a user
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhoneNumber("123456789");
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        // Create an order associated with the user
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setDeliveryAddress("Test Address");
        order.setContactPhone("987654321");
        order.setDeliveryMethod("COURIER");
        order.setStatus(OrderStatus.PAID);
        order.setUser(user);
        orderRepository.save(order);

        // Retrieve the order from the database and check if the user is correct
        Order retrievedOrder = orderRepository.findById(order.getId()).orElse(null);
        assertNotNull(retrievedOrder);
        assertNotNull(retrievedOrder.getUser());
        assertEquals(user.getId(), retrievedOrder.getUser().getId());
    }
}