

import java.util.ArrayList;
import java.util.List;

public class Morador {
    private String id;
    private String nome;
    private String apartamento;
    private String telefone;
    private String email;
    private List<Visita> historicoVisitas;
    private List<Multa> multas;

    public Morador(String id, String nome, String apartamento, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.apartamento = apartamento;
        this.telefone = telefone;
        this.email = email;
        this.historicoVisitas = new ArrayList<>();
        this.multas = new ArrayList<>();
    }

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

    public List<Visita> getHistoricoVisitas() {
        return historicoVisitas;
    }

    public void setHistoricoVisitas(List<Visita> historicoVisitas) {
        this.historicoVisitas = historicoVisitas;
    }

    public List<Multa> getMultas() {
        return multas;
    }

    public void setMultas(List<Multa> multas) {
        this.multas = multas;
    }
}