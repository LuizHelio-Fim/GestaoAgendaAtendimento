package controller;

import java.util.List;
import javax.swing.JOptionPane;

import model.Cliente;
import service.ClienteService;

public class ClienteController {
	
	private final ClienteService clienteService;

	public ClienteController() {
		this.clienteService = new ClienteService();
	}
	
	public void cadastrarCliente(Cliente cliente) {
		try {
			clienteService.cadastrarCliente(cliente);
			JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente: " + e.getMessage());
		}
	}
	
	public Cliente buscarPorCpf(String cpf) {
		return clienteService.buscarPorCpf(cpf);
	}
	
	public List<Cliente> listarTodos() {
		return clienteService.listarTodos();
	}
	
}
