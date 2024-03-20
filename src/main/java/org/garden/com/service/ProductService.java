package org.garden.com.service;

import org.garden.com.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getFilteredProducts(Long categoryId, Double minPrice, Double maxPrice, Boolean discount, String sort);

    Product editProduct(long id, Product product);

    Product findProductById(long id);

    ResponseEntity<Void> deleteProduct(long id);
}
