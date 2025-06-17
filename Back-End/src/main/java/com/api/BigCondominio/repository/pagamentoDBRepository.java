package com.api.BigCondominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.BigCondominio.model.pagamentoDB;

import java.util.List;

public interface pagamentoDBRepository extends JpaRepository<pagamentoDB, Long> {

    @Query("SELECT p FROM pagamentoDB p WHERE p.tipo <> 'multa' OR (p.tipo = 'multa' AND p.status = 'pago')")
    List<pagamentoDB> findPagamentosCondOutrosEouMultaPaga();

}