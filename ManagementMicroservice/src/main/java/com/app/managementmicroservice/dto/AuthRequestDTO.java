package com.app.managementmicroservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthRequestDTO {

    @NotNull( message = "El email es obligatorio." )
    @NotEmpty( message = "El email es obligatorio." )
    private String email;

    @NotNull( message = "La contraseña es obligatorio." )
    @NotEmpty( message = "La contraseña es obligatorio." )
    private String password;

}
