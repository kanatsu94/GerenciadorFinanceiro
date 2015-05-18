package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the categoria database table.
 * 
 */
@Entity
@NamedQuery(name="Categoria.findAll", query="SELECT c FROM Categoria c")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

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

}