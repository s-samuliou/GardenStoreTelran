package org.garden.com.service;

import org.garden.com.entity.OrderItem;
import org.garden.com.exceptions.OrderItemNotFoundException;
import org.garden.com.repository.OrderItemJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderItemServiceImplTest {

    @Mock
    private OrderItemJpaRepository repository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    public void testGetAll() {
        // Given
        OrderItem order1 = new OrderItem();
        OrderItem order2 = new OrderItem();
        List<OrderItem> expected = Arrays.asList(order1, order2);

        // When
        when(repository.findAll()).thenReturn(expected);
        List<OrderItem> actual = orderItemService.getAll();

        // Then
        assertEquals(expected, actual);
        verify(repository, times(1)).findAll();
    }
    @Test
    public void testFindById_Found() {
        Long itemId = 1L;
        OrderItem mockOrderItem = new OrderItem();
        mockOrderItem.setId(itemId);

        Mockito.when(repository.findById(itemId)).thenReturn(Optional.of(mockOrderItem));

        OrderItem result = orderItemService.findById(itemId);
        Assertions.assertEquals(mockOrderItem, result, "The returned OrderItem should be the same as the mock OrderItem");
    }

    /**
     * Test the findById method in OrderItemServiceImpl class.
     * Use case: No OrderItem is found.
     */
    @Test
    public void testFindById_NotFound() {
        Long itemId = 1L;
        Mockito.when(repository.findById(itemId)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                OrderItemNotFoundException.class,
                () -> orderItemService.findById(itemId),
                "OrderItemNotFoundException should be thrown as no OrderItem with the given id was found"
        );
    }
}