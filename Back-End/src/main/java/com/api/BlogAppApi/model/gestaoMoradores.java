package com.api.BlogAppApi.model;
import com.api.BlogAppApi.utils.Morador;

import java.util.ArrayList;
import java.util.List;

public class gestaoMoradores {
    private List<moradorDB> moradores;

    public gestaoMoradores() {
        this.moradores = new ArrayList<>();
    }

    public void cadastrarMorador(moradorDB morador) {
        if (morador == null || morador.getNome().isEmpty()) {
            throw new IllegalArgumentException("Dados do morador inválidos");
        }
        moradores.add(morador);
    }

    public void atualizarMorador(Morador morador) {
        // Implementação da atualização
    }

    public ArrayList<moradorDB> listarMoradores() {
        return new ArrayList<moradorDB>(moradores);
    }
}