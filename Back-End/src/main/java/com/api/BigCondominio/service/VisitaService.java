package com.api.BigCondominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.BigCondominio.model.visitaDB;
import com.api.BigCondominio.repository.visitaDBRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {

    @Autowired
    visitaDBRepository visitaDBRepository;

    public List<visitaDB> findAll() {
        return visitaDBRepository.findAll();
    }

    public Optional<visitaDB> findById(long id) {
        return visitaDBRepository.findById(id);
    }

    @Transactional
    public visitaDB save(visitaDB visita) {
        return visitaDBRepository.save(visita);
    }

    @Transactional
    public void delete(visitaDB visita) {
        visitaDBRepository.delete(visita);
    }
}
