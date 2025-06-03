
package com.api.BlogAppApi.GestaoCondominio;

import com.api.BlogAppApi.model.moradorDB;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class gestaomoradores {
    private ArrayList<moradorDB> moradores;

    public gestaomoradores() {
        this.moradores = new ArrayList<>();
    }

    public void cadastrarMorador(moradorDB morador) {
        if (morador == null || morador.getNome().isEmpty()) {
            throw new IllegalArgumentException("Dados do morador inválidos");
        }
        moradores.add(morador);
    }

    public void atualizarMorador(moradorDB moradorAtualizado) {
        if (moradorAtualizado == null || moradorAtualizado.getId() == null) {
            throw new IllegalArgumentException("Morador inválido para atualização");
        }

        for (int i = 0; i < moradores.size(); i++) {
            moradorDB existente = moradores.get(i);
            if (existente.getId().equals(moradorAtualizado.getId())) {
                existente.setNome(moradorAtualizado.getNome());
                existente.setApartamento(moradorAtualizado.getApartamento());
                existente.setTelefone(moradorAtualizado.getTelefone());
                existente.setEmail(moradorAtualizado.getEmail());
                return;
            }
        }

        throw new IllegalArgumentException("Morador com ID " + moradorAtualizado.getId() + " não encontrado");
    }

    public ArrayList<moradorDB> listarMoradores() {
        return new ArrayList<>(moradores);
    }
}
