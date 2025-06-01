package com.api.BlogAppApi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "morador")
public class moradorDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String CPF;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, length = 10)
    private String apartamento;

    @Column(nullable = false, length = 100)
    private String bloco;

    @Column(nullable = false, length = 20)
    private String telefone;  

    @OneToMany(mappedBy = "moradorResponsavel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<visitaDB> historicoVisitas = new ArrayList<>();

    @OneToMany(mappedBy = "morador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultaDB> multas = new ArrayList<>();

    public moradorDB() {}

    public moradorDB(Long id, String nome, String CPF, String email, String senha, String apartamento, String bloco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.email = email;
        this.senha = senha;
        this.apartamento = apartamento;
        this.bloco = bloco;
        this.telefone = telefone;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCPF(){
        return CPF;
    }

    public void setCPF(String CPF){
        this.CPF = CPF;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getBloco(){
        return bloco;
    }
    
    public void setBloco(String bloco){
        this.bloco = bloco;
    }

    public List<visitaDB> getHistoricoVisitas() {
        return historicoVisitas;
    }

    public void setHistoricoVisitas(List<visitaDB> historicoVisitas) {
        this.historicoVisitas = historicoVisitas;
    }

    public List<MultaDB> getMultas() {
        return multas;
    }

    public void setMultas(List<MultaDB> multas) {
        this.multas = multas;
    }
}
