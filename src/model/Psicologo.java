package model;

public class Psicologo extends Usuario {

    private String crp;
    private String especialidade;

    // Construtor vazio
    public Psicologo() {}

    // Construtor completo
    public Psicologo(int id, String cpf, String nome, String email, String senha, String crp, String especialidade) {
        super(id, cpf, nome, email, senha);
        this.crp = crp;
        this.especialidade = especialidade;
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

    @Override
    public String getResumo() {
        return "Psicólogo: " + getNome() + " - CRP: " + crp;
    }
}
