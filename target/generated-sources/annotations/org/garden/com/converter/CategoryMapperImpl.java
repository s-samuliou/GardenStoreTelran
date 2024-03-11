package org.garden.com.converter;

import javax.annotation.processing.Generated;
import org.garden.com.dto.CategoryCreateDto;
import org.garden.com.dto.CategoryDto;
import org.garden.com.entity.Category;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-11T15:42:50+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        long id = 0L;

        id = category.getId();

        String categoryName = null;

        CategoryDto categoryDto = new CategoryDto( id, categoryName );

        return categoryDto;
    }

    @Override
    public CategoryCreateDto categoryToCreateCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        long id = 0L;

        id = category.getId();

        String categoryName = null;

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto( id, categoryName );

        return categoryCreateDto;
    }

    @Override
    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDto.getId() );

        return category;
    }

    @Override
    public Category createCategoryDtoToCategory(CategoryCreateDto categoryCreateDto) {
        if ( categoryCreateDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryCreateDto.getId() );

        return category;
    }
}
