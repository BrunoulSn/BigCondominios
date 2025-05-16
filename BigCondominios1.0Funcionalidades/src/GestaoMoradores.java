
import java.util.ArrayList;
import java.util.List;

public class GestaoMoradores {
    private List<Morador> moradores;

    public GestaoMoradores() {
        this.moradores = new ArrayList<>();
    }

    public void cadastrarMorador(Morador morador) {
        if (morador == null || morador.getNome().isEmpty()) {
            throw new IllegalArgumentException("Dados do morador inválidos");
        }
        moradores.add(morador);
    }

    public void atualizarMorador(Morador morador) {
        // Implementação da atualização
    }

    public List<Morador> listarMoradores() {
        return new ArrayList<>(moradores);
    }
}