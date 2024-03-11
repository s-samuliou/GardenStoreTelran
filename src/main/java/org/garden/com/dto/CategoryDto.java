package org.garden.com.dto;

public class CategoryDto {

    private long id;

    private String categoryName;

    public CategoryDto(long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
