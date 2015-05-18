package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the parcela_seq database table.
 * 
 */
@Entity
@Table(name="parcela_seq")
@NamedQuery(name="ParcelaSeq.findAll", query="SELECT p FROM ParcelaSeq p")
public class ParcelaSeq implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int sequencia;

	public ParcelaSeq() {
	}
	
	public int getSequencia(){
		return this.sequencia;
	}
	
	public void nextSequencia(){
		this.sequencia += 1;
	}
}