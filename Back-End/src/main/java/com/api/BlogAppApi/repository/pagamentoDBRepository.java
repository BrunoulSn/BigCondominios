package com.api.BlogAppApi.repository;

import com.api.BlogAppApi.model.pagamentoDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface pagamentoDBRepository extends JpaRepository<pagamentoDB, Long> {

    @Query("SELECT p FROM pagamentoDB p WHERE p.tipo <> 'multa' OR (p.tipo = 'multa' AND p.status = 'pago')")
    List<pagamentoDB> findPagamentosCondOutrosEouMultaPaga();

}