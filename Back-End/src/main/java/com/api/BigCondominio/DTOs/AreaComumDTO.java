package com.api.BigCondominio.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AreaComumDTO(
        Long id,

        @NotBlank(message = "Nome da área comum é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotNull(message = "Disponibilidade é obrigatória")
        Boolean disponivel
) {}
