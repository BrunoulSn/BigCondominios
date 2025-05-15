package com.api.BlogAppApi.utils;

import java.time.LocalDateTime;

public class Reserva {
    private String id;
    private Morador morador;
    private AreaComum area;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String status;

    public Reserva(String id, Morador morador, AreaComum area, LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.morador = morador;
        this.area = area;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.status = "AGENDADA";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public AreaComum getArea() {
        return area;
    }

    public void setArea(AreaComum area) {
        this.area = area;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}