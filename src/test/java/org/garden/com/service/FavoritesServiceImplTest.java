package org.garden.com.service;

import jakarta.validation.Validator;
import org.garden.com.entity.Favorites;
import org.garden.com.entity.Product;
import org.garden.com.entity.User;
import org.garden.com.exceptions.FavoriteNotFoundException;
import org.garden.com.repository.FavoritesJpaRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FavoritesServiceImplTest {

    @InjectMocks
    private FavoritesServiceImpl favoritesService;

    @Mock
    private FavoritesJpaRepository repository;

    @Mock
    private Validator validator;

    public FavoritesServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addFavoriteProduct_ValidFavorite_ReturnsCreatedFavorite() {
        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);

        Favorites favorite = new Favorites();
        favorite.setUser(user);
        favorite.setProduct(product);

        when(repository.save(favorite)).thenReturn(favorite);
        Favorites createdFavorite = favoritesService.addFavorite(favorite);

        assertNotNull(createdFavorite);
        assertEquals(favorite, createdFavorite);
        verify(repository, times(1)).save(favorite);
    }

    @Test
    public void getAllFavorites_ReturnsListOfFavorites() {
        List<Favorites> favoriteList = new ArrayList<>();
        favoriteList.add(new Favorites());
        favoriteList.add(new Favorites());

        when(repository.findAll()).thenReturn(favoriteList);

        List<Favorites> retrievedFavorites = favoritesService.getAll();

        assertNotNull(retrievedFavorites);
        assertEquals(2, retrievedFavorites.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void deleteFavorite_ExistingFavorite_DeletesFavorite() {
        long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        favoritesService.deleteById(id);

        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void deleteFavorite_NonExistingFavorite_ThrowsFavoriteNotFoundException() {
        long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(FavoriteNotFoundException.class, () -> favoritesService.deleteById(id));

        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(id);
    }
}
