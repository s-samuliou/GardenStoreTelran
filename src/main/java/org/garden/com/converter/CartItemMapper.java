package org.garden.com.converter;

import org.garden.com.dto.CartItemDto;
import org.garden.com.dto.CreateCartItemDto;
import org.garden.com.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface CartItemMapper {

    @Mapping(source = "product.id", target = "product_id")
    @Mapping(source = "cart.id", target = "cart_id")
    @Mapping(source = "quantity", target = "quantity")
    CartItemDto cartItemToCartItemDto(CartItem cartItem);

    @Mapping(source = "product.id", target = "product_id")
    @Mapping(source = "quantity", target = "quantity")
    CreateCartItemDto cartItemToCreateCartItemDto(CartItem cartItem);

    CartItem cartItemDtoToCartItem(CartItemDto cartItemDto);

    @Mapping(source = "product_id", target = "product.id")
    @Mapping(source = "quantity", target = "quantity")
    CartItem createCartItemDtoToCartItem(CreateCartItemDto createCartItemDto);
}
