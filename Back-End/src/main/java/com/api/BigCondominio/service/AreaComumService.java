package com.api.BigCondominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.BigCondominio.model.AreaComumDB;
import com.api.BigCondominio.repository.AreaComumDBRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AreaComumService {

    @Autowired
    AreaComumDBRepository areaComumDBRepository;

    public List<AreaComumDB> findAll() {
        return areaComumDBRepository.findAll();
    }

    public Optional<AreaComumDB> findById(long id) {
        return areaComumDBRepository.findById(id);
    }

    @Transactional
    public AreaComumDB save(AreaComumDB area) {
        return areaComumDBRepository.save(area);
    }

    @Transactional
    public void delete(AreaComumDB area) {
        areaComumDBRepository.delete(area);
    }
}
