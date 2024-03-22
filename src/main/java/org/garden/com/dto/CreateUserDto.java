package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateUserDto {
    @Schema(description = "The name of the user", example = "Stephen")
    private String name;
    @Schema(description = "The email of the user", example = "example@gardenshop.com")
    private String email;
    @Schema(description = "The password of the user")
    private String password;
    @Schema(description = "The phone number of the user", example = "01759999999")
    private String phoneNumber;

    public CreateUserDto(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}