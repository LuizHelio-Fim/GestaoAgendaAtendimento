package service;

import model.Consulta;
import model.PedidoAgendamento;
import repository.ConsultaRepositorio;
import repository.PedidoAgendamentoRepositorio;

import java.util.ArrayList;
import java.util.List;

public class PedidoAgendamentoService {

    private final PedidoAgendamentoRepositorio pedidoRepositorio;
    private final ConsultaRepositorio consultaRepositorio;

    public PedidoAgendamentoService() {
        this.pedidoRepositorio = new PedidoAgendamentoRepositorio();
        this.consultaRepositorio = new ConsultaRepositorio();
    }

    public void solicitarAgendamento(PedidoAgendamento pedido) {
        List<PedidoAgendamento> pedidos = pedidoRepositorio.carregar();
        pedidos.add(pedido);
        pedidoRepositorio.salvar(pedidos);
    }

    public List<PedidoAgendamento> listarPendentes() {
        List<PedidoAgendamento> resultado = new ArrayList<>();
        for (PedidoAgendamento p : pedidoRepositorio.carregar()) {
            if (p.getStatus().equalsIgnoreCase("PENDENTE")) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public boolean aceitarPedido(int idPedido) {
        List<PedidoAgendamento> pedidos = pedidoRepositorio.carregar();
        PedidoAgendamento pedidoAceito = null;

        for (PedidoAgendamento p : pedidos) {
            if (p.getId() == idPedido) {
                pedidoAceito = p;
                p.setStatus("ACEITO");
                break;
            }
        }

        if (pedidoAceito == null) return false;

        // Salvar os pedidos com status atualizado
        
        pedidoRepositorio.salvar(pedidos);

        // Criar a consulta
        
        List<Consulta> consultas = consultaRepositorio.carregar();
        Consulta novaConsulta = new Consulta();
        novaConsulta.setId(gerarIdConsulta(consultas));
        novaConsulta.setCpfCliente(pedidoAceito.getCpfCliente());
        novaConsulta.setCpfPsicologo(pedidoAceito.getCpfPsicologo());
        novaConsulta.setDataHora(pedidoAceito.getDataHoraSolicitada());
        novaConsulta.setStatus("AGENDADA");

        consultas.add(novaConsulta);
        consultaRepositorio.salvar(consultas);

        return true;
    }

    public boolean rejeitarPedido(int idPedido) {
        List<PedidoAgendamento> pedidos = pedidoRepositorio.carregar();
        for (PedidoAgendamento p : pedidos) {
            if (p.getId() == idPedido) {
                p.setStatus("REJEITADO");
                pedidoRepositorio.salvar(pedidos);
                return true;
            }
        }
        return false;
    }

    private int gerarIdConsulta(List<Consulta> consultas) {
        int maior = 0;
        for (Consulta c : consultas) {
            if (c.getId() > maior) {
                maior = c.getId();
            }
        }
        return maior + 1;
    }
}
