package view;

import controller.PedidoAgendamentoController;
import controller.ConsultaController;
import controller.PsicologoController;
import model.Cliente;
import model.Consulta;
import model.PedidoAgendamento;
import model.Psicologo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;

public class MenuClienteView extends JFrame {

    private final Cliente cliente;
    private final PedidoAgendamentoController pedidoController;
    private final ConsultaController consultaController;
    private final PsicologoController psicologoController = new PsicologoController();

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
        // 1. Selecionar psicólogo
        HashMap<String, String> mapaCpfPorNome = new HashMap<>();
        for (Psicologo p : psicologoController.listarTodos()) {
            String label = "Dr(a). " + p.getNome() + " - " + p.getEspecialidade();
            mapaCpfPorNome.put(label, p.getCpf());
        }

        if (mapaCpfPorNome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum psicólogo disponível.");
            return;
        }

        JComboBox<String> comboPsicologo = new JComboBox<>(mapaCpfPorNome.keySet().toArray(new String[0]));
        int op = JOptionPane.showConfirmDialog(this, comboPsicologo, "Selecione um psicólogo", JOptionPane.OK_CANCEL_OPTION);
        if (op != JOptionPane.OK_OPTION) return;

        String selecao = (String) comboPsicologo.getSelectedItem();
        String cpfPsicologo = mapaCpfPorNome.get(selecao);

        // 2. Selecionar data (dias disponíveis)
        List<String> diasDisponiveis = pedidoController.gerarDiasDisponiveis(cpfPsicologo, 15);
        if (diasDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum dia disponível nos próximos 15 dias.");
            return;
        }

        JComboBox<String> comboData = new JComboBox<>(diasDisponiveis.toArray(new String[0]));
        int diaEscolhido = JOptionPane.showConfirmDialog(this, comboData, "Selecione a data", JOptionPane.OK_CANCEL_OPTION);
        if (diaEscolhido != JOptionPane.OK_OPTION) return;

        String dataSelecionada = (String) comboData.getSelectedItem();

        // 3. Selecionar horário disponível
        List<String> horarios = pedidoController.gerarHorariosDisponiveis(cpfPsicologo, dataSelecionada);
        if (horarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum horário disponível nesta data.");
            return;
        }

        JComboBox<String> comboHorario = new JComboBox<>(horarios.toArray(new String[0]));
        int horarioOk = JOptionPane.showConfirmDialog(this, comboHorario, "Selecione o horário", JOptionPane.OK_CANCEL_OPTION);
        if (horarioOk != JOptionPane.OK_OPTION) return;

        String dataHoraSelecionada = (String) comboHorario.getSelectedItem();

        // 4. Mensagem opcional
        String mensagem = JOptionPane.showInputDialog(this, "Mensagem ao psicólogo:");
        if (mensagem == null) mensagem = "";

        PedidoAgendamento pedido = new PedidoAgendamento(
            0,					// ignora o ID
            cliente.getCpf(),
            cpfPsicologo,
            dataHoraSelecionada,
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
            PsicologoController psicologoController = new PsicologoController();

            StringBuilder sb = new StringBuilder("Minhas Consultas:\n");
            for (Consulta c : consultas) {
                String nomePsicologo = "Desconhecido";

                try {
                    var p = psicologoController.buscarPorCpf(c.getCpfPsicologo());
                    nomePsicologo = p.getNome();
                } catch (Exception ignored) {}

                sb.append("Psicólogo(a): ").append(nomePsicologo)
                  .append(" | Data: ").append(c.getDataHora())
                  .append(" | Status: ").append(c.getStatus())
                  .append("\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }
}
