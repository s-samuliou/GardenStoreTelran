package org.garden.com.controller;

import org.garden.com.converter.ProductMapper;
import org.garden.com.dto.CreateProductDto;
import org.garden.com.dto.ProductDto;
import org.garden.com.entity.Product;
import org.garden.com.exceptions.InvalidProductException;
import org.garden.com.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
