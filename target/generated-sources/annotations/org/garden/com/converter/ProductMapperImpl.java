package org.garden.com.converter;

import javax.annotation.processing.Generated;
import org.garden.com.dto.CreateProductDto;
import org.garden.com.dto.ProductDto;
import org.garden.com.entity.Product;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-14T20:43:42+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto productToProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        if ( product.getId() != null ) {
            productDto.setId( product.getId() );
        }
        productDto.setName( product.getName() );
        productDto.setDescription( product.getDescription() );
        productDto.setPrice( product.getPrice() );
        productDto.setCategoryId( product.getCategoryId() );
        productDto.setImageUrl( product.getImageUrl() );

        return productDto;
    }

    @Override
    public CreateProductDto productToCreateProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        CreateProductDto createProductDto = new CreateProductDto();

        createProductDto.setName( product.getName() );
        createProductDto.setDescription( product.getDescription() );
        createProductDto.setPrice( product.getPrice() );
        createProductDto.setCategoryId( product.getCategoryId() );
        createProductDto.setImageUrl( product.getImageUrl() );

        return createProductDto;
    }

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDto.getId() );
        product.setName( productDto.getName() );
        product.setDescription( productDto.getDescription() );
        product.setPrice( productDto.getPrice() );
        product.setCategoryId( productDto.getCategoryId() );
        product.setImageUrl( productDto.getImageUrl() );

        return product;
    }

    @Override
    public Product createProductDtoToProduct(CreateProductDto createProductDto) {
        if ( createProductDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( createProductDto.getName() );
        product.setDescription( createProductDto.getDescription() );
        product.setPrice( createProductDto.getPrice() );
        product.setCategoryId( createProductDto.getCategoryId() );
        product.setImageUrl( createProductDto.getImageUrl() );

        return product;
    }
}
