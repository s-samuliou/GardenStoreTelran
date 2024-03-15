package org.garden.com.converter;

import javax.annotation.processing.Generated;
import org.garden.com.dto.CategoryCreateDto;
import org.garden.com.dto.CategoryDto;
import org.garden.com.dto.EditCategoryDto;
import org.garden.com.entity.Category;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-15T14:09:41+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        long id = 0L;
        String name = null;

        id = category.getId();
        name = category.getName();

        CategoryDto categoryDto = new CategoryDto( id, name );

        return categoryDto;
    }

    @Override
    public CategoryCreateDto categoryToCreateCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        long id = 0L;
        String name = null;

        id = category.getId();
        name = category.getName();

        CategoryCreateDto categoryCreateDto = new CategoryCreateDto( id, name );

        return categoryCreateDto;
    }

    @Override
    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDto.getId() );
        category.setName( categoryDto.getName() );

        return category;
    }

    @Override
    public Category createCategoryDtoToCategory(CategoryCreateDto categoryCreateDto) {
        if ( categoryCreateDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( categoryCreateDto.getName() );
        category.setId( categoryCreateDto.getId() );

        return category;
    }

    @Override
    public Category editCategoryDtoToCategory(EditCategoryDto editCategoryDto) {
        if ( editCategoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( editCategoryDto.getId() );
        category.setName( editCategoryDto.getName() );

        return category;
    }

    @Override
    public EditCategoryDto categoryToEditCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        EditCategoryDto editCategoryDto = new EditCategoryDto();

        editCategoryDto.setId( category.getId() );
        editCategoryDto.setName( category.getName() );

        return editCategoryDto;
    }
}
