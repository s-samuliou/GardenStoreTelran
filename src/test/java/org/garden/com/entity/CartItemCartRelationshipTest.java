package org.garden.com.entity;

import org.garden.com.repository.CartItemJpaRepository;
import org.garden.com.repository.CartJpaRepository;
import org.garden.com.repository.ProductJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartItemCartRelationshipTest {

    @Autowired
    private CartItemJpaRepository itemsJpaRepository;

    @MockBean
    private ProductJpaRepository productJpaRepository;

    @MockBean
    private CartJpaRepository cartJpaRepository;

    @Test
    public void testCartItemsCartRelationship(){
        Cart cart = new Cart();
        cart.setId(1);
        cart.setUser(new User());

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setCategory(new Category());
        product.setDescription("TestDescription");
        product.setPrice(11.99);
        product.setImageUrl("TestImageUrl");

        when(productJpaRepository.save(any(Product.class))).thenReturn(product);
        when(cartJpaRepository.save(any(Cart.class))).thenReturn(cart);

        CartItem item = new CartItem();
        item.setQuantity(1L);
        item.setProduct(product);
        item.setCart(cart);
        item = itemsJpaRepository.save(item);

        CartItem retrievedItem = itemsJpaRepository.findById(item.getId()).orElse(null);
        assertNotNull(retrievedItem);
        assertNotNull(retrievedItem.getCart());
        assertEquals(cart.getId(), retrievedItem.getCart().getId());
    }
}
