package com.api.BlogAppApi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
public class ReservaDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id")
    private moradorDB morador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "area_id")
    private AreaComumDB area;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(nullable = false, length = 20)
    private String status;

    public ReservaDB() {
        this.status = "AGENDADA";
    }

    public ReservaDB(Long id, moradorDB morador, AreaComumDB area, LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.morador = morador;
        this.area = area;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.status = "AGENDADA";
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public moradorDB getMorador() {
        return morador;
    }

    public void setMorador(moradorDB morador) {
        this.morador = morador;
    }

    public AreaComumDB getArea() {
        return area;
    }

    public void setArea(AreaComumDB area) {
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
