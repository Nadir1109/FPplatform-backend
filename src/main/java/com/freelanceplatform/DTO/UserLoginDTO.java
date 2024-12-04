package com.freelanceplatform.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO voor het inloggen van een gebruiker")
public class UserLoginDTO {

    @NotNull
    @NotEmpty
    @Schema(description = "Emailadres van de gebruiker", example = "user@example.com")
    private String email;

    @NotNull
    @NotEmpty
    @Schema(description = "Wachtwoord van de gebruiker", example = "password123")
    private String password;
}
