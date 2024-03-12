package org.garden.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    private long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
}
