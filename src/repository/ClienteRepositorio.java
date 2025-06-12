package repository;

import model.Cliente;

public class ClienteRepositorio extends RepositorioCSV<Cliente> {

    public ClienteRepositorio() {
        super("clientes.csv");
    }

    @Override
    public String converterParaCSV(Cliente c) {
    	return c.getId() + "," +
    		   c.getCpf() + "," + 
    		   c.getNome() + "," + 
    		   c.getEmail() + "," + 
    		   c.getSenha() + "," +
    		   c.getTelefone();
    }

    @Override
    public Cliente converterDeCSV(String linha) {
        String[] partes = linha.split(",");
        Cliente c = new Cliente();
        c.setId(Integer.parseInt(partes[0]));
        c.setCpf(partes[1]);
        c.setNome(partes[2]);
        c.setEmail(partes[3]);
        c.setSenha(partes[4]);
        c.setTelefone(partes[5]);
        return c;
    }
}
