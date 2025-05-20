package com.api.BlogAppApi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "morador")
public class moradorDB {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 10)
    private String apartamento;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "morador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<visitaDB> historicoVisitas = new ArrayList<>();

    @OneToMany(mappedBy = "morador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<multaDB> multas = new ArrayList<>();

    public moradorDB() {}

    public moradorDB(String id, String nome, String apartamento, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.apartamento = apartamento;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<visitaDB> getHistoricoVisitas() {
        return historicoVisitas;
    }

    public void setHistoricoVisitas(List<visitaDB> historicoVisitas) {
        this.historicoVisitas = historicoVisitas;
    }

    public List<multaDB> getMultas() {
        return multas;
    }

    public void setMultas(List<multaDB> multas) {
        this.multas = multas;
    }
}
