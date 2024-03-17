package org.garden.com.converter;

import javax.annotation.processing.Generated;
import org.garden.com.dto.CreateUserDto;
import org.garden.com.dto.UserDto;
import org.garden.com.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-14T20:43:42+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        String name = null;
        String email = null;
        String phoneNumber = null;
        long id = 0L;

        name = user.getName();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        id = user.getId();

        UserDto userDto = new UserDto( id, name, email, phoneNumber );

        return userDto;
    }

    @Override
    public CreateUserDto userToCreateUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        String name = null;
        String email = null;
        String password = null;
        String phoneNumber = null;

        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        phoneNumber = user.getPhoneNumber();

        CreateUserDto createUserDto = new CreateUserDto( name, email, password, phoneNumber );

        return createUserDto;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setName( userDto.getName() );
        user.setEmail( userDto.getEmail() );
        user.setPhoneNumber( userDto.getPhoneNumber() );

        return user;
    }

    @Override
    public User createUserDtoToUser(CreateUserDto createUserDto) {
        if ( createUserDto == null ) {
            return null;
        }

        User user = new User();

        user.setName( createUserDto.getName() );
        user.setEmail( createUserDto.getEmail() );
        user.setPassword( createUserDto.getPassword() );
        user.setPhoneNumber( createUserDto.getPhoneNumber() );

        return user;
    }
}
