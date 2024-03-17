package org.garden.com.service;

import org.garden.com.entity.Order;
import org.garden.com.exceptions.OrderNotFoundException;
import org.garden.com.repository.OrderJpaRepository;
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

    @MockBean
    private OrderJpaRepository orderRepository;

    @Test
    public void testFindById_ExistingOrder() {
        // Mock data
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        Optional<Order> optionalOrder = Optional.of(expectedOrder);
        when(orderRepository.findById(1L)).thenReturn(optionalOrder);

        // Call the service method
        Order actualOrder = orderService.findById(1L);

        // Verify the result
        assertNotNull(actualOrder);
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testFindById_NonExistingOrder() {
        // Mock repository behavior to return empty optional
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that OrderNotFoundException is thrown
        assertThrows(OrderNotFoundException.class, () -> orderService.findById(1));
    }

    @Test
    public void testDeleteById_ExistingOrder() {
        // Mock data
        Order expectedOrder = new Order();
        expectedOrder.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));

        // Call the service method
        Order deletedOrder = orderService.deleteById(1);

        // Verify the result
        assertNotNull(deletedOrder);
        assertEquals(expectedOrder, deletedOrder);
        verify(orderRepository, times(1)).delete(expectedOrder);
    }

    @Test
    public void testDeleteById_NonExistingOrder() {
        // Mock repository behavior to return empty optional
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that OrderNotFoundException is thrown
        assertThrows(OrderNotFoundException.class, () -> orderService.deleteById(1));
        // Verify that delete method is not called
        verify(orderRepository, never()).delete(any());
    }
}
