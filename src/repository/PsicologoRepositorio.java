package repository;

import model.Psicologo;
import model.perfil.PerfilPsicologo;

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
    		   p.getEspecialidade() + "," +
    		   p.getPerfil().getAnosExperiencia() + "," +
    		   p.getPerfil().getDescricao() + "," +
    		   p.getPerfil().getHorarioAtendimento();
    }

    @Override
    public Psicologo converterDeCSV(String linha) {
        String[] partes = linha.split(",", -1);

        PerfilPsicologo perfil = new PerfilPsicologo(
            Integer.parseInt(partes[7]),
            partes[8],
            partes[9]
        );
        
        return new Psicologo(
            Integer.parseInt(partes[0]),
            partes[1],
            partes[2],
            partes[3],
            partes[4],
            partes[5],
            partes[6],
            perfil
        );
    }

}
