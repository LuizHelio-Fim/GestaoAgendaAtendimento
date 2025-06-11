package controller;

import java.util.List;

import javax.swing.JOptionPane;

import model.Psicologo;
import service.PsicologoService;

public class PsicologoController {
	
	private final PsicologoService psicologoService;
	
	public PsicologoController() {
		this.psicologoService = new PsicologoService();
	}
	
	public void cadastrarPsicologo(Psicologo psicologo) {
		try {
			psicologoService.cadastrarPsicologo(psicologo);
			JOptionPane.showMessageDialog(null, "Psicologo cadastrado com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar psicologo: " + e.getMessage());
		}
	}
	
	public Psicologo buscarPorCrp(String crp) {
		return psicologoService.buscarPorCrp(crp);
	}
	
	public List<Psicologo> listarTodos() {
		return psicologoService.listarTodos();
	}
}
