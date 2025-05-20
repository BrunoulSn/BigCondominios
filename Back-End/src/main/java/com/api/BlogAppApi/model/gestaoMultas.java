package com.api.BlogAppApi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class gestaoMultas {
    private List<multaDB> multas;

    public gestaoMultas() {
        this.multas = new ArrayList<>();
    }

    public void aplicarMulta(multaDB multa) {
        if (multa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da multa deve ser maior que zero");
        }
        multas.add(multa);
    }

    public List<multaDB> listarMultasPorMorador(moradorDB morador) {
        return multas.stream()
                .filter(m -> m.getMorador().equals(morador))
                .collect(Collectors.toList());
    }

    public List<multaDB> listarMultasPendentes() {
        return multas.stream()
                .filter(m -> "PENDENTE".equals(m.getStatus()))
                .collect(Collectors.toList());
    }

    public List<multaDB> listarMultasVencidas() {
        return multas.stream()
                .filter(m -> "PENDENTE".equals(m.getStatus()))
                .filter(m -> m.getDataVencimento().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void registrarPagamentoMulta(String multaId, String comprovante) {
        multaDB multa = buscarMultaPorId(multaId);
        if (multa != null && "PENDENTE".equals(multa.getStatus())) {
            multa.pagarMulta(comprovante);
        } else {
            throw new IllegalStateException("Multa não encontrada ou não está pendente");
        }
    }

    public void contestarMulta(String multaId, String motivo) {
        multaDB multa = buscarMultaPorId(multaId);
        if (multa != null && "PENDENTE".equals(multa.getStatus())) {
            multa.contestarMulta(motivo);
        } else {
            throw new IllegalStateException("Multa não encontrada ou não pode ser contestada");
        }
    }

    public void cancelarMulta(String multaId, String motivo) {
        multaDB multa = buscarMultaPorId(multaId);
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
                .map(multaDB::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private multaDB buscarMultaPorId(String id) {
        return multas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean possuiMultasPendentes(moradorDB moradorInadimplente) {
        return multas.stream()
                .filter(m -> m.getMorador().equals(moradorInadimplente))
                .anyMatch(m -> "PENDENTE".equals(m.getStatus()));
    }
}