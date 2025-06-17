package com.api.BigCondominio.model;
import java.time.LocalDate;

import jakarta.persistence.*;

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

    @Column(name = "data", nullable = false)
    private LocalDate dataReserva;

    @Column(nullable = false, length = 20)
    private String status;

    public ReservaDB() {
        this.status = "AGENDADA";
    }

    public ReservaDB(Long id, moradorDB morador, AreaComumDB area, LocalDate data) {
        this.id = id;
        this.morador = morador;
        this.area = area;
        this.dataReserva = data;
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
    
    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate data) {
        this.dataReserva = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
