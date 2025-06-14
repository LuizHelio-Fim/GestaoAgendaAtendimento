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
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel(new GridLayout(4, 1));
        campoCpf = new JTextField();
        campoSenha = new JPasswordField();

        painelCampos.add(new JLabel("CPF:"));
        painelCampos.add(campoCpf);
        painelCampos.add(new JLabel("Senha:"));
        painelCampos.add(campoSenha);
        add(painelCampos, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton botaoEntrar = new JButton("Entrar");
        JButton botaoCadastrar = new JButton("Cadastre-se");
        painelBotoes.add(botaoEntrar);
        painelBotoes.add(botaoCadastrar);
        add(painelBotoes, BorderLayout.SOUTH);

        botaoEntrar.addActionListener(e -> {
            String cpf = campoCpf.getText().trim();
            String senha = new String(campoSenha.getPassword()).trim();

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

        botaoCadastrar.addActionListener(e -> {
            Object[] opcoes = {"Cliente", "Psicólogo"};
            int escolha = JOptionPane.showOptionDialog(this,
                    "Você deseja se cadastrar como:",
                    "Novo Cadastro",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]);

            if (escolha == 0) {
                new CadastroClienteView();
            } else if (escolha == 1) {
                new CadastroPsicologoView();
            }
        });

        setVisible(true);
    }
}
