package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MultaDTO(
        Long id,
        @NotNull Long moradorId,
        @NotBlank String descricao,
        @NotNull BigDecimal valor,
        @NotNull LocalDateTime dataOcorrencia,
        @NotNull LocalDateTime dataVencimento,
        @NotBlank String status,
        @NotBlank String gravidade
) {}
