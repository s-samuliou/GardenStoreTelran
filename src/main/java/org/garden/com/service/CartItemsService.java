package org.garden.com.service;

import org.garden.com.entity.CartItem;

import java.util.List;

public interface CartItemsService {

    List<CartItem> getListOfItems(Long cartId);

    void deleteItemById(long id);

}
