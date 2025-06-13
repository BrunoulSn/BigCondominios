package com.api.BlogAppApi.DTOs;

import com.api.BlogAppApi.model.pagamentoDB;
import com.api.BlogAppApi.model.MultaDB;

import java.util.List;

public class PagamentoMultaResponseDTO {
    private List<pagamentoDB> pagamentos;
    private List<MultaDB> multasPagas;

    public PagamentoMultaResponseDTO(List<pagamentoDB> pagamentos, List<MultaDB> multasPagas) {
        this.pagamentos = pagamentos;
        this.multasPagas = multasPagas;
    }

    public List<pagamentoDB> getPagamentos() {
        return pagamentos;
    }

    public List<MultaDB> getMultasPagas() {
        return multasPagas;
    }
}
