package interfaces;

import java.io.Serializable;

public interface Dao<T> {
	boolean salvar(T t);
	boolean remover(T t);
	T procurar(Serializable id);
	boolean atualiza(T t);
}
