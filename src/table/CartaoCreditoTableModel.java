package table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.CartaoCredito;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CartaoCreditoTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	// LISTA DE CARTAOCREDITO QUE REPRESENTAM AS LINHAS.
	private List<CartaoCredito> linhas;

	// ARRAY DE STRINGS COM O NOME DAS COLUNAS.
	private String[] colunas = new String[] { "Cod.", "Descrição", "Dia Vencimento",
			"Dia Fechamento"};
	
	// CRIA UM CARTAOCREDITOTABLEMODEL VAZIO.
	public CartaoCreditoTableModel() {
		linhas = new ArrayList<CartaoCredito>();
	}

	/*
	 * CRIA UM CARTAOCREDITOTABLEMODEL CARREGADO COM A LISTA DE CARTAOCREDITO
	 * ESPECIFICADA.
	 */
	public CartaoCreditoTableModel(List<CartaoCredito> listaDeCartaoCredito) {
		linhas = new ArrayList<CartaoCredito>(listaDeCartaoCredito);
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
		CartaoCredito cartaocredito = linhas.get(rowIndex);
		DateTimeFormatter dataFormat = DateTimeFormat.forPattern("dd/MM/yyyy");

		// RETORNA O CAMPO REFERENTE A COLUNA ESPECIFICADA.
		// AQUI E FEITO UM SWITCH PARA VERIFICAR QUAL E A COLUNA
		// E RETORNAR O CAMPO ADEQUADO. AS COLUNAS SAO AS MESMAS
		// QUE FORAM ESPECIFICADAS NO ARRAY "COLUNAS".
		switch (columnIndex) {
		case 0:
			return cartaocredito.getId();
		case 1:
			return cartaocredito.getDescricao();
		case 2:
			return dataFormat.print(cartaocredito.getVencimento());
		case 3:
			return dataFormat.print(cartaocredito.getDiasFechamento());
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
	// CartaoCredito cartaocredito = linhas.get(rowIndex);
	//
	// // SETA O VALOR DO CAMPO RESPECTIVO
	// switch (columnIndex) {
	// case 0:
	// cartaocredito.setId((Integer) aValue);
	// case 1:
	// cartaocredito.setProduto((Produto) aValue);
	// case 2:
	// cartaocredito.setQuantidade((Float) aValue);
	// case 3:
	// cartaocredito.setValorUnitario((Double) aValue);
	// case 4:
	// cartaocredito.setDataFabricacao((Date) aValue);
	// case 5:
	// cartaocredito.setDataValidade((Date) aValue);
	//
	// default:
	// // ISTO NAO DEVERIA ACONTECER...
	// }
	// fireTableCellUpdated(rowIndex, columnIndex);
	// }
	//
	// // MODIFICA NA LINHA ESPECIFICADA
	// public void setValueAt(CartaoCredito aValue, int rowIndex) {
	//
	// // CARREGA O ITEM DA LINHA QUE DEVE SER MODIFICADO
	// CartaoCredito cartaocredito = linhas.get(rowIndex);
	//
	// cartaocredito.setCodigoCartaoCredito(aValue.getCodigoCartaoCredito());
	// cartaocredito.setProduto(aValue.getProduto());
	// cartaocredito.setQuantidade(aValue.getQuantidade());
	// cartaocredito.setValorUnitario(aValue.getValorUnitario());
	// cartaocredito.setDataFabricacao(aValue.getDataFabricacao());
	// cartaocredito.setDataValidade(aValue.getDataValidade());
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

	public CartaoCredito getCartaoCredito(int indiceLinha) {
		return linhas.get(indiceLinha);
	}

	// ADICIONA UM REGISTRO.
	public void addCartaoCredito(CartaoCredito m) {
		// ADICIONA O REGISTRO.
		linhas.add(m);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	// REMOVE A LINHA ESPECIFICADA.
	public void removeCartaoCredito(int indiceLinha) {
		linhas.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	// ADICIONA UMA LISTA DE ESTOQUEPRODUTO AO FINAL DOS REGISTROS.
	public void addListaDeCartaoCredito(List<CartaoCredito> cartaocredito) {
		// PEGA O TAMANHO ANTIGO DA TABELA.
		int tamanhoAntigo = getRowCount();

		// ADICIONA OS REGISTROS.
		linhas.addAll(cartaocredito);

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
