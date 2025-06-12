package model;

public class Cliente extends Usuario {
	
	private String telefone;
	
	// Construtores
	public Cliente() {}
	
	public Cliente(int id, String cpf, String nome, String email, String senha, String telefone) {
		super(id, cpf, nome, email, senha);
		this.telefone = telefone;
	}
	
	// Getters e Setters

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public String getResumo() {
		return "Cliente: " + getNome() + " - Tel: " + this.telefone;
	}

}
