package org.garden.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.garden.com.converter.ProductMapper;
import org.garden.com.dto.CreateProductDto;
import org.garden.com.dto.EditProductDto;
import org.garden.com.dto.ProductDto;
import org.garden.com.entity.Product;
import org.garden.com.exceptions.ProductInvalidArgumentException;
import org.garden.com.exceptions.ProductNotFoundException;
import org.garden.com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name="Product Controller", description="Handles operations related to products")
@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product based on the provided data",
            responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created product"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping()
    public ResponseEntity<CreateProductDto> create(@RequestBody CreateProductDto createProductDto) {
        log.info("Received request to create product: {}", createProductDto);
        Product product = mapper.createProductDtoToProduct(createProductDto);
        Product createdProduct = service.create(product);
        CreateProductDto createdProductDto = mapper.productToCreateProductDto(createdProduct);
        log.debug("Product created: {}", createdProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all products with optional filtering and sorting options",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved  products"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping()
    public List<ProductDto> getAll(
            @RequestParam(required = false) Long categoryId, @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice, @RequestParam(required = false) Boolean discount,
            @RequestParam(required = false) String sort) {

        log.info("Received request to get all products. CategoryId: {}, MinPrice: {}, MaxPrice: {}, Discount: {}, Sort: {}", categoryId, minPrice, maxPrice, discount, sort);
        List<Product> products = service.getFiltered(categoryId, minPrice, maxPrice, discount, sort);
        List<ProductDto> productDtos = products.stream()
                .map(product -> mapper.productToProductDto(product))
                .collect(Collectors.toList());
        log.debug("Found {} products", products.size());
        return productDtos;
    }

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product with the provided ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated product"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public EditProductDto editById(@PathVariable("id") long id, @RequestBody EditProductDto editProductDto) {
        log.info("Received request to update product with ID {}: {}", id, editProductDto);
        Product product = mapper.editProductDtoToProduct(editProductDto);
        Product editedProduct = service.editById(id, product);
        EditProductDto updatedProductDto = mapper.productToEditProductDto(editedProduct);
        log.debug("Product updated: {}", updatedProductDto);
        return updatedProductDto;
    }

    @Operation(
            summary = "Get a product by ID",
            description = "Retrieves a product by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable("id") long id) {
        log.info("Received request to get product with ID: {}", id);
        Product product = service.findById(id);
        ProductDto productDto = mapper.productToProductDto(product);
        log.debug("Found product: {}", productDto);
        return productDto;
    }

    @Operation(
            summary = "Delete a product by ID",
            description = "Deletes a product with the provided ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted product"),
                    @ApiResponse(responseCode = "404", description = "Product not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        log.info("Received request to delete product with ID: {}", id);
        service.deleteById(id);
    }

    @ExceptionHandler({ProductInvalidArgumentException.class, ProductNotFoundException.class})
    public ResponseEntity<String> handleProductException(Exception exception) {
        HttpStatus status = (exception instanceof ProductInvalidArgumentException) ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        log.error("Error occurred: {}", exception.getMessage());
        return ResponseEntity.status(status).body(exception.getMessage());
    }
}
