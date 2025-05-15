package com.api.BlogAppApi.utils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Iniciando os serviços
        GestaoMoradores gestaoMoradores = new GestaoMoradores();
        GestaoReservas gestaoReservas = new GestaoReservas();
        GestaoMultas gestaoMultas = new GestaoMultas();


        try {
            System.out.println("=== Teste de Sistema de Reservas e Inadimplência ===\n");

            // Cadastrando moradores
            Morador morador1 = new Morador("1", "João Silva", "101", "11999999999", "joao@email.com");
            Morador morador2 = new Morador("2", "Maria Santos", "102", "11988888888", "maria@email.com");
            Morador moradorInadimplente = new Morador("3", "Pedro Oliveira", "103", "11977777777", "pedro@email.com");

            gestaoMoradores.cadastrarMorador(morador1);
            gestaoMoradores.cadastrarMorador(morador2);
            gestaoMoradores.cadastrarMorador(moradorInadimplente);

            System.out.println("Moradores cadastrados:");
            gestaoMoradores.listarMoradores().forEach(m -> 
                System.out.println("- " + m.getNome() + " (Apto " + m.getApartamento() + ")"));
            System.out.println();

            // Criando áreas comuns
            AreaComum salaoFestas = new AreaComum("Salão de Festas");
            AreaComum churrasqueira = new AreaComum("Churrasqueira");
            AreaComum quadra = new AreaComum("Quadra Poliesportiva");

            // Registrando as áreas
            gestaoReservas.adicionarAreaComum(salaoFestas);
            gestaoReservas.adicionarAreaComum(churrasqueira);
            gestaoReservas.adicionarAreaComum(quadra);

            // Teste 1: Reserva bem-sucedida
            System.out.println("=== Teste 1: Reserva Normal ===");
            LocalDateTime dataReserva1 = LocalDateTime.now().plusDays(1);
            Reserva reserva1 = new Reserva("1", morador1, salaoFestas, 
                dataReserva1, dataReserva1.plusHours(4));

            try {
                gestaoReservas.fazerReserva(reserva1);
                System.out.println("Reserva realizada com sucesso para " + morador1.getNome());
                System.out.println("Local: " + salaoFestas.getNome());
                System.out.println("Data: " + dataReserva1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                System.out.println();
            } catch (Exception e) {
                System.out.println("Erro ao fazer reserva: " + e.getMessage());
            }

            // Teste 2: Tentativa de reserva no mesmo horário
            System.out.println("=== Teste 2: Tentativa de Reserva Conflitante ===");
            Reserva reservaConflitante = new Reserva("2", morador2, salaoFestas, 
                dataReserva1, dataReserva1.plusHours(4));

            try {
                gestaoReservas.fazerReserva(reservaConflitante);
            } catch (Exception e) {
                System.out.println("Erro esperado: " + e.getMessage());
                System.out.println();
            }

            // Teste 3: Morador inadimplente tentando fazer reserva
            System.out.println("=== Teste 3: Reserva com Morador Inadimplente ===");
            
            // Registrando multa para o morador inadimplente
            Multa multa = new Multa("1", moradorInadimplente, "Taxa de condomínio atrasada", 
                new BigDecimal("500.00"), "GRAVE");
            gestaoMultas.aplicarMulta(multa);

            LocalDateTime dataReserva2 = LocalDateTime.now().plusDays(2);
            Reserva reservaInadimplente = new Reserva("3", moradorInadimplente, churrasqueira, 
                dataReserva2, dataReserva2.plusHours(4));

            try {
                if (gestaoMultas.possuiMultasPendentes(moradorInadimplente)) {
                    throw new IllegalStateException("Morador possui débitos pendentes e não pode fazer reservas");
                }
                gestaoReservas.fazerReserva(reservaInadimplente);
            } catch (Exception e) {
                System.out.println("Erro esperado: " + e.getMessage());
                System.out.println();
            }

            // Teste 4: Verificação de disponibilidade
            System.out.println("=== Teste 4: Verificação de Disponibilidade ===");
            LocalDateTime dataConsulta = LocalDateTime.now().plusDays(1);
            List<AreaComum> areasDisponiveis = gestaoReservas.listarAreasDisponiveisParaData(dataConsulta);
            
            System.out.println("Áreas disponíveis para " + 
                dataConsulta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ":");
            areasDisponiveis.forEach(area -> System.out.println("- " + area.getNome()));
            System.out.println();

            // Teste 5: Cancelamento de reserva
            System.out.println("=== Teste 5: Cancelamento de Reserva ===");
            try {
                gestaoReservas.cancelarReserva(reserva1.getId());
                System.out.println("Reserva cancelada com sucesso!");
                System.out.println();
            } catch (Exception e) {
                System.out.println("Erro ao cancelar reserva: " + e.getMessage());
            }

            // Teste 6: Relatório de reservas futuras
            System.out.println("=== Teste 6: Relatório de Reservas Futuras ===");
            List<Reserva> reservasFuturas = gestaoReservas.listarReservasFuturas();
            if (reservasFuturas.isEmpty()) {
                System.out.println("Não há reservas futuras");
            } else {
                System.out.println("Reservas futuras:");
                reservasFuturas.forEach(r -> {
                    System.out.println("- Local: " + r.getArea().getNome());
                    System.out.println("  Morador: " + r.getMorador().getNome());
                    System.out.println("  Data: " + r.getDataHoraInicio()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                });
            }

            // Resumo final
            System.out.println("\n=== Resumo do Sistema ===");
            System.out.println("Total de moradores: " + gestaoMoradores.listarMoradores().size());
            System.out.println("Total de áreas comuns: " + gestaoReservas.listarTodasAreas().size());
            System.out.println("Moradores inadimplentes: " + 
                gestaoMoradores.listarMoradores().stream()
                    .filter(gestaoMultas::possuiMultasPendentes)
                    .count());

        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}