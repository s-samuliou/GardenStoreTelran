package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryCreateDto {

    @Schema(description = "The name of category", example = "Table")
    private String name;

    public CategoryCreateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
