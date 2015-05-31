package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.TableColumnModel;

import controller.DespesaReceitaController;

public class ReceitaView extends JInternalFrame {
	/*
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ReceitaView() {
		initComponents();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {
		setBounds(100, 100, 700, 500);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);

		controllerDespesaReceita = new DespesaReceitaController();

		JButton btnAdicionarReceita = new JButton(BTN_ADICIONAR);
		btnAdicionarReceita
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						addReceitaView(evt);
					}
				});
		btnAdicionarReceita.setBounds(20, 42, 89, 23);

		JButton btnEditarReceita = new JButton(BTN_EDITAR);
		btnEditarReceita.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editReceitaView(evt);
			}
		});
		btnEditarReceita.setBounds(121, 42, 89, 23);

		vector.addElement("Pago e Não Pago");
		vector.addElement("Não Pago");
		vector.addElement("Pago");

		comboFiltro = new JComboBox(vector);
		comboFiltro.setBounds(20, 11, 190, 20);

		btnRemoverReceita = new JButton(BTN_REMOVER);
		btnRemoverReceita
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						removerAction(evt);
					}
				});
		btnRemoverReceita.setBounds(20, 437, 89, 23);

		fieldBusca = new JTextField();
		fieldBusca.setToolTipText(DICA_FIELD_BUSCAR);
		fieldBusca.setBounds(502, 11, 172, 20);
		fieldBusca.setColumns(10);
		fieldBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarAction(arg0);
			}
		});

		scrollPaneTabela = new JScrollPane();

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);

		carregaTabela();

		btnBuscar = new JButton(BTN_BUSCAR);
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buscarAction(evt);
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																scrollPaneTabela,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																660,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				btnAdicionarReceita)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnEditarReceita)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				76,
																				Short.MAX_VALUE)
																		.addComponent(
																				comboFiltro,
																				GroupLayout.PREFERRED_SIZE,
																				141,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				fieldBusca,
																				GroupLayout.PREFERRED_SIZE,
																				201,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnBuscar))
														.addComponent(
																btnRemoverReceita,
																Alignment.LEADING))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																fieldBusca,
																GroupLayout.PREFERRED_SIZE,
																31,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnBuscar,
																GroupLayout.PREFERRED_SIZE,
																31,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnAdicionarReceita,
																GroupLayout.DEFAULT_SIZE,
																28,
																Short.MAX_VALUE)
														.addComponent(
																btnEditarReceita,
																GroupLayout.PREFERRED_SIZE,
																28,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																comboFiltro,
																GroupLayout.PREFERRED_SIZE,
																29,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(scrollPaneTabela,
												GroupLayout.DEFAULT_SIZE, 364,
												Short.MAX_VALUE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(btnRemoverReceita,
												GroupLayout.PREFERRED_SIZE, 28,
												GroupLayout.PREFERRED_SIZE)
										.addGap(8)));

		scrollPaneTabela.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		addReceitaView = null;
		editReceitaView = null;
	}

	private void carregaTabela() {
		this.table.setModel(controllerDespesaReceita.getReceitaTableModel());
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

	private void buscarAction(java.awt.event.ActionEvent evt) {
		this.table.setModel(controllerDespesaReceita.buscar(
				comboFiltro.getSelectedIndex(), fieldBusca.getText(), 2));
	}

	private void removerAction(java.awt.event.ActionEvent evt) {
		boolean flag = false;

		if (table.getSelectedRow() == -1)
			showMessage(BTN_REMOVER, ERRO_REMOVER, 0);
		else
			flag = controllerDespesaReceita.remover(table.getValueAt(
					table.getSelectedRow(), 0));

		if (flag)
			carregaTabela();
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
							carregaTabela();
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
				showMessage(BTN_BUSCAR, ERRO_EDITAR, 0);

			else {
				editReceitaView = controllerDespesaReceita
						.abrirEditarReceitaView(table.getValueAt(
								table.getSelectedRow(), 0));
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
								carregaTabela();
							}
						});

				editReceitaView.setPosicao();
				editReceitaView.setVisible(true);
			}
		}
		else if(editReceitaView != null){
			showMessage(BTN_EDITAR, ATENCAO_EDITAR, 2);
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

	public void showMessage(String title, String msg, int type) {
		JOptionPane.showMessageDialog(null, msg, title, type);
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
	private String BTN_BUSCAR = "Buscar";
	private String DICA_FIELD_BUSCAR = "Digite a descrição da despesa.";
	private String ERRO_REMOVER = "Selecione uma receita para remover.";
	private String ERRO_EDITAR = "Selecione uma receita para editar.";
	private String ATENCAO_EDITAR = "Você já está editando uma receita.\nTermine a edição da receita atual para editar outra.";

	private JButton btnRemoverReceita;
	private JScrollPane scrollPaneTabela;
	@SuppressWarnings("rawtypes")
	private JComboBox comboFiltro;
	private Vector<String> vector = new Vector<String>();
	private TableColumnModel columnModel;

	private JTextField fieldBusca;
	private JButton btnBuscar;
	private JTable table;

	private AdicionaReceitaView addReceitaView;
	private EditarReceitaView editReceitaView;

	protected static DespesaReceitaController controllerDespesaReceita;
}
