package org.garden.com.converter;

import org.garden.com.dto.CartItemDto;
import org.garden.com.dto.CreateCartItemsDto;
import org.garden.com.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface CartItemsMapper {

    @Mapping(source = "product.id", target = "product_id")
    @Mapping(source = "cart.id", target = "cart_id")
    @Mapping(source = "quantity", target = "quantity")
    CartItemDto cartItemsToCartItemsDto(CartItem cartItem);

    @Mapping(source = "product.id", target = "product_id")
    @Mapping(source = "quantity", target = "quantity")
    CreateCartItemsDto cartItemsToCreateCartItemsDto(CartItem cartItem);

    CartItem cartItemsDtoToCartItems(CartItemDto cartItemDto);

    @Mapping(source = "product_id", target = "product.id")
    @Mapping(source = "quantity", target = "quantity")
    CartItem createCartItemsDtoToCartItems(CreateCartItemsDto createCartItemsDto);
}
