package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;

import model.CartaoCredito;
import model.Categoria;
import model.Conta;
import model.DespesaReceita;
import model.Tipo;

import org.joda.time.LocalDate;

import table.DespesaReceitaTableModel;
import util.ExibeMensagem;
import view.EditarDespesaView;
import view.EditarReceitaView;
import dao.CategoriaDAO;
import dao.ContaDAO;
import dao.DespesaReceitaDAO;
import dao.TipoDAO;
import factory.DespesaReceitaFactory;

@SuppressWarnings("rawtypes")
public class DespesaReceitaController {

	private DespesaReceitaDAO dao;
	private TipoDAO daoTipo;
	private CategoriaDAO daoCategoria;
	private ContaDAO daoConta;

	private CartaoCreditoController cartaoController;

	private static String TITLE_ADD = "Nova ";
	private static String TITLE_REMOVER = "Remover ";
	private static String TITLE_ATUALIZAR = "Atualizar ";

	private static String NULL_DESCRICAO = "- Informe uma descrição.\n";
	private static String NULL_VENCIMENTO = "- Informe a data de vencimento.\n";
	private static String NULL_MOVIMENTACAO = "- Informe a data de movimentação.\n";
	private static String NULL_VALOR = "- Informe o valor.\n";
	private static String ERRO_VALOR = "- Informe um valor válido.\n";
	private static String SALVAR_SUCESSO = " salva com sucesso!";
	private static String SALVAR_FALHA = "Erro ao salvar ";
	private static String MSG_PARCELA = "Deseja replicar esta ação para todas as parcelas não pagas?";
	private static String REMOVER_CONFIRMACAO = "Tem certeza que deseja remover?";
	private static String REMOVER_SUCESSO = "(s) removida(s) com sucesso!";
	private static String REMOVER_FALHA = "Erro ao remover ";
	private static String ATUALIZAR_SUCESSO = "(s) atualizada(s) com sucesso!";
	private static String ATUALIZAR_FALHA = "Erro ao atualizar ";
	private static String REMOVER_ID_0 = "Não é possível remover. Esta é uma ";

	// MSG = MENSAGEM DO SISTEMA PARA INFORMAR O USUARIO O QUE ESTA ACONTECENDO.
	// TYPE = TIPO DA MENSAGEM (ERRO, INFORMACAO, SUCESSO).
	private String msg;
	private short type;
	private String title;

	private List<DespesaReceita> listaReceita;
	private List<DespesaReceita> listaDespesa;
	private List<Conta> listaConta;
	private List<Categoria> listaCategoriaReceita;
	private List<Categoria> listaCategoriaDespesa;

	private Tipo RECEITA;
	private Tipo DESPESA;

	private String totalReceita;
	private String totalDespesa;
	private DecimalFormat df;

	/*
	 * INICIALIZACAO DAS VARIAVEIS NO CONSTRUTOR
	 */
	public DespesaReceitaController() {
		this.msg = "";
		this.dao = new DespesaReceitaDAO();
		this.daoTipo = new TipoDAO();
		this.daoCategoria = new CategoriaDAO();
		this.daoConta = new ContaDAO();
		this.listaDespesa = null;
		this.listaReceita = null;
		this.listaConta = null;
		this.listaCategoriaReceita = null;
		this.listaCategoriaDespesa = null;
		this.RECEITA = daoTipo.procurar(2);
		this.DESPESA = daoTipo.procurar(1);
		this.totalDespesa = "";
		this.totalReceita = "";
		this.df = new DecimalFormat("#,##0.00");
		this.cartaoController = new CartaoCreditoController();
	}

	/*
	 * RECUPERA TODOS OS REGISTROS DE UM DETERMINADO TIPO, NO BANCO DE DADOS.
	 */
	public List<DespesaReceita> listarTudo(Tipo t) {
		List<DespesaReceita> lista;

		lista = dao.listaTudoPorTipo(t);

		return lista;
	}

	/*
	 * CRIA A TABLE MODEL DE DESPESA OU RECEITA, DE ACORDO COM OS PARAMETROS
	 * INFORMADOS.
	 */
	public DespesaReceitaTableModel buscar(int pago, String descricao,
			int tipo, int mes, int ano) {
		List<DespesaReceita> novaLista = new ArrayList<>();
		descricao = descricao.toLowerCase();

		if (tipo == 1) {
			this.getDespesaTableModel(mes, ano);
			novaLista.addAll(this.listaDespesa);
			this.listaDespesa.clear();

			for (DespesaReceita r : novaLista) {
				// PAGO = 0 > TODOS OS REGISTROS, COM E SEM DATA DE
				// MOVIMENTACAO.
				// PAGO = 1 > SOMENTE REGISTROS SEM DATA DE MOVIMENTACAO.
				// PAGO = 2 > SOMENTE REGISTROS COM DATA DE MOVIMENTACAO.
				if (r.getDescricao().toLowerCase().contains(descricao)
						&& pago == 0)
					this.listaDespesa.add(r);
				else if (r.getDescricao().toLowerCase().contains(descricao)
						&& pago == 1 && r.getDataMovimentacao() == null)
					this.listaDespesa.add(r);
				else if (r.getDescricao().toLowerCase().contains(descricao)
						&& pago == 2 && r.getDataMovimentacao() != null)
					this.listaDespesa.add(r);
			}

			return new DespesaReceitaTableModel(this.listaDespesa);
		} else {
			this.getReceitaTableModel(mes, ano);
			novaLista.addAll(this.listaReceita);
			this.listaReceita.clear();

			for (DespesaReceita r : novaLista) {
				// PAGO = 0 > TODOS OS REGISTROS, COM E SEM DATA DE
				// MOVIMENTACAO.
				// PAGO = 1 > SOMENTE REGISTROS SEM DATA DE MOVIMENTACAO.
				// PAGO = 2 > SOMENTE REGISTROS COM DATA DE MOVIMENTACAO.
				if (r.getDescricao().toLowerCase().contains(descricao)
						&& pago == 0)
					this.listaReceita.add(r);
				else if (r.getDescricao().toLowerCase().contains(descricao)
						&& pago == 1 && r.getDataMovimentacao() == null)
					this.listaReceita.add(r);
				else if (r.getDescricao().toLowerCase().contains(descricao)
						&& pago == 2 && r.getDataMovimentacao() != null)
					this.listaReceita.add(r);
			}

			return new DespesaReceitaTableModel(this.listaReceita);
		}
	}

	/*
	 * CRIA A TABLE MODEL DE RECEITA COM OS PARAMETROS INFORMADOS.
	 */
	public DespesaReceitaTableModel getReceitaTableModel(int mes, int ano) {
		this.listaReceita = listarTudo(RECEITA);
		List<DespesaReceita> novaLista = new ArrayList<>();
		novaLista.addAll(this.listaReceita);
		this.listaReceita.clear();

		for (DespesaReceita r : novaLista) {
			// VERIFICA SE O MES DE VENCIMENTO DA RECEITA E O MESMO
			// MES QUE FOI SELECIONADO, E SE O ANO DA RECEITA E O
			// MESMO QUE O ANO SELECIONADO.
			if (r.getDataVencimento().getMonthOfYear() == mes
					&& r.getDataVencimento().getYear() == ano) {
				this.listaReceita.add(r);
			}
			/*
			 * VERIFICA SE A DATA DE VENCIMENTO DA RECEITA E MENOR QUE A DATA
			 * SELECIONADA SE FOR MENOR E A RECEITA FOR FIXA, ENTAO CRIAMOS A
			 * RECEITA.
			 */
			else if ((r.getDataVencimento().getMonthOfYear() < mes
					&& r.getDataVencimento().getYear() <= ano && r.getFixa())
					|| (r.getDataVencimento().getYear() < ano && r.getFixa())) {

				/*
				 * VERIFICA SE O ULTIMO REGISTRO DESSA RECEITA CONTINUA COMO
				 * FIXO.
				 */
				if (verificaFixa(novaLista, r)) {
					DespesaReceita nova = new DespesaReceita(r.getDescricao(),
							r.getDataVencimento().withMonthOfYear(mes)
									.withYear(ano), r.getValor());
					nova.setCategoriaBean(r.getCategoriaBean());
					nova.setContaBean(r.getContaBean());
					nova.setTipoBean(RECEITA);
					nova.setFixa(true);
					nova.setCartaoCreditoBean(r.getCartaoCreditoBean());
					
					/*
					 * VERIFICAMOS SE O REGISTRO JA FOI ADICIONADO A NOSSA LISTA
					 * QUE IRA ALIMENTAR A TABLE MODEL.
					 */
					if (!this.listaReceita.contains(nova)
							&& !comparaPorDataVencimento(novaLista, nova)) {

						nova.setDataMovimentacao(null);
						nova.setParcelaId(null);

						this.listaReceita.add(nova);
					}
				}
			}
		}
		Collections.sort(this.listaReceita);

		Double pago = 0.0;
		Double naoPago = 0.0;

		for (DespesaReceita r : this.listaReceita) {
			if (r.getDataMovimentacao() == null)
				naoPago = naoPago + r.getValor();
			else
				pago = pago + r.getValor();
		}

		this.totalReceita = "Pago: R$" + df.format(pago) + " | Não pago: R$"
				+ df.format(naoPago) + " | Total: R$"
				+ df.format(pago + naoPago);

		return new DespesaReceitaTableModel(this.listaReceita);
	}
	
	/*
	 * CRIA A TABLE MODEL DE DESPESA COM OS PARAMETROS INFORMADOS.
	 */
	public DespesaReceitaTableModel getDespesaTableModel(int mes, int ano) {	
		return new DespesaReceitaTableModel(getListaDespesa(mes, ano));
	}
	
	/*
	 * CRIA A LISTA DE DESPESA COM OS PARAMETROS INFORMADOS.
	 */
	public List<DespesaReceita> getListaDespesa(int mes, int ano) {
		this.listaDespesa = listarTudo(DESPESA);
		List<DespesaReceita> novaLista = new ArrayList<>();
		novaLista.addAll(this.listaDespesa);
		this.listaDespesa.clear();

		for (DespesaReceita d : novaLista) {
			// VERIFICA SE O MES DE VENCIMENTO DA DESPESA E O MESMO
			// MES QUE FOI SELECIONADO, E SE O ANO DA DESPESA E O
			// MESMO QUE O ANO SELECIONADO.
			if (d.getDataVencimento().getMonthOfYear() == mes
					&& d.getDataVencimento().getYear() == ano) {
				this.listaDespesa.add(d);
			}
			/*
			 * VERIFICA SE A DATA DE VENCIMENTO DA DESPESA E MENOR QUE A DATA
			 * SELECIONADA, SE FOR MENOR E A DESPESA FOR FIXA, ENTAO CRIAMOS A
			 * DESPESA.
			 */
			else if ((d.getDataVencimento().getMonthOfYear() < mes
					&& d.getDataVencimento().getYear() <= ano && d.getFixa())
					|| (d.getDataVencimento().getYear() < ano && d.getFixa())) {

				/*
				 * VERIFICA SE O ULTIMO REGISTRO DESSA DESPESA CONTINUA COMO
				 * FIXO.
				 */
				if (verificaFixa(novaLista, d)) {
					DespesaReceita nova = new DespesaReceita(d.getDescricao(),
							d.getDataVencimento().withMonthOfYear(mes)
									.withYear(ano), d.getValor());
					nova.setCategoriaBean(d.getCategoriaBean());
					nova.setContaBean(d.getContaBean());
					nova.setTipoBean(DESPESA);
					nova.setCartaoCreditoBean(d.getCartaoCreditoBean());
					nova.setFixa(true);

					/*
					 * VERIFICAMOS SE O REGISTRO JA FOI ADICIONADO A NOSSA LISTA
					 * QUE IRA ALIMENTAR A TABLE MODEL.
					 */
					if (!this.listaDespesa.contains(nova)
							&& !comparaPorDataVencimento(novaLista, nova)) {

						nova.setDataMovimentacao(null);
						nova.setParcelaId(null);

						this.listaDespesa.add(nova);
					}
				}
			}
		}
		Collections.sort(this.listaDespesa);

		Double pago = 0.0;
		Double naoPago = 0.0;

		for (DespesaReceita d : this.listaDespesa) {
			if (d.getDataMovimentacao() == null)
				naoPago = naoPago + d.getValor();
			else
				pago = pago + d.getValor();
		}

		this.totalDespesa = "Pago: R$" + df.format(pago) + " | Não pago: R$"
				+ df.format(naoPago) + " | Total: R$"
				+ df.format(pago + naoPago);

		return this.listaDespesa;
	}

	private boolean verificaFixa(List<DespesaReceita> lista, DespesaReceita dr) {
		ArrayList<DespesaReceita> listaFixa = new ArrayList<>();

		for (DespesaReceita t : lista) {
			if (t.equals(dr)) {
				listaFixa.add(t);
			}
		}

		Collections.sort(listaFixa);

		return listaFixa.get(listaFixa.size() - 1).getFixa();
	}

	/*
	 * VERIFICA SE UMA DESPESA/RECEITA E IGUAL CONSIDERANDO A DATA DE VENCIMENTO
	 */
	private boolean comparaPorDataVencimento(List<DespesaReceita> lista,
			DespesaReceita dr) {
		for (DespesaReceita deRe : lista) {
			if (deRe.equalsDateVencimento(dr))
				return true;
		}

		return false;
	}

	/*
	 * RETORNA O TOTAL DOS VALORES DAS RECEITA
	 */
	public String getTotalReceita() {
		return this.totalReceita.toString();
	}

	/*
	 * RETORNA O TOTAL DOS VALORES DAS DESPESAS
	 */
	public String getTotalDespesa() {
		return this.totalDespesa.toString();
	}

	/*
	 * SALVA UMA DESPESA OU RECEITA.
	 */
	public boolean salvar(String descricao, Date dataVencimento,
			Date dataMovimentacao, String valor, boolean fixa,
			Object categoria, int tipoId, Object conta, Object cartaoCredito,
			String parcelas, boolean pago) {
		Tipo tipo;
		if (tipoId == 1)
			tipo = DESPESA;
		else
			tipo = RECEITA;

		this.title = TITLE_ADD + tipo.getDescricao();
		this.type = 0;
		boolean flag = true;
		
		if (validaCampos(descricao, dataVencimento, dataMovimentacao, valor,
				pago, cartaoCredito)) {
			LocalDate localDateMovimentacao = null;
			LocalDate localDateVencimento = null;
			this.title = tipo.getDescricao();

			if (parcelas.equals(""))
				parcelas = "1";
			if (dataMovimentacao != null)
				localDateMovimentacao = new LocalDate(dataMovimentacao);
			if (cartaoCredito != null)
				if (((CartaoCredito) cartaoCredito).getId() == 0)
					cartaoCredito = null;
			if(dataVencimento != null)
				localDateVencimento = new LocalDate(dataVencimento);
			
			
			ArrayList<String> erros = DespesaReceitaFactory.novaDespesaReceita(
					descricao, localDateVencimento, localDateMovimentacao,
					Double.parseDouble(valor.replace(",", ".")), fixa,
					(Categoria) categoria, tipo, (Conta) conta,
					(CartaoCredito) cartaoCredito, Integer.parseInt(parcelas));

			if (!erros.isEmpty()) {
				this.type = 0;
				this.msg = SALVAR_FALHA + tipo.getDescricao().toLowerCase()
						+ ":\n\n";
				flag = false;
				for (String s : erros) {
					this.msg = this.msg + s + "\n";
				}
			} else {
				this.type = 1;
				this.msg = tipo.getDescricao() + SALVAR_SUCESSO;
			}

			ExibeMensagem.showMessage(this.msg, this.title, this.type);
		} else {
			flag = false;
		}
		return flag;
	}

	/*
	 * VERIFICA SE EXISTEM CAMPOS OBRIGATORIOS QUE NAO FORAM PREENCHIDOS.
	 */
	private boolean validaCampos(String descricao, Date dataVencimento,
			Date dataMovimentacao, String valor, boolean pago,
			Object cartaoCredito) {
		boolean retorno = true;
		this.msg = "Você precisa fornecer as seguintes informações:\n\n";
		this.title = "Erro";
		// DESCRICAO.REPLACEALL().ISEMPTY() = REMOVE TODOS OS ESPACOS EM
		// BRANCO E CONFERE SE FICOU VAZIO.
		if (descricao.isEmpty() || descricao.replaceAll("\\s+", "").isEmpty()) {
			this.msg = msg + DespesaReceitaController.NULL_DESCRICAO;
			retorno = false;
		}
		if (dataVencimento == null && cartaoCredito == null) {
			this.msg = msg + DespesaReceitaController.NULL_VENCIMENTO;
			retorno = false;
		}
		if (dataMovimentacao == null && pago) {
			this.msg = msg + DespesaReceitaController.NULL_MOVIMENTACAO;
			retorno = false;
		}
		if (valor.isEmpty()) {
			this.msg = msg + DespesaReceitaController.NULL_VALOR;
			retorno = false;
		} else {
			try {
				Double.parseDouble(valor.replace(",", "."));
			} catch (Exception e) {
				this.msg = msg + DespesaReceitaController.ERRO_VALOR;
				retorno = false;
			}
		}

		if (!retorno)
			ExibeMensagem.showMessage(this.msg, this.title, this.type);

		return retorno;
	}

	/*
	 * REMOVE UMA DESPESA OU RECEITA.
	 */
	public boolean remover(Object id, int tipo) {
		boolean flag = false;
		Tipo t;

		if (tipo == 1)
			t = DESPESA;
		else
			t = RECEITA;

		if ((int) id == 0) {
			this.title = TITLE_REMOVER;
			this.msg = REMOVER_ID_0 + t.getDescricao().toLowerCase()
					+ " criada automaticamente.";
			this.type = 2;
			ExibeMensagem.showMessage(this.msg, this.title, this.type);
		}

		else {
			DespesaReceita dr = dao.procurar((int) id);
			int num = 1;

			this.title = TITLE_REMOVER + t.getDescricao();
			this.msg = REMOVER_CONFIRMACAO;
			this.type = 0;

			if (ExibeMensagem.showQuestionMessage(this.msg, this.title,
					this.type) == 0) {
				flag = true;

				this.title = TITLE_REMOVER + t.getDescricao();
				this.msg = MSG_PARCELA;

				// NESSE CASO, INDICA QUE TEREMOS SOMENTE A OPCAO DE RESPONDER
				// SIM OU NAO NO CONFIRMDIALOG.
				this.type = 0;

				if (dr.getParcelaId() != null
						&& dr.getDataMovimentacao() == null
						&& ExibeMensagem.showQuestionMessage(this.msg,
								this.title, this.type) == 0) {

					num = dao.removerPorParcelaId(dr.getParcelaId());

					// CASO TENHA REMOVIDO, O SISTEMA EXIBE UMA MENSAGEM DE
					// SUCESSO
					if (num <= 0) {
						flag = false;
					}
				} else
					// CASO TENHA REMOVIDO, O SISTEMA EXIBE UMA MENSAGEM DE
					// SUCESSO
					flag = dao.remover(dr);

				if (flag) {
					this.msg = num + " "
							+ dr.getTipoBean().getDescricao().toLowerCase()
							+ REMOVER_SUCESSO;
					this.title = TITLE_REMOVER;
					this.type = 1;
				} else {
					this.msg = REMOVER_FALHA
							+ dr.getTipoBean().getDescricao().toLowerCase()
							+ ".";
					this.title = TITLE_REMOVER;
					this.type = 0;
				}

				ExibeMensagem.showMessage(this.msg, this.title, this.type);
			}
		}

		return flag;
	}

	/*
	 * ATUALIZA UMA DESPESA OU RECEITA.
	 */
	public boolean atualizar(String descricao, Date dataVencimento,
			Date dataMovimentacao, String valor, boolean fixa,
			Object categoria, Object conta, Object cartaoCredito, boolean pago,
			int id, int tipo) {
		DespesaReceita dr;
		this.type = 0;
		boolean flag = true;

		if (id == 0) {
			flag = salvar(descricao, dataVencimento, dataMovimentacao, valor,
					fixa, categoria, tipo, conta, cartaoCredito, "1", pago);
		}

		else if (validaCampos(descricao, dataVencimento, dataMovimentacao,
				valor, pago, cartaoCredito)) {

			dr = dao.procurar(id);

			dr.setDescricao(descricao);
			dr.setDataVencimento(new LocalDate(dataVencimento));
			dr.setValor(Double.parseDouble(valor.replace(",", ".")));
			dr.setFixa(fixa);
			dr.setCategoriaBean((Categoria) categoria);
			dr.setContaBean((Conta) conta);

			/*
			 * CONFERE SE O TIPO E DE DESPESA OU DE RECEITA. SE FOR RECEITA,
			 * IGNORA O CARTAO DE CREDITO.
			 */
			if (tipo == 1) {
				if (((CartaoCredito) cartaoCredito).getId() <= 0) {
					dr.setCartaoCreditoBean(null);
				} else
					dr.setCartaoCreditoBean((CartaoCredito) cartaoCredito);
			}

			LocalDate movimentacao;

			if (dataMovimentacao != null && pago) {
				movimentacao = new LocalDate(dataMovimentacao);
				dr.setDataMovimentacao(movimentacao);
			} else {
				dr.setDataMovimentacao(null);
			}

			this.title = TITLE_ATUALIZAR + dr.getTipoBean().getDescricao();
			this.msg = MSG_PARCELA;

			// NESSE CASO, INDICA QUE TEREMOS SOMENTE A OPCAO DE RESPONDER
			// SIM OU NAO NO CONFIRMDIALOG.
			this.type = 0;

			// VERIFICA SE EXISTEM PARCELAS DESTA DESPESA/RECEITA
			// E CASO EXISTA, PERGUNTA SE QUER PROPAGAR A ALTERACAO.
			if (dr.getParcelaId() != null
					&& dao.procurar(dr.getId()).getDataMovimentacao() == null
					&& ExibeMensagem.showQuestionMessage(this.msg, this.title,
							this.type) == 0)

				flag = dao.atualizarPorParcelaId(dr);

			else {
				// CASO TENHA ATUALIZADO, O SISTEMA EXIBE UMA MENSAGEM DE
				// SUCESSO
				flag = dao.atualizar(dr);
			}

			if (flag) {
				this.msg = dr.getTipoBean().getDescricao() + ATUALIZAR_SUCESSO;
				this.title = TITLE_ATUALIZAR;
				this.type = 1;
			} else {
				this.msg = ATUALIZAR_FALHA
						+ dr.getTipoBean().getDescricao().toLowerCase() + ".";
				this.title = TITLE_ATUALIZAR;
				this.type = 0;
			}

			ExibeMensagem.showMessage(this.msg, this.title, this.type);
		} else {
			flag = false;
		}
		return flag;
	}

	/*
	 * ATUALIZA UMA DESPESA/RECEITA SEM REALIZAR VERIFICACOES.
	 */
	public boolean atualizarSemVerificao(DespesaReceita dr) {
		return this.dao.atualizar(dr);
	}

	/*
	 * CRIA A TELA DE EDICAO DE RECEITA
	 */
	public EditarReceitaView abrirEditarReceitaView(int linha) {
		EditarReceitaView editarReceitaView = new EditarReceitaView();

		DespesaReceita dr = listaReceita.get(linha);

		JComboBox comboConta = getComboConta();
		comboConta.setSelectedItem(dr.getContaBean());
		JComboBox comboCategoria = getComboCategoriaReceita();
		comboCategoria.setSelectedItem(dr.getCategoriaBean());

		editarReceitaView.setCod(dr.getId());
		editarReceitaView.setDescricao(dr.getDescricao());
		editarReceitaView.setConta(comboConta);
		editarReceitaView.setCategoria(comboCategoria);
		editarReceitaView.setValor(dr.getValor());
		editarReceitaView.setReceitaFixa(dr.getFixa());
		editarReceitaView.setDataVencimento(dr.getDataVencimento());
		if (dr.getDataMovimentacao() != null) {
			editarReceitaView.setPago(true);
			editarReceitaView.setDataPagamento(dr.getDataMovimentacao());
		} else {
			editarReceitaView.setPago(false);
		}

		return editarReceitaView;
	}

	/*
	 * CRIA A TELA DE EDICAO DE DESPESA
	 */
	@SuppressWarnings("unchecked")
	public EditarDespesaView abrirEditarDespesaView(int linha) {
		EditarDespesaView editarDespesaView = new EditarDespesaView();

		DespesaReceita dr = listaDespesa.get(linha);
		if (dr.getId() != 0)
			dr = dao.procurar(dr.getId());

		JComboBox comboConta = getComboConta();
		comboConta.setSelectedItem(dr.getContaBean());

		JComboBox comboCategoria = getComboCategoriaDespesa();
		comboCategoria.setSelectedItem(dr.getCategoriaBean());

		JComboBox comboCartao = getComboCartaoCredito(true);
		if (dr.getCartaoCreditoBean() != null) {
			if (dr.getCartaoCreditoBean().getAtivo())
				comboCartao.setSelectedItem(dr.getCartaoCreditoBean());
			else {
				comboCartao.addItem(dr.getCartaoCreditoBean());
				comboCartao.setSelectedIndex(comboCartao.getItemCount() - 1);
			}
		} else
			comboCartao.setSelectedIndex(0);

		editarDespesaView.setCod(dr.getId());
		editarDespesaView.setDescricao(dr.getDescricao());
		editarDespesaView.setConta(comboConta);
		editarDespesaView.setCategoria(comboCategoria);
		editarDespesaView.setCartao(comboCartao);
		editarDespesaView.setValor(dr.getValor());
		editarDespesaView.setDespesaFixa(dr.getFixa());
		editarDespesaView.setDataVencimento(dr.getDataVencimento());
		if (dr.getDataMovimentacao() != null) {
			editarDespesaView.setPago(true);
			editarDespesaView.setDataPagamento(dr.getDataMovimentacao());
		} else {
			editarDespesaView.setPago(false);
		}

		return editarDespesaView;
	}

	/*
	 * CRIA O JCOMBOBOX DE CONTA
	 */
	public JComboBox<Conta> getComboConta() {
		JComboBox<Conta> combo = new JComboBox<Conta>();

		if (listaConta == null)
			listaConta = daoConta.listaTudo();

		for (Conta c : listaConta) {
			combo.addItem(c);
		}

		return combo;
	}

	/*
	 * CRIA O JCOMBOBOX DE CATEGORIA DE DESPESA
	 */
	public JComboBox<Categoria> getComboCategoriaDespesa() {
		JComboBox<Categoria> combo = new JComboBox<Categoria>();

		if (listaCategoriaDespesa == null)
			listaCategoriaDespesa = daoCategoria.listaTudoPorTipo(daoTipo
					.procurar(1));

		for (Categoria c : listaCategoriaDespesa) {
			combo.addItem(c);
		}

		return combo;
	}

	/*
	 * CRIA O JCOMBOBOX DE CATEGORIA DE RECEITA
	 */
	public JComboBox<Categoria> getComboCategoriaReceita() {
		JComboBox<Categoria> combo = new JComboBox<Categoria>();

		if (listaCategoriaReceita == null)
			listaCategoriaReceita = daoCategoria.listaTudoPorTipo(daoTipo
					.procurar(2));

		for (Categoria c : listaCategoriaReceita) {
			combo.addItem(c);
		}

		return combo;
	}

	/*
	 * UTILIZA O CONTROLLER DE CARTAO DE CREDITO PARA RECUPERAR UM COMBOBOX DE
	 * CARTAO DE CREDITO.
	 */
	public JComboBox<CartaoCredito> getComboCartaoCredito(boolean ativo) {
		return cartaoController.getComboCartaoCredito(ativo);
	}

}