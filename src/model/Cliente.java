package model;

import model.perfil.PerfilCliente;

public class Cliente extends Usuario {
	
	private String telefone;
	private PerfilCliente perfil;
	
	// Construtores
	public Cliente() {}
	
	public Cliente(int id, String cpf, String nome, 
			String email, String senha, String telefone, PerfilCliente perfil) {
		super(id, cpf, nome, email, senha);
		this.telefone = telefone;
		this.perfil = perfil;
	}
	
	// Getters e Setters

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public PerfilCliente getPerfil() {
        return perfil;
    }
    public void setPerfil(PerfilCliente perfil) {
        this.perfil = perfil;
    }
	
	@Override
	public String getResumo() {
		return "Cliente: " + getNome() + " - Tel: " + this.telefone;
	}

}
