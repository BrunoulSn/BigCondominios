package com.api.BlogAppApi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visita")
public class visitaDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeVisitante;

    @Column(nullable = false, length = 50)
    private String documento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id")
    private moradorDB moradorResponsavel;

    @Column
    private LocalDateTime dataHoraEntrada;

    @Column
    private LocalDateTime dataHoraSaida;

    @Column(nullable = false, length = 20)
    private String status;

    public visitaDB() {
        this.status = "AGENDADA";
    }

    public visitaDB(Long id, String nomeVisitante, String documento, moradorDB moradorResponsavel) {
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

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public moradorDB getMoradorResponsavel() {
        return moradorResponsavel;
    }

    public void setMoradorResponsavel(moradorDB moradorResponsavel) {
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
