package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.NotBlank;

public record MoradorDTO(
        Long id,
        @NotBlank String nome,
        @NotBlank String CPF,
        @NotBlank String email,
        @NotBlank String senha,
        @NotBlank String apartamento,
        @NotBlank String bloco,
        @NotBlank String telefone              
) {}
