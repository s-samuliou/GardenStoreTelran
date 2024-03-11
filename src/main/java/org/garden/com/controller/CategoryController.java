package org.garden.com.controller;

import org.garden.com.converter.CategoryMapper;
import org.garden.com.dto.CategoryCreateDto;
import org.garden.com.dto.CategoryDto;
import org.garden.com.entity.Category;
import org.garden.com.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    CategoryMapper mapper;

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.getAllCategories().stream().map(mapper::categoryToCategoryDto).collect(Collectors.toList());
    }

    @PostMapping
    public CategoryCreateDto createCategory(@RequestBody CategoryCreateDto dto) {
        Category category = mapper.createCategoryDtoToCategory(dto);
        categoryService.createCategory(category);

        return mapper.categoryToCreateCategoryDto(category);

    }


}
