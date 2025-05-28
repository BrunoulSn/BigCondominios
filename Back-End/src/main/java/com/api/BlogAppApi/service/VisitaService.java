package com.api.BlogAppApi.service;

import com.api.BlogAppApi.model.visitaDB;
import com.api.BlogAppApi.repository.visitaDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
