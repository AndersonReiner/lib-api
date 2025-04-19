package com.anderson.lib_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CursoDto(UUID id,
                       @NotBlank
                       String nome,
                       @NotNull
                       Integer cargaHoraria) {
}
