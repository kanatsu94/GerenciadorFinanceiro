package interfaces;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {
	boolean salvar(T t);
	boolean remover(T t);
	T procurar(Serializable id);
	boolean atualizar(T t);
	List<T> listaTudo();
	List<T> consultaDinamica(T t);
}
