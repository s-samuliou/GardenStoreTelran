package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EditProductDto {

    @Schema(description = "The updated name of the product", example = "New Table")
    private String name;

    @Schema(description = "The updated description of the product", example = "A modern table with glass top")
    private String description;

    @Schema(description = "The updated price of the product", example = "129.99")
    private double price;

    @Schema(description = "The updated ID of the category to which the product belongs", example = "2")
    private long categoryId;

    @Schema(description = "The updated URL of the product image", example = "http://example.com/new_table.jpg")
    private String imageUrl;

    public EditProductDto() {
    }

    public EditProductDto(String name, String description, double price, long categoryId, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
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
