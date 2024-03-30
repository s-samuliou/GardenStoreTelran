package org.garden.com.entity;

import org.garden.com.enums.Role;
import org.garden.com.repository.CartJpaRepository;
import org.garden.com.repository.UserJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCartRelationshipTest {

    @Autowired
    private CartJpaRepository cartJpaRepository;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @Test
    public void testUserCartRelationship(){
        User user = new User();
        user.setId(1);
        user.setRole(Role.CUSTOMER);
        user.setName("Vasya");
        user.setEmail("Vasya@228.com");
        user.setPassword("asdgee34");
        user.setPhoneNumber("21453243");

        when(userJpaRepository.save(any(User.class))).thenReturn(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCartItemsList(new ArrayList<>());
        cart = cartJpaRepository.save(cart);

        Cart retrievedCart = cartJpaRepository.findById(cart.getId()).orElse(null);
        assertNotNull(retrievedCart);
        assertNotNull(retrievedCart.getUser());
        assertEquals(user.getId(), retrievedCart.getUser().getId());
    }
}
