package com.api.BigCondominio.DTOs;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record VisitaDTO(
        Long id,

        @NotBlank(message = "Nome do visitante é obrigatório")
        @Size(min = 3, max = 100, message = "Nome do visitante deve ter entre 3 e 100 caracteres")
        String nomeVisitante,

        @NotBlank(message = "Documento do visitante é obrigatório")
        @Size(min = 5, max = 20, message = "Documento deve ter entre 5 e 20 caracteres")
        String documento,

        @NotNull(message = "ID do morador responsável é obrigatório")
        Long moradorResponsavelId,

        @PastOrPresent(message = "Data de entrada não pode ser no futuro")
        LocalDateTime dataHoraEntrada,

        @FutureOrPresent(message = "Data de saída não pode ser no passado")
        LocalDateTime dataHoraSaida,

        @NotBlank(message = "Status é obrigatório")
        @Pattern(regexp = "^(registrada|encerrada|cancelada)$", message = "Status deve ser 'registrada', 'encerrada' ou 'cancelada'")
        String status
) {}
