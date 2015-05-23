package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tipo database table.
 * 
 */
@Entity
@NamedQuery(name=Tipo.LISTAR_TUDO, query="SELECT t FROM Tipo t")
public class Tipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String LISTAR_TUDO = "Tipo.findAll";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String descricao;

	// CONSTRUTOR MARCADO COMO DEPRECATED, POIS SEU USO DEVE SER EVITADO.
	@Deprecated
	public Tipo() {
	}
	
	// CONSTRUTOR COM OS ATRIBUTOS INDISPENSAVEIS PARA O OBJETO.
	public Tipo(String descricao){
		this.setDescricao(descricao);
	}

	public int getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}