package com.api.BlogAppApi.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "area_comum")
public class AreaComumDB  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 70)

    private String nome;
    @Column(nullable = false)

    private boolean disponivel;
    @Column(nullable = false)

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaDB> reservas = new ArrayList<>();

    public AreaComumDB (String nome) {
        this.nome = nome;
        this.disponivel = true;
        this.reservas = new ArrayList<>();
    }
    public AreaComumDB() {
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

    public List<ReservaDB> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservaDB> reservas) {
        this.reservas = reservas;
    }
}
