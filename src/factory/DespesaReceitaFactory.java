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
	private static String ERRO_DATA_VENCIMENTO = "- A data de vencimento é anterior a data atual.\n Se a data estiver correta, informe a data da movimentação.";
	private static String ERRO_SALVAR = "Erro ao salvar ";
	private static String ERRO_REPETIR = "- Não é possível repetir despesas/receitas com data de vencimento anterior a data atual.";
	private static String ERRO_VALOR = "- O valor deve ser maior do que zero.";

	public static ArrayList<String> novaDespesaReceita(String descricao,
			LocalDate dataVencimento, LocalDate dataMovimentacao, Double valor,
			boolean fixa, Categoria categoria, Tipo tipo, Conta conta,
			CartaoCredito cartaoCredito, int parcelas) {

		DespesaReceita nova;
		Integer parcelaId = null;
		ParcelaSeq parcelaSeq;
		ParcelaSeqDAO daoParcela;
		DespesaReceitaDAO daoDespesaReceita = new DespesaReceitaDAO();
		LocalDate date_1 = dataVencimento;
		LocalDate date_2 = new LocalDate();
		int diaFechamento = 0;

		ArrayList<String> erros = new ArrayList<String>();
		erros.addAll(validar(dataVencimento, dataMovimentacao, parcelas, valor));

		if (erros.isEmpty()) {

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
			
			if (cartaoCredito != null) {
				diaFechamento = date_1.withDayOfMonth(cartaoCredito.getVencimento()).minusDays(cartaoCredito.getDiasFechamento()).getDayOfMonth();
			}

			// LACO DE REPETICAO QUE IRA CRIAR AS DESPESAS/RECEITAS.
			for (int i = 0; i < parcelas; i++) {

				// VERIFICA SE A DESPESA/RECEITA E RELACIONADA A UM
				// CARTAO DE CREDITO.
				// CASO SEJA RELACIONADO A UM CARTAO, ENTAO TEMOS QUE DEFINIR
				// A DATA DE VENCIMENTO.
				if (cartaoCredito != null) {
					// AQUI DEFINIMOS SE A DESPESA IRA ENTRAR NO MES ATUAL, OU NO PROXIMO.
					if (date_2.getDayOfMonth() >= diaFechamento) {
						// A DATA DE VENCIMENTO RECEBE A DATA ATUAL, COM O VALOR
						// DO DIA
						// SUBSTITUIDO PELO DIA DA FATURA DO CARTAO.
						// WITHDATE(ANO, MES, DIA)
						dataVencimento = date_2.withDayOfMonth(cartaoCredito
								.getVencimento());
						// O MES DA DATA DE VENCIMENTO E INCREMENTADO EM UM.
						dataVencimento = dataVencimento.plusMonths(1);

						// A DATA DE REFERENCIA E ALTERADA PARA NAO ENTRAR MAIS
						// NESSA CONDICIONAL.
						date_2 = dataVencimento;
					}
					// SE O DIA ATUAL FOR ANTERIOR AO DIA DE VENCIMENTO DA
					// FATURA,
					// ENTAO A DATA DE VENCIMENTO SERA DEFINIDA PARA O MESMO
					// MES.
					else {
						dataVencimento = date_2.withDayOfMonth(cartaoCredito
								.getVencimento());
						dataVencimento = dataVencimento.plusMonths(i);
					}
				}

				// SE NAO HOUVER CARTAO PARA REPETIR A DESPESA/RECEITA,
				// ENTAO ELA SERA REPETIDA COM A DATA DE VENCIMENTO INFORMADA.
				else {
					dataVencimento = date_1.plusMonths(i);
				}

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
		}

		return erros;
	}

	private static ArrayList<String> validar(LocalDate dataVencimento,
			LocalDate dataMovimentacao, int parcelas, Double valor) {

		ArrayList<String> erros = new ArrayList<>();

		if (parcelas > 1 && dataVencimento.isBefore(LocalDate.now())) {
			erros.add(ERRO_REPETIR);
		}

		// VERIFICA SE A DATA DE VENCIMENTO E ANTERIOR A DATA ATUAL.
		else if (dataVencimento.isBefore(LocalDate.now())) {
			// SE FOR, A DESPESA/RECEITA DEVE POSSUIR UMA DATA DE MOVIMENTACAO.
			if (dataMovimentacao == null)
				erros.add(ERRO_DATA_VENCIMENTO);
		}
		
		if(valor <= 0){
			erros.add(ERRO_VALOR);
		}

		return erros;
	}
}