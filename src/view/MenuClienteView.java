package view;

import controller.PedidoAgendamentoController;
import controller.ConsultaController;
import model.Cliente;
import model.Consulta;
import model.PedidoAgendamento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuClienteView extends JFrame {

    private final Cliente cliente;
    private final PedidoAgendamentoController pedidoController;
    private final ConsultaController consultaController;

    public MenuClienteView(Cliente cliente) {
        this.cliente = cliente;
        this.pedidoController = new PedidoAgendamentoController();
        this.consultaController = new ConsultaController();

        setTitle("Menu do Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 5, 5));

        JButton btnAgendar = new JButton("Solicitar Agendamento");
        JButton btnMinhasConsultas = new JButton("Ver Minhas Consultas");
        JButton btnSair = new JButton("Sair");

        add(new JLabel("Bem-vindo, " + cliente.getNome(), SwingConstants.CENTER));
        add(btnAgendar);
        add(btnMinhasConsultas);
        add(btnSair);

        btnAgendar.addActionListener(e -> abrirTelaAgendamento());
        btnMinhasConsultas.addActionListener(e -> exibirConsultas());
        btnSair.addActionListener(e -> {
            dispose();
            new LoginView();
        });

        setVisible(true);
    }

    private void abrirTelaAgendamento() {
        String cpfPsicologo = JOptionPane.showInputDialog(this, "CPF do psicólogo:");
        String dataHora = JOptionPane.showInputDialog(this, "Data e hora (ex: 2025-06-20 14:00):");
        String mensagem = JOptionPane.showInputDialog(this, "Mensagem ao psicólogo:");

        if (cpfPsicologo == null || dataHora == null || mensagem == null) return;

        PedidoAgendamento pedido = new PedidoAgendamento(
            0, 						// será ignorado, ID pode ser gerado por lógica futura
            cliente.getCpf(),
            cpfPsicologo,
            dataHora,
            mensagem,
            "PENDENTE"
        );

        pedidoController.solicitarAgendamento(pedido);
    }

    private void exibirConsultas() {
        List<Consulta> consultas = consultaController.listarPorCliente(cliente.getCpf());

        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Você não possui consultas agendadas.");
        } else {
            StringBuilder sb = new StringBuilder("Minhas Consultas:\n");
            for (Consulta c : consultas) {
                sb.append("ID: ").append(c.getId())
                  .append(" | Psicólogo: ").append(c.getCpfPsicologo())
                  .append(" | DataHora: ").append(c.getDataHora())
                  .append(" | Status: ").append(c.getStatus()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }
}
