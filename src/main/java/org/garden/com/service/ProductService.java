package org.garden.com.service;

import org.garden.com.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product editProduct(long id, Product product);

    Product findProductById(long id);

    ResponseEntity<Void> deleteProduct(long id);
}
