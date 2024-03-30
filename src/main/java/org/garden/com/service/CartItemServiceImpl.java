package org.garden.com.service;

import org.garden.com.entity.Cart;
import org.garden.com.entity.CartItem;
import org.garden.com.exceptions.CartItemNotFoundException;
import org.garden.com.repository.CartItemJpaRepository;
import org.garden.com.repository.CartJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemJpaRepository repository;

    @Autowired
    private CartJpaRepository cartJpaRepository;

    private static final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Override
    public List<CartItem> getAll(Long cartId) {
        log.info("Fetching list of items from the cart");
        Cart cartById = cartJpaRepository.findById(cartId).get();
        List<CartItem> cartItems = cartById.getCartItemsList();
        log.info("Found {} items", cartItems.size());
        return cartItems;
    }

    @Override
    public ResponseEntity<Void> deleteById(long id) {
        log.info("Deleting cart item with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Cart item with ID {} deleted", id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.warn("Cart item not deleted: {}", id);
            throw new CartItemNotFoundException("Cart item with id: {} not found" + id);
        }
    }
}
