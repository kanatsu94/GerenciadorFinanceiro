package table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.DespesaReceita;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DespesaReceitaTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	// LISTA DE DESPESARECEITA QUE REPRESENTAM AS LINHAS.
	private List<DespesaReceita> linhas;

	// ARRAY DE STRINGS COM O NOME DAS COLUNAS.
	private String[] colunas = new String[] { "Cod.", "Descrição", "Valor",
			"Categoria", "Vencimento", "Pago" };

	// CRIA UM DESPESARECEITATABLEMODEL VAZIO.
	public DespesaReceitaTableModel() {
		linhas = new ArrayList<DespesaReceita>();
	}

	/*
	 * CRIA UM DESPESARECEITATABLEMODEL CARREGADO COM A LISTA DE DESPESARECEITA
	 * ESPECIFICADA.
	 */
	public DespesaReceitaTableModel(List<DespesaReceita> listaDeDespesaReceita) {
		linhas = new ArrayList<DespesaReceita>(listaDeDespesaReceita);
	}

	// RETORNA A QUANTIDADE DE COLUNAS.
	@Override
	public int getColumnCount() {
		// ESTA RETORNANDO O TAMANHO DO ARRAY "COLUNAS".
		return colunas.length;
	}

	// RETORNA A QUANTIDADE DE LINHAS.
	@Override
	public int getRowCount() {
		// RETORNA O TAMANHO DA LISTA DE ESTOQUEPRODUTO.
		return linhas.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		// RETORNA O CONTEUDO DO ARRAY QUE POSSUI O NOME DAS COLUNAS.
		return colunas[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DespesaReceita despesareceita = linhas.get(rowIndex);
		DateTimeFormatter dataFormat = DateTimeFormat.forPattern("dd/MM/yyyy");

		// RETORNA O CAMPO REFERENTE A COLUNA ESPECIFICADA.
		// AQUI E FEITO UM SWITCH PARA VERIFICAR QUAL E A COLUNA
		// E RETORNAR O CAMPO ADEQUADO. AS COLUNAS SAO AS MESMAS
		// QUE FORAM ESPECIFICADAS NO ARRAY "COLUNAS".
		switch (columnIndex) {
		case 0:
			return despesareceita.getId();
		case 1:
			return despesareceita.getDescricao();
		case 2:
			return despesareceita.getValor();
		case 3:
			return despesareceita.getCategoriaBean().getDescricao();
		case 4:
			return dataFormat.print(despesareceita.getDataVencimento());
		case 5:
			if(despesareceita.getDataMovimentacao() != null)
				return dataFormat.print(despesareceita.getDataMovimentacao());
			else
				return "-";
		default:
			// ISTO NAO DEVERIA ACONTECER...
			throw new IndexOutOfBoundsException(
					"Erro (IndexOutOfBoundsException)");
		}
	}

	// @Override
	// // MODIFICA NA LINHA E COLUNA ESPECIFICADA.
	// public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	// // CARREGA O ITEM DA LINHA QUE DEVE SER MODIFICADO.
	// DespesaReceita despesareceita = linhas.get(rowIndex);
	//
	// // SETA O VALOR DO CAMPO RESPECTIVO
	// switch (columnIndex) {
	// case 0:
	// despesareceita.setId((Integer) aValue);
	// case 1:
	// despesareceita.setProduto((Produto) aValue);
	// case 2:
	// despesareceita.setQuantidade((Float) aValue);
	// case 3:
	// despesareceita.setValorUnitario((Double) aValue);
	// case 4:
	// despesareceita.setDataFabricacao((Date) aValue);
	// case 5:
	// despesareceita.setDataValidade((Date) aValue);
	//
	// default:
	// // ISTO NAO DEVERIA ACONTECER...
	// }
	// fireTableCellUpdated(rowIndex, columnIndex);
	// }
	//
	// // MODIFICA NA LINHA ESPECIFICADA
	// public void setValueAt(DespesaReceita aValue, int rowIndex) {
	//
	// // CARREGA O ITEM DA LINHA QUE DEVE SER MODIFICADO
	// DespesaReceita despesareceita = linhas.get(rowIndex);
	//
	// despesareceita.setCodigoDespesaReceita(aValue.getCodigoDespesaReceita());
	// despesareceita.setProduto(aValue.getProduto());
	// despesareceita.setQuantidade(aValue.getQuantidade());
	// despesareceita.setValorUnitario(aValue.getValorUnitario());
	// despesareceita.setDataFabricacao(aValue.getDataFabricacao());
	// despesareceita.setDataValidade(aValue.getDataValidade());
	//
	// fireTableCellUpdated(rowIndex, 0);
	// fireTableCellUpdated(rowIndex, 1);
	// fireTableCellUpdated(rowIndex, 2);
	// fireTableCellUpdated(rowIndex, 3);
	// fireTableCellUpdated(rowIndex, 4);
	// fireTableCellUpdated(rowIndex, 5);
	//
	// };

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public DespesaReceita getDespesaReceita(int indiceLinha) {
		return linhas.get(indiceLinha);
	}

	// ADICIONA UM REGISTRO.
	public void addDespesaReceita(DespesaReceita m) {
		// ADICIONA O REGISTRO.
		linhas.add(m);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	// REMOVE A LINHA ESPECIFICADA.
	public void removeDespesaReceita(int indiceLinha) {
		linhas.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	// ADICIONA UMA LISTA DE ESTOQUEPRODUTO AO FINAL DOS REGISTROS.
	public void addListaDeDespesaReceita(List<DespesaReceita> despesareceita) {
		// PEGA O TAMANHO ANTIGO DA TABELA.
		int tamanhoAntigo = getRowCount();

		// ADICIONA OS REGISTROS.
		linhas.addAll(despesareceita);

		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	// REMOVE TODOS OS REGISTROS.
	public void limpar() {
		linhas.clear();

		fireTableDataChanged();
	}

	// VERIFICA SE ESTE TABLE MODEL ESTA VAZIO.
	public boolean isEmpty() {
		return linhas.isEmpty();
	}
}
