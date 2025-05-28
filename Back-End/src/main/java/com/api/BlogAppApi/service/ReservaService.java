package com.api.BlogAppApi.service;

import com.api.BlogAppApi.model.ReservaDB;
import com.api.BlogAppApi.repository.ReservaDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    ReservaDBRepository reservaDBRepository;

    public List<ReservaDB> findAll() {
        return reservaDBRepository.findAll();
    }

    public Optional<ReservaDB> findById(long id) {
        return reservaDBRepository.findById(id);
    }

    @Transactional
    public ReservaDB save(ReservaDB reserva) {
        return reservaDBRepository.save(reserva);
    }

    @Transactional
    public void delete(ReservaDB reserva) {
        reservaDBRepository.delete(reserva);
    }
}
