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
        setLayout(new GridLayout(8, 2, 5, 5));

        // Campos
        JTextField campoCpf = new JTextField();
        JTextField campoNome = new JTextField();
        JTextField campoEmail = new JTextField();
        JTextField campoSenha = new JTextField();
        JTextField campoTelefone = new JTextField();
        JTextField campoSexo = new JTextField();
        JTextField campoIdade = new JTextField();
        JTextField campoObs = new JTextField();

        // Labels e campos
        add(new JLabel("CPF:"));
        add(campoCpf);
        add(new JLabel("Nome:"));
        add(campoNome);
        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(new JLabel("Telefone:"));
        add(campoTelefone);
        add(new JLabel("Sexo:"));
        add(campoSexo);
        add(new JLabel("Idade:"));
        add(campoIdade);
        add(new JLabel("Observações:"));
        add(campoObs);

        JButton botaoCadastrar = new JButton("Cadastrar");
        add(new JLabel());
        add(botaoCadastrar);

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
