package view;

import controller.ConsultaController;
import controller.PedidoAgendamentoController;
import model.Consulta;
import model.PedidoAgendamento;
import model.Psicologo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPsicologoView extends JFrame {

    private final Psicologo psicologo;
    private final PedidoAgendamentoController pedidoController;
    private final ConsultaController consultaController;

    public MenuPsicologoView(Psicologo psicologo) {
        this.psicologo = psicologo;
        this.pedidoController = new PedidoAgendamentoController();
        this.consultaController = new ConsultaController();

        setTitle("Menu do Psicólogo");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 5, 5));

        JButton btnPedidos = new JButton("Ver Pedidos de Agendamento");
        JButton btnConsultas = new JButton("Ver Agenda (Consultas)");
        JButton btnSair = new JButton("Sair");

        add(new JLabel("Bem-vindo, Dr(a). " + psicologo.getNome(), SwingConstants.CENTER));
        add(btnPedidos);
        add(btnConsultas);
        add(btnSair);

        btnPedidos.addActionListener(e -> exibirPedidos());
        btnConsultas.addActionListener(e -> exibirConsultas());
        btnSair.addActionListener(e -> {
            dispose();
            new LoginView();
        });

        setVisible(true);
    }

    private void exibirPedidos() {
        List<PedidoAgendamento> pedidos = pedidoController.listarPendentes();
        StringBuilder sb = new StringBuilder();

        for (PedidoAgendamento p : pedidos) {
            if (p.getCpfPsicologo().equals(psicologo.getCpf())) {
                sb.append("ID: ").append(p.getId())
                  .append(" | Cliente: ").append(p.getCpfCliente())
                  .append(" | DataHora: ").append(p.getDataHoraSolicitada())
                  .append(" | Mensagem: ").append(p.getMensagem()).append("\n");
            }
        }

        if (sb.length() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum pedido pendente.");
            return;
        }

        JOptionPane.showMessageDialog(this, sb.toString());

        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do pedido para aceitar ou 'rID' para rejeitar (ex: r3):");
        if (idStr == null) return;

        try {
            if (idStr.startsWith("r")) {
                int id = Integer.parseInt(idStr.substring(1));
                pedidoController.rejeitarPedido(id);
            } else {
                int id = Integer.parseInt(idStr);
                pedidoController.aceitarPedido(id);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida.");
        }
    }

    private void exibirConsultas() {
        List<Consulta> consultas = consultaController.listarPorPsicologo(psicologo.getCpf());

        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma consulta agendada.");
        } else {
            StringBuilder sb = new StringBuilder("Consultas:\n");
            for (Consulta c : consultas) {
                sb.append("ID: ").append(c.getId())
                  .append(" | Cliente: ").append(c.getCpfCliente())
                  .append(" | DataHora: ").append(c.getDataHora())
                  .append(" | Status: ").append(c.getStatus()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }
}
