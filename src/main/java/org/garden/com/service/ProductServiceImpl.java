package org.garden.com.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.garden.com.entity.Cart;
import org.garden.com.entity.CartItem;
import org.garden.com.entity.Product;
import org.garden.com.entity.User;
import org.garden.com.exceptions.ProductInvalidArgumentException;
import org.garden.com.exceptions.ProductNotFoundException;
import org.garden.com.repository.CartItemJpaRepository;
import org.garden.com.repository.CartJpaRepository;
import org.garden.com.repository.ProductJpaRepository;
import org.garden.com.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductJpaRepository repository;

    @Autowired
    private CartJpaRepository cartJpaRepository;

    @Autowired
    private CartItemJpaRepository itemJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private Validator validator;

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product create(Product product) {
        log.info("Creating product: {}", product);
        validateProduct(product);
        Product createdProduct = repository.save(product);
        log.debug("Product created: {}", createdProduct);
        return createdProduct;
    }

    @Override
    public List<Product> getFiltered(Long categoryId, Double minPrice, Double maxPrice, Boolean discount, String sort) {
        log.info("Fetching filtered products. CategoryId: {}, MinPrice: {}, MaxPrice: {}, Discount: {}, Sort: {}", categoryId, minPrice, maxPrice, discount, sort);
        List<Product> products = repository.findFiltered(categoryId, minPrice, maxPrice, discount, sort);
        log.debug("Found {} filtered products", products.size());
        return products;
    }

    @Override
    public Product editById(long id, Product product) {
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
        log.debug("Product updated: {}", updatedProduct);
        return updatedProduct;
    }

    @Override
    public Product findById(long id) {
        log.info("Fetching product with ID: {}", id);
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        log.debug("Found product: {}", product);
        return product;
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting product with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.debug("Product with ID {} deleted", id);
        } else {
            log.warn("Product not deleted: {}", id);
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public CartItem addToCart(Product product, long quantity, Long userId) {
        log.info("Addition the product with ID: {} to user's cart(user ID: {})", product, userId);
        validateProduct(product);

        Optional<User> optionalUser = userJpaRepository.findById(userId);
        if (optionalUser.isEmpty()){
            log.error("User with ID {} not found", userId);
            return null;
        }

        User user = optionalUser.get();
        Cart cart = user.getCart();

        if (cart == null) {
            log.info("Creating a new cart for user with ID: {}", userId);
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItemsList(new ArrayList<>());
            user.setCart(cart);
        }
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);

            CartItem saved = itemJpaRepository.save(cartItem);
            cart.getCartItemsList().add(saved);
            cartJpaRepository.save(cart);

            log.debug("Product with ID: {} added to cart", saved.getProduct().getId());
            return saved;
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
