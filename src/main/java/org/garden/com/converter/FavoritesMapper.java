package org.garden.com.converter;

import org.garden.com.dto.FavoritesDto;
import org.garden.com.entity.Favorites;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoritesMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    FavoritesDto favoritesToFavoritesDto(Favorites favorites);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "productId", target = "product.id")
    Favorites favoritesDtoToFavorites(FavoritesDto favoritesDto);
}
