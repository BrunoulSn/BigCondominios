package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MultaDTO(
        Long id,

        @NotNull(message = "ID do morador é obrigatório")
        Long moradorId,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
        String descricao,

        @NotNull(message = "Valor da multa é obrigatório")
        @DecimalMin(value = "0.01", inclusive = true, message = "Valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "Data da ocorrência é obrigatória")
        @PastOrPresent(message = "Data da ocorrência não pode ser no futuro")
        LocalDateTime dataOcorrencia,

        @NotNull(message = "Data de vencimento é obrigatória")
        @Future(message = "Data de vencimento deve ser no futuro")
        LocalDateTime dataVencimento,

        @NotBlank(message = "Status é obrigatório")
        @Pattern(regexp = "^(aberta|paga|vencida)$", message = "Status deve ser 'aberta', 'paga' ou 'vencida'")
        String status,

        @NotBlank(message = "Gravidade é obrigatória")
        @Pattern(regexp = "^(leve|moderada|grave)$", message = "Gravidade deve ser 'leve', 'moderada' ou 'grave'")
        String gravidade
) {}
