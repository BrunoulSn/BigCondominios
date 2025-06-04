package com.api.BlogAppApi.service;

import com.api.BlogAppApi.Utils.CriptografiaUtil;
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
        List<moradorDB> moradores = moradorDBRepository.findAll();
        moradores.forEach(morador -> {
            morador.setCPF(CriptografiaUtil.descriptografarAES(morador.getCPF()));
            morador.setEmail(CriptografiaUtil.descriptografarAES(morador.getEmail()));
        });
        return moradores;
    }

    public Optional<moradorDB> findById(long id) {
        Optional<moradorDB> optionalMorador = moradorDBRepository.findById(id);
        optionalMorador.ifPresent(morador -> {
            morador.setCPF(CriptografiaUtil.descriptografarAES(morador.getCPF()));
            morador.setEmail(CriptografiaUtil.descriptografarAES(morador.getEmail()));
        });
        return optionalMorador;
    }

    @Transactional
    public moradorDB save(moradorDB morador) {
        morador.setSenha(CriptografiaUtil.hashSenha(morador.getSenha()));
        morador.setCPF(CriptografiaUtil.criptografarAES(morador.getCPF()));
        morador.setEmail(CriptografiaUtil.criptografarAES(morador.getEmail()));
        return moradorDBRepository.save(morador);
    }

    @Transactional
    public void delete(moradorDB morador) {
        moradorDBRepository.delete(morador);
    }
}
