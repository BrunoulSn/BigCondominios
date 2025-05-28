
package com.api.BlogAppApi.GestaoCondominio;

import com.api.BlogAppApi.model.moradorDB;
import com.api.BlogAppApi.model.MultaDB;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
public class gestaoMultas {
    private List<MultaDB> multas;

    public gestaoMultas() {
        this.multas = new ArrayList<>();
    }

    public void aplicarMulta(MultaDB multa) {
        if (multa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da multa deve ser maior que zero");
        }
        multas.add(multa);
    }

    public List<MultaDB> listarMultasPorMorador(moradorDB morador) {
        return multas.stream()
                .filter(m -> m.getMorador().equals(morador))
                .collect(Collectors.toList());
    }

    public List<MultaDB> listarMultasPendentes() {
        return multas.stream()
                .filter(m -> "PENDENTE".equals(m.getStatus()))
                .collect(Collectors.toList());
    }

    public List<MultaDB> listarMultasVencidas() {
        return multas.stream()
                .filter(m -> "PENDENTE".equals(m.getStatus()))
                .filter(m -> m.getDataVencimento().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void registrarPagamentoMulta(long multaId, String comprovante) {
        MultaDB multa = buscarMultaPorId(multaId);
        if (multa != null && "PENDENTE".equals(multa.getStatus())) {
            multa.pagarMulta(comprovante);
        } else {
            throw new IllegalStateException("Multa não encontrada ou não está pendente");
        }
    }

    public void contestarMulta(long multaId, String motivo) {
        MultaDB multa = buscarMultaPorId(multaId);
        if (multa != null && "PENDENTE".equals(multa.getStatus())) {
            multa.contestarMulta(motivo);
        } else {
            throw new IllegalStateException("Multa não encontrada ou não pode ser contestada");
        }
    }

    public void cancelarMulta(String multaId, String motivo) {
        Long id = Long.parseLong(multaId);
        MultaDB multa = buscarMultaPorId(id);
        if (multa != null && !"PAGA".equals(multa.getStatus())) {
            multa.cancelarMulta(motivo);
        } else {
            throw new IllegalStateException("Multa não encontrada ou já foi paga");
        }
    }

    public BigDecimal calcularTotalMultasPendentes(moradorDB morador) {
        return multas.stream()
                .filter(m -> m.getMorador().equals(morador))
                .filter(m -> "PENDENTE".equals(m.getStatus()))
                .map(MultaDB::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private MultaDB buscarMultaPorId(long id) {
        return multas.stream()
                .filter(m -> Objects.equals(m.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public boolean possuiMultasPendentes(moradorDB moradorInadimplente) {
        return multas.stream()
                .filter(m -> m.getMorador().equals(moradorInadimplente))
                .anyMatch(m -> "PENDENTE".equals(m.getStatus()));
    }
}
