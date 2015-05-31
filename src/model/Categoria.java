package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the categoria database table.
 * 
 */
@Entity
@NamedQuery(name=Categoria.LISTAR_TUDO, query="SELECT c FROM Categoria c")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String LISTAR_TUDO = "Categoria.findAll";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String descricao;

	//bi-directional many-to-one association to Tipo
	@ManyToOne
	@JoinColumn(name="tipo")
	private Tipo tipoBean;

	// CONSTRUTOR MARCADO COMO DEPRECATED, POIS SEU USO DEVE SER EVITADO.
	@Deprecated
	public Categoria() {
	}
	
	// CONSTRUTOR COM OS ATRIBUTOS INDISPENSAVEIS PARA O OBJETO.
	public Categoria(String descricao, Tipo tipo){
		this.setDescricao(descricao);
		this.setTipoBean(tipo);
	}
	
	// GET
	public int getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Tipo getTipoBean() {
		return this.tipoBean;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setTipoBean(Tipo tipoBean) {
		this.tipoBean = tipoBean;
	}
	
	// NAO ALTERAR ESTE METODO.
	// ELE ESTA SENDO USADO PARA MOSTRAR OS NOMES
	// DOS ITENS NO JCOMBOBOX
	@Override
	public String toString(){
		return this.descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id != other.id)
			return false;
		return true;
	}
}