package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;

import model.CartaoCredito;
import model.DespesaReceita;

import org.joda.time.LocalDate;

import table.CartaoCreditoTableModel;
import util.ExibeMensagem;
import view.EditarCartaoView;
import dao.CartaoCreditoDAO;
import factory.CartaoCreditoFactory;

public class CartaoCreditoController {
	private CartaoCreditoDAO dao;

	// MSG = MENSAGEM DO SISTEMA PARA INFORMAR O USUARIO O QUE ESTA ACONTECENDO.
	// TYPE = TIPO DA MENSAGEM (ERRO, INFORMACAO, SUCESSO).
	private String msg;
	private short type;
	private String title;

	private DecimalFormat df;

	private List<CartaoCredito> listaCartaoCredito;
	private List<DespesaReceita> listaDespesaFatura;

	private static String NULL_DESCRICAO = "- Informe uma descrição.\n";
	private static String NULL_DIA_VENC = "- Informe o dia de vencimento.\n";
	private static String NULL_DIA_FECH = "- Informe quantos dias antes do vencimento, a fatura fecha.\n";
	private static String TITLE_ERRO = "Cartão de Crédito - Erro";
	private static String TITLE = "Cartão de Crédito";
	private static String TITLE_EFETIVAR_ERRO = "Efetivar Cartão de Crédito - Erro";
	private static String TITLE_EFETIVAR = "Efetivar Cartão de Crédito";
	private static String TITLE_DESATIVAR = "Cartão de Crédito - Desativar";
	private static String TITLE_ATUALIZAR = "Cartão de Crédito - Atualizar";
	private static String MSG_ERRO_VALIDACAO = "Você precisa fornecer as seguintes informações:\n\n";
	private static String MSG_SALVAR_SUCESSO = "Cartão de crédito salvo com sucesso!";
	private static String MSG_EFETIVAR_VAZIO = "Não existem despesas nesta fatura!";
	private static String MSG_EFETIVAR_ERRO_PT1 = "Erro ao efetivar despesa ";
	private static String MSG_EFETIVAR_ERRO_PT2 = ".\nDespesas efetivadas: ";
	private static String MSG_EFETIVAR_SUCESSO = " despesa(s) efetivada(s) com sucesso!";
	private static String MSG_EFETIVAR_ERRO_DATA = "Não é possível efetivar.\n\nEsta fatura ainda não está fechada.";
	private static String MSG_DESATIVAR_SUCESSO = "O cartão foi desativado com sucesso!\n\n"
			+ "As despesas que estiverem associadas a este cartão, continuarão associadas a ele.";
	private static String MSG_DESATIVAR_ERRO = "Ocorreu um erro ao tentar desativar o cartão.";
	private static String QUESTION_EFETIVAR = "Deseja mesmo efetivar?";
	private static String QUESTION_DESATIVAR = "Tem certeza que deseja desativar este cartão?";
	private static String MSG_ATUALIZAR_SUCESSO = "Cartão de Crédito atualizado com sucesso!";
	private static String MSG_ATUALIZAR_ERRO = "Ocorreu um erro ao tentar atualizar o cartão de crédito.";

	/*
	 * INICIALIZA AS VARIAVEIS NO CONSTRUTOR
	 */
	public CartaoCreditoController() {
		this.dao = new CartaoCreditoDAO();
		this.msg = "";
		this.type = 0;
		this.title = "";
		this.df = new DecimalFormat("#,##0.00");
		this.listaCartaoCredito = dao.listaAtivos();
		this.listaDespesaFatura = new ArrayList<>();
	}

	/*
	 * RETORNA O TABLE MODEL DE CARTAO DE CREDITO.
	 */
	public CartaoCreditoTableModel getTableModel(String descricao) {
		List<CartaoCredito> nova = new ArrayList<>();
		descricao = descricao.toLowerCase();
		nova.addAll(dao.listaAtivos());

		if (!this.listaCartaoCredito.isEmpty())
			this.listaCartaoCredito.clear();

		for (CartaoCredito c : nova) {
			if (c.getDescricao().toLowerCase().contains(descricao))
				this.listaCartaoCredito.add(c);
		}

		Collections.sort(this.listaCartaoCredito);

		return new CartaoCreditoTableModel(listaCartaoCredito);
	}

	/*
	 * CRIA E SALVA UM CARTAO DE CREDITO NO BANCO DE DADOS.
	 */
	public boolean salvar(String descricao, String diaVencimento,
			String diaFechamento) {
		boolean flag = true;

		/*
		 * FAZ A VALIDACAO DOS CAMPOS ANTES DE TENTAR SALVAR.
		 */
		if (validaCampos(descricao, diaVencimento, diaFechamento)) {
			this.msg = "";

			/*
			 * O METODO DE CRIAR CARTAO RETORNA UMA LISTA DE ERROS.
			 */
			ArrayList<String> erros = CartaoCreditoFactory.novoCartaoCredito(
					descricao, (short) Integer.parseInt(diaVencimento),
					(short) Integer.parseInt(diaFechamento));

			/*
			 * SE OCORRER ALGUM ERRO AO CRIAR E SALVAR O CARTAO ENTAO UMA
			 * MENSAGEM APRESENTANDO OS ERROS SERA EXIBIDA.
			 */
			if (!erros.isEmpty()) {
				this.title = TITLE_ERRO;
				for (String s : erros)
					this.msg = this.msg + s;
				this.type = 0;
				flag = false;
			}
			/*
			 * CASO NENHUM ERRO OCORRA, ENTAO UMA MENSAGEM DE SUCESSO SERA
			 * EXIBIDA.
			 */
			else {
				this.title = TITLE;
				this.msg = MSG_SALVAR_SUCESSO;
				this.type = 1;
			}

			ExibeMensagem.showMessage(this.msg, this.title, this.type);
		} else {
			flag = false;
		}

		return flag;
	}

	/*
	 * VERIFICA SE DADOS OBRIGATORIOS NAO FORAM INFORMADOS
	 */
	private boolean validaCampos(String descricao, String diaVencimento,
			String diaFechamento) {
		boolean retorno = true;

		this.msg = MSG_ERRO_VALIDACAO;
		this.title = TITLE_ERRO;
		this.type = 0;

		if (descricao.isEmpty() || descricao.replaceAll("\\s+", "").isEmpty()) {
			this.msg = msg + CartaoCreditoController.NULL_DESCRICAO;
			retorno = false;
		}
		if (diaVencimento.isEmpty()) {
			this.msg = msg + CartaoCreditoController.NULL_DIA_VENC;
			retorno = false;
		}
		if (diaFechamento.isEmpty()) {
			this.msg = msg + CartaoCreditoController.NULL_DIA_FECH;
			retorno = false;
		}

		if (!retorno) {
			ExibeMensagem.showMessage(this.msg, this.title, this.type);
		}

		return retorno;
	}

	/*
	 * DESATIVA UM CARTAO DE CREDITO. O CARTAO NAO SERA REMOVIDO, POIS PODEMOS
	 * TER DESPESAS ASSOCIADAS A ELE.
	 */
	public boolean desativar(Object id) {
		boolean flag = true;

		if (ExibeMensagem.showQuestionMessage(QUESTION_DESATIVAR,
				TITLE_DESATIVAR, 0) == 0) {
			CartaoCredito desativar = dao.procurar((int) id);
			desativar.setAtivo(false);
			if (dao.atualizar(desativar)) {
				ExibeMensagem.showMessage(MSG_DESATIVAR_SUCESSO,
						TITLE_DESATIVAR, 1);
			} else {
				flag = false;
				ExibeMensagem.showMessage(MSG_DESATIVAR_ERRO, TITLE_DESATIVAR,
						0);
			}
		} else {
			flag = false;
		}

		return flag;
	}

	/*
	 * ATUALIZA UM CARTAO DE CREDITO.
	 */
	public boolean atualizar(String descricao, String diaVencimento,
			String diaFechamento, int cod) {
		boolean flag = true;

		if (validaCampos(descricao, diaVencimento, diaFechamento)) {
			ArrayList<String> erros = CartaoCreditoFactory.validarDados(
					(short) Integer.parseInt(diaVencimento),
					(short) Integer.parseInt(diaFechamento));

			if (erros.size() == 1) {
				CartaoCredito atualizar = dao.procurar(cod);

				atualizar.setDescricao(descricao);
				atualizar
						.setVencimento((short) Integer.parseInt(diaVencimento));
				atualizar.setDiasFechamento((short) Integer
						.parseInt(diaFechamento));

				if (dao.atualizar(atualizar)) {
					ExibeMensagem.showMessage(MSG_ATUALIZAR_SUCESSO,
							TITLE_ATUALIZAR, 1);
				} else {
					flag = false;
					ExibeMensagem.showMessage(MSG_ATUALIZAR_ERRO,
							TITLE_ATUALIZAR, 0);
				}
			} else {
				flag = false;

				this.msg = "";
				this.type = 0;
				this.title = TITLE_ATUALIZAR;
				for (String s : erros) {
					this.msg = this.msg + s + "\n";
				}
				
				ExibeMensagem.showMessage(this.msg, this.title, this.type);
			}
		} else {
			flag = false;
		}

		return flag;
	}

	/*
	 * RETORNA UM RESUMO DA FATURA DO CARTAO PARA O MES E ANO SELECIONADO.
	 */
	public ArrayList<String> getResumoCartaoCredito(Object cartao, int mes,
			int ano) {
		this.listaDespesaFatura.clear();
		CartaoCredito cartaoCredito = dao.procurar(((CartaoCredito) cartao)
				.getId());
		ArrayList<DespesaReceita> lista = new ArrayList<>();
		Double valorTotal = 0.0;
		ArrayList<String> resumo = new ArrayList<String>();

		for (DespesaReceita d : cartaoCredito.getDespesaReceitas()) {
			if (d.getDataVencimento().getMonthOfYear() == mes
					&& d.getDataVencimento().getYear() == ano
					&& d.getDataMovimentacao() == null) {
				lista.add(d);
			}
		}

		this.listaDespesaFatura.addAll(lista);

		resumo.add("O dia de vencimento da fatura é "
				+ cartaoCredito.getVencimento() + ".");
		resumo.add("A fatura fecha " + cartaoCredito.getDiasFechamento()
				+ " dias antes do vencimento.");

		if (lista.isEmpty()) {
			resumo.add("Este cartão não possui despesas para este mês.");
			resumo.add("O valor total das despesas é de R$0,00");
		} else {
			resumo.add("O número de despesas para este mês é " + lista.size()
					+ ".");
			for (DespesaReceita d : lista) {
				valorTotal = valorTotal + d.getValor();
			}
			resumo.add("O valor total das despesas é de R$"
					+ df.format(valorTotal));
		}

		return resumo;
	}

	/*
	 * EFETIVA AS DESPESAS DO MES E ANO SELECIONADOS.
	 */
	public boolean efetivarCartao(Object cartao, int mes, int ano) {
		DespesaReceitaController despesaReceitaController = new DespesaReceitaController();
		int count = 0;

		if (ExibeMensagem.showQuestionMessage(QUESTION_EFETIVAR,
				TITLE_EFETIVAR, 0) == 0) {
			if (((CartaoCredito) cartao).podeEfetivar(mes, ano)) {
				if (this.listaDespesaFatura.isEmpty()) {
					ExibeMensagem.showMessage(MSG_EFETIVAR_VAZIO,
							TITLE_EFETIVAR, 2);
					return false;
				} else {
					for (DespesaReceita d : this.listaDespesaFatura) {
						DespesaReceita dr = d;
						dr.setDataMovimentacao(new LocalDate());
						if (despesaReceitaController.atualizarSemVerificao(dr)) {
							count++;
						} else {
							ExibeMensagem.showMessage(
									MSG_EFETIVAR_ERRO_PT1 + d.getDescricao()
											+ MSG_EFETIVAR_ERRO_PT2 + count,
									TITLE_EFETIVAR_ERRO, 0);
							return false;
						}
					}
					ExibeMensagem.showMessage(count + MSG_EFETIVAR_SUCESSO,
							TITLE_EFETIVAR, 1);
					return true;
				}
			}
			ExibeMensagem.showMessage(MSG_EFETIVAR_ERRO_DATA,
					TITLE_EFETIVAR_ERRO, 2);
		}

		return false;
	}

	/*
	 * CRIA A TELA DE EDICAO DE CARTAO DE CREDITO.
	 */
	public EditarCartaoView abrirEditarCartaoView(int linha) {
		EditarCartaoView view = new EditarCartaoView();

		CartaoCredito editar = this.listaCartaoCredito.get(linha);

		view.setCod(editar.getId());
		view.setDescricao(editar.getDescricao());
		view.setDiaVencimento("" + editar.getVencimento());
		view.setDiasFechamento("" + editar.getDiasFechamento());

		return view;
	}

	/*
	 * CRIA O JCOMBOBOX DE CARTAO DE CREDITO
	 */
	public JComboBox<CartaoCredito> getComboCartaoCredito(boolean ativo) {
		JComboBox<CartaoCredito> combo = new JComboBox<CartaoCredito>();
		CartaoCredito selecione = new CartaoCredito("Selecione...", (short) 0,
				(short) 0);

		listaCartaoCredito = dao.listaTudo();

		combo.addItem(selecione);

		for (CartaoCredito cd : listaCartaoCredito) {
			if (cd.getAtivo())
				combo.addItem(cd);
		}

		return combo;
	}
}