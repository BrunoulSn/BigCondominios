package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record ReservaDTO(
        Long id,

        @NotNull(message = "ID do morador é obrigatório")
        Long moradorId,

        @NotNull(message = "ID da área é obrigatório")
        Long areaId,

        @NotNull(message = "Data e hora de início é obrigatória")
        @FutureOrPresent(message = "Data de início deve ser no presente ou futuro")
        LocalDateTime dataHoraInicio,

        @NotNull(message = "Data e hora de fim é obrigatória")
        @Future(message = "Data de fim deve ser no futuro")
        LocalDateTime dataHoraFim,

        @NotBlank(message = "Status é obrigatório")
        @Pattern(regexp = "^(pendente|confirmada|cancelada)$", message = "Status deve ser 'pendente', 'confirmada' ou 'cancelada'")
        String status
) {}
