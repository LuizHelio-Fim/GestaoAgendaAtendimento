package repository;

import model.Cliente;
import model.perfil.PerfilCliente;

public class ClienteRepositorio extends RepositorioCSV<Cliente> {

    public ClienteRepositorio() {
        super("data/clientes.csv");
    }

    @Override
    public String converterParaCSV(Cliente c) {
    	return c.getId() + "," +
    		   c.getCpf() + "," + 
    		   c.getNome() + "," + 
    		   c.getEmail() + "," + 
    		   c.getSenha() + "," +
    		   c.getTelefone() + "," +
    		   c.getPerfil().getSexo() + "," +
    		   c.getPerfil().getIdade() + "," +
    		   c.getPerfil().getObservacoes();
    }

    @Override
    public Cliente converterDeCSV(String linha) {
        String[] partes = linha.split(",", -1);

        PerfilCliente perfil = new PerfilCliente(
        		partes[6], 
        		Integer.parseInt(partes[7]), 
        		partes[8]);
        
        return new Cliente(
            Integer.parseInt(partes[0]),
            partes[1],
            partes[2],
            partes[3],
            partes[4],
            partes[5],
            perfil
        );
    }
}
