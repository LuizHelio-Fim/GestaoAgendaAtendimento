package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositorioCSV<T> implements IRepositorio<T> {
	
	protected String caminhoArquivo;
	
	public RepositorioCSV(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}
	
	public abstract String converterParaCSV(T entidade);
	public abstract T converterDeCSV(String linha);

	// metodo padrao para salvar em arquivo csv
	
	@Override
	public void salvar(List<T> entidades) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
			for(T entidadade : entidades) {
				writer.write(converterParaCSV(entidadade));
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erro ao salvar arquivo: " + e.getMessage());
		}
	}
	
	// metodo padrao para carregar um arquivo csv

	@Override
	public List<T> carregar() {
	    List<T> lista = new ArrayList<>();
	    File arquivo = new File(caminhoArquivo);

	    if (!arquivo.exists()) return lista;

	    try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
	        String linha;

	        while ((linha = reader.readLine()) != null) {
	            if (linha.trim().isEmpty()) continue;		 // Ignora linhas em branco
	            T objeto = converterDeCSV(linha);
	            if (objeto != null) lista.add(objeto); 		// Adiciona só se for válido
	        }
	    } catch (IOException e) {
	        System.err.println("Erro ao carregar arquivo: " + e.getMessage());
	    }

	    return lista;
	}


}
