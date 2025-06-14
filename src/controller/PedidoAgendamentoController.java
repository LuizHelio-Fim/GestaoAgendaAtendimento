package controller;

import model.PedidoAgendamento;
import service.PedidoAgendamentoService;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoAgendamentoController {

    private final PedidoAgendamentoService service;

    public PedidoAgendamentoController() {
        this.service = new PedidoAgendamentoService();
    }

    // Criar um novo pedido de agendamento
    public void solicitarAgendamento(PedidoAgendamento pedido) {
        try {
            service.solicitarAgendamento(pedido);
            JOptionPane.showMessageDialog(null, "Pedido de agendamento enviado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao enviar pedido: " + e.getMessage());
        }
    }

    // Lista todos os pedidos de agendamento pendentes
    public List<PedidoAgendamento> listarPendentes() {
        return service.listarPendentes();
    }

    // Lista pedidos pendentes apenas para o psicólogo logado
    public List<PedidoAgendamento> listarPendentesPorPsicologo(String cpfPsicologo) {
        return listarPendentes().stream()
            .filter(p -> p.getCpfPsicologo().equals(cpfPsicologo))
            .collect(Collectors.toList());
    }

    // Aceita um pedido pendente e gera uma consulta
    public void aceitarPedido(int idPedido) {
        boolean sucesso = service.aceitarPedido(idPedido);
        if (sucesso) {
            JOptionPane.showMessageDialog(null, "Pedido aceito! Consulta agendada.");
        } else {
            JOptionPane.showMessageDialog(null, "Pedido não encontrado.");
        }
    }

    // Rejeita um pedido com justificativa
    public void rejeitarPedido(int idPedido, String motivo) {
        boolean sucesso = service.rejeitarPedido(idPedido, motivo);
        if (sucesso) {
            JOptionPane.showMessageDialog(null, "Pedido rejeitado com justificativa.");
        } else {
            JOptionPane.showMessageDialog(null, "Pedido não encontrado.");
        }
    }

    // Métodos de agendamento
    public List<String> gerarHorariosDisponiveis(String cpfPsicologo, String dataStr) {
        return service.gerarHorariosDisponiveis(cpfPsicologo, dataStr);
    }

    public List<String> gerarDiasDisponiveis(String cpfPsicologo, int diasFuturos) {
        return service.gerarDiasDisponiveis(cpfPsicologo, diasFuturos);
    }
}
