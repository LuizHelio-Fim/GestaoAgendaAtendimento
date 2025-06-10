package repository;

import java.util.List;

public interface IRepositorio<T> {
	void salvar(List<T> entidades);
	List<T> carregar();
}
