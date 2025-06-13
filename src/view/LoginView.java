package view;

import controller.AutenticacaoController;
import model.Usuario;
import model.Cliente;
import model.Psicologo;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField campoCpf;
    private JPasswordField campoSenha;

    public LoginView() {
        setTitle("Login");
        setSize(300, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        campoCpf = new JTextField();
        campoSenha = new JPasswordField();

        JButton botaoEntrar = new JButton("Entrar");

        add(new JLabel("CPF:"));
        add(campoCpf);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(botaoEntrar);

        botaoEntrar.addActionListener(e -> {
            String cpf = campoCpf.getText();
            String senha = new String(campoSenha.getPassword());

            AutenticacaoController auth = new AutenticacaoController();
            Usuario usuario = auth.autenticar(cpf, senha);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "CPF ou senha incorretos.");
            } else {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                dispose();

                if (usuario instanceof Cliente) {
                    new MenuClienteView((Cliente) usuario);
                } else if (usuario instanceof Psicologo) {
                    new MenuPsicologoView((Psicologo) usuario);
                }
            }
        });

        setVisible(true);
    }
}
