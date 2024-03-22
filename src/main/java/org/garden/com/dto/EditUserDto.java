package org.garden.com.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EditUserDto {
    @Schema(description = "The updated name of the user", example = "Stevland")
    private String name;
    @Schema(description = "The updated phone number of the user", example = "01758888888")
    private String phoneNumber;

    public EditUserDto(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
