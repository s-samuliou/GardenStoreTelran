package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FavoriteCreateDto {

    @Schema(description = "The ID of the user", example = "1")
    private long userId;

    @Schema(description = "The ID of the favorite product", example = "1")
    private long productId;

    public FavoriteCreateDto() {
    }

    public FavoriteCreateDto(long userId, long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
