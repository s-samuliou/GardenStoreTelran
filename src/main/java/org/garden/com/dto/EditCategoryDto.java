package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EditCategoryDto {

    @Schema(description = "The updated name of the", example = "New One")
    private String name;

    public EditCategoryDto(String name) {
        this.name = name;
    }


    public EditCategoryDto() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
