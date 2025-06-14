package view;

import controller.PedidoAgendamentoController;
import model.PedidoAgendamento;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class ResponderPedidoView extends JFrame {

    public ResponderPedidoView(String cpfPsicologo) {
        setTitle("Responder Pedido");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 5, 5));

        PedidoAgendamentoController controller = new PedidoAgendamentoController();
        List<PedidoAgendamento> pedidos = controller.listarPendentesPorPsicologo(cpfPsicologo);

        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum pedido pendente.");
            dispose();
            return;
        }

        HashMap<String, PedidoAgendamento> mapaPedidos = new HashMap<>();
        for (PedidoAgendamento p : pedidos) {
            String label = p.getDataHoraSolicitada() + " - " + p.getMensagem();
            mapaPedidos.put(label, p);
        }

        JComboBox<String> combo = new JComboBox<>(mapaPedidos.keySet().toArray(new String[0]));
        JButton btnAceitar = new JButton("Aceitar");
        JButton btnNegar = new JButton("Negar");

        JTextField campoJustificativa = new JTextField();

        add(new JLabel("Selecione um pedido:"));
        add(combo);
        add(new JLabel("Justificativa (se negar):"));
        add(campoJustificativa);
        add(new JLabel());
        add(new JLabel());
        add(btnAceitar);
        add(btnNegar);

        btnAceitar.addActionListener(e -> {
            PedidoAgendamento selecionado = mapaPedidos.get(combo.getSelectedItem());
            controller.aceitarPedido(selecionado.getId());
            JOptionPane.showMessageDialog(this, "Pedido aceito e consulta agendada!");
            dispose();
        });

        btnNegar.addActionListener(e -> {
            PedidoAgendamento selecionado = mapaPedidos.get(combo.getSelectedItem());
            String motivo = campoJustificativa.getText().trim();
            if (motivo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o motivo da recusa.");
                return;
            }
            controller.rejeitarPedido(selecionado.getId(), motivo);
            JOptionPane.showMessageDialog(this, "Pedido negado com justificativa.");
            dispose();
        });

        setVisible(true);
    }
}
