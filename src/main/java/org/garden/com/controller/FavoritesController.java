package org.garden.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.garden.com.converter.FavoritesMapper;
import org.garden.com.dto.FavoriteCreateDto;
import org.garden.com.dto.FavoritesDto;
import org.garden.com.entity.Favorites;
import org.garden.com.exceptions.FavoriteInvalidArgumentException;
import org.garden.com.exceptions.FavoriteNotFoundException;
import org.garden.com.service.FavoritesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name="Favorites Controller", description="Handles operations related to favorites")
@RestController
@RequestMapping("/v1/favorites")
public class FavoritesController {

    @Autowired
    FavoritesService service;

    @Autowired
    FavoritesMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Operation(
            summary = "Create a new favorite product",
            description = "Creates a new favorite product based on the provided data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created favorite product"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping()
    public ResponseEntity<FavoriteCreateDto> create(@RequestBody FavoriteCreateDto favoriteCreateDto) {
        log.info("Received request to create favorite product: {}", favoriteCreateDto);
        Favorites favorite = mapper.favoritesCreateDtoToFavorites(favoriteCreateDto);
        Favorites createdFavorite = service.addFavorite(favorite);
        FavoriteCreateDto createdFavoriteDto = mapper.favoritesToFavoriteCreateDto(createdFavorite);
        log.info("Favorite product created: {}", createdFavoriteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFavoriteDto);
    }

    @Operation(
            summary = "Get all favorite products",
            description = "Retrieves a list of all favorite products",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved favorite products"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public List<FavoritesDto> getListFavorites() {
        log.info("Received request to get all favorite products");
        List<Favorites> favoritesProducts = service.getAll();
        List<FavoritesDto> favoritesProductsDto = favoritesProducts.stream()
                .map(product -> mapper.favoritesToFavoritesDto(product))
                .collect(Collectors.toList());
        log.info("Found {} products", favoritesProducts.size());
        return favoritesProductsDto;
    }

    @Operation(
            summary = "Delete favorite product by ID",
            description = "Deletes a favorite product by its ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted favorite product"),
                    @ApiResponse(responseCode = "404", description = "Favorite product not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") long id) {
        log.info("Received request to delete favorite product with ID: {}", id);
        return service.deleteById(id);
    }

    @ExceptionHandler({FavoriteInvalidArgumentException.class, FavoriteNotFoundException.class})
    public ResponseEntity<String> handleProductException(Exception exception) {
        HttpStatus status = (exception instanceof FavoriteInvalidArgumentException) ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        log.error("Error occurred: {}", exception.getMessage());
        return ResponseEntity.status(status).body(exception.getMessage());
    }
}
