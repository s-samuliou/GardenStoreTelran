package org.garden.com.converter;

import org.garden.com.dto.CreateProductDto;
import org.garden.com.dto.EditProductDto;
import org.garden.com.dto.ProductDto;
import org.garden.com.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "imageUrl", target = "imageUrl")
    ProductDto productToProductDto(Product product);

    CreateProductDto productToCreateProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "imageUrl", target = "imageUrl")
    Product createProductDtoToProduct(CreateProductDto createProductDto);

    EditProductDto productToEditProductDto(Product product);

    Product editProductDtoToProduct(EditProductDto editProductDto);
}
