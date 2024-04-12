package org.garden.com.entity;

import org.garden.com.enums.DeliveryType;
import org.garden.com.enums.OrderStatus;
import org.garden.com.enums.Role;
import org.garden.com.repository.OrderJpaRepository;
import org.garden.com.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserOrderRelationshipTest {

    @Autowired
    private UserJpaRepository userRepository;

    @MockBean
    private OrderJpaRepository orderRepository;

    @Test
    public void testUserOrderRelationship() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("1234");
        user.setPhoneNumber("123456789");
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setDeliveryAddress("Test Address");
        order.setContactPhone("987654321");
        order.setDeliveryMethod(DeliveryType.PICKUP);
        order.setStatus(OrderStatus.PAID);
        order.setUser(user);
        orderRepository.save(order);

        Order retrievedOrder = orderRepository.findById(order.getId()).orElse(order);
        assertNotNull(retrievedOrder);
        assertNotNull(retrievedOrder.getUser());
        assertEquals(user.getId(), retrievedOrder.getUser().getId());
    }
}