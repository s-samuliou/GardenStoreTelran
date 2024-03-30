package org.garden.com.service;

import org.garden.com.entity.CartItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartItemService {

    List<CartItem> getAll(Long cartId);

    ResponseEntity<Void> deleteById(long id);
}
