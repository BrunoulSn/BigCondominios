package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AreaComumDTO(
        Long id,
        @NotBlank String nome,
        @NotNull boolean disponivel
) {}
