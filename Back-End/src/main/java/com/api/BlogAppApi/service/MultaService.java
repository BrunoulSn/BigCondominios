package com.api.BlogAppApi.service;

import com.api.BlogAppApi.model.MultaDB;
import com.api.BlogAppApi.repository.MultaDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MultaService {

    @Autowired
    MultaDBRepository multaDBRepository;

    public List<MultaDB> findAll() {
        return multaDBRepository.findAll();
    }

    public Optional<MultaDB> findById(long id) {
        return multaDBRepository.findById(id);
    }

    @Transactional
    public MultaDB save(MultaDB multa) {
        return multaDBRepository.save(multa);
    }

    @Transactional
    public void delete(MultaDB multa) {
        multaDBRepository.delete(multa);
    }
}
