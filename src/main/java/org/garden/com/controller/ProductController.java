package org.garden.com.controller;

import org.garden.com.converter.ProductMapper;
import org.garden.com.dto.CreateProductDto;
import org.garden.com.dto.ProductDto;
import org.garden.com.entity.Product;
import org.garden.com.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CreateProductDto createProduct(@RequestBody CreateProductDto createProductDto) {
        Product product = mapper.createProductDtoToProduct(createProductDto);
        service.createProduct(product);

        return mapper.productToCreateProductDto(product);
    }

    @GetMapping()
    public List<ProductDto> getAllProducts() {
        List<Product> products = service.getAllProducts();

        return products.stream()
                .map(product -> mapper.productToProductDto(product))
                .collect(Collectors.toList());
    }
}
