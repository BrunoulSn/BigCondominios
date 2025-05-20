package com.api.BlogAppApi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class gestaoFinanceira {
    private List<pagamentoDB> pagamentos;

    public gestaoFinanceira() {
        this.pagamentos = new ArrayList<>();
    }

    public void registrarPagamento(pagamentoDB pagamento) {
        if (pagamento.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero");
        }
        pagamentos.add(pagamento);
    }
}