package repository;

import model.Consulta;

public class ConsultaRepositorio extends RepositorioCSV<Consulta> {

	public ConsultaRepositorio(String caminhoArquivo) {
		super("consultas.csv");
	}

	@Override
	public String converterParaCSV(Consulta c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consulta converterDeCSV(String linha) {
		String[] partes = linha.split(",");
		Consulta c = new Consulta();
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
