package com.anderson.lib_api.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDto(@NotBlank
                      String usuario,
                      @NotBlank
                      String senha) {
}
