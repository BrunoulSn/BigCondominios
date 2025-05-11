
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pagamento {
    private String id;
    private Morador morador;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
    private String tipo;
    private String status;
    private String formaPagamento;
    
    public Pagamento(String id, Morador morador, BigDecimal valor, String tipo) {
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