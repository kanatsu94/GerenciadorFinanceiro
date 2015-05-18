package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the cartao_credito database table.
 * 
 */
@Entity
@Table(name = "cartao_credito")
@NamedQuery(name = "CartaoCredito.findAll", query = "SELECT c FROM CartaoCredito c")
public class CartaoCredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descricao;

	// DIA DO MES EM QUE A FATURA VENCE
	private short vencimento;
	
	// QUANTIDADE DE DIAS ANTES DO VENCIMENTO DA FATURA, DETERMINA O FECHAMENTO DA FATURA.
	// NAO E A DATA DE FECHAMENTO!
	// E A QUANTIDADE DE DIAS ANTES DO VENCIMENTO. A DATA DE FECHAMENTO E CALCULADA.
	@Column(name="dias_fechamento")
	private short diasFechamento;

	// bi-directional many-to-one association to DespesaReceita
	@OneToMany(mappedBy = "cartaoCreditoBean", fetch = FetchType.EAGER)
	private List<DespesaReceita> despesaReceitas;

	// CONSTRUTOR MARCADO COMO DEPRECATED, POIS SEU USO DEVE SER EVITADO.
	@Deprecated
	public CartaoCredito() {
	}

	// CONSTRUTOR COM OS ATRIBUTOS INDISPENSAVEIS PARA O OBJETO.
	public CartaoCredito(String descricao, short vencimento) {
		this.setDescricao(descricao);
		this.setVencimento(vencimento);
	}

	public int getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public short getVencimento() {
		return this.vencimento;
	}
	
	public short getDiasFechamento(){
		return this.diasFechamento;
	}

	public List<DespesaReceita> getDespesaReceitas() {
		return this.despesaReceitas;
	}

	// SET
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setVencimento(short vencimento) {
		this.vencimento = vencimento;

		for(DespesaReceita dr : this.despesaReceitas){
			dr.atualizaVencimento(vencimento);
		}
	}

	public void setDespesaReceitas(List<DespesaReceita> despesaReceitas) {
		this.despesaReceitas = despesaReceitas;
	}

	public DespesaReceita addDespesaReceita(DespesaReceita despesaReceita) {
		getDespesaReceitas().add(despesaReceita);
		despesaReceita.setCartaoCreditoBean(this);

		return despesaReceita;
	}
	
	public void setDiasFechamento(short diasFechamento){
		this.diasFechamento = diasFechamento;
	}

	public DespesaReceita removeDespesaReceita(DespesaReceita despesaReceita) {
		getDespesaReceitas().remove(despesaReceita);
		despesaReceita.setCartaoCreditoBean(null);

		return despesaReceita;
	}

}