package model.perfil;

public class PerfilPsicologo {

    private int anosExperiencia;
    private String descricao;
    private String horarioAtendimento; 	// Seg a Sex, 9h-17h

    // Construtores
    
    public PerfilPsicologo() {}

    public PerfilPsicologo(int anosExperiencia, String descricao, String horarioAtendimento) {
        this.anosExperiencia = anosExperiencia;
        this.descricao = descricao;
        this.horarioAtendimento = horarioAtendimento;
    }

    // Getters e Setters

    public int getAnosExperiencia() {
        return anosExperiencia;
    }
    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorarioAtendimento() {
        return horarioAtendimento;
    }
    public void setHorarioAtendimento(String horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
    }
}
