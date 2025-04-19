package com.anderson.lib_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LivroDto(UUID id,
                       @NotBlank
                       String titulo,
                       @NotBlank
                       String autor,
                       @NotNull
                       LocalDate dataPublic,
                       @NotNull
                       Integer quantidade) {
}
