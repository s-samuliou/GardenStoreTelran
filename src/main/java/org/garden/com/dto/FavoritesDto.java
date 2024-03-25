package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FavoritesDto {

    @Schema(description = "The ID of the favorite", example = "1")
    private long favoriteId;

    @Schema(description = "The ID of the user", example = "1")
    private long userId;

    @Schema(description = "The favorite productDto", example = "1")
    private ProductDto products;

    public FavoritesDto() {
    }

    public FavoritesDto(long favoriteId, long userId, ProductDto products) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.products = products;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ProductDto getProducts() {
        return products;
    }

    public void setProducts(ProductDto products) {
        this.products = products;
    }

    public long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }
}
