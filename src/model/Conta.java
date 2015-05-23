package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the conta database table.
 * 
 */
@Entity
@NamedQuery(name = Conta.LISTAR_TUDO, query = "SELECT c FROM Conta c")
public class Conta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String LISTAR_TUDO = "Conta.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descricao;

	private Double saldo;

	// bi-directional many-to-one association to DespesaReceita
	@OneToMany(mappedBy = "contaBean")
	private List<DespesaReceita> despesaReceitas;

	// CONSTRUTOR VAZIO PARA USO DO HIBERNATE. ANOTADO COMO DEPRECATED PARA
	// EVITAR SEU USO.
	@Deprecated
	public Conta() {
	}

	// CONSTRUTOR COM OS ATRIBUTOS QUE SAO INDISPENSAVEIS PARA O OBJETO.
	public Conta(String descricao, Double saldo) {
		this.setDescricao(descricao);
		this.saldo = saldo;
	}

	// GET
	public int getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Double getSaldo() {
		return this.saldo;
	}

	public List<DespesaReceita> getDespesaReceitas() {
		return this.despesaReceitas;
	}

	// SET
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	// REGRA PARA ALTERAR O VALOR DO SALDO.
	public void movimentacao(Double valor) {
		this.saldo += valor;
	}

	public DespesaReceita addDespesaReceita(DespesaReceita despesaReceita) {
		getDespesaReceitas().add(despesaReceita);
		despesaReceita.setContaBean(this);

		return despesaReceita;
	}

	public DespesaReceita removeDespesaReceita(DespesaReceita despesaReceita) {
		getDespesaReceitas().remove(despesaReceita);
		despesaReceita.setContaBean(null);

		return despesaReceita;
	}

}