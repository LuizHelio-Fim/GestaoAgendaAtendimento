package controller;

import java.util.List;

import javax.swing.JOptionPane;

import model.Consulta;
import service.ConsultaService;

public class ConsultaController {
	
	private final ConsultaService consultaService;

	public ConsultaController() {
		this.consultaService = new ConsultaService();
	}
	
	public void agendarConsulta(Consulta consulta) {
		try {
			consultaService.agendarConsulta(consulta);
			JOptionPane.showMessageDialog(null, "Consulta agendada com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao agendar consulta: " + e.getMessage());
		}
	}
	
	public boolean cancelarConsulta(int id) {
		boolean certo = consultaService.cancelarConsulta(id);
		if (certo) {
			JOptionPane.showMessageDialog(null, "Consulta cancelada com sucesso.");
		} else {
			JOptionPane.showMessageDialog(null, "Consulta nao encontrada.");
		}
		return certo;
	}
	
	public List<Consulta> listarTodos() {
		return consultaService.listarConsultas();
	}
	
	public List<Consulta> listarPorCliente(String cpf) {
		return consultaService.listarPorCliente(cpf);
	}
	
	public List<Consulta> listarPorPsicologo(String cpf) {
		return consultaService.listarPorPsicologo(cpf);
	}
}
