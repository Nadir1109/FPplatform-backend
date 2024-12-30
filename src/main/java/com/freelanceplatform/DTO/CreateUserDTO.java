package com.freelanceplatform.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    @Email
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Role is required")
    private UserRole role; // Rol van de gebruiker: CLIENT of FREELANCER
}