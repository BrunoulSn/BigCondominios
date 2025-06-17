package com.api.BigCondominio.DTOs;

import java.util.List;

import com.api.BigCondominio.model.MultaDB;
import com.api.BigCondominio.model.pagamentoDB;

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
