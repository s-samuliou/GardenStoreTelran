package org.garden.com.service;

import org.garden.com.entity.Favorites;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavoritesService {

    Favorites addFavorite(Favorites favorite);

    List<Favorites> getAll();

    ResponseEntity<Void> delete(Long id);
}
