package org.garden.com.service;

import org.garden.com.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();
}
