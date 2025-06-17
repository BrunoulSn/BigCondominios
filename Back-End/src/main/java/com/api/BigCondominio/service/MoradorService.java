package com.api.BigCondominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.BigCondominio.Utils.CriptografiaUtil;
import com.api.BigCondominio.model.moradorDB;
import com.api.BigCondominio.repository.moradorDBRepository;

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
        return moradorDBRepository.findById(id)
            .map(moradorOriginal -> {
                moradorDB clone = new moradorDB();
                clone.setId(moradorOriginal.getId());
                clone.setNome(moradorOriginal.getNome());
                clone.setApartamento(moradorOriginal.getApartamento());
                clone.setBloco(moradorOriginal.getBloco());
                clone.setTelefone(moradorOriginal.getTelefone());
                clone.setSenha(moradorOriginal.getSenha());

                // descriptografados apenas no clone
                clone.setCPF(CriptografiaUtil.descriptografarAES(moradorOriginal.getCPF()));
                clone.setEmail(CriptografiaUtil.descriptografarAES(moradorOriginal.getEmail()));

                return clone;
            });
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
