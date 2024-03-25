package org.garden.com.service;

import org.garden.com.entity.Favorites;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavoritesService {

    Favorites addFavoriteProduct(Favorites favorite);

    List<Favorites> getAllFavorites();

    ResponseEntity<Void> deleteFavorite(Long id);
}
