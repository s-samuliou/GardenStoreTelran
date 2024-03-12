package org.garden.com.converter;

import org.garden.com.dto.UserDto;
import org.garden.com.entity.UserEntity;
import org.garden.com.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter<UserEntity, UserDto> {
    @Override
    public UserDto toDto(UserEntity userEntity) {
        return new UserDto(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getPhoneNumber(), userEntity.getRole().name());
    }

    @Override
    public UserEntity toEntity(UserDto userDto) {
        return new UserEntity(userDto.getName(), userDto.getEmail(), userDto.getPassword(), userDto.getPhoneNumber(), Role.valueOf(userDto.getRole()));
    }
}