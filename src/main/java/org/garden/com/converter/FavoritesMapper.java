package org.garden.com.converter;

import org.garden.com.dto.FavoriteCreateDto;
import org.garden.com.dto.FavoritesDto;
import org.garden.com.entity.Favorites;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoritesMapper {

    @Mapping(source = "id", target = "favoriteId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product", target = "products")
    FavoritesDto favoritesToFavoritesDto(Favorites favorites);

    @Mapping(source = "favoriteId", target = "id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "products", target = "product")
    Favorites favoritesDtoToFavorites(FavoritesDto favoritesDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    FavoriteCreateDto favoritesToFavoriteCreateDto(Favorites favorites);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "productId", target = "product.id")
    Favorites favoritesCreateDtoToFavorites(FavoriteCreateDto favoriteCreateDto);
}
