import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestaoReservas {
    private List<Reserva> reservas = new ArrayList<>();
    private List<AreaComum> areasComuns = new ArrayList<>();

    public void adicionarAreaComum(AreaComum area) {
        areasComuns.add(area);
    }

    public List<AreaComum> listarAreasDisponiveisParaData(LocalDateTime data) {
        return areasComuns.stream()
                .filter(area -> isAreaDisponivel(area, data))
                .collect(Collectors.toList());
    }

    public void cancelarReserva(String reservaId) {
        Reserva reserva = reservas.stream()
                .filter(r -> r.getId().equals(reservaId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        if ("CONCLUIDA".equals(reserva.getStatus())) {
            throw new IllegalStateException("Não é possível cancelar uma reserva já concluída");
        }

        reserva.setStatus("CANCELADA");
    }

    public List<Reserva> listarReservasFuturas() {
        LocalDateTime agora = LocalDateTime.now();
        return reservas.stream()
                .filter(r -> r.getDataHoraInicio().isAfter(agora))
                .filter(r -> !"CANCELADA".equals(r.getStatus()))
                .collect(Collectors.toList());
    }

    public List<AreaComum> listarTodasAreas() {
        return new ArrayList<>(areasComuns);
    }

    private boolean isAreaDisponivel(AreaComum area, LocalDateTime data) {
        return reservas.stream()
                .filter(r -> r.getArea().equals(area))
                .filter(r -> !"CANCELADA".equals(r.getStatus()))
                .noneMatch(r -> verificaConflitoDeDatas(r, data));
    }

    private boolean verificaConflitoDeDatas(Reserva reserva, LocalDateTime data) {
        LocalDateTime inicio = data;
        LocalDateTime fim = data.plusHours(4); // Assumindo reserva padrão de 4 horas
        return !(fim.isBefore(reserva.getDataHoraInicio()) ||
                inicio.isAfter(reserva.getDataHoraFim()));
    }

    public void fazerReserva(Reserva reserva1) {
        reservas.add(reserva1);
    }
}