package com.api.BlogAppApi.service;

import com.api.BlogAppApi.model.moradorDB;
import com.api.BlogAppApi.repository.moradorDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MoradorService {

    @Autowired
    moradorDBRepository moradorDBRepository;

    public List<moradorDB> findAll() {
        return moradorDBRepository.findAll();
    }

    public Optional<moradorDB> findById(long id) {
        return moradorDBRepository.findById(id);
    }

    @Transactional
    public moradorDB save(moradorDB morador) {
        return moradorDBRepository.save(morador);
    }

    @Transactional
    public void delete(moradorDB morador) {
        moradorDBRepository.delete(morador);
    }
}
