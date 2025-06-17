
package com.api.BigCondominio.GestaoCondominio;

import org.springframework.stereotype.Service;

import com.api.BigCondominio.model.AreaComumDB;
import com.api.BigCondominio.model.ReservaDB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class gestaoReserva {
    private List<ReservaDB> reservas = new ArrayList<>();
    private List<AreaComumDB> areasComuns = new ArrayList<>();

    public void adicionarAreaComum(AreaComumDB area) {
        areasComuns.add(area);
    }

    public List<AreaComumDB> listarAreasDisponiveisParaData(LocalDateTime data) {
        return areasComuns.stream()
                .filter(area -> isAreaDisponivel(area, data))
                .collect(Collectors.toList());
    }

    public void cancelarReserva(String reservaId) {
        ReservaDB reserva = reservas.stream()
                .filter(r -> r.getId().equals(reservaId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        if ("CONCLUIDA".equals(reserva.getStatus())) {
            throw new IllegalStateException("Não é possível cancelar uma reserva já concluída");
        }

        reserva.setStatus("CANCELADA");
    }

    public List<ReservaDB> listarReservasFuturas() {
        LocalDate agora = LocalDate.now();
        return reservas.stream()
                .filter(r -> r.getDataReserva().isAfter(agora))
                .filter(r -> !"CANCELADA".equals(r.getStatus()))
                .collect(Collectors.toList());
    }

    public List<AreaComumDB> listarTodasAreas() {
        return new ArrayList<>(areasComuns);
    }

    private boolean isAreaDisponivel(AreaComumDB area, LocalDateTime data) {
        LocalDate dataReserva = data.toLocalDate(); // converte aqui

        return reservas.stream()
                .filter(r -> r.getArea().equals(area))
                .filter(r -> !"CANCELADA".equalsIgnoreCase(r.getStatus()))
                .noneMatch(r -> verificaConflitoDeDatas(r, dataReserva));
    }

    private boolean verificaConflitoDeDatas(ReservaDB reserva, LocalDate data) {
        return reserva.getDataReserva().equals(data);
    }
    
    public void fazerReserva(ReservaDB reserva1) {
        reservas.add(reserva1);
    }
}
