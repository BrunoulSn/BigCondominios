package com.api.BlogAppApi.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record VisitaDTO(
        Long id,
        @NotBlank String nomeVisitante,
        @NotBlank String documento,
        @NotNull Long moradorResponsavelId,
        LocalDateTime dataHoraEntrada,
        LocalDateTime dataHoraSaida,
        @NotBlank String status
) {}
