package factory;

import java.util.ArrayList;

import model.CartaoCredito;
import model.Categoria;
import model.Conta;
import model.DespesaReceita;
import model.ParcelaSeq;
import model.Tipo;

import org.joda.time.LocalDate;

import dao.DespesaReceitaDAO;
import dao.ParcelaSeqDAO;

public abstract class DespesaReceitaFactory {
	private static String ERRO_SALVAR = "Erro ao salvar ";

	public static ArrayList<String> novaDespesaReceita(String descricao,
			LocalDate dataVencimento, LocalDate dataMovimentacao, Double valor,
			boolean fixa, Categoria categoria, Tipo tipo, Conta conta,
			CartaoCredito cartaoCredito, int parcelas) {

		DespesaReceita nova;
		Integer parcelaId = null;
		ParcelaSeq parcelaSeq;
		ParcelaSeqDAO daoParcela;
		DespesaReceitaDAO daoDespesaReceita = new DespesaReceitaDAO();
		LocalDate dateAux = dataVencimento;

		ArrayList<String> erros = new ArrayList<String>();

		// SE A DESPESA/RECEITA FOR REPETIR, ENTAO PEGAMOS UM IDENTIFICADOR
		// DE PARCELA.
		if (parcelas > 1) {
			daoParcela = new ParcelaSeqDAO();

			// RECUPERA O ULTIMO ID UTILIZADO
			parcelaSeq = daoParcela.procurar(1);

			// INCREMENTA EM UM, QUE SERA O ID UTILIZADO.
			parcelaSeq.nextSequencia();
			parcelaId = parcelaSeq.getSequencia();

			// ATUALIZA O NOVO VALOR.
			daoParcela.atualizar(parcelaSeq);
		}

		// LACO DE REPETICAO QUE IRA CRIAR AS DESPESAS/RECEITAS.
		for (int i = 0, j = 0; i < parcelas; i++) {
			if (cartaoCredito != null) {
				dataVencimento = getDataVencimento(cartaoCredito, (i + j));

				if (i == 0
						&& verificaDataFechamento(dataVencimento, cartaoCredito)) {
					j++;
					dataVencimento = dataVencimento.plusMonths(j);
				}
			} else {
				dataVencimento = dateAux.plusMonths(i);
			}

			// SE EXISTIR MAIS DE UMA DESPESA/RECEITA
			// E A DATA DE MOVIMENTACAO FOI INFORMADA,
			// ENTAO SOMENTE A PRIMEIRA SERA MARCADA COMO PAGO.
			if (i > 0)
				dataMovimentacao = null;

			nova = new DespesaReceita(descricao, dataVencimento, valor);

			// CAMPOS NOT NULL
			nova.setTipoBean(tipo);
			nova.setCategoriaBean(categoria);
			nova.setContaBean(conta);
			nova.setFixa(fixa);

			// CAMPOS OPCIONAIS
			nova.setDataMovimentacao(dataMovimentacao);
			nova.setCartaoCreditoBean(cartaoCredito);
			nova.setParcelaId(parcelaId);

			// SALVA A DESPESA/RECEITA NO BANCO DE DADOS.
			if (!daoDespesaReceita.salvar(nova)) {
				// SE OCORRER ALGUM ERRO, CAPTURAMOS O NOME DA DESPESA E
				// INTERROMPEMOS A ACAO.
				erros.add(ERRO_SALVAR + nova.getDescricao() + ".");
				erros.add("\nRegistros salvos: " + i);
				return erros;
			}
		}

		return erros;
	}

	private static LocalDate getDataVencimento(CartaoCredito cartaoCredito,
			int aux) {
		// CASA EXISTA UM CARTAO, ENTAO DEFINIMOS A DATA DE FECHAMENTO
		// E A DATA DE VENCIMENTO.

		// DESCOBRE A DATA DE VENCIMENTO DO CARTAO
		// CASO A DATA SEJA DIA 30, POR EXEMPLO, SE
		// TENTARMOS CRIAR NO MES DE FEVEREIRO
		// TEREMOS UMA EXCECAO. AQUI O DIA E
		// DECREMENTADO ATE QUE SE ENCONTRE O DIA CERTO.
		for (int i = 0, j = 0; j < 1; i++) {
			try {
				LocalDate dataCartaoVencimento = new LocalDate()
						.plusMonths(aux).withDayOfMonth(
								cartaoCredito.getVencimento() - i);
				j = 1;

				return dataCartaoVencimento;
			} catch (Exception e) {
				j = 0;
			}
		}
		return null;
	}

	private static boolean verificaDataFechamento(
			LocalDate dataCartaoVencimento, CartaoCredito cartaoCredito) {
		LocalDate dateAux = new LocalDate();

		if (dateAux.getDayOfMonth() >= dataCartaoVencimento.minusDays(
				cartaoCredito.getDiasFechamento()).getDayOfMonth()) {
			return true;
		}

		return false;
	}
}