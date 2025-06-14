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
        setLayout(new GridLayout(9, 2, 5, 5));

        // Campos
        JTextField campoCpf = new JTextField();
        JTextField campoNome = new JTextField();
        JTextField campoEmail = new JTextField();
        JTextField campoSenha = new JTextField();
        JTextField campoCrp = new JTextField();
        JTextField campoEspecialidade = new JTextField();
        JTextField campoExp = new JTextField();
        JTextField campoDescricao = new JTextField();
        JTextField campoHorario = new JTextField();

        // Labels e campos
        add(new JLabel("CPF:"));
        add(campoCpf);
        add(new JLabel("Nome:"));
        add(campoNome);
        add(new JLabel("Email:"));
        add(campoEmail);
        add(new JLabel("Senha:"));
        add(campoSenha);
        add(new JLabel("CRP:"));
        add(campoCrp);
        add(new JLabel("Especialidade:"));
        add(campoEspecialidade);
        add(new JLabel("Anos de experiência:"));
        add(campoExp);
        add(new JLabel("Descrição:"));
        add(campoDescricao);
        add(new JLabel("Horário de atendimento:"));
        add(campoHorario);

        JButton botaoCadastrar = new JButton("Cadastrar");
        add(new JLabel());
        add(botaoCadastrar);

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
