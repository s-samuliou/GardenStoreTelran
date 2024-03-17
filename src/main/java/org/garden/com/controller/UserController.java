package org.garden.com.controller;

import org.garden.com.converter.UserMapper;
import org.garden.com.dto.CreateUserDto;
import org.garden.com.dto.EditUserDto;
import org.garden.com.dto.UserDto;
import org.garden.com.entity.User;
import org.garden.com.exceptions.UserNotFoundException;
import org.garden.com.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserServiceImpl service;

    @Autowired
    UserMapper mapper;

    @PostMapping()
    public ResponseEntity<CreateUserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        try {
            User user = mapper.createUserDtoToUser(createUserDto);
            User createdUser = service.create(user);
            CreateUserDto createdUserDto = mapper.userToCreateUserDto(createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            List<User> users = service.getAllUsers();
            List<UserDto> userDtos = users.stream()
                    .map(user -> mapper.userToUserDto(user))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(userDtos);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditUserDto> updateUserById(@PathVariable("id") long id, @RequestBody EditUserDto editUserDto) {
        try {
            User user = mapper.editUserDtoToUser(editUserDto);
            service.editUser(id, user);
            return ResponseEntity.status(HttpStatus.OK).body(mapper.userToEditUserDto(user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) {
        try {
            User user = service.findUserById(id);
            mapper.userToUserDto(user);
            return ResponseEntity.status(HttpStatus.OK).body(mapper.userToUserDto(user));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") long id) {
        return service.deleteUser(id);
    }
}
