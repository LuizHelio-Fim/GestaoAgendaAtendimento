package repository;

import model.Cliente;
import model.Psicologo;

public class PsicologoRepositorio extends RepositorioCSV<Psicologo>{
	
	public PsicologoRepositorio() {
        super("psicologos.csv");
    }

    @Override
    public String converterParaCSV(Psicologo p) {
        //TODO adicionar atributos
    	return null;
    }

    @Override
    public Psicologo converterDeCSV(String linha) {
        String[] partes = linha.split(",");
        Cliente p = new Psicologo();
        //TODO adicionar atributos
        return p;
    }

}
