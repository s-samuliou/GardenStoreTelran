package org.garden.com.service;

import jakarta.validation.Validator;
import org.garden.com.entity.*;
import org.garden.com.exceptions.ProductNotFoundException;
import org.garden.com.repository.CartItemJpaRepository;
import org.garden.com.repository.CartJpaRepository;
import org.garden.com.repository.ProductJpaRepository;
import org.garden.com.repository.UserJpaRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private CartItemJpaRepository itemsJpaRepository;

    @Mock
    private CartJpaRepository cartJpaRepository;

    @Mock
    private ProductJpaRepository repository;

    @Mock
    private Validator validator;

    public ProductServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createProduct_ValidProduct_ReturnsCreatedProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10.0);
        product.setCategory(new Category());
        product.setImageUrl("test_image_url");

        when(repository.save(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals(product, createdProduct);
        verify(repository, times(1)).save(product);
    }

    @Test
    public void createProduct_InvalidProduct_ThrowsException() {
        Long categoryId = 1L;
        Double minPrice = 10.0;
        Double maxPrice = 100.0;
        Boolean discount = true;
        String sort = "name";
        List<Product> productList = new ArrayList<>();
        when(repository.findFiltered(categoryId, minPrice, maxPrice, discount, sort)).thenReturn(productList);

        List<Product> filteredProducts = productService.getFiltered(categoryId, minPrice, maxPrice, discount, sort);

        assertNotNull(filteredProducts);
        assertEquals(productList, filteredProducts);
        verify(repository, times(1)).findFiltered(categoryId, minPrice, maxPrice, discount, sort);
    }

    @Test
    public void editProduct_ExistingProduct_ReturnsUpdatedProduct() {
        long id = 1L;
        Product existingProduct = new Product();
        Product updatedProduct = new Product();
        when(repository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(repository.save(existingProduct)).thenReturn(updatedProduct);

        Product result = productService.editById(id, existingProduct);

        assertNotNull(result);
        assertEquals(updatedProduct, result);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existingProduct);
    }

    @Test
    public void findProductById_ExistingProduct_ReturnsProduct() {
        long id = 1L;
        Product product = new Product();
        when(repository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.findById(id);

        assertNotNull(result);
        assertEquals(product, result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testAddProductToCart() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        Long quantity = 2L;
        Long userId = 100L;

        User user = new User();
        user.setId(userId);
        user.setCart(new Cart());
        Optional<User> optionalUser = Optional.of(user);
        when(userJpaRepository.findById(anyLong())).thenReturn(optionalUser);

        CartItem savedCartItem = new CartItem();
        savedCartItem.setId(1L);
        savedCartItem.setProduct(product);
        savedCartItem.setQuantity(quantity);
        savedCartItem.setCart(user.getCart());
        when(itemsJpaRepository.save(any(CartItem.class))).thenReturn(savedCartItem);

        CartItem addedCartItem = productService.addToCart(product, quantity, userId);

        assertEquals(savedCartItem, addedCartItem);

        verify(userJpaRepository).findById(userId);
        verify(itemsJpaRepository).save(any(CartItem.class));
        verify(cartJpaRepository).save(user.getCart());
    }

    @Test
    public void deleteProduct_ExistingProduct_DeletesProduct() {
        long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        productService.deleteById(id);

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void deleteProduct_NonExistingProduct_ThrowsProductNotFoundException() {
        long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(id));
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(id);
    }
}
