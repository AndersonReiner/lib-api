package com.anderson.lib_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AlunoDto(UUID id,
                       @NotBlank
                       String nome,
                       @NotBlank
                       String email,
                       @NotNull
                       LocalDate dataNasc,
                       @NotBlank
                       String cpf,
                       @NotNull
                       UUID id_curso,
                       @NotNull
                       Integer matricula,
                       @NotBlank
                       String telefone) {
}
