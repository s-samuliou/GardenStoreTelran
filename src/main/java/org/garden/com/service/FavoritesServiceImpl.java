package org.garden.com.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.garden.com.entity.Favorites;
import org.garden.com.exceptions.FavoriteInvalidArgumentException;
import org.garden.com.exceptions.FavoriteNotFoundException;
import org.garden.com.repository.FavoritesJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    private FavoritesJpaRepository repository;

    @Autowired
    private Validator validator;

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Favorites addFavorite(Favorites favorite) {
        log.info("Creating favorite product: {}", favorite);
        validateProduct(favorite);
        Favorites createdFavoriteProduct = repository.save(favorite);
        log.debug("Product created: {}", createdFavoriteProduct);
        return createdFavoriteProduct;
    }

    @Override
    public List<Favorites> getAll() {
        log.info("Fetching favorites products");
        List<Favorites> favoriteProducts = repository.findAll();
        log.debug("Found {} filtered products", favoriteProducts.size());
        return favoriteProducts;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting favorite product with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.debug("Favorite product with ID {} deleted", id);
        } else {
            log.warn("Favorite product not deleted: {}", id);
            throw new FavoriteNotFoundException("Favorite product not found with id: " + id);
        }
    }

    private void validateProduct(Favorites favorite) {
        log.info("Validating favorite product: {}", favorite);
        Set<ConstraintViolation<Favorites>> violations = validator.validate(favorite);
        if (!violations.isEmpty()) {
            log.warn("Validation exception: {}", favorite);
            throw new FavoriteInvalidArgumentException(violations.iterator().next().getMessage());
        }
    }
}
