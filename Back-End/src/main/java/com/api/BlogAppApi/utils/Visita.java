package com.api.BlogAppApi.utils;

import java.time.LocalDateTime;

public class Visita {
    private String id;
    private String nomeVisitante;
    private String documento;
    private Morador moradorResponsavel;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private String status;

    public Visita(String id, String nomeVisitante, String documento, Morador moradorResponsavel) {
        this.id = id;
        this.nomeVisitante = nomeVisitante;
        this.documento = documento;
        this.moradorResponsavel = moradorResponsavel;
        this.status = "AGENDADA";
    }

    public void registrarEntrada() {
        this.dataHoraEntrada = LocalDateTime.now();
        this.status = "EM_ANDAMENTO";
    }

    public void registrarSaida() {
        this.dataHoraSaida = LocalDateTime.now();
        this.status = "FINALIZADA";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public void setNomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Morador getMoradorResponsavel() {
        return moradorResponsavel;
    }

    public void setMoradorResponsavel(Morador moradorResponsavel) {
        this.moradorResponsavel = moradorResponsavel;
    }

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}