
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestaoMultas {
    private List<Multa> multas;

    public GestaoMultas() {
        this.multas = new ArrayList<>();
    }

    public void aplicarMulta(Multa multa) {
        if (multa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da multa deve ser maior que zero");
        }
        multas.add(multa);
    }

    public List<Multa> listarMultasPorMorador(Morador morador) {
        return multas.stream()
                .filter(m -> m.getMorador().equals(morador))
                .collect(Collectors.toList());
    }

    public List<Multa> listarMultasPendentes() {
        return multas.stream()
                .filter(m -> "PENDENTE".equals(m.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Multa> listarMultasVencidas() {
        return multas.stream()
                .filter(m -> "PENDENTE".equals(m.getStatus()))
                .filter(m -> m.getDataVencimento().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public void registrarPagamentoMulta(String multaId, String comprovante) {
        Multa multa = buscarMultaPorId(multaId);
        if (multa != null && "PENDENTE".equals(multa.getStatus())) {
            multa.pagarMulta(comprovante);
        } else {
            throw new IllegalStateException("Multa não encontrada ou não está pendente");
        }
    }

    public void contestarMulta(String multaId, String motivo) {
        Multa multa = buscarMultaPorId(multaId);
        if (multa != null && "PENDENTE".equals(multa.getStatus())) {
            multa.contestarMulta(motivo);
        } else {
            throw new IllegalStateException("Multa não encontrada ou não pode ser contestada");
        }
    }

    public void cancelarMulta(String multaId, String motivo) {
        Multa multa = buscarMultaPorId(multaId);
        if (multa != null && !"PAGA".equals(multa.getStatus())) {
            multa.cancelarMulta(motivo);
        } else {
            throw new IllegalStateException("Multa não encontrada ou já foi paga");
        }
    }

    public BigDecimal calcularTotalMultasPendentes(Morador morador) {
        return multas.stream()
                .filter(m -> m.getMorador().equals(morador))
                .filter(m -> "PENDENTE".equals(m.getStatus()))
                .map(Multa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Multa buscarMultaPorId(String id) {
        return multas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean possuiMultasPendentes(Morador moradorInadimplente) {
        return multas.stream()
                .filter(m -> m.getMorador().equals(moradorInadimplente))
                .anyMatch(m -> "PENDENTE".equals(m.getStatus()));
    }
}