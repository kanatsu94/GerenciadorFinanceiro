package factory;

import java.util.ArrayList;

import dao.CartaoCreditoDAO;
import model.CartaoCredito;

public abstract class CartaoCreditoFactory {
	private static String ERRO_VALIDACAO = "Voc� forneceu valores inv�lidos:\n";
	private static String ERRO_DIA_VENCIMENTO = "- Dia de vencimento inv�lido. Informe um valor entre 1 e 31.\n";
	private static String ERRO_DIA_FECHAMENTO = "- Dias para fechamento da fatura inv�lido. Informe um valor entre 1 e 31.\n";
	private static String ERRO_SALVAR = "Ocorreu um erro ao salvar o cart�o de cr�dito.";

	public static ArrayList<String> novoCartaoCredito(String descricao,
			short diaVencimento, short diaFechamento) {
		ArrayList<String> erros = validarDados(diaVencimento, diaFechamento);

		if (erros.size() > 1) {
			return erros;
		}

		else {
			erros.clear();
			CartaoCredito novo = new CartaoCredito(descricao, diaVencimento,
					diaFechamento, true);
			CartaoCreditoDAO daoCartao = new CartaoCreditoDAO();

			if (!daoCartao.salvar(novo)) {
				erros.add(ERRO_SALVAR);
			}

			return erros;
		}
	}

	public static ArrayList<String> validarDados(short diaVencimento,
			short diaFechamento) {
		ArrayList<String> erros = new ArrayList<>();

		erros.add(ERRO_VALIDACAO);

		if (diaVencimento < 1 || diaVencimento > 31) {
			erros.add(ERRO_DIA_VENCIMENTO);
		}
		if (diaFechamento < 1 || diaFechamento > 31) {
			erros.add(ERRO_DIA_FECHAMENTO);
		}

		return erros;
	}
}