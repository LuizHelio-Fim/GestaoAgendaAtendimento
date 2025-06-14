package view;

import controller.ClienteController;
import model.Cliente;
import model.perfil.PerfilCliente;

import javax.swing.*;
import java.awt.*;

public class CadastroClienteView extends JFrame {

    public CadastroClienteView() {
        setTitle("Cadastro de Cliente");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JTextField campoCpf = new JTextField(20);
        JTextField campoNome = new JTextField(20);
        JTextField campoEmail = new JTextField(20);
        JTextField campoSenha = new JTextField(20);
        JTextField campoTelefone = new JTextField(20);
        JTextField campoSexo = new JTextField(20);
        JTextField campoIdade = new JTextField(20);
        JTextField campoObs = new JTextField(20);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        add(campoCpf, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(campoNome, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(campoEmail, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        add(campoSenha, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        add(campoTelefone, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        add(campoSexo, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        add(campoIdade, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1;
        add(campoObs, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton botaoCadastrar = new JButton("Cadastrar");
        add(botaoCadastrar, gbc);

        botaoCadastrar.addActionListener(e -> {
            try {
                ClienteController controller = new ClienteController();
                int novoId = controller.gerarProximoId();

                Cliente cliente = new Cliente(
                    novoId,
                    campoCpf.getText(),
                    campoNome.getText(),
                    campoEmail.getText(),
                    campoSenha.getText(),
                    campoTelefone.getText(),
                    new PerfilCliente(
                        campoSexo.getText(),
                        Integer.parseInt(campoIdade.getText()),
                        campoObs.getText()
                    )
                );
                controller.cadastrarCliente(cliente);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
