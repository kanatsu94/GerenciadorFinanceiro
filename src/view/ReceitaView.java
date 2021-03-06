package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.TableColumnModel;

import org.joda.time.LocalDate;

import util.ExibeMensagem;
import util.NumberFieldDocument;
import controller.DespesaReceitaController;

public class ReceitaView extends JInternalFrame {
	/*
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ReceitaView() {
		controllerDespesaReceita = new DespesaReceitaController();
		this.vetorPago = new Vector<String>();
		
		this.vetorPago.addElement("Pago e N�o Pago");
		this.vetorPago.addElement("N�o Pago");
		this.vetorPago.addElement("Pago");
		
		this.vetorMes = new Vector<String>();

		this.vetorMes.addElement("Jan");
		this.vetorMes.addElement("Fev");
		this.vetorMes.addElement("Mar");
		this.vetorMes.addElement("Abr");
		this.vetorMes.addElement("Mai");
		this.vetorMes.addElement("Jun");
		this.vetorMes.addElement("Jul");
		this.vetorMes.addElement("Ago");
		this.vetorMes.addElement("Set");
		this.vetorMes.addElement("Out");
		this.vetorMes.addElement("Nov");
		this.vetorMes.addElement("Dez");
		
		this.RECEITA = 2;

		initComponents();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {
		setBounds(100, 100, 700, 500);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);

		comboSituacao = new JComboBox(vetorPago);
		comboSituacao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				filtrarAction();
			}
		});
		comboSituacao.setBounds(20, 11, 190, 20);
		comboSituacao.setToolTipText(DICA_COMBO_SITUACAO);

		fieldBusca = new JTextField();
		fieldBusca.setToolTipText(DICA_FIELD_BUSCAR);
		fieldBusca.setBounds(502, 11, 172, 20);
		fieldBusca.setColumns(10);
		fieldBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtrarAction();
			}
		});


		scrollPaneTabela = new JScrollPane();

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);

		lblTotal = new JLabel(LBL_TOTAL);
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 12));

		comboMes = new JComboBox(vetorMes);
		comboMes.setToolTipText(DICA_COMBO_MES);
		comboMes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filtrarAction();
			}
		});

		fieldAno = new JTextField();
		fieldAno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtrarAction();
			}
		});
		fieldAno.setDocument(new NumberFieldDocument());
		fieldAno.setColumns(10);
		fieldAno.setToolTipText(DICA_FIELD_ANO);
		fieldAno.setText("" +new LocalDate().getYear());

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		this.btnAdicionarReceita = new JButton(BTN_ADICIONAR);
		menuBar.add(btnAdicionarReceita);
		btnAdicionarReceita
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						addReceitaView(evt);
					}
				});
		btnAdicionarReceita.setBounds(20, 42, 89, 23);

		this.btnEditarReceita = new JButton(BTN_EDITAR);
		menuBar.add(btnEditarReceita);
		btnEditarReceita.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editReceitaView(evt);
			}
		});
		btnEditarReceita.setBounds(121, 42, 89, 23);

		btnRemoverReceita = new JButton(BTN_REMOVER);
		menuBar.add(btnRemoverReceita);
		btnRemoverReceita
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						removerAction(evt);
					}
				});
		btnRemoverReceita.setBounds(20, 437, 89, 23);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneTabela, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
						.addComponent(lblTotal)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(fieldAno, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboMes, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboSituacao, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(fieldBusca, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(fieldBusca, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
						.addComponent(fieldAno, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboMes, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboSituacao, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblTotal)
					.addContainerGap())
		);

		scrollPaneTabela.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		addReceitaView = null;
		editReceitaView = null;

		comboMes.setSelectedIndex(new LocalDate().getMonthOfYear() - 1);
	}

	private void carregaTabela(int mes, String ano) {
		this.table.setModel(controllerDespesaReceita.getReceitaTableModel(mes, Integer.parseInt(ano)));
		this.lblTotal.setText(controllerDespesaReceita.getTotalReceita());
		setColumnWidth();
	}

	private void setColumnWidth() {
		this.columnModel = this.table.getColumnModel();

		this.columnModel.getColumn(0).setPreferredWidth(40);
		this.columnModel.getColumn(1).setPreferredWidth(200);
		this.columnModel.getColumn(2).setPreferredWidth(100);
		this.columnModel.getColumn(3).setPreferredWidth(140);
		this.columnModel.getColumn(4).setPreferredWidth(90);
		this.columnModel.getColumn(5).setPreferredWidth(90);
	}

	private void filtrarAction() {
		this.table.setModel(controllerDespesaReceita.buscar(
				comboSituacao.getSelectedIndex(), fieldBusca.getText(), 2, comboMes.getSelectedIndex() + 1, Integer.parseInt(fieldAno.getText())));
		this.lblTotal.setText(controllerDespesaReceita.getTotalReceita());
		setColumnWidth();
	}

	private void removerAction(java.awt.event.ActionEvent evt) {
		boolean flag = false;

		if (table.getSelectedRow() == -1)
			ExibeMensagem.showMessage(ERRO_REMOVER, BTN_REMOVER, 0);
		else
			flag = controllerDespesaReceita.remover(table.getValueAt(
					table.getSelectedRow(), 0), RECEITA);

		if (flag)
			carregaTabela(comboMes.getSelectedIndex() + 1, fieldAno.getText());
	}

	private void addReceitaView(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itemEstoqueProdutoActionPerformed
		if (addReceitaView == null) {
			addReceitaView = new AdicionaReceitaView();
			PrincipalView.getPainel().add(addReceitaView);
			addReceitaView.setIconifiable(false);
			addReceitaView.setClosable(false);

			// METODO PARA SER EXECUTADO QUANDO A INTERNAL FRAME
			// FOR FECHADA.
			addReceitaView
					.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
						public void internalFrameClosing(InternalFrameEvent e) {
							addReceitaView = null;
							carregaTabela(comboMes.getSelectedIndex() + 1, fieldAno.getText());
						}
					});

			addReceitaView.setPosicao();
			addReceitaView.setVisible(true);
		}

		// TRAZ A TELA DE ADICIONAR RECEITA PARA FRENTE
		try {
			addReceitaView.setSelected(true);
			addReceitaView.requestFocusInWindow();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editReceitaView(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itemEstoqueProdutoActionPerformed
		if (editReceitaView == null) {

			if (table.getSelectedRow() == -1)
				ExibeMensagem.showMessage(ERRO_EDITAR, BTN_EDITAR, 0);

			else {
				editReceitaView = controllerDespesaReceita
						.abrirEditarReceitaView(
									table.getSelectedRow()
								);
				PrincipalView.getPainel().add(editReceitaView);
				editReceitaView.setIconifiable(false);
				editReceitaView.setClosable(false);

				// METODO PARA SER EXECUTADO QUANDO A INTERNAL FRAME
				// FOR FECHADA.
				editReceitaView
						.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
							public void internalFrameClosing(
									InternalFrameEvent e) {
								editReceitaView = null;
								carregaTabela(comboMes.getSelectedIndex() + 1, fieldAno.getText());
								comboSituacao.setSelectedIndex(0);
							}
						});

				editReceitaView.setPosicao();
				editReceitaView.setVisible(true);
			}
		} else if (editReceitaView != null) {
			ExibeMensagem.showMessage(ATENCAO_EDITAR, BTN_EDITAR, 2);
		}

		// TRAZ A TELA DE ADICIONAR RECEITA PARA FRENTE
		if (editReceitaView != null) {
			try {
				editReceitaView.setSelected(true);
				editReceitaView.requestFocusInWindow();
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
	}

	private String NOME_TELA = "Receita";
	private String BTN_EDITAR = "Editar";
	private String BTN_ADICIONAR = "Adicionar";
	private String BTN_REMOVER = "Remover";
	private String LBL_TOTAL = "Total: R$";
	private String DICA_FIELD_BUSCAR = "Digite a descri��o da receita.";
	private String DICA_FIELD_ANO = "Digite o ano.";
	private String DICA_COMBO_MES = "Selecione o m�s.";
	private String DICA_COMBO_SITUACAO = "Selecione a situa��o.";
	private String ERRO_REMOVER = "Selecione uma receita para remover.";
	private String ERRO_EDITAR = "Selecione uma receita para editar.";
	private String ATENCAO_EDITAR = "Voc� j� est� editando uma receita.\nTermine a edi��o da receita atual para editar outra.";

	private JButton btnRemoverReceita;
	private JButton btnAdicionarReceita;
	private JButton btnEditarReceita;
	private JScrollPane scrollPaneTabela;
	@SuppressWarnings("rawtypes")
	private JComboBox comboSituacao;
	private Vector<String> vetorPago;
	private Vector<String> vetorMes;
	private TableColumnModel columnModel;

	private JTextField fieldBusca;
	private JTable table;

	private JLabel lblTotal;

	private AdicionaReceitaView addReceitaView;
	private EditarReceitaView editReceitaView;

	protected static DespesaReceitaController controllerDespesaReceita;
	private JMenuBar menuBar;
	@SuppressWarnings("rawtypes")
	private JComboBox comboMes;
	private JTextField fieldAno;
	
	private int RECEITA;
}
