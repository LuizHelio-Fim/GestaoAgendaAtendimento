package repository;

import model.Psicologo;

public class PsicologoRepositorio extends RepositorioCSV<Psicologo>{
	
	public PsicologoRepositorio() {
        super("psicologos.csv");
    }

    @Override
    public String converterParaCSV(Psicologo p) {
    	return p.getId() + "," +
    		   p.getCpf() + "," +
    		   p.getNome() + "," +
    		   p.getEmail() + "," +
    		   p.getSenha() + "," +
    		   p.getCrp() + "," +
    		   p.getEspecialidade();
    }

    @Override
    public Psicologo converterDeCSV(String linha) {
        String[] partes = linha.split(",");
        Psicologo p = new Psicologo();
        p.setId(Integer.parseInt(partes[0]));
        p.setCpf(partes[1]);
        p.setNome(partes[2]);
        p.setEmail(partes[3]);
        p.setSenha(partes[4]);
        p.setCrp(partes[5]);
        p.setEspecialidade(partes[6]);
        return p;
    }

}
