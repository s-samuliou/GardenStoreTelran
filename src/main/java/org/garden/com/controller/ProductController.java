package org.garden.com.controller;

import org.garden.com.converter.ProductMapper;
import org.garden.com.dto.CreateProductDto;
import org.garden.com.dto.EditProductDto;
import org.garden.com.dto.ProductDto;
import org.garden.com.entity.Product;
import org.garden.com.exceptions.InvalidProductException;
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
    ProductServiceImpl service;

    @Autowired
    ProductMapper mapper;

    @PostMapping()
    public ResponseEntity<CreateProductDto> createProduct(@RequestBody CreateProductDto createProductDto) {
        try {
            Product product = mapper.createProductDtoToProduct(createProductDto);
            Product createdProduct = service.createProduct(product);
            CreateProductDto createdProductDto = mapper.productToCreateProductDto(createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
        } catch (InvalidProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        try {
            List<Product> products = service.getAllProducts();
            List<ProductDto> productDtos = products.stream()
                    .map(product -> mapper.productToProductDto(product))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(productDtos);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditProductDto> updateProductById(@PathVariable("id") long id, @RequestBody EditProductDto editProductDto) {
        try {
            Product product = mapper.editProductDtoToProduct(editProductDto);
            service.editProduct(id, product);
            return ResponseEntity.status(HttpStatus.OK).body(mapper.productToEditProductDto(product));
        } catch (InvalidProductException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id) {
        try {
            Product product = service.findProductById(id);
            mapper.productToProductDto(product);
            return ResponseEntity.status(HttpStatus.OK).body(mapper.productToProductDto(product));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") long id) {
        return service.deleteProduct(id);
    }
}
