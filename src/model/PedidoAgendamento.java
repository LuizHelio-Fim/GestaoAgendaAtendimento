package model;

public class PedidoAgendamento {
	
	private int id;
    private String cpfCliente;
    private String cpfPsicologo;
    private String dataHoraSolicitada;
    private String mensagem;
    private String status; 				// PENDENTE, ACEITO, REJEITADO
    
    // Construtores
    
    public PedidoAgendamento() {}

	public PedidoAgendamento(int id, String cpfCliente, String cpfPsicologo, 
							String dataHoraSolicitada, String mensagem, String status) {
		this.id = id;
		this.cpfCliente = cpfCliente;
		this.cpfPsicologo = cpfPsicologo;
		this.dataHoraSolicitada = dataHoraSolicitada;
		this.mensagem = mensagem;
		this.status = status;
	}
	
	// Getters e Setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getCpfPsicologo() {
		return cpfPsicologo;
	}

	public void setCpfPsicologo(String cpfPsicologo) {
		this.cpfPsicologo = cpfPsicologo;
	}

	public String getDataHoraSolicitada() {
		return dataHoraSolicitada;
	}

	public void setDataHoraSolicitada(String dataHoraSolicitada) {
		this.dataHoraSolicitada = dataHoraSolicitada;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}