package org.garden.com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garden.com.converter.FavoritesMapper;
import org.garden.com.dto.FavoriteCreateDto;
import org.garden.com.dto.FavoritesDto;
import org.garden.com.entity.Favorites;
import org.garden.com.exceptions.FavoriteInvalidArgumentException;
import org.garden.com.exceptions.FavoriteNotFoundException;
import org.garden.com.service.FavoritesServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FavoritesController.class)
public class FavoritesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoritesServiceImpl favoritesService;

    @MockBean
    private FavoritesMapper mapper;

    @Test
    public void createFavoriteProduct_ValidInput_ReturnsCreated() throws Exception {
        FavoriteCreateDto favoriteCreateDto = new FavoriteCreateDto();
        favoriteCreateDto.setUserId(1L);
        favoriteCreateDto.setProductId(1L);

        Favorites createdFavorite = new Favorites();
        createdFavorite.setId(1L);

        when(mapper.favoritesCreateDtoToFavorites(any())).thenReturn(new Favorites());
        when(favoritesService.addFavoriteProduct(any())).thenReturn(createdFavorite);

        mockMvc.perform(post("/v1/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(favoriteCreateDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getListFavorites_ReturnsListOfFavoritesDto() throws Exception {
        Favorites favorites1 = new Favorites();
        favorites1.setId(1L);
        Favorites favorites2 = new Favorites();
        favorites2.setId(2L);

        FavoritesDto favoritesDto1 = new FavoritesDto();
        favoritesDto1.setFavoriteId(1L);
        FavoritesDto favoritesDto2 = new FavoritesDto();
        favoritesDto2.setFavoriteId(2L);

        when(favoritesService.getAllFavorites()).thenReturn(Arrays.asList(favorites1, favorites2));
        when(mapper.favoritesToFavoritesDto(favorites1)).thenReturn(favoritesDto1);
        when(mapper.favoritesToFavoritesDto(favorites2)).thenReturn(favoritesDto2);

        mockMvc.perform(get("/v1/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].favoriteId", is(1)))
                .andExpect(jsonPath("$[1].favoriteId", is(2)));
    }

    @Test
    public void deleteFavoriteProduct_ExistingProduct_ReturnsNoContent() throws Exception {
        when(favoritesService.deleteFavorite(anyLong())).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/v1/favorites/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void handleFavoriteException_FavoriteNotFoundException_ReturnsNotFound() throws Exception {
        FavoriteNotFoundException exception = new FavoriteNotFoundException("Favorite product not found");

        when(favoritesService.deleteFavorite(anyLong())).thenThrow(exception);

        mockMvc.perform(delete("/v1/favorites/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Favorite product not found"));
    }

    @Test
    public void handleFavoriteException_FavoriteInvalidArgumentException_ReturnsBadRequest() throws Exception {
        FavoriteInvalidArgumentException exception = new FavoriteInvalidArgumentException("Invalid favorite product data");

        when(favoritesService.deleteFavorite(anyLong())).thenThrow(exception);

        mockMvc.perform(delete("/v1/favorites/{id}", 1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid favorite product data"));
    }
}
