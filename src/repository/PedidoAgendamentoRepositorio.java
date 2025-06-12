package repository;

import model.PedidoAgendamento;

public class PedidoAgendamentoRepositorio extends RepositorioCSV<PedidoAgendamento> {

	public PedidoAgendamentoRepositorio() {
		super("Pedidos_agendamento.csv");
	}

	@Override
	public String converterParaCSV(PedidoAgendamento p) {
		return p.getId() + "," +
				p.getCpfCliente() + "," +
				p.getCpfPsicologo() + "," +
				p.getDataHoraSolicitada() + "," +
				p.getMensagem() + "," +
				p.getStatus();
	}

	@Override
	public PedidoAgendamento converterDeCSV(String linha) {
		String[] partes = linha.split(",", - 1); 	// -1 para garantir a leitura de campos vazios
		
		return new PedidoAgendamento(
				Integer.parseInt(partes[0]),
				partes[1],
				partes[2],
				partes[3],
				partes[4],
				partes[6]
		);
	}
}