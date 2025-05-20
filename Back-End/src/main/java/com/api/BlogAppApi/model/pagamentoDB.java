package com.api.BlogAppApi.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class pagamentoDB {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "morador_id", nullable = false)
    private moradorDB morador;

    @Column(nullable = false)
    private BigDecimal valor;

    private LocalDateTime dataPagamento;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String status;

    private String formaPagamento;

    public pagamentoDB() {
        // Construtor padrão exigido pelo JPA
    }

    public pagamentoDB(String id, moradorDB morador, BigDecimal valor, String tipo) {
        this.id = id;
        this.morador = morador;
        this.valor = valor;
        this.tipo = tipo;
        this.status = "PENDENTE";
    }

    public void registrarPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
        this.dataPagamento = LocalDateTime.now();
        this.status = "PAGO";
    }

    // Getters e Setters...

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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
