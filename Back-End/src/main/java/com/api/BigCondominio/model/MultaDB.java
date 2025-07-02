package com.api.BigCondominio.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "multa") // você pode ajustar o nome conforme desejar

public class  MultaDB{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id", nullable = false)
    private moradorDB morador;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate dataOcorrencia;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false, length = 20)
    private String status; // PENDENTE, PAGA, CONTESTADA, CANCELADA

    @Column(nullable = false, length = 10)
    private String gravidade; // LEVE, MEDIA, GRAVE

    @Column(length = 100)
    private String motivo;

    @Column(length = 255)
    private String observacoes;

    @Column(length = 100)
    private String registradoPor;

    @Column
    private LocalDate dataPagamento;

    @Column(length = 255)
    private String comprovantePagamento;

    public void pagarMulta(String comprovante) {
        this.status = "PAGA";
        this.dataPagamento = LocalDate.now();
        this.comprovantePagamento = comprovante;
    }

    public void contestarMulta(String motivo) {
        this.status = "CONTESTADA";
        this.observacoes = motivo;
    }

    public void cancelarMulta(String motivo) {
        this.status = "CANCELADA";
        this.observacoes = motivo;
    }

    public moradorDB getMorador() {
        return morador;
    }

    public void setMorador(moradorDB morador) {
        this.morador = morador;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(String registradoPor) {
        this.registradoPor = registradoPor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getComprovantePagamento() {
        return comprovantePagamento;
    }

    public void setComprovantePagamento(String comprovantePagamento) {
        this.comprovantePagamento = comprovantePagamento;
    }
}
