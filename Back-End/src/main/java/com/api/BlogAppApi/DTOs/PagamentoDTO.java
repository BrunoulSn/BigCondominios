package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoDTO(
        Long id,
        @NotNull Long moradorId,
        @NotNull BigDecimal valor,
        LocalDateTime dataPagamento,
        @NotBlank String tipo,
        @NotBlank String status,
        String formaPagamento
) {}
