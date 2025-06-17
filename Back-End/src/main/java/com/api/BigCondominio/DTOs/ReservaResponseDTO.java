package com.api.BigCondominio.DTOs;

import java.time.LocalDate;

import com.api.BigCondominio.model.AreaComumDB;
import com.api.BigCondominio.model.ReservaDB;
import com.api.BigCondominio.model.moradorDB;

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
