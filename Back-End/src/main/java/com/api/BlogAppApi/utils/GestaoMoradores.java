package com.api.BlogAppApi.utils;
import java.util.ArrayList;
import java.util.List;

public class GestaoMoradores {
    private List<Morador> moradores;

    public GestaoMoradores() {
        this.moradores = new ArrayList<>();
    }

    public List<Morador> listarMoradores() {
        return new ArrayList<>(moradores);
    }
}