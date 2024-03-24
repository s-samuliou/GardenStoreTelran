package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryDto {
    @Schema(description = "The unique identifier of the category", example = "1")
    private long id;
    @Schema(description = "The name of category", example = "Table")
    private String name;

    public CategoryDto(long id, String name) {
        this.id = id;
        this.name = name;
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
}
