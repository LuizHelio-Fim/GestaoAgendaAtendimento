package controller;

import model.PedidoAgendamento;
import service.PedidoAgendamentoService;

import javax.swing.*;
import java.util.List;

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

    // Lista todos os pedidos de agendamento
    
    public List<PedidoAgendamento> listarPendentes() {
        return service.listarPendentes();
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

    // Rejeita o pedido
    
    public void rejeitarPedido(int idPedido) {
        boolean sucesso = service.rejeitarPedido(idPedido);
        if (sucesso) {
            JOptionPane.showMessageDialog(null, "Pedido rejeitado com sucesso.");
        } else {
            JOptionPane.showMessageDialog(null, "Pedido não encontrado.");
        }
    }
}
