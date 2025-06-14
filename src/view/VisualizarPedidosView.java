package view;

import controller.PedidoAgendamentoController;
import controller.ClienteController;
import model.PedidoAgendamento;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisualizarPedidosView extends JFrame {

    public VisualizarPedidosView(String cpfPsicologo) {
        setTitle("Pedidos Pendentes");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        PedidoAgendamentoController pedidoController = new PedidoAgendamentoController();
        ClienteController clienteController = new ClienteController();

        List<PedidoAgendamento> pedidos = pedidoController.listarPendentesPorPsicologo(cpfPsicologo);

        if (pedidos.isEmpty()) {
            area.setText("Nenhum pedido pendente.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (PedidoAgendamento p : pedidos) {
                Cliente c = clienteController.buscarPorCpf(p.getCpfCliente());
                sb.append("Cliente: ").append(c.getNome())
                  .append("\nDataHora: ").append(p.getDataHoraSolicitada())
                  .append("\nMensagem: ").append(p.getMensagem())
                  .append("\n---\n");
            }
            area.setText(sb.toString());
        }

        setVisible(true);
    }
}
