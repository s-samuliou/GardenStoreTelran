package org.garden.com.converter;

import org.garden.com.dto.CreateUserDto;
import org.garden.com.dto.UserDto;
import org.garden.com.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper{

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    UserDto userToUserDto(User user);

    CreateUserDto userToCreateUserDto(User user);

    User userDtoToUser(UserDto userDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    User createUserDtoToUser(CreateUserDto createUserDto);
}
