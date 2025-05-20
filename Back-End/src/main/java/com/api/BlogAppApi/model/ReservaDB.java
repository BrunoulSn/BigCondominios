package com.api.BlogAppApi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class reservaDB {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ou IDENTITY se for usar Long
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id")
    private moradorDB morador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "area_id")
    private areaComumDB area;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(nullable = false, length = 20)
    private String status;

    public reservaDB() {
        this.status = "AGENDADA";
    }

    public reservaDB(String id, moradorDB morador, areaComumDB area, LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.morador = morador;
        this.area = area;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.status = "AGENDADA";
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public moradorDB getMorador() {
        return morador;
    }

    public void setMorador(moradorDB morador) {
        this.morador = morador;
    }

    public areaComumDB getArea() {
        return area;
    }

    public void setArea(areaComumDB area) {
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
