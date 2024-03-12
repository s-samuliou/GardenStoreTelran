package org.garden.com.controller;

import lombok.RequiredArgsConstructor;
import org.garden.com.converter.Converter;
import org.garden.com.dto.UserDto;
import org.garden.com.entity.UserEntity;
import org.garden.com.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final Converter<UserEntity, UserDto> converter;

    @GetMapping
    public List<UserDto> list() {
        return userService.getAll().stream().map(converter::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        return converter.toDto(userService.create(converter.toEntity(dto)));
    }
}
