package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.joda.time.LocalDate;

/**
 * The persistent class for the cartao_credito database table.
 * 
 */
@Entity
@Table(name = "cartao_credito")
@NamedQueries({
	@NamedQuery(name = CartaoCredito.LISTAR_TUDO, query = "SELECT c FROM CartaoCredito c"),
	@NamedQuery(name = CartaoCredito.LISTAR_ATIVOS, query="SELECT c FROM CartaoCredito c WHERE c.ativo = true")
})
public class CartaoCredito implements Serializable, Comparable<CartaoCredito> {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String LISTAR_TUDO = "CartaoCredito.findAll";
	@Transient
	public static final String LISTAR_ATIVOS = "CartaoCredito.findByAtivo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descricao;
	
	private boolean ativo;

	// DIA DO MES EM QUE A FATURA VENCE
	private short vencimento;

	// QUANTIDADE DE DIAS ANTES DO VENCIMENTO DA FATURA, DETERMINA O FECHAMENTO
	// DA FATURA.
	// NAO E A DATA DE FECHAMENTO!
	// E A QUANTIDADE DE DIAS ANTES DO VENCIMENTO. A DATA DE FECHAMENTO E
	// CALCULADA.
	@Column(name = "dias_fechamento")
	private short diasFechamento;

	// bi-directional many-to-one association to DespesaReceita
	@OneToMany(mappedBy = "cartaoCreditoBean", fetch = FetchType.EAGER)
	private List<DespesaReceita> despesaReceitas;

	// CONSTRUTOR MARCADO COMO DEPRECATED, POIS SEU USO DEVE SER EVITADO.
	@Deprecated
	public CartaoCredito() {
	}

	// CONSTRUTOR COM OS ATRIBUTOS INDISPENSAVEIS PARA O OBJETO.
	public CartaoCredito(String descricao, short vencimento,
			short diasFechamento, boolean ativo) {
		this.setDescricao(descricao);
		this.setVencimento(vencimento);
		this.setDiasFechamento(diasFechamento);
		this.ativo = ativo;
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

	public short getDiasFechamento() {
		return this.diasFechamento;
	}
	
	public boolean getAtivo(){
		return this.ativo;
	}

	public List<DespesaReceita> getDespesaReceitas() {
		return this.despesaReceitas;
	}

	// SET
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setAtivo(boolean ativo){
		this.ativo = ativo;
	}

	public void setVencimento(short vencimento) {
		this.vencimento = vencimento;
		if (this.despesaReceitas != null)
			for (DespesaReceita dr : this.despesaReceitas) {
				/*
				 * AO TROCAR A DATA DE VENCIMENTO DO CARTAO
				 * VERIFICAMOS AS DESPESAS NAO PAGAS RELACIONADAS
				 * A ELE, E SETAMOS A NOVA DATA DE VALIDADE.
				 */
				if(dr.getDataMovimentacao() == null)
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

	public void setDiasFechamento(short diasFechamento) {
		this.diasFechamento = diasFechamento;
	}

	public DespesaReceita removeDespesaReceita(DespesaReceita despesaReceita) {
		getDespesaReceitas().remove(despesaReceita);
		despesaReceita.setCartaoCreditoBean(null);

		return despesaReceita;
	}

	// DESCOBRE A DATA DE FECHAMENTO DA FATURA
	public LocalDate getDataFechamento() {
		LocalDate dataFechamento = new LocalDate();

		for (int i = 0, j = 0; j < 1; i++) {
			try {
				LocalDate dataCartaoVencimento = new LocalDate()
						.withDayOfMonth(this.getVencimento() - i);
				j = 1;

				dataFechamento = dataCartaoVencimento.plusMonths(1)
						.minusDays(this.diasFechamento);
			} catch (Exception e) {
				j = 0;
			}
		}

		return dataFechamento;
	}

	/*
	 * VERIFICA SE A FATURA PODE SER EFETIVADA.
	 */
	public boolean podeEfetivar(int mes, int ano) {
		LocalDate hoje = new LocalDate();
		LocalDate dataFechamento = getDataFechamento();

		if (mes > hoje.getMonthOfYear() && ano >= hoje.getYear())
			return false;
		else {
			if (mes == hoje.getMonthOfYear() && ano == hoje.getYear()) {
				if (hoje.isAfter(dataFechamento))
					return true;
			} else if(mes < hoje.getMonthOfYear() && ano <= hoje.getYear()
					|| mes > hoje.getMonthOfYear() && ano < hoje.getYear())
				return true;
		}
		
		return false;
	}

	// NAO ALTERAR ESTE METODO.
	// ELE ESTA SENDO USADO PARA MOSTRAR OS NOMES
	// DOS ITENS NO JCOMBOBOX
	@Override
	public String toString() {
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
		CartaoCredito other = (CartaoCredito) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(CartaoCredito outra) {
		return this.descricao.compareTo(outra.getDescricao());
	  }
}