package org.garden.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.converter.ProductMapper;
import org.garden.com.dto.CreateProductDto;
import org.garden.com.dto.EditProductDto;
import org.garden.com.entity.Product;
import org.garden.com.service.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private ProductMapper productMapper;

    @Test
    public void testCreateProduct() throws Exception {
        CreateProductDto createProductDto = new CreateProductDto();
        createProductDto.setName("Test Product");
        createProductDto.setDescription("Test Description");
        createProductDto.setPrice(10.0);
        createProductDto.setCategoryId(1L);
        createProductDto.setImageUrl("test_image.jpg");

        Product createdProduct = new Product();
        when(productService.create(any(Product.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createProductDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Определяем поведение мок-сервиса
        List<Product> products = new ArrayList<>();
        when(productService.getFiltered(anyLong(), anyDouble(), anyDouble(), anyBoolean(), anyString()))
                .thenReturn(products);

        mockMvc.perform(get("/v1/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateProductById() throws Exception {
        EditProductDto editProductDto = new EditProductDto();

        Product updatedProduct = new Product();
        when(productService.edit(anyLong(), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/v1/products/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(editProductDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product();
        when(productService.findById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/v1/products/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProductById() throws Exception {
        ResponseEntity<Void> responseEntity = ResponseEntity.ok().build();
        when(productService.delete(anyLong())).thenReturn(responseEntity);

        mockMvc.perform(delete("/v1/products/{id}", 1))
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
