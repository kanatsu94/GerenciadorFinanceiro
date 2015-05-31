package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.CartaoCredito;
import model.Categoria;
import model.Conta;
import model.DespesaReceita;
import model.Tipo;

import org.joda.time.LocalDate;

import table.DespesaReceitaTableModel;
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
	}

	// RECUPERA TODOS OS REGISTROS DE DESPESA/RECEITA DO
	// BANCO DE DADOS E DEVOLVE UMA LIST.
	public List<DespesaReceita> listarTudo(Tipo t) {
		List<DespesaReceita> lista;

		lista = dao.listaTudoPorTipo(t);

		return lista;
	}

	// BUSCA TODAS AS DESPESAS/RECEITAS POR DESCRICAO.
	@SuppressWarnings("deprecation")
	public List<DespesaReceita> buscaDescricao(String descricao, Tipo tipo) {
		List<DespesaReceita> lista;

		DespesaReceita dr = new DespesaReceita();
		dr.setDescricao(descricao);
		dr.setTipoBean(tipo);
		lista = dao.consultaDinamica(dr);

		return lista;
	}

	@SuppressWarnings("deprecation")
	public DespesaReceitaTableModel buscar(int pago, String descricao, int tipo) {
		DespesaReceita dr = new DespesaReceita();

		dr.setDescricao(descricao);

		if (tipo == 1)
			dr.setTipoBean(DESPESA);
		else
			dr.setTipoBean(RECEITA);

		// DATA NULL = BUSCA REGISTROS COM E SEM DATA DE MOVIMENTACAO
		// DATA ANTERIOR A ATUAL = BUSCA REGISTROS SEM DATA DE MOVIMENTACAO
		// DATA ATUAL = BUSCA REGISTROS COM DATA DE MOVIMENTACAO
		if (pago == 0)
			dr.setDataMovimentacao(null);
		else if (pago == 1)
			dr.setDataMovimentacao(new LocalDate().minusDays(1));
		else
			dr.setDataMovimentacao(new LocalDate());

		return new DespesaReceitaTableModel(dao.consultaDinamica(dr));
	}

	public DespesaReceitaTableModel getTableModel(Tipo t) {
		return new DespesaReceitaTableModel(listarTudo(t));
	}

	public DespesaReceitaTableModel getReceitaTableModel() {
		List<DespesaReceita> lista = listarTudo(RECEITA);
		Collections.sort(lista);

		this.listaReceita = lista;

		return new DespesaReceitaTableModel(lista);
	}

	public DespesaReceitaTableModel getDespesaTableModel() {
		this.listaDespesa = listarTudo(DESPESA);

		return new DespesaReceitaTableModel(listaDespesa);
	}

	// SALVA UMA DESPESA/RECEITA
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
				pago)) {
			LocalDate movimentacao = null;

			if (parcelas.equals(""))
				parcelas = "1";
			if (dataMovimentacao != null)
				movimentacao = new LocalDate(dataMovimentacao);

			ArrayList<String> erros = DespesaReceitaFactory.novaDespesaReceita(
					descricao, new LocalDate(dataVencimento), movimentacao,
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
		} else {
			flag = false;
		}

		showMessage();

		return flag;
	}

	// VALIDACAO DOS CAMPOS
	private boolean validaCampos(String descricao, Date dataVencimento,
			Date dataMovimentacao, String valor, boolean pago) {
		boolean retorno = true;
		this.msg = "Você precisa fornecer as seguintes informações:\n\n";
		// DESCRICAO.REPLACEALL().ISEMPTY() = REMOVE TODOS OS ESPACOS EM
		// BRANCO E CONFERE SE FICOU VAZIO.
		if (descricao.isEmpty() || descricao.replaceAll("\\s+", "").isEmpty()) {
			this.msg = msg + DespesaReceitaController.NULL_DESCRICAO;
			retorno = false;
		}
		if (dataVencimento == null) {
			this.msg = msg + DespesaReceitaController.NULL_VENCIMENTO;
			retorno = false;
		}
		if (dataMovimentacao == null && pago) {
			this.msg = msg + DespesaReceitaController.NULL_MOVIMENTACAO;
			retorno = false;
		}
		if (valor.equals("")) {
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

		return retorno;
	}

	// REMOVE DESPESA/RECEITA
	public boolean remover(Object id) {
		DespesaReceita dr = dao.procurar((int) id);
		boolean flag = false;

		this.title = TITLE_REMOVER + dr.getTipoBean().getDescricao();
		this.msg = REMOVER_CONFIRMACAO;
		this.type = 0;

		if (showQuestionMessage() == 0) {
			flag = true;
			if (dr.getParcelaId() != null && dr.getDataMovimentacao() == null) {
				this.title = TITLE_REMOVER + dr.getTipoBean().getDescricao();
				this.msg = MSG_PARCELA;

				// NESSE CASO, INDICA QUE TEREMOS SOMENTE A OPCAO DE RESPONDER
				// SIM OU NAO NO CONFIRMDIALOG.
				this.type = 0;

				// CASO QUERIA REMOVER AS PARCELAS NAO PAGAS:
				if (showQuestionMessage() == 0) {
					int num = dao.removerPorParcelaId(dr.getParcelaId());

					// CASO TENHA REMOVIDO, O SISTEMA EXIBE UMA MENSAGEM DE
					// SUCESSO
					if (num > 0) {
						this.msg = num + " "
								+ dr.getTipoBean().getDescricao().toLowerCase()
								+ REMOVER_SUCESSO;
						this.title = TITLE_REMOVER;
						this.type = 1;
					}
					// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM
					// DE
					// ERRO.
					else {
						this.msg = REMOVER_FALHA
								+ dr.getTipoBean().getDescricao().toLowerCase()
								+ ".";
						this.title = TITLE_REMOVER;
						this.type = 0;
						flag = false;
					}
				} else {

					// CASO TENHA REMOVIDO, O SISTEMA EXIBE UMA MENSAGEM DE
					// SUCESSO
					if (dao.remover(dr)) {
						this.msg = dr.getTipoBean().getDescricao()
								+ REMOVER_SUCESSO;
						this.title = TITLE_REMOVER;
						this.type = 1;
					}

					// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM
					// DE
					// ERRO.
					else {
						this.msg = REMOVER_FALHA
								+ dr.getTipoBean().getDescricao().toLowerCase()
								+ ".";
						this.title = TITLE_REMOVER;
						this.type = 0;
						flag = false;
					}
				}
			} else {

				// CASO TENHA REMOVIDO, O SISTEMA EXIBE UMA MENSAGEM DE SUCESSO
				if (dao.remover(dr)) {
					this.msg = dr.getTipoBean().getDescricao()
							+ REMOVER_SUCESSO;
					this.title = TITLE_REMOVER;
					this.type = 1;
				}

				// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM DE
				// ERRO.
				else {
					this.msg = REMOVER_FALHA
							+ dr.getTipoBean().getDescricao().toLowerCase()
							+ ".";
					this.title = TITLE_REMOVER;
					this.type = 0;
					flag = false;
				}
			}
			showMessage();
		}
		return flag;
	}

	// ATUALIZA UMA DESPESA / RECEITA
	public boolean atualizar(String descricao, Date dataVencimento,
			Date dataMovimentacao, String valor, boolean fixa,
			Object categoria, Object conta, Object cartaoCredito, boolean pago,
			int id) {
		DespesaReceita dr;
		this.type = 0;
		boolean flag = true;

		if (validaCampos(descricao, dataVencimento, dataMovimentacao, valor,
				pago)) {
			dr = dao.procurar(id);

			LocalDate movimentacao = null;

			if (dataMovimentacao != null && pago)
				movimentacao = new LocalDate(dataMovimentacao);

			dr.setDescricao(descricao);
			dr.setDataVencimento(new LocalDate(dataVencimento));
			dr.setDataMovimentacao(movimentacao);
			dr.setValor(Double.parseDouble(valor.replace(",", ".")));
			dr.setFixa(fixa);
			dr.setCategoriaBean((Categoria) categoria);
			dr.setContaBean((Conta) conta);
			dr.setCartaoCreditoBean((CartaoCredito) cartaoCredito);

			// VERIFICA SE EXISTEM PARCELAS DESTA DESPESA/RECEITA.
			if (dr.getParcelaId() != null
					&& dao.procurar(dr.getId()).getDataMovimentacao() == null) {
				this.title = TITLE_ATUALIZAR + dr.getTipoBean().getDescricao();
				this.msg = MSG_PARCELA;

				// NESSE CASO, INDICA QUE TEREMOS SOMENTE A OPCAO DE RESPONDER
				// SIM OU NAO NO CONFIRMDIALOG.
				this.type = 0;

				// CASO QUERIA ATUALIZAR AS PARCELAS NAO PAGAS:
				if (showQuestionMessage() == 0) {
					// CASO TENHA ATUALIZADO, O SISTEMA EXIBE UMA MENSAGEM DE
					// SUCESSO
					if (dao.atualizarPorParcelaId(dr)) {
						this.msg = dr.getTipoBean().getDescricao()
								+ ATUALIZAR_SUCESSO;
						this.title = TITLE_ATUALIZAR;
						this.type = 1;
					}
					// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM
					// DE
					// ERRO.
					else {
						this.msg = ATUALIZAR_FALHA
								+ dr.getTipoBean().getDescricao().toLowerCase()
								+ ".";
						this.title = TITLE_ATUALIZAR;
						this.type = 0;
					}
				} else {

					// CASO TENHA ATUALIZADO, O SISTEMA EXIBE UMA MENSAGEM DE
					// SUCESSO
					if (dao.atualizar(dr)) {
						this.msg = dr.getTipoBean().getDescricao()
								+ ATUALIZAR_SUCESSO;
						this.title = TITLE_ATUALIZAR;
						this.type = 1;
					}

					// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM
					// DE
					// ERRO.
					else {
						this.msg = ATUALIZAR_SUCESSO
								+ dr.getTipoBean().getDescricao().toLowerCase()
								+ ".";
						this.title = TITLE_ATUALIZAR;
						this.type = 0;
					}
				}
			} else {

				// CASO TENHA ATUALIZADO, O SISTEMA EXIBE UMA MENSAGEM DE
				// SUCESSO
				if (dao.atualizar(dr)) {
					this.msg = dr.getTipoBean().getDescricao()
							+ ATUALIZAR_SUCESSO;
					this.title = TITLE_ATUALIZAR;
					this.type = 1;
				}

				// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM DE
				// ERRO.
				else {
					this.msg = ATUALIZAR_SUCESSO
							+ dr.getTipoBean().getDescricao().toLowerCase()
							+ ".";
					this.title = TITLE_ATUALIZAR;
					this.type = 0;
				}
			}
		} else {
			flag = false;
		}

		showMessage();
		return flag;
	}

	public EditarReceitaView abrirEditarReceitaView(Object id) {
		EditarReceitaView editarReceitaView;

		for (DespesaReceita dr : listaReceita) {
			if (dr.getId() == (int) id) {
				editarReceitaView = new EditarReceitaView();

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
					editarReceitaView
							.setDataPagamento(dr.getDataMovimentacao());
				} else {
					editarReceitaView.setPago(false);
				}

				return editarReceitaView;
			}
		}

		return null;
	}

	// CRIA O JCOMBOBOX DE CONTA
	public JComboBox<Conta> getComboConta() {
		JComboBox<Conta> combo = new JComboBox<Conta>();

		if (listaConta == null)
			listaConta = daoConta.listaTudo();

		for (Conta c : listaConta) {
			combo.addItem(c);
		}

		return combo;
	}

	// CRIA O JCOMBOBOX DE CATEGORIA DE DESPESA
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

	// CRIA O JCOMBOBOX DE CATEGORIA DE RECEITA
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

	// EXIBE MENSAGENS
	private void showMessage() {
		JOptionPane.showMessageDialog(null, this.msg, this.title, this.type);
	}

	private int showQuestionMessage() {

		// 0 = SIM
		// 1 = NAO
		// 2 = CANCELAR
		return JOptionPane.showConfirmDialog(null, this.msg, this.title,
				this.type);
	}
}