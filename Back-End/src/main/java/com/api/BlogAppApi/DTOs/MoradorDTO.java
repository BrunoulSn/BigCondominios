package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.NotBlank;

public record MoradorDTO(
        Long id,
        @NotBlank String nome,
        @NotBlank String apartamento,
        @NotBlank String telefone,
        @NotBlank String email
) {}
