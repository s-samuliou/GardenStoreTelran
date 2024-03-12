package org.garden.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;

    public UserDto(long id, String name, String email, String phoneNumber, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
