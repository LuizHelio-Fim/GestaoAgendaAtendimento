package service;

import java.util.List;

import model.Psicologo;
import repository.PsicologoRepositorio;

public class PsicologoService {
	
	private final PsicologoRepositorio psicologoRepositorio;

	public PsicologoService() {
		this.psicologoRepositorio = new PsicologoRepositorio();
	}
	
	public void cadastrarPsicologo(Psicologo psicologo) {
		List<Psicologo> psicologos = psicologoRepositorio.carregar();
		psicologos.add(psicologo);
		psicologoRepositorio.salvar(psicologos);
	}
	
	public List<Psicologo> listarTodos() {
		return psicologoRepositorio.carregar();
	}
	
	public Psicologo buscarPorCrp(String crp) {
		return psicologoRepositorio.carregar().stream()
				.filter(p -> p.getCrp().equals(crp))
				.findFirst()
				.orElse(null);
	}
	
	
}
