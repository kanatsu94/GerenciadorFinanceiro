package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.CartaoCredito;
import model.Categoria;
import model.Conta;
import model.DespesaReceita;
import model.Tipo;

import org.joda.time.LocalDate;

import table.DespesaReceitaTableModel;
import dao.DespesaReceitaDAO;
import dao.TipoDAO;
import factory.DespesaReceitaFactory;

public class DespesaReceitaController {
	private DespesaReceitaDAO dao;
	private TipoDAO daoTipo;

	private static String TITLE_ADD = "Nova ";
	private static String TITLE_REMOVER = "Remover ";
	private static String TITLE_ATUALIZAR = "Atualizar ";

	private static String NULL_DESCRICAO = "- Informe uma descrição.\n";
	private static String NULL_VENCIMENTO = "- Informe a data de vencimento.\n";
	private static String NULL_MOVIMENTACAO = "- Informe a data de movimentação.\n";
	private static String NULL_VALOR = "- Informe o valor.\n";
	private static String NULL_CATEGORIA = "- Informe a categoria.\n";
	private static String NULL_CONTA = "- Informe uma conta.\n";
	private static String SALVAR_SUCESSO = " salva com sucesso!";
	private static String SALVAR_FALHA = "Erro ao salvar ";
	private static String MSG_PARCELA = "Deseja replicar esta ação para todas as parcelas não pagas?";
	private static String REMOVER_SUCESSO = "(s) removida(s) com sucesso!";
	private static String REMOVER_FALHA = "Erro ao remover ";
	private static String ATUALIZAR_SUCESSO = "(s) atualizada(s) com sucesso!";
	private static String ATUALIZAR_FALHA = "Erro ao atualizar ";

	// MSG = MENSAGEM DO SISTEMA PARA INFORMAR O USUARIO O QUE ESTA ACONTECENDO.
	// TYPE = TIPO DA MENSAGEM (ERRO, INFORMACAO, SUCESSO).
	private String msg;
	private short type;
	private String title;

	public DespesaReceitaController() {
		this.msg = "";
		this.dao = new DespesaReceitaDAO();
		this.daoTipo = new TipoDAO();
	}

	// RECUPERA TODOS OS REGISTROS DE DESPESA/RECEITA DO
	// BANCO DE DADOS E DEVOLVE UMA LIST.
	public List<DespesaReceita> listarTudo(Tipo t) {
		List<DespesaReceita> lista;

		lista = dao.listaTudoPorTipo(t);

		return lista;
	}

	// BUSCA TODAS AS DESPESAS/RECEITAS POR DESCRICAO.
	public List<DespesaReceita> buscaDescricao(String descricao, Tipo tipo) {
		List<DespesaReceita> lista;

		DespesaReceita dr = new DespesaReceita(descricao, null, null);
		dr.setDescricao(descricao);
		dr.setTipoBean(tipo);
		lista = dao.consultaDinamica(dr);

		return lista;
	}
	
	public DespesaReceitaTableModel getTableModel(Tipo t){
		return new DespesaReceitaTableModel(listarTudo(t));
	}
	
	public DespesaReceitaTableModel getReceitaTableModel(){
		return new DespesaReceitaTableModel(listarTudo(daoTipo.procurar(2)));
	}
	
	public DespesaReceitaTableModel getDespesaTableModel(){
		return new DespesaReceitaTableModel(listarTudo(daoTipo.procurar(1)));
	}

	// SALVA UMA DESPESA/RECEITA
	public void salvar(String descricao, LocalDate dataVencimento,
			LocalDate dataMovimentacao, Double valor, boolean fixa,
			Categoria categoria, Tipo tipo, Conta conta,
			CartaoCredito cartaoCredito, int parcelas, boolean pago) {
		this.title = TITLE_ADD + tipo.getDescricao();
		this.type = 0;

		if (validaCampos(descricao, dataVencimento, dataMovimentacao, valor,
				categoria, conta, pago)) {
			ArrayList<String> erros = DespesaReceitaFactory.novaDespesaReceita(
					descricao, dataVencimento, dataMovimentacao, valor, fixa,
					categoria, tipo, conta, cartaoCredito, parcelas);
			if (!erros.isEmpty()) {
				this.type = 0;
				this.msg = SALVAR_FALHA + tipo.getDescricao().toLowerCase()
						+ ":\n\n";
				for (String s : erros) {
					this.msg = this.msg + s + "\n";
				}
			} else {
				this.type = 1;
				this.msg = tipo.getDescricao() + SALVAR_SUCESSO;
			}
		}

		showMessage();
	}

	// VALIDACAO DOS CAMPOS
	private boolean validaCampos(String descricao, LocalDate dataVencimento,
			LocalDate dataMovimentacao, Double valor, Categoria categoria,
			Conta conta, boolean pago) {
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
		if (valor == null) {
			this.msg = msg + DespesaReceitaController.NULL_VALOR;
			retorno = false;
		}
		if (categoria == null) {
			this.msg = msg + DespesaReceitaController.NULL_CATEGORIA;
			retorno = false;
		}
		if (conta == null) {
			this.msg = msg + DespesaReceitaController.NULL_CONTA;
			retorno = false;
		}

		return retorno;
	}

	// REMOVE DESPESA/RECEITA
	public void remover(DespesaReceita dr) {
		if (dr.getParcelaId() != null) {
			this.title = TITLE_REMOVER + dr.getTipoBean().getDescricao();
			this.msg = MSG_PARCELA;
			
			// NESSE CASO, INDICA QUE TEREMOS SOMENTE A OPCAO DE RESPONDER
			// SIM OU NAO NO CONFIRMDIALOG.
			this.type = 0;

			// CASO QUERIA REMOVER AS PARCELAS NAO PAGAS:
			if(showQuestionMessage() == 0){
				int num = dao.removerPorParcelaId(dr.getParcelaId());
				
				// CASO TENHA REMOVIDO, O SISTEMA EXIBE UMA MENSAGEM DE SUCESSO
				if(num > 0){
					this.msg = num + " " + dr.getTipoBean().getDescricao().toLowerCase() + REMOVER_SUCESSO;
					this.title = TITLE_REMOVER;
					this.type = 1;
					showMessage();
				}
				// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM DE ERRO.
				else{
					this.msg = REMOVER_FALHA + dr.getTipoBean().getDescricao().toLowerCase() + ".";
					this.title = TITLE_REMOVER;
					this.type = 0;
					showMessage();
				}
			}
			else{
				
				// CASO TENHA REMOVIDO, O SISTEMA EXIBE UMA MENSAGEM DE SUCESSO
				if(dao.remover(dr)){
					this.msg = dr.getTipoBean().getDescricao() + REMOVER_SUCESSO;
					this.title = TITLE_REMOVER;
					this.type = 1;
					showMessage();
				}
				
				// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM DE ERRO.
				else{
					this.msg = REMOVER_FALHA + dr.getTipoBean().getDescricao().toLowerCase() + ".";
					this.title = TITLE_REMOVER;
					this.type = 0;
					showMessage();
				}
			}
		}
		else{
			
			// CASO TENHA REMOVIDO, O SISTEMA EXIBE UMA MENSAGEM DE SUCESSO
			if(dao.remover(dr)){
				this.msg = dr.getTipoBean().getDescricao() + REMOVER_SUCESSO;
				this.title = TITLE_REMOVER;
				this.type = 1;
				showMessage();
			}
			
			// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM DE ERRO.
			else{
				this.msg = REMOVER_FALHA + dr.getTipoBean().getDescricao().toLowerCase() + ".";
				this.title = TITLE_REMOVER;
				this.type = 0;
				showMessage();
			}
		}
	}
	
	// ATUALIZA UMA DESPESA / RECEITA
	public void atualizar(DespesaReceita dr){
		if (dr.getParcelaId() != null) {
			this.title = TITLE_ATUALIZAR + dr.getTipoBean().getDescricao();
			this.msg = MSG_PARCELA;
			
			// NESSE CASO, INDICA QUE TEREMOS SOMENTE A OPCAO DE RESPONDER
			// SIM OU NAO NO CONFIRMDIALOG.
			this.type = 0;

			// CASO QUERIA ATUALIZAR AS PARCELAS NAO PAGAS:
			if(showQuestionMessage() == 0){
				// CASO TENHA ATUALIZADO, O SISTEMA EXIBE UMA MENSAGEM DE SUCESSO
				if(dao.atualizarPorParcelaId(dr)){
					this.msg = dr.getTipoBean().getDescricao() + ATUALIZAR_SUCESSO;
					this.title = TITLE_ATUALIZAR;
					this.type = 1;
					showMessage();
				}
				// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM DE ERRO.
				else{
					this.msg = ATUALIZAR_FALHA + dr.getTipoBean().getDescricao().toLowerCase() + ".";
					this.title = TITLE_ATUALIZAR;
					this.type = 0;
					showMessage();
				}
			}
			else{
				
				// CASO TENHA ATUALIZADO, O SISTEMA EXIBE UMA MENSAGEM DE SUCESSO
				if(dao.atualizar(dr)){
					this.msg = dr.getTipoBean().getDescricao() + ATUALIZAR_SUCESSO;
					this.title = TITLE_ATUALIZAR;
					this.type = 1;
					showMessage();
				}
				
				// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM DE ERRO.
				else{
					this.msg = ATUALIZAR_SUCESSO + dr.getTipoBean().getDescricao().toLowerCase() + ".";
					this.title = TITLE_ATUALIZAR;
					this.type = 0;
					showMessage();
				}
			}
		}
		else{
			
			// CASO TENHA ATUALIZADO, O SISTEMA EXIBE UMA MENSAGEM DE SUCESSO
			if(dao.atualizar(dr)){
				this.msg = dr.getTipoBean().getDescricao() + ATUALIZAR_SUCESSO;
				this.title = TITLE_ATUALIZAR;
				this.type = 1;
				showMessage();
			}
			
			// CASO ALGO TENHA DADO ERRADO, O SISTEMA EXIBE UMA MENSAGEM DE ERRO.
			else{
				this.msg = ATUALIZAR_SUCESSO + dr.getTipoBean().getDescricao().toLowerCase() + ".";
				this.title = TITLE_ATUALIZAR;
				this.type = 0;
				showMessage();
			}
		}
	}

	// EXIBE MENSAGENS
	private void showMessage() {
		JOptionPane.showMessageDialog(null, this.msg, this.title, this.type);
	}

	private int showQuestionMessage() {

		// 0 = SIM
		// 1 = NAO
		// 2 = CANCELAR
		return JOptionPane.showConfirmDialog(null, this.msg, this.title, this.type);
	}
}