package com.api.BigCondominio.DTOs;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ReservaDTO(
        Long id,

        @NotNull(message = "ID do morador é obrigatório")
        Long moradorId,

        @NotNull(message = "ID da área é obrigatório")
        Long areaId,

        @NotNull(message = "Data e hora de início é obrigatória")
        @FutureOrPresent(message = "Data de início deve ser no presente ou futuro")
        LocalDate data
) {}
