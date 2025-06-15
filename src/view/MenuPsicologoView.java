package view;

import controller.ClienteController;
import controller.ConsultaController;
import controller.PedidoAgendamentoController;
import model.Consulta;
import model.Psicologo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPsicologoView extends JFrame {

    private final Psicologo psicologo;
    private final ConsultaController consultaController;

    public MenuPsicologoView(Psicologo psicologo) {
        this.psicologo = psicologo;
        new PedidoAgendamentoController();
        this.consultaController = new ConsultaController();

        setTitle("Menu do Psicólogo");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 5, 5));

        JLabel label = new JLabel("Bem-vindo, Dr(a). " + psicologo.getNome(), SwingConstants.CENTER);
        JButton btnVisualizarPedidos = new JButton("Visualizar Pedidos");
        JButton btnResponderPedidos = new JButton("Responder Pedido");
        JButton btnConsultas = new JButton("Ver Agenda (Consultas)");
        JButton btnSair = new JButton("Sair");

        add(label);
        add(btnVisualizarPedidos);
        add(btnResponderPedidos);
        add(btnConsultas);
        add(new JLabel()); // espaço vazio
        add(btnSair);

        btnVisualizarPedidos.addActionListener(e -> new VisualizarPedidosView(psicologo.getCpf()));
        btnResponderPedidos.addActionListener(e -> new ResponderPedidoView(psicologo.getCpf()));
        btnConsultas.addActionListener(e -> exibirConsultas());
        btnSair.addActionListener(e -> {
            dispose();
            new LoginView();
        });

        setVisible(true);
    }

    private void exibirConsultas() {
        List<Consulta> consultas = consultaController.listarPorPsicologo(psicologo.getCpf());

        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma consulta agendada.");
        } else {
            ClienteController clienteController = new ClienteController();

            StringBuilder sb = new StringBuilder("Consultas:\n");
            for (Consulta c : consultas) {
                String nomeCliente = "Desconhecido";
                String telefone = "N/A";

                try {
                    var cliente = clienteController.buscarPorCpf(c.getCpfCliente());
                    nomeCliente = cliente.getNome();
                    telefone = cliente.getTelefone();
                } catch (Exception ignored) {}

                sb.append("Cliente: ").append(nomeCliente)
                  .append(" | Telefone: ").append(telefone)
                  .append(" | Data: ").append(c.getDataHora())
                  .append(" | Status: ").append(c.getStatus())
                  .append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }
}
