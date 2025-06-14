package view;

import controller.PsicologoController;
import model.Psicologo;
import model.perfil.PerfilPsicologo;

import javax.swing.*;
import java.awt.*;

public class CadastroPsicologoView extends JFrame {

    public CadastroPsicologoView() {
        setTitle("Cadastro de Psicólogo");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Campos
        JTextField campoCpf = new JTextField(20);
        JTextField campoNome = new JTextField(20);
        JTextField campoEmail = new JTextField(20);
        JTextField campoSenha = new JTextField(20);
        JTextField campoCrp = new JTextField(20);
        JTextField campoEspecialidade = new JTextField(20);
        JTextField campoExp = new JTextField(20);
        JTextField campoDescricao = new JTextField(20);
        JTextField campoHorario = new JTextField(20);

        int y = 0;

        // Label e campo - cada par na mesma linha
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        add(campoCpf, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(campoNome, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(campoEmail, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        add(campoSenha, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("CRP:"), gbc);
        gbc.gridx = 1;
        add(campoCrp, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Especialidade:"), gbc);
        gbc.gridx = 1;
        add(campoEspecialidade, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Anos de experiência:"), gbc);
        gbc.gridx = 1;
        add(campoExp, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        add(campoDescricao, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        add(new JLabel("Horário de atendimento:"), gbc);
        gbc.gridx = 1;
        add(campoHorario, gbc);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton botaoCadastrar = new JButton("Cadastrar");
        add(botaoCadastrar, gbc);

        botaoCadastrar.addActionListener(e -> {
            try {
                PsicologoController controller = new PsicologoController();
                int novoId = controller.gerarProximoId();

                Psicologo psicologo = new Psicologo(
                    novoId,
                    campoCpf.getText(),
                    campoNome.getText(),
                    campoEmail.getText(),
                    campoSenha.getText(),
                    campoCrp.getText(),
                    campoEspecialidade.getText(),
                    new PerfilPsicologo(
                        Integer.parseInt(campoExp.getText()),
                        campoDescricao.getText(),
                        campoHorario.getText()
                    )
                );
                controller.cadastrarPsicologo(psicologo);
                JOptionPane.showMessageDialog(this, "Psicólogo cadastrado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
