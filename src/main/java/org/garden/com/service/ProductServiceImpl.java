package org.garden.com.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product create(Product product) {
        log.info("Creating product: {}", product);
        validateProduct(product);
        Product createdProduct = repository.save(product);
        log.info("Product created: {}", createdProduct);
        return createdProduct;
    }

    @Override
    public List<Product> getFiltered(Long categoryId, Double minPrice, Double maxPrice, Boolean discount, String sort) {
        log.info("Fetching filtered products. CategoryId: {}, MinPrice: {}, MaxPrice: {}, Discount: {}, Sort: {}", categoryId, minPrice, maxPrice, discount, sort);
        List<Product> products = repository.findFiltered(categoryId, minPrice, maxPrice, discount, sort);
        log.info("Found {} filtered products", products.size());
        return products;
    }

    @Override
    public Product edit(long id, Product product) {
        log.info("Editing product with ID {}: {}", id, product);
        validateProduct(product);
        Product existingProduct = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = repository.save(existingProduct);
        log.info("Product updated: {}", updatedProduct);
        return updatedProduct;
    }

    @Override
    public Product findById(long id) {
        log.info("Fetching product with ID: {}", id);
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        log.info("Found product: {}", product);
        return product;
    }

    @Override
    public ResponseEntity<Void> delete(long id) {
        log.info("Deleting product with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Product with ID {} deleted", id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            log.warn("Product not deleted: {}", id);
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

    private void validateProduct(Product product) {
        log.info("Validating product: {}", product);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            log.warn("Validation exception: {}", product);
            throw new ProductInvalidArgumentException(violations.iterator().next().getMessage());
        }
    }
}
