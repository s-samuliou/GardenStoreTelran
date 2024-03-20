package org.garden.com.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.garden.com.entity.Product;
import org.garden.com.exceptions.ProductInvalidArgumentException;
import org.garden.com.exceptions.ProductNotFoundException;
import org.garden.com.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductJpaRepository repository;

    @Autowired
    private Validator validator;

    @Override
    public Product createProduct(Product product) {
        validateProduct(product);
        return repository.save(product);
    }

    @Override
    public List<Product> getFilteredProducts(Long categoryId, Double minPrice, Double maxPrice, Boolean discount, String sort) {
        return repository.findFilteredProducts(categoryId, minPrice, maxPrice, discount, sort);
    }

    @Override
    public Product editProduct(long id, Product product) {
        validateProduct(product);
        Product existingProduct = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

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
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

    private void validateProduct(Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            throw new ProductInvalidArgumentException(violations.iterator().next().getMessage());
        }
    }
}
