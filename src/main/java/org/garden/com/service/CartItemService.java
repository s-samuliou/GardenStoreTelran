package org.garden.com.service;

import org.garden.com.entity.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> getAll(Long cartId);

    void deleteById(long id);
}
