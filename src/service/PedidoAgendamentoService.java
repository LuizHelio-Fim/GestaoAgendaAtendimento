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
        int novoId = gerarIdPedido(pedidos);
        pedido.setId(novoId);
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
        novaConsulta.setId(consultaService.gerarProximoId());
        novaConsulta.setCpfCliente(pedidoAceito.getCpfCliente());
        novaConsulta.setCpfPsicologo(pedidoAceito.getCpfPsicologo());
        novaConsulta.setDataHora(pedidoAceito.getDataHoraSolicitada());
        novaConsulta.setStatus("AGENDADA");

        consultas.add(novaConsulta);
        consultaRepositorio.salvar(consultas);

        return true;
    }

    public boolean rejeitarPedido(int idPedido, String motivo) {
        List<PedidoAgendamento> pedidos = pedidoRepositorio.carregar();

        for (PedidoAgendamento p : pedidos) {
            if (p.getId() == idPedido) {
                p.setStatus("REJEITADO");

                // Anexa a justificativa no campo de mensagem
                String mensagemOriginal = p.getMensagem();
                p.setMensagem(mensagemOriginal + " | Motivo da recusa: " + motivo);

                pedidoRepositorio.salvar(pedidos);
                return true;
            }
        }
        return false;
    }
    
    private int gerarIdPedido(List<PedidoAgendamento> pedidos) {
        int maior = 0;
        for (PedidoAgendamento p : pedidos) {
            if (p.getId() > maior) {
                maior = p.getId();
            }
        }
        return maior + 1;
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
        if (horarioStr == null) return Collections.emptyList();

        // Remove espaços extras
        horarioStr = horarioStr.trim().replaceAll(" +", " ");

        // Verificacao do formato esperado
        if (!horarioStr.matches("(?i)[A-Z][a-z]{2} a [A-Z][a-z]{2} \\d{2}h-\\d{2}h")) {
            System.err.println("Formato de horário inválido: " + horarioStr);
            return Collections.emptyList();
        }

        // Divide em partes: ["Seg", "a", "Sex", "09h-18h"]
        String[] partes = horarioStr.split(" ");
        DayOfWeek diaInicio = traduzirDia(partes[0]);
        DayOfWeek diaFim = traduzirDia(partes[2]);
        String[] faixa = partes[3].split("-");

        int horaInicio = Integer.parseInt(faixa[0].replace("h", ""));
        int horaFim = Integer.parseInt(faixa[1].replace("h", ""));

        // Converte a data
        LocalDate data;
        try {
            data = LocalDate.parse(dataStr);
        } catch (Exception e) {
            return Collections.emptyList();
        }

        // Validacao se o dia está dentro do intervalo informado
        DayOfWeek diaSelecionado = data.getDayOfWeek();
        if (diaSelecionado.getValue() < diaInicio.getValue() || diaSelecionado.getValue() > diaFim.getValue()) {
            return Collections.emptyList();
        }

        // Geração de blocos de horário
        List<String> horariosGerados = new ArrayList<>();
        LocalTime atual = LocalTime.of(horaInicio, 0);
        LocalTime fim = LocalTime.of(horaFim, 0);

        while (!atual.isAfter(fim.minusMinutes(30))) {
            horariosGerados.add(data + " " + atual);
            atual = atual.plusMinutes(35);           // 30min + 5min de intervalo
        }

        // Consulta os pedidos pendentes para o psicólogo
        List<String> ocupados = listarPendentes().stream()
            .filter(p -> p.getCpfPsicologo().equals(cpfPsicologo) && p.getDataHoraSolicitada().startsWith(dataStr))
            .map(PedidoAgendamento::getDataHoraSolicitada)
            .collect(Collectors.toList());

        // Consulta os atendimentos já agendados
        ocupados.addAll(consultaService.listarPorPsicologo(cpfPsicologo).stream()
            .filter(c -> c.getDataHora().startsWith(dataStr))
            .map(Consulta::getDataHora)
            .collect(Collectors.toList()));

        // Retorna apenas horários que não estão ocupados
        return horariosGerados.stream()
            .filter(horario -> !ocupados.contains(horario))
            .collect(Collectors.toList());
    }

    
    public List<String> gerarDiasDisponiveis(String cpfPsicologo, int diasFuturos) {
        Psicologo psicologo = psicologoService.buscarPorCpf(cpfPsicologo);
        if (psicologo == null) return Collections.emptyList();

        String horarioStr = psicologo.getPerfil().getHorarioAtendimento();
        if(horarioStr == null) return Collections.emptyList();
        horarioStr = horarioStr.trim().replaceAll(" +", " ");

        if (!horarioStr.matches("(?i)[A-Z][a-z]{2} a [A-Z][a-z]{2} \\d{2}h-\\d{2}h")) {
        	System.err.println("Formato de horário invalido: " + horarioStr);
        	return Collections.emptyList();
        }

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