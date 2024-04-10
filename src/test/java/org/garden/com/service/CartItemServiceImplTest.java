package org.garden.com.service;

import org.garden.com.entity.Cart;
import org.garden.com.entity.CartItem;
import org.garden.com.exceptions.CartItemNotFoundException;
import org.garden.com.repository.CartItemJpaRepository;
import org.garden.com.repository.CartJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartItemServiceImplTest {

    @Mock
    private CartItemJpaRepository repository;

    @Mock
    private CartJpaRepository cartJpaRepository;

    @InjectMocks
    private CartItemServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetListOfItems() {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        cartItems.add(new CartItem());
        cart.setCartItemsList(cartItems);

        when(cartJpaRepository.findById(cartId)).thenReturn(Optional.of(cart));

        List<CartItem> result = service.getAll(cartId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testDeleteItemById() {
        long cartItemId = 1L;

        when(repository.existsById(cartItemId)).thenReturn(true);

        service.deleteById(cartItemId);

        verify(repository, times(1)).existsById(cartItemId);
        verify(repository, times(1)).deleteById(cartItemId);
    }

    @Test
    public void testDeleteItemById_NotFound() {
        long cartItemId = 1L;

        when(repository.existsById(cartItemId)).thenReturn(false);

        CartItemNotFoundException exception = assertThrows(CartItemNotFoundException.class,
                () -> service.deleteById(cartItemId));

        assertEquals("Cart item with id: {} not found" + cartItemId, exception.getMessage());

        verify(repository, times(1)).existsById(cartItemId);
        verify(repository, never()).deleteById(cartItemId);
    }
}