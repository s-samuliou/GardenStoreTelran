package org.garden.com.entity;

import org.garden.com.repository.CategoryJpaRepository;
import org.garden.com.repository.ProductJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRelationshipTest {

    @Autowired
    private ProductJpaRepository productRepository;

    @MockBean
    private CategoryJpaRepository categoryRepository;

    @Test
    public void testProductCategoryRelationship() {
        Category category = new Category();
        category.setName("Test Category");
        category.setId(1);

        when(categoryRepository.save(category)).thenReturn(category);


        Product product = new Product();
        product.setName("Test Product");
        product.setCategory(category);
        product.setDescription("TestDescription");
        product.setPrice(11.99);
        product.setImageUrl("TestImageUrl");
        product = productRepository.save(product);

        Product retrievedProduct = productRepository.findById(product.getId()).orElse(null);
        assertNotNull(retrievedProduct);
        assertNotNull(retrievedProduct.getCategory());
        assertEquals(category.getId(), retrievedProduct.getCategoryId());
    }
}
