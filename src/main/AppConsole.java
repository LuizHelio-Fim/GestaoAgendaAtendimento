package main;

import controller.AutenticacaoController;
import controller.ClienteController;
import controller.PsicologoController;
import controller.PedidoAgendamentoController;
import controller.ConsultaController;

import model.Cliente;
import model.Psicologo;
import model.Consulta;
import model.PedidoAgendamento;
import model.perfil.PerfilCliente;
import model.perfil.PerfilPsicologo;

import java.util.Scanner;

public class AppConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AutenticacaoController auth = new AutenticacaoController();

        System.out.println("=== Login ===");
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        String tipo = auth.tipoUsuario(cpf, senha);

        switch (tipo) {
            case "cliente":
                System.out.println("Bem-vindo, cliente!");
                clienteMenu(cpf, scanner);
                break;
            case "psicologo":
                System.out.println("Bem-vindo, psicólogo!");
                psicologoMenu(cpf, scanner);
                break;
            default:
                System.out.println("Login inválido.");
        }

        scanner.close();
    }

    private static void clienteMenu(String cpfCliente, Scanner scanner) {
        PedidoAgendamentoController pedidoCtrl = new PedidoAgendamentoController();

        System.out.println("1. Solicitar agendamento");
        System.out.print("Escolha: ");
        int opcao = scanner.nextInt(); scanner.nextLine();

        if (opcao == 1) {
            System.out.print("CPF do psicólogo: ");
            String cpfPsicologo = scanner.nextLine();
            System.out.print("Data e hora (ex: 2025-06-16 14:00): ");
            String dataHora = scanner.nextLine();
            System.out.print("Mensagem: ");
            String msg = scanner.nextLine();

            PedidoAgendamento pedido = new PedidoAgendamento(1, cpfCliente, cpfPsicologo, dataHora, msg, "PENDENTE");
            pedidoCtrl.solicitarAgendamento(pedido);
        }
    }

    private static void psicologoMenu(String cpfPsicologo, Scanner scanner) {
        PedidoAgendamentoController pedidoCtrl = new PedidoAgendamentoController();
        ConsultaController consultaCtrl = new ConsultaController();

        System.out.println("1. Ver pedidos pendentes");
        System.out.println("2. Ver minhas consultas");
        System.out.print("Escolha: ");
        int opcao = scanner.nextInt(); scanner.nextLine();

        if (opcao == 1) {
            var pedidos = pedidoCtrl.listarPendentes();
            for (PedidoAgendamento p : pedidos) {
                if (p.getCpfPsicologo().equals(cpfPsicologo)) {
                    System.out.println("ID: " + p.getId() + " | Cliente: " + p.getCpfCliente() + " | DataHora: " + p.getDataHoraSolicitada());
                }
            }

            System.out.print("Digite o ID para aceitar ou -1 para rejeitar todos: ");
            int id = scanner.nextInt();
            if (id != -1) {
                pedidoCtrl.aceitarPedido(id);
            }
        } else if (opcao == 2) {
            var consultas = consultaCtrl.listarPorPsicologo(cpfPsicologo);
            for (Consulta c : consultas) {
                System.out.println("Consulta #" + c.getId() + " com " + c.getCpfCliente() + " em " + c.getDataHora());
            }
        }
    }
}
