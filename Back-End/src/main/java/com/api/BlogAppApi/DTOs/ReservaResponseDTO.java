package com.api.BlogAppApi.DTOs;

import com.api.BlogAppApi.model.ReservaDB;
import com.api.BlogAppApi.model.AreaComumDB;
import com.api.BlogAppApi.model.moradorDB;

import java.time.LocalDate;

public record ReservaResponseDTO(
    Long id,
    String status,
    LocalDate dataReserva,
    moradorDB morador,
    AreaComumDB area
) {
    public static ReservaResponseDTO fromEntity(ReservaDB reserva) {
        return new ReservaResponseDTO(
            reserva.getId(),
            reserva.getStatus(),
            reserva.getDataReserva(),
            reserva.getMorador(),
            reserva.getArea()
        );
    }
}
