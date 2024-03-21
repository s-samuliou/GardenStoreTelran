package org.garden.com.controller;

import org.garden.com.converter.CategoryMapper;
import org.garden.com.dto.CategoryCreateDto;
import org.garden.com.dto.CategoryDto;
import org.garden.com.dto.EditCategoryDto;
import org.garden.com.entity.Category;
import org.garden.com.exceptions.CategoryNotFoundException;
import org.garden.com.exceptions.InvalidCategoryArgumentException;
import org.garden.com.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryMapper mapper;

    @GetMapping
    public List<CategoryDto> getAllCategories() {

            List<Category> categories = categoryService.getAllCategories();
            List<CategoryDto> categoryDtoList = categories.stream()
                    .map(category -> mapper.categoryToCategoryDto(category))
                    .collect(Collectors.toList());
            return categoryDtoList;

    }

    @PostMapping()
    public CategoryCreateDto createProduct(@RequestBody CategoryCreateDto categoryCreateDto) {

            Category category = mapper.createCategoryDtoToCategory(categoryCreateDto);
            Category createdCategory = categoryService.createCategory(category);
            CategoryCreateDto createdCategoryDto = mapper.categoryToCreateCategoryDto(createdCategory);
           return createdCategoryDto;
    }

    @PutMapping("/{id}")
    public EditCategoryDto editCategory(@PathVariable(name = "id") long id, @RequestBody EditCategoryDto editCategoryDto) {
            Category category = mapper.editCategoryDtoToCategory(editCategoryDto);
            Category editedCategory = categoryService.editCategory(id, category);
            EditCategoryDto editedCategoryDto = mapper.categoryToEditCategoryDto(editedCategory);
            return  editedCategoryDto;
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable(name = "id") long id) {
            categoryService.deleteCategoryById(id);
    }

    @ExceptionHandler({InvalidCategoryArgumentException.class, CategoryNotFoundException.class})
    public ResponseEntity<String> handleCategoryException(Exception exception) {
        HttpStatus status = (exception instanceof InvalidCategoryArgumentException) ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(exception.getMessage());
    }

}
