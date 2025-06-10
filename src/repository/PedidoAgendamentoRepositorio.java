package repository;

import model.PedidoAgendamento;

public class PedidoAgendamentoRepositorio extends RepositorioCSV<PedidoAgendamento> {

	public PedidoAgendamentoRepositorio(String caminhoArquivo) {
		super("Pedidos_agendamento.csv");
	}

	@Override
	public String converterParaCSV(PedidoAgendamento p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PedidoAgendamento converterDeCSV(String linha) {
		String[] partes = linha.split(",");
		PedidoAgendamento p = new PedidoAgendamento();
		// TODO Auto-generated method stub
		return null;
	}

}
