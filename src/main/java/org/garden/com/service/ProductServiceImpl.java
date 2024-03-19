package org.garden.com.service;

import org.garden.com.entity.Product;
import org.garden.com.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductJpaRepository repository;

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public List<Product> getFilteredProducts(Long categoryId, Double minPrice, Double maxPrice, Boolean discount, String sort) {
        return repository.findFilteredProducts(categoryId, minPrice, maxPrice, discount, sort);
    }

    @Override
    public Product editProduct(long id, Product product) {
        Product existingProduct = repository.findById(id).orElseThrow();

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setUpdatedAt(LocalDateTime.now());

        return repository.save(existingProduct);
    }

    @Override
    public Product findProductById(long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public ResponseEntity<Void> deleteProduct(long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
