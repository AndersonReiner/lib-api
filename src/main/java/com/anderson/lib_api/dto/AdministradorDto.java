package com.anderson.lib_api.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdministradorDto(UUID id,
                               @NotBlank                                                
                               String nome,
                               @NotBlank
                               String email,
                               @NotNull
                               LocalDate dataNasc,
                               @NotBlank
                               String cpf,
                               @NotBlank
                               String usuario,
                               @NotBlank
                               String senha){
    
}
