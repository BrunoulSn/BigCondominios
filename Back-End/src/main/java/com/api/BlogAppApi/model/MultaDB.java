package com.api.BlogAppApi.model;

import com.api.BlogAppApi.utils.Morador;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "multa") // você pode ajustar o nome conforme desejar

public class multaDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "morador_id")
    private Morador morador;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataOcorrencia;

    @Column(nullable = false)
    private LocalDateTime dataVencimento;

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
    private LocalDateTime dataPagamento;

    @Column(length = 255)
    private String comprovantePagamento;

    public void pagarMulta(String comprovante) {
        this.status = "PAGA";
        this.dataPagamento = LocalDateTime.now();
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

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public String getDescricao() {
        return descricao;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
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

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
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

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getComprovantePagamento() {
        return comprovantePagamento;
    }

    public void setComprovantePagamento(String comprovantePagamento) {
        this.comprovantePagamento = comprovantePagamento;
    }
}
