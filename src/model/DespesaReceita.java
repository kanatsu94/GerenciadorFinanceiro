package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import dao.DespesaReceitaDAO;


/**
 * The persistent class for the despesa_receita database table.
 * 
 */
@Entity
@Table(name="despesa_receita")
@NamedQueries({
		@NamedQuery(name=DespesaReceita.LISTAR_TUDO, query="SELECT d FROM DespesaReceita d"),
		@NamedQuery(name=DespesaReceita.REMOVER_POR_PARCELA_ID, query="DELETE FROM DespesaReceita d WHERE d.parcelaId = :parcela_id AND d.dataMovimentacao = null"),
		@NamedQuery(name=DespesaReceita.LISTAR_POR_PARCELA_ID, query="SELECT d FROM DespesaReceita d WHERE d.parcelaId = :parcela_id AND d.dataMovimentacao = null")
})
public class DespesaReceita implements Serializable, Comparable<DespesaReceita> {
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String REMOVER_POR_PARCELA_ID = "DespesaReceita.removerPorParcelaId";
	@Transient
	public static final String LISTAR_TUDO = "DespesaReceita.findAll";
	@Transient
	public static final String LISTAR_POR_PARCELA_ID = "DespesaReceita.findByParcelaId";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name="data_movimentacao")
	private LocalDate dataMovimentacao;

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name="data_vencimento")
	private LocalDate dataVencimento;

	private String descricao;

	private boolean fixa;

	private Double valor;
	
	// O ID DE PARCELA IDENTIFICA AS DESPESAS/RECEITAS QUE
	// REPETEM.
	@Column(name="parcela_id")
	private Integer parcelaId;

	//bi-directional many-to-one association to CartaoCredito
	// 
	@ManyToOne
	@JoinColumn(name="cartao_credito")
	private CartaoCredito cartaoCreditoBean;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="categoria")
	private Categoria categoriaBean;

	//bi-directional many-to-one association to Conta
	@ManyToOne
	@JoinColumn(name="conta")
	private Conta contaBean;

	//bi-directional many-to-one association to Tipo
	@ManyToOne
	@JoinColumn(name="tipo")
	private Tipo tipoBean;

	// CONSTRUTOR MARCADO COMO DEPRECATED, POIS SEU USO DEVE SER EVITADO.
	@Deprecated
	public DespesaReceita() {
	}
	
	// CONSTRUTOR COM OS ATRIBUTOS INDISPENSAVEIS PARA O OBJETO.
	public DespesaReceita(String descricao, LocalDate dataVencimento, Double valor){
		this.setDescricao(descricao);
		this.setDataVencimento(dataVencimento);
		this.setValor(valor);
	}
	
	// GET E SET
	public int getId() {
		return this.id;
	}

	public LocalDate getDataMovimentacao() {
		return this.dataMovimentacao;
	}

	public void setDataMovimentacao(LocalDate dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	public LocalDate getDataVencimento() {
		return this.dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean getFixa() {
		return this.fixa;
	}

	public void setFixa(boolean fixa) {
		this.fixa = fixa;
	}

	public Double getValor() {
		return this.valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public CartaoCredito getCartaoCreditoBean() {
		return this.cartaoCreditoBean;
	}

	public void setCartaoCreditoBean(CartaoCredito cartaoCreditoBean) {
		this.cartaoCreditoBean = cartaoCreditoBean;
	}

	public Categoria getCategoriaBean() {
		return this.categoriaBean;
	}

	public void setCategoriaBean(Categoria categoriaBean) {
		this.categoriaBean = categoriaBean;
	}

	public Conta getContaBean() {
		return this.contaBean;
	}

	public void setContaBean(Conta contaBean) {
		this.contaBean = contaBean;
	}

	public Tipo getTipoBean() {
		return this.tipoBean;
	}

	public void setTipoBean(Tipo tipoBean) {
		this.tipoBean = tipoBean;
	}
	public Integer getParcelaId(){
		return this.parcelaId;
	}
	public void setParcelaId(Integer parcelaId){
		this.parcelaId = parcelaId;
	}
	
	public void atualizaVencimento(short dataVencimento){
		DespesaReceitaDAO dao = new DespesaReceitaDAO();
		
		this.dataVencimento = this.dataVencimento.withDayOfMonth(dataVencimento);
		dao.atualizar(this);
	}
	
	// UTILIZA O EQUALS PARA VERIFICAR SE E IGUAL,
	// DEPOIS COMPARA AS DATAS DE VENCIMENTO.
	public boolean equalsDateVencimento(Object obj){
		if(this.equals(obj)){
			return ((DespesaReceita)obj).getDataVencimento().equals(this.getDataVencimento());
		}
		return false;
	}
	
	@Override
	public String toString(){
		return this.descricao;
	}
	
	@Override
	public int compareTo(DespesaReceita outra) {
	    if (this.dataVencimento.isBefore(outra.dataVencimento)) {
	      return -1;
	    }

	    if (this.dataVencimento.isAfter(outra.dataVencimento)) {
	      return 1;
	    }

	    return 0;
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cartaoCreditoBean == null) ? 0 : cartaoCreditoBean
						.hashCode());
		result = prime * result
				+ ((categoriaBean == null) ? 0 : categoriaBean.hashCode());
		result = prime * result
				+ ((contaBean == null) ? 0 : contaBean.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((tipoBean == null) ? 0 : tipoBean.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		DespesaReceita other = (DespesaReceita) obj;
		if (cartaoCreditoBean == null) {
			if (other.cartaoCreditoBean != null)
				return false;
		} else if (!cartaoCreditoBean.equals(other.cartaoCreditoBean))
			return false;
		if (categoriaBean == null) {
			if (other.categoriaBean != null)
				return false;
		} else if (!categoriaBean.equals(other.categoriaBean))
			return false;
		if (contaBean == null) {
			if (other.contaBean != null)
				return false;
		} else if (!contaBean.equals(other.contaBean))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (tipoBean == null) {
			if (other.tipoBean != null)
				return false;
		} else if (!tipoBean.equals(other.tipoBean))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}