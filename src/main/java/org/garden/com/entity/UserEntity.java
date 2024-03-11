package org.garden.com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.garden.com.enums.Role;
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @NotBlank(message = "Name is required")
    @Length(max = 255, message = "Dont be more then 255")
    @Column(name = "name")
    private String name;

    @Email
    @NotBlank(message = "Email is required")
    @Column(name = "email")
    private String email;

    private String password;

    @NotBlank(message = "Phone number is required")
    @Length(max = 255, message = "Dont be more then 255")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity(String name, String email, String password, String phoneNumber, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}