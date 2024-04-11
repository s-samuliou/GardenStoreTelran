package org.garden.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.garden.com.converter.CategoryMapper;
import org.garden.com.dto.CategoryCreateDto;
import org.garden.com.dto.CategoryDto;
import org.garden.com.dto.EditCategoryDto;
import org.garden.com.entity.Category;
import org.garden.com.exceptions.CategoryNotFoundException;
import org.garden.com.exceptions.InvalidCategoryArgumentException;
import org.garden.com.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Category Controller", description = "Handles operations related to categories")
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Operation(
            summary = "Get all categories",
            description = "Retrieves a list of all categories",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoryDto>> getAll() {
        log.info("Received request to get all categories");
        List<Category> categories = service.getAll();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(category -> mapper.categoryToCategoryDto(category))
                .collect(Collectors.toList());
        log.debug("Found {} categories", categories.size());
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtoList);
    }

    @Operation(
            summary = "Create a new category",
            description = "Creates a new category based on the provided data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created category"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
            }
    )
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryCreateDto> create(@RequestBody CategoryCreateDto categoryCreateDto) {
        log.info("Received request to create category: {}", categoryCreateDto);
        Category category = mapper.createCategoryDtoToCategory(categoryCreateDto);
        Category createdCategory = service.create(category);
        CategoryCreateDto createdCategoryDto = mapper.categoryToCreateCategoryDto(createdCategory);
        log.debug("Category created: {}", createdCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategoryDto);
    }

    @Operation(
            summary = "Update a category",
            description = "Updates an existing category with the provided ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated category"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EditCategoryDto> edit(@PathVariable(name = "id") long id, @RequestBody EditCategoryDto editCategoryDto) {
        log.info("Received request to update a category with ID {}: {}", id, editCategoryDto);
        Category category = mapper.editCategoryDtoToCategory(editCategoryDto);
        Category editedCategory = service.edit(id, category);
        EditCategoryDto editedCategoryDto = mapper.categoryToEditCategoryDto(editedCategory);
        log.debug("Category updated: {}", editedCategoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(editedCategoryDto);
    }

    @Operation(
            summary = "Delete a category by ID",
            description = "Deletes a category with the provided ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted category"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") long id) {
        log.info("Received request to delete category with ID: {}", id);
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler({InvalidCategoryArgumentException.class, CategoryNotFoundException.class})
    public ResponseEntity<String> handleCategoryException(Exception exception) {
        HttpStatus status = (exception instanceof InvalidCategoryArgumentException) ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(exception.getMessage());
    }
}
