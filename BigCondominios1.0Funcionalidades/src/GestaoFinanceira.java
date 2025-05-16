// services/GestaoFinanceira.java

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GestaoFinanceira {
    private List<Pagamento> pagamentos;

    public GestaoFinanceira() {
        this.pagamentos = new ArrayList<>();
    }

    public void registrarPagamento(Pagamento pagamento) {
        if (pagamento.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero");
        }
        pagamentos.add(pagamento);
    }
}