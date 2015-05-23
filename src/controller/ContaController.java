package controller;

import javax.swing.JOptionPane;

import model.Conta;
import dao.ContaDAO;

public class ContaController {
	private static String NULL_DESCRICAO = "Informe uma descrição.";
	private static String MSG_ADD_SUCESSO = "Conta cadastrada com sucesso!";
	private static String MSG_ADD_FRACASSO = "Ocorreu um erro ao tentar cadastrar a conta.\nTente novamente.";
	private static String MSG_REM_SUCESSO = "Conta removida com sucesso!";
	private static String MSG_REM_FRACASSO = "Ocorreu um erro ao remover a conta.\nTente novamente.";
	private static String MSG_UPDATE_SUCESSO = "Conta atualizada com sucesso!";
	private static String MSG_UPDATE_FRACASSO = "Ocorreu um erro ao atualizar a conta.\nTente novamente.";
	
	private static String TITLE_ADD_CONTA = "Nova Conta";
	private static String TITLE_REM_CONTA = "Remover Conta";
	private static String TITLE_UPDATE_CONTA = "Atualizar Conta";
	
	private Conta conta;
	private ContaDAO dao;
	
	// MSG = MENSAGEM DO SISTEMA PARA INFORMAR O USUARIO O QUE ESTA ACONTECENDO.
	// TYPE = TIPO DA MENSAGEM (ERRO, INFORMACAO, SUCESSO).
	private String msg;
	private short type;
	private String title;
	
	public ContaController(){
		this.msg = null;
		this.dao = new ContaDAO();
	}
	
	public void salvar(String descricao, Double saldo){
		this.type = 1;
		this.title = TITLE_ADD_CONTA;
		
		// (DESCRICAO.REPLACEALL()).ISEMPTY() = REMOVE TODOS OS ESPACOS EM BRANCO E CONFERE SE FICOU VAZIO.
		if(descricao.isEmpty() || descricao.replaceAll("\\s+", "").isEmpty()){
			this.msg = NULL_DESCRICAO;
			this.type = 0;
		}
		else{
			this.conta = new Conta(descricao, saldo);
			if(this.dao.salvar(conta))
				this.msg = MSG_ADD_SUCESSO;
			else{
				this.msg = MSG_ADD_FRACASSO;
				this.type = 0;
			}
		}
		showMessage();
	}
	
	public void remover(Conta conta){
		this.type = 1;
		this.title = TITLE_REM_CONTA;
		if(this.dao.remover(conta))
			this.msg = MSG_REM_SUCESSO;
		else{
			this.msg = MSG_REM_FRACASSO;
			this.type = 0;
		}
		showMessage();
	}
	
	public void atualizar(Conta conta){
		this.type = 1;
		this.title = TITLE_UPDATE_CONTA;
		if(this.dao.atualizar(conta)){
			this.msg = MSG_UPDATE_SUCESSO;
		}
		else{
			this.msg = MSG_UPDATE_FRACASSO;
			this.type = 0;
		}
		showMessage();
	}
	
	private void showMessage(){
		JOptionPane.showMessageDialog(null, this.msg, this.title, this.type);
	}
}
