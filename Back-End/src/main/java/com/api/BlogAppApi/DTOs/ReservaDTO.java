package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReservaDTO(
        Long id,
        @NotNull Long moradorId,
        @NotNull Long areaId,
        @NotNull LocalDateTime dataHoraInicio,
        @NotNull LocalDateTime dataHoraFim,
        @NotBlank String status
) {}
