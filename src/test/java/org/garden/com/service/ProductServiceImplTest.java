package org.garden.com.service;

import org.garden.com.entity.Category;
import org.garden.com.entity.Product;
import org.garden.com.exceptions.ProductNotFoundException;
import org.garden.com.repository.ProductJpaRepository;
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
    private ProductJpaRepository repository;

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

        Product createdProduct = productService.createProduct(product);

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
        when(repository.findFilteredProducts(categoryId, minPrice, maxPrice, discount, sort)).thenReturn(productList);

        List<Product> filteredProducts = productService.getFilteredProducts(categoryId, minPrice, maxPrice, discount, sort);

        assertNotNull(filteredProducts);
        assertEquals(productList, filteredProducts);
        verify(repository, times(1)).findFilteredProducts(categoryId, minPrice, maxPrice, discount, sort);
    }

    @Test
    public void editProduct_ExistingProduct_ReturnsUpdatedProduct() {
        long id = 1L;
        Product existingProduct = new Product();
        Product updatedProduct = new Product();
        when(repository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(repository.save(existingProduct)).thenReturn(updatedProduct);

        Product result = productService.editProduct(id, existingProduct);

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

        Product result = productService.findProductById(id);

        assertNotNull(result);
        assertEquals(product, result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void deleteProduct_ExistingProduct_ReturnsOkResponse() {
        long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> result = productService.deleteProduct(id);

        assertNotNull(result);
        assertEquals(ResponseEntity.status(HttpStatus.OK).build(), result);
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void deleteProduct_NonExistingProduct_ThrowsProductNotFoundException() {
        long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(id));
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(id);
    }
}
