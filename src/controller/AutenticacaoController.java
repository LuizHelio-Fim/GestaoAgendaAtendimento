package controller;

import model.Cliente;
import model.Psicologo;
import model.Usuario;
import service.ClienteService;
import service.PsicologoService;

public class AutenticacaoController {
	
	private final ClienteService clienteService;
	private final PsicologoService psicologoService;
	
	public AutenticacaoController() {
		this.clienteService = new ClienteService();
		this.psicologoService = new PsicologoService();
	}
	
	// Faz a autenticacao de um usuario pelo cpf e senha
	
	public Usuario autenticar(String cpf, String senha) {
		Cliente cliente = clienteService.buscarPorCpf(cpf);
		if (cliente != null && cliente.getSenha().equals(senha)) {
			return cliente;
		}
		
		Psicologo psicologo = psicologoService.buscarPorCrp(cpf);
		if (psicologo != null && psicologo.getSenha().equals(senha)) {
			return psicologo;
		}
		
		return null;
	}
	
	// Identificar o tipo de usuario autenticado
	
	public String tipoUsuario(String cpf, String senha) {
		Usuario usuario = autenticar(cpf, senha);
		if (usuario instanceof Cliente) return "cliente";
		if (usuario instanceof Psicologo) return "psicologo";
		return "invalido";
	}
}
