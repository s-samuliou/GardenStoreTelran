package org.garden.com.service;

import org.garden.com.entity.Favorites;

import java.util.List;

public interface FavoritesService {

    Favorites addFavorite(Favorites favorite);

    List<Favorites> getAll();

    void deleteById(Long id);
}
