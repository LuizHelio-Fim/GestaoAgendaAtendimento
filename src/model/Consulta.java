package model;

public class Consulta {

    private int id;
    private String cpfCliente;
    private String cpfPsicologo;
    private String dataHora;
    private String status;			 // AGENDADA, CONCLUIDA, CANCELADA

    // Construtores
    
    public Consulta() {}

    public Consulta(int id, String cpfCliente, String cpfPsicologo, String dataHora, String status) {
        this.id = id;
        this.cpfCliente = cpfCliente;
        this.cpfPsicologo = cpfPsicologo;
        this.dataHora = dataHora;
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

    public String getDataHora() {
        return dataHora;
    }
    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}