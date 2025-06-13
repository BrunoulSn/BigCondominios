package com.api.BlogAppApi.service;

import com.api.BlogAppApi.DTOs.PagamentoMultaResponseDTO;
import com.api.BlogAppApi.DTOs.PagamentoMultaResponseDTO;
import com.api.BlogAppApi.repository.MultaDBRepository;
import com.api.BlogAppApi.model.MultaDB;
import com.api.BlogAppApi.model.moradorDB;
import com.api.BlogAppApi.model.pagamentoDB;
import com.api.BlogAppApi.repository.pagamentoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    pagamentoDBRepository pagamentoDBRepository;

    public List<pagamentoDB> findAll() {
        return pagamentoDBRepository.findAll();
    }

    public Optional<pagamentoDB> findById(long id) {
        return pagamentoDBRepository.findById(id);
    }

    @Transactional
    public pagamentoDB save(pagamentoDB pagamento) {
        return pagamentoDBRepository.save(pagamento);
    }

    @Transactional
    public void delete(pagamentoDB pagamento) {
        pagamentoDBRepository.delete(pagamento);
    }

    @Autowired
    private com.api.BlogAppApi.repository.moradorDBRepository moradorDBRepository;

    public Optional<moradorDB> buscarMoradorPorId(Long id) {
        return moradorDBRepository.findById(id);
    }

    @Autowired
    private MultaDBRepository multaDBRepository;

    public PagamentoMultaResponseDTO buscarPagamentosEAsMultasPagas() {
        List<pagamentoDB> pagamentos = pagamentoDBRepository.findAll();
        List<MultaDB> multasPagas = multaDBRepository.findByStatus("paga");

        return new PagamentoMultaResponseDTO(pagamentos, multasPagas);
    }

}
