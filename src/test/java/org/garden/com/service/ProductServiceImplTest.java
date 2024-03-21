package org.garden.com.service;

import org.garden.com.entity.Product;
import org.garden.com.repository.ProductJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductJpaRepository productRepository;

    @Test
    public void testCreateProduct_Success() {
        Product product = new Product();
        product.setName("TestName");
        product.setDescription("TestDescription");
        product.setPrice(11.99);
        product.setCategoryId(1);
        product.setImageUrl("TestImageUrl");

        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals(product.getName(), createdProduct.getName());
        assertEquals(product.getDescription(), createdProduct.getDescription());
        assertEquals(product.getPrice(), createdProduct.getPrice(), 0.01); // Указываем допустимую погрешность для сравнения чисел с плавающей точкой
        assertEquals(product.getCategoryId(), createdProduct.getCategoryId());
        assertEquals(product.getImageUrl(), createdProduct.getImageUrl());
    }

    @Test
    public void testGetAllProducts_Success() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Product product = new Product();

            product.setName("TestName" + i);
            product.setDescription("TestDescription" + i);
            product.setPrice(11.99);
            product.setCategoryId(1);
            product.setImageUrl("TestImageUrl" + i);

            products.add(product);
        }

        when(productRepository.findAll()).thenReturn(products);

        /*List<Product> retrievedProducts = productService.getFilteredProducts();

        assertEquals(products.size(), retrievedProducts.size());

        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i), retrievedProducts.get(i));
        }*/
    }
}
