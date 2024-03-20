package org.garden.com.controller;

import org.garden.com.converter.ProductMapper;
import org.garden.com.dto.CreateProductDto;
import org.garden.com.dto.EditProductDto;
import org.garden.com.dto.ProductDto;
import org.garden.com.entity.Product;
import org.garden.com.exceptions.ProductInvalidArgumentException;
import org.garden.com.exceptions.ProductNotFoundException;
import org.garden.com.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl service;

    @Autowired
    private ProductMapper mapper;

    @PostMapping()
    public CreateProductDto createProduct(@RequestBody CreateProductDto createProductDto) {
        Product product = mapper.createProductDtoToProduct(createProductDto);
        Product createdProduct = service.createProduct(product);
        CreateProductDto createdProductDto = mapper.productToCreateProductDto(createdProduct);
        return createdProductDto;
    }

    @GetMapping()
    public List<ProductDto> getAllProducts(
            @RequestParam(required = false) Long categoryId, @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice, @RequestParam(required = false) Boolean discount,
            @RequestParam(required = false) String sort) {

        List<Product> products = service.getFilteredProducts(categoryId, minPrice, maxPrice, discount, sort);
        List<ProductDto> productDtos = products.stream()
                .map(product -> mapper.productToProductDto(product))
                .collect(Collectors.toList());
        return productDtos;
    }

    @PutMapping("/{id}")
    public EditProductDto updateProductById(@PathVariable("id") long id, @RequestBody EditProductDto editProductDto) {
        Product product = mapper.editProductDtoToProduct(editProductDto);
        service.editProduct(id, product);
        return mapper.productToEditProductDto(product);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") long id) {
        Product product = service.findProductById(id);
        mapper.productToProductDto(product);
        return mapper.productToProductDto(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") long id) {
        return service.deleteProduct(id);
    }

    @ExceptionHandler({ProductInvalidArgumentException.class, ProductNotFoundException.class})
    public ResponseEntity<String> handleProductException(Exception exception) {
        HttpStatus status = (exception instanceof ProductInvalidArgumentException) ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(exception.getMessage());
    }
}
