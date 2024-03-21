package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProductDto {

    @Schema(description = "The unique identifier of the product", example = "1")
    private long id;

    @Schema(description = "The name of the product", example = "Table")
    private String name;

    @Schema(description = "The description of the product", example = "A sturdy wooden table")
    private String description;

    @Schema(description = "The price of the product", example = "99.99")
    private double price;

    @Schema(description = "The ID of the category to which the product belongs", example = "1")
    private long categoryId;

    @Schema(description = "The URL of the product image", example = "http://example.com/table.jpg")
    private String imageUrl;

    public ProductDto() {
    }

    public ProductDto(long id, String name, String description, double price, long categoryId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
