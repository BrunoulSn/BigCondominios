package com.api.BlogAppApi.utils;

import java.util.ArrayList;
import java.util.List;

public class AreaComum {
    private String nome;
    private boolean disponivel;
    private List<Reserva> reservas;

    public AreaComum(String nome) {
        this.nome = nome;
        this.disponivel = true;
        this.reservas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;   
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

}