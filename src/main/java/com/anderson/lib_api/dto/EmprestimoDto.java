package com.anderson.lib_api.dto;

import com.anderson.lib_api.models.Aluno;
import com.anderson.lib_api.models.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record EmprestimoDto(UUID id,
                            LocalDateTime created_at,
                            LocalDate data_vencimento,
                            LocalDate data_devolucao,
                            boolean is_active,
                            @NotNull
                            UUID id_aluno,
                            @NotNull
                            UUID id_livro) {
}
