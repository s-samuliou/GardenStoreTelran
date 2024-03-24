package org.garden.com.converter;

import org.garden.com.dto.CategoryCreateDto;
import org.garden.com.dto.CategoryDto;
import org.garden.com.dto.EditCategoryDto;
import org.garden.com.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "name", target = "name")
    CategoryDto categoryToCategoryDto(Category category);

    CategoryCreateDto categoryToCreateCategoryDto(Category category);

    Category categoryDtoToCategory(CategoryDto categoryDto);

    @Mapping(source = "name", target = "name")
    Category createCategoryDtoToCategory(CategoryCreateDto categoryCreateDto);

    @Mapping(source = "name", target = "name")
    Category editCategoryDtoToCategory(EditCategoryDto editCategoryDto);

    EditCategoryDto categoryToEditCategoryDto(Category category);
}
