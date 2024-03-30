package org.garden.com.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.garden.com.converter.UserMapper;
import org.garden.com.dto.CreateUserDto;
import org.garden.com.dto.EditUserDto;
import org.garden.com.dto.UserDto;
import org.garden.com.entity.User;
import org.garden.com.exceptions.UserInvalidArgumentException;
import org.garden.com.exceptions.UserNotFoundException;
import org.garden.com.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Tag(name="User Controller", description="Handles operations related to users")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user based on the provided data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created user"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserDto createUser(@RequestBody CreateUserDto createUserDto) {
        log.info("Received request to create user: {}", createUserDto);
        User user = mapper.createUserDtoToUser(createUserDto);
        User createdUser = service.createUser(user);
        CreateUserDto createdUserDto = mapper.userToCreateUserDto(createdUser);
        log.info("User created: {}", createdUserDto);
        return createdUserDto;
    }

    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved  users"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping()
    public List<UserDto> getAllUsers() {
        log.info("Received request to get all users");
        List<User> users = service.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(user -> mapper.userToUserDto(user))
                .collect(Collectors.toList());
        log.info("Found {} users", users.size());
        return userDtos;
    }

    @Operation(
            summary = "Update a user",
            description = "Updates an existing user with the provided ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated user"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PutMapping("/{id}")
    public EditUserDto updateUserById(@PathVariable("id") long id, @RequestBody EditUserDto editUserDto) {
        log.info("Received request to update user with ID {}: {}", id, editUserDto);
        User user = mapper.editUserDtoToUser(editUserDto);
        service.editUser(id, user);
        EditUserDto updatedUserDto = mapper.userToEditUserDto(user);
        log.info("User updated: {}", updatedUserDto);
        return updatedUserDto;
    }

    @Operation(
            summary = "Get a user by ID",
            description = "Retrieves a user by its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") long id) {
        log.info("Received request to get user with ID: {}", id);
        User user = service.findUserById(id);
       UserDto userDto = mapper.userToUserDto(user);
        log.info("Found user: {}", userDto);
        return userDto;
    }
    @Operation(
            summary = "Delete a user by ID",
            description = "Deletes a user with the provided ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted user"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") long id) {
        log.info("Received request to delete user with ID: {}", id);
        return service.deleteUser(id);
    }

    @ExceptionHandler({UserInvalidArgumentException.class, UserNotFoundException.class})
    public ResponseEntity<String> handleUserException(Exception exception) {
        HttpStatus status = (exception instanceof UserInvalidArgumentException)
                ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        log.error("Error occurred: {}", exception.getMessage());
        return ResponseEntity.status(status).body(exception.getMessage());
    }
}
