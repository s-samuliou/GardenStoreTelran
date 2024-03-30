package org.garden.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.converter.CartItemMapper;
import org.garden.com.dto.CreateCartItemDto;
import org.garden.com.entity.CartItem;
import org.garden.com.entity.Product;
import org.garden.com.service.CartItemService;
import org.garden.com.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartItemService cartItemService;

    @MockBean
    private ProductService productService;

    @MockBean
    private CartItemMapper cartItemMapper;

    @Test
    public void testGetAllItemsFromCart() throws Exception {
        List<CartItem> itemList = new ArrayList<>();
        when(cartItemService.getAll(anyLong())).thenReturn(itemList);

        mockMvc.perform(get("/v1/cart/{cartId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddProductIntoCart() throws Exception {

        CreateCartItemDto createCartItemDto = new CreateCartItemDto();
        createCartItemDto.setProduct_id(1L);
        createCartItemDto.setQuantity(1L);

        Product product = new Product();
        product.setId(1L);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(createCartItemDto.getQuantity());

        when(productService.findProductById(1L)).thenReturn(product);

        when(cartItemMapper.createCartItemDtoToCartItem(createCartItemDto)).thenReturn(cartItem);

        when(productService.addToCart(product, cartItem.getQuantity(), 1L)).thenReturn(cartItem);

        when(cartItemMapper.cartItemToCreateCartItemDto(cartItem)).thenReturn(createCartItemDto);

        mockMvc.perform(post("/v1/cart/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createCartItemDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCartItemById() throws Exception {
        mockMvc.perform(delete("/v1/cart/{id}", 1L))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
