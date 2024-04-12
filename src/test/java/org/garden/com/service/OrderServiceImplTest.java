package org.garden.com.service;

import org.garden.com.entity.Order;
import org.garden.com.exceptions.OrderNotFoundException;
import org.garden.com.repository.OrderJpaRepository;
import org.garden.com.security.JwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private JwtService jwtService;

    @MockBean
    private OrderJpaRepository orderRepository;

    @Test
    public void testFindById_ExistingOrder() {
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        Optional<Order> optionalOrder = Optional.of(expectedOrder);
        when(orderRepository.findById(1L)).thenReturn(optionalOrder);

        Order actualOrder = orderService.findById(1L);

        assertNotNull(actualOrder);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testFindById_NonExistingOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.findById(1L));
    }

    @Test
    public void testDeleteById_ExistingOrder() {
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));

        Order deletedOrder = orderService.deleteById(1);

        assertNotNull(deletedOrder);
        assertEquals(expectedOrder, deletedOrder);
        verify(orderRepository, times(1)).delete(expectedOrder);
    }

    @Test
    public void testDeleteById_NonExistingOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteById(1));
        verify(orderRepository, never()).delete(any());
    }
}
