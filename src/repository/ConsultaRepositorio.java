package repository;

import model.Consulta;

public class ConsultaRepositorio extends RepositorioCSV<Consulta> {

	public ConsultaRepositorio() {
		super("consultas.csv");
	}

	@Override
	public String converterParaCSV(Consulta c) {
		return c.getId() + "," +
				c.getCpfCliente() + "," +
				c.getCpfPsicologo() + "," +
				c.getDataHora() + "," +
				c.getStatus();
	}

	@Override
	public Consulta converterDeCSV(String linha) {
		String[] partes = linha.split(",", -1);		// -1 para garantir a leitura de campos vazios
		
		return new Consulta(
				Integer.parseInt(partes[0]),
				partes[1],
				partes[2],
				partes[3],
				partes[4]
		);
		
	}
}