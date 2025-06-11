package service;

import java.util.List;

import model.Cliente;
import repository.ClienteRepositorio;

public class ClienteService {
	
	private final ClienteRepositorio clienteRepositorio;

	public ClienteService() {
		this.clienteRepositorio = new ClienteRepositorio();
	}
	
	public void cadastrarCliente(Cliente cliente) {
		List<Cliente> clientes = clienteRepositorio.carregar();
		clientes.add(cliente);
		clienteRepositorio.salvar(clientes);
	}
	
	public List<Cliente> listarTodos() {
		return clienteRepositorio.carregar();
	}
	
	public Cliente buscarPorCpf(String cpf) {
		return clienteRepositorio.carregar().stream()
				.filter(c -> c.getCpf().equals(cpf))
				.findFirst()
				.orElse(null);
	}
	
	
}
