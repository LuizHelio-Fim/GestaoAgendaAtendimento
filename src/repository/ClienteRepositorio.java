package repository;

import model.Cliente;

public class ClienteRepositorio extends RepositorioCSV<Cliente> {

    public ClienteRepositorio() {
        super("clientes.csv");
    }

    @Override
    public String converterParaCSV(Cliente c) {
        //TODO adicionar atributos
    	return null;
    }

    @Override
    public Cliente converterDeCSV(String linha) {
        String[] partes = linha.split(",");
        Cliente c = new Cliente();
        //TODO adicionar atributos
        return c;
    }
}
