// models/Multa.java

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Multa {
    private String id;
    private Morador morador;
    private String descricao;
    private BigDecimal valor;
    private LocalDateTime dataOcorrencia;
    private LocalDateTime dataVencimento;
    private String status; // PENDENTE, PAGA, CONTESTADA, CANCELADA
    private String gravidade; // LEVE, MEDIA, GRAVE
    private String motivo;
    private String observacoes;
    private String registradoPor;
    private LocalDateTime dataPagamento;
    private String comprovantePagamento;

    public Multa(String id, Morador morador, String descricao, BigDecimal valor, String gravidade) {
        this.id = id;
        this.morador = morador;
        this.descricao = descricao;
        this.valor = valor;
        this.gravidade = gravidade;
        this.dataOcorrencia = LocalDateTime.now();
        this.dataVencimento = LocalDateTime.now().plusDays(30);
        this.status = "PENDENTE";
    }

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

    public String getDescricao() {
        return descricao;
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