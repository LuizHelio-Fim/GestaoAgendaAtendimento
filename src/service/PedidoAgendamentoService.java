package service;

import model.Consulta;
import model.PedidoAgendamento;
import model.Psicologo;
import repository.ConsultaRepositorio;
import repository.PedidoAgendamentoRepositorio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoAgendamentoService {

    private final PedidoAgendamentoRepositorio pedidoRepositorio;
    private final ConsultaRepositorio consultaRepositorio;
    private final PsicologoService psicologoService;
    private final ConsultaService consultaService;

    public PedidoAgendamentoService() {
        this.pedidoRepositorio = new PedidoAgendamentoRepositorio();
        this.consultaRepositorio = new ConsultaRepositorio();
		this.psicologoService = new PsicologoService();
		this.consultaService = new ConsultaService();
    }

    public void solicitarAgendamento(PedidoAgendamento pedido) {
        List<PedidoAgendamento> pedidos = pedidoRepositorio.carregar();
        pedidos.add(pedido);
        pedidoRepositorio.salvar(pedidos);
    }

    public List<PedidoAgendamento> listarPendentes() {
        List<PedidoAgendamento> resultado = new ArrayList<>();
        for (PedidoAgendamento p : pedidoRepositorio.carregar()) {
            if (p.getStatus().equalsIgnoreCase("PENDENTE")) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public boolean aceitarPedido(int idPedido) {
        List<PedidoAgendamento> pedidos = pedidoRepositorio.carregar();
        PedidoAgendamento pedidoAceito = null;

        for (PedidoAgendamento p : pedidos) {
            if (p.getId() == idPedido) {
                pedidoAceito = p;
                p.setStatus("ACEITO");
                break;
            }
        }

        if (pedidoAceito == null) return false;

        // Salvar os pedidos com status atualizado
        
        pedidoRepositorio.salvar(pedidos);

        // Criar a consulta
        
        List<Consulta> consultas = consultaRepositorio.carregar();
        Consulta novaConsulta = new Consulta();
        novaConsulta.setId(gerarIdConsulta(consultas));
        novaConsulta.setCpfCliente(pedidoAceito.getCpfCliente());
        novaConsulta.setCpfPsicologo(pedidoAceito.getCpfPsicologo());
        novaConsulta.setDataHora(pedidoAceito.getDataHoraSolicitada());
        novaConsulta.setStatus("AGENDADA");

        consultas.add(novaConsulta);
        consultaRepositorio.salvar(consultas);

        return true;
    }

    public boolean rejeitarPedido(int idPedido) {
        List<PedidoAgendamento> pedidos = pedidoRepositorio.carregar();
        for (PedidoAgendamento p : pedidos) {
            if (p.getId() == idPedido) {
                p.setStatus("REJEITADO");
                pedidoRepositorio.salvar(pedidos);
                return true;
            }
        }
        return false;
    }

    private int gerarIdConsulta(List<Consulta> consultas) {
        int maior = 0;
        for (Consulta c : consultas) {
            if (c.getId() > maior) {
                maior = c.getId();
            }
        }
        return maior + 1;
    }
    
    public List<String> gerarHorariosDisponiveis(String cpfPsicologo, String dataStr) {
        Psicologo psicologo = psicologoService.buscarPorCpf(cpfPsicologo);
        if (psicologo == null) return Collections.emptyList();

        String horarioStr = psicologo.getPerfil().getHorarioAtendimento();
        if (!horarioStr.matches("(?i)[A-Z][a-z]{2} a [A-Z][a-z]{2} \\d{2}h-\\d{2}h")) return Collections.emptyList();

        String[] partes = horarioStr.split(" ");
        DayOfWeek diaInicio = traduzirDia(partes[0]);
        DayOfWeek diaFim = traduzirDia(partes[2]);
        String[] faixa = partes[3].split("-");

        int horaInicio = Integer.parseInt(faixa[0].replace("h", ""));
        int horaFim = Integer.parseInt(faixa[1].replace("h", ""));

        // Converte a data para corresponder ao metodo
        LocalDate data;
        try {
            data = LocalDate.parse(dataStr);
        } catch (Exception e) {
            return Collections.emptyList();
        }

        DayOfWeek diaSelecionado = data.getDayOfWeek();
        if (diaSelecionado.getValue() < diaInicio.getValue() || diaSelecionado.getValue() > diaFim.getValue()) {
            return Collections.emptyList();			 // Dia fora do intervalo permitido
        }

        // Geração dos blocos
        List<String> horariosGerados = new ArrayList<>();
        LocalTime atual = LocalTime.of(horaInicio, 0);
        LocalTime fim = LocalTime.of(horaFim, 0);

        while (atual.plusMinutes(30).isBefore(fim.plusMinutes(1))) {
            horariosGerados.add(data + " " + atual);
            atual = atual.plusMinutes(35);			 // 30min + 5min de intervalo
        }

        // Horários ocupados por pedidos pendentes
        List<String> ocupados = listarPendentes().stream()
            .filter(p -> p.getCpfPsicologo().equals(cpfPsicologo) && p.getDataHoraSolicitada().startsWith(dataStr))
            .map(PedidoAgendamento::getDataHoraSolicitada)
            .collect(Collectors.toList());

        // Horários ocupados por consultas agendadas
        ocupados.addAll(consultaService.listarPorPsicologo(cpfPsicologo).stream()
            .filter(c -> c.getDataHora().startsWith(dataStr))
            .map(Consulta::getDataHora)
            .collect(Collectors.toList()));

        // Retorna somente os horários livres
        return horariosGerados.stream()
            .filter(horario -> !ocupados.contains(horario))
            .collect(Collectors.toList());
    }
    
    public List<String> gerarDiasDisponiveis(String cpfPsicologo, int diasFuturos) {
        Psicologo psicologo = psicologoService.buscarPorCpf(cpfPsicologo);
        if (psicologo == null) return Collections.emptyList();

        String horarioStr = psicologo.getPerfil().getHorarioAtendimento();
        if (!horarioStr.matches("(?i)[A-Z][a-z]{2} a [A-Z][a-z]{2} \\d{2}h-\\d{2}h")) return Collections.emptyList();

        String[] partes = horarioStr.split(" ");
        DayOfWeek diaInicio = traduzirDia(partes[0]);
        DayOfWeek diaFim = traduzirDia(partes[2]);

        List<String> diasDisponiveis = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < diasFuturos; i++) {
            LocalDate data = hoje.plusDays(i);
            DayOfWeek dia = data.getDayOfWeek();
            if (dia.getValue() >= diaInicio.getValue() && dia.getValue() <= diaFim.getValue()) {
                diasDisponiveis.add(data.toString()); // formato YYYY-MM-DD
            }
        }

        return diasDisponiveis;
    }

    private DayOfWeek traduzirDia(String diaAbrev) {
        switch (diaAbrev.toLowerCase()) {
            case "dom": return DayOfWeek.SUNDAY;
            case "seg": return DayOfWeek.MONDAY;
            case "ter": return DayOfWeek.TUESDAY;
            case "qua": return DayOfWeek.WEDNESDAY;
            case "qui": return DayOfWeek.THURSDAY;
            case "sex": return DayOfWeek.FRIDAY;
            case "sab": return DayOfWeek.SATURDAY;
            default: throw new IllegalArgumentException("Dia inválido: " + diaAbrev);
        }
    }
}