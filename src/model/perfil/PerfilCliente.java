package model.perfil;

public class PerfilCliente {

    private String sexo;
    private int idade;
    private String observacoes; // informações adicionais do psicólogo

    // Construtores
    
    public PerfilCliente() {}

    public PerfilCliente(String sexo, int idade, String observacoes) {
        this.sexo = sexo;
        this.idade = idade;
        this.observacoes = observacoes;
    }

    // Getters e Setters

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
