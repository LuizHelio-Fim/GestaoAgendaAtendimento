package service;

import java.util.ArrayList;
import java.util.List;

import model.Consulta;
import repository.ConsultaRepositorio;

public class ConsultaService {
	
	private final ConsultaRepositorio consultaRepositorio;
	
	public ConsultaService() {
		this.consultaRepositorio = new ConsultaRepositorio();
	}
	
	// Cadastrar nova consulta ao sistema
	
	public void agendarConsulta(Consulta novaConsulta) throws Exception {
		List<Consulta> consultas = consultaRepositorio.carregar();
		
		// Verificar se ja existe uma consulta no mesmo horario com o mesmo psicologo
		for (Consulta c : consultas) {
			if (c.getCpfPsicologo().equals(novaConsulta.getCpfPsicologo()) &&
				c.getDataHora().equals(novaConsulta.getDataHora())) {
					throw new Exception("Horario ja ocupado para esse psicologo.");
				}
		}
		
		consultas.add(novaConsulta);
		consultaRepositorio.salvar(consultas);
	}
	
	// Cancelar uma consulta
	
	public boolean cancelarConsulta (int id) {
		List<Consulta> consultas = consultaRepositorio.carregar();
		boolean removido = consultas.removeIf(c -> c.getId() == id);
		if (removido) {
			consultaRepositorio.salvar(consultas);
		}
		return removido;
	}
	
	// Retornar todas as consultas
	
	public List<Consulta> listarConsultas() {
		return consultaRepositorio.carregar();
	}
	
	// Retornar todas as consultas de um cliente
	
	public List<Consulta> listarPorCliente(String cpfCliente) {
		List<Consulta> resultado = new ArrayList<Consulta>();
		for (Consulta c : consultaRepositorio.carregar()) {
			if (c.getCpfCliente().equals(cpfCliente)) {
				resultado.add(c);
			}
		}
		return resultado;
	}
	
	// Retornar todas as consultas de um psicologo
	
		public List<Consulta> listarPorPsicologo(String cpfPsicologo) {
			List<Consulta> resultado = new ArrayList<Consulta>();
			for (Consulta c : consultaRepositorio.carregar()) {
				if (c.getCpfPsicologo().equals(cpfPsicologo)) {
					resultado.add(c);
				}
			}
			return resultado;
		}
}
