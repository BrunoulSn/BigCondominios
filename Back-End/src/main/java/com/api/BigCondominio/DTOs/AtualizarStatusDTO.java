package com.api.BigCondominio.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AtualizarStatusDTO(
        @NotBlank(message = "Status é obrigatório")
        @Pattern(regexp = "^(aberta|paga|vencida)$", message = "Status deve ser 'aberta', 'paga' ou 'vencida'")
        String status
) {}