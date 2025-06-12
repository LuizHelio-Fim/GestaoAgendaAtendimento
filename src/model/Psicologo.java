package model;

import model.perfil.PerfilPsicologo;

public class Psicologo extends Usuario {

    private String crp;
    private String especialidade;
    private PerfilPsicologo perfil;
    
    // Construtores

    public Psicologo() {}

    public Psicologo(int id, String cpf, String nome, String email, 
    		String senha, String crp, String especialidade, PerfilPsicologo perfil) {
        super(id, cpf, nome, email, senha);
        this.crp = crp;
        this.especialidade = especialidade;
        this.setPerfil(perfil);
    }

    // Getters e Setters
    
    public String getCrp() {
    	return crp;
    }
    
    public void setCrp(String crp) {
    	this.crp = crp;
    }
    
    public String getEspecialidade() {
    	return especialidade;
    }
    
    public void setEspecialidade(String especialidade) {
    	this.especialidade = especialidade;
    }
    
    public PerfilPsicologo getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilPsicologo perfil) {
		this.perfil = perfil;
	}

    @Override
    public String getResumo() {
        return "Psicólogo: " + getNome() + " - CRP: " + crp;
    }
}
