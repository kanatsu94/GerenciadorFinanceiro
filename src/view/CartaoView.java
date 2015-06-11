package view;

import java.awt.Dimension;
<<<<<<< HEAD
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Vector;
=======
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Principal;
>>>>>>> origin/master

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.TableColumnModel;

import org.joda.time.LocalDate;

import util.NumberFieldDocument;
import controller.CartaoCreditoController;

public class CartaoView extends JInternalFrame {
	/*
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CartaoView() {
		controller = new CartaoCreditoController();
		
		initComponents();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {
		setBounds(100, 100, 700, 500);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
<<<<<<< HEAD

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

		btnBuscar = new JButton(BTN_BUSCAR);
		btnBuscar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buscarAction(evt);
			}
		});

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		this.btnAdicionarCartao = new JButton(BTN_ADICIONAR);
		menuBar.add(btnAdicionarCartao);
		btnAdicionarCartao
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						addReceitaView(evt);
=======
		
		btnAdicionarCartao = new JButton(BTN_ADICIONAR);
		btnAdicionarCartao.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						addCartaoView();
>>>>>>> origin/master
					}
				});
		btnAdicionarCartao.setBounds(20, 42, 89, 23);

		this.btnEditarCartao = new JButton(BTN_EDITAR);
		menuBar.add(btnEditarCartao);
		btnEditarCartao.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
<<<<<<< HEAD
				editReceitaView(evt);
=======
				edtCartaoView();
>>>>>>> origin/master
			}
		});
		btnEditarCartao.setBounds(121, 42, 89, 23);

		btnRemoverCartao = new JButton(BTN_REMOVER);
		menuBar.add(btnRemoverCartao);
		btnRemoverCartao
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						removerAction(evt);
					}
				});
		btnRemoverCartao.setBounds(20, 437, 89, 23);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
<<<<<<< HEAD
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneTabela, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(292)
							.addComponent(fieldBusca, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBuscar)))
=======
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneTabela, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAdicionarCartao)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEditaCartao)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRemoveCartao)
							.addPreferredGap(ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
							.addComponent(fieldBusca, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBusca)))
>>>>>>> origin/master
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
<<<<<<< HEAD
						.addComponent(btnBuscar, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
						.addComponent(fieldBusca, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
					.addGap(44))
=======
						.addComponent(fieldBusca, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBusca, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdicionarCartao, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
						.addComponent(btnEditaCartao, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRemoveCartao, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneTabela, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
>>>>>>> origin/master
		);

		scrollPaneTabela.setViewportView(table);
		getContentPane().setLayout(groupLayout);
//		addReceitaView = null;
//		editReceitaView = null;
	}

	private void carregaTabela() {
		this.table.setModel(controller.getTableModel());
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
<<<<<<< HEAD

	private void buscarAction(java.awt.event.ActionEvent evt) {
//		this.table.setModel(controllerDespesaReceita.buscar(
//				comboSituacao.getSelectedIndex(), fieldBusca.getText(), 2, comboMes.getSelectedIndex() + 1, Integer.parseInt(fieldAno.getText())));
//		this.lblTotal.setText(controllerDespesaReceita.getTotalReceita());
//		setColumnWidth();
	}

	private void removerAction(java.awt.event.ActionEvent evt) {
//		boolean flag = false;
//
//		if (table.getSelectedRow() == -1)
//			showMessage(BTN_REMOVER, ERRO_REMOVER, 0);
//		else
//			flag = controllerDespesaReceita.remover(table.getValueAt(
//					table.getSelectedRow(), 0), RECEITA);
//
//		if (flag)
//			carregaTabela(comboMes.getSelectedIndex() + 1, fieldAno.getText());
	}

	private void addReceitaView(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itemEstoqueProdutoActionPerformed
//		if (addReceitaView == null) {
//			addReceitaView = new AdicionaReceitaView();
//			PrincipalView.getPainel().add(addReceitaView);
//			addReceitaView.setIconifiable(false);
//			addReceitaView.setClosable(false);
//
//			// METODO PARA SER EXECUTADO QUANDO A INTERNAL FRAME
//			// FOR FECHADA.
//			addReceitaView
//					.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
//						public void internalFrameClosing(InternalFrameEvent e) {
//							addReceitaView = null;
//							carregaTabela(comboMes.getSelectedIndex() + 1, fieldAno.getText());
//						}
//					});
//
//			addReceitaView.setPosicao();
//			addReceitaView.setVisible(true);
//		}
//
//		// TRAZ A TELA DE ADICIONAR RECEITA PARA FRENTE
//		try {
//			addReceitaView.setSelected(true);
//			addReceitaView.requestFocusInWindow();
//		} catch (PropertyVetoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private void editReceitaView(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itemEstoqueProdutoActionPerformed
//		if (editReceitaView == null) {
//
//			if (table.getSelectedRow() == -1)
//				showMessage(BTN_BUSCAR, ERRO_EDITAR, 0);
//
//			else {
//				editReceitaView = controllerDespesaReceita
//						.abrirEditarReceitaView(table.getValueAt(
//								table.getSelectedRow(), 0));
//				PrincipalView.getPainel().add(editReceitaView);
//				editReceitaView.setIconifiable(false);
//				editReceitaView.setClosable(false);
//
//				// METODO PARA SER EXECUTADO QUANDO A INTERNAL FRAME
//				// FOR FECHADA.
//				editReceitaView
//						.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
//							public void internalFrameClosing(
//									InternalFrameEvent e) {
//								editReceitaView = null;
//								carregaTabela(comboMes.getSelectedIndex() + 1, fieldAno.getText());
//							}
//						});
//
//				editReceitaView.setPosicao();
//				editReceitaView.setVisible(true);
//			}
//		} else if (editReceitaView != null) {
//			showMessage(BTN_EDITAR, ATENCAO_EDITAR, 2);
//		}
//
//		// TRAZ A TELA DE ADICIONAR RECEITA PARA FRENTE
//		if (editReceitaView != null) {
//			try {
//				editReceitaView.setSelected(true);
//				editReceitaView.requestFocusInWindow();
//			} catch (PropertyVetoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	public void showMessage(String title, String msg, int type) {
		JOptionPane.showMessageDialog(null, msg, title, type);
	}

=======
	
	private void addCartaoView(){
		adicionaCartao = new AdicionaCartao();
		PrincipalView.getPainel().add(adicionaCartao);
		adicionaCartao.setIconifiable(false);
		adicionaCartao.setClosable(false);
		adicionaCartao.moveToFront();
	}
	
	private void edtCartaoView(){
		editaCartao = new EditaCartao();
		PrincipalView.getPainel().add(editaCartao);
		editaCartao.setIconifiable(false);
		editaCartao.setClosable(false);
		editaCartao.moveToFront();
	}
	
>>>>>>> origin/master
	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
	}
<<<<<<< HEAD

	private String NOME_TELA = "Cartão de crédito";
	private String BTN_EDITAR = "Editar";
	private String BTN_ADICIONAR = "Adicionar";
	private String BTN_REMOVER = "Remover";
	private String BTN_BUSCAR = "Buscar";
=======
	
	private final String NOME_TELA = "Cartão de crédito";
	private final String BTN_ADICIONAR = "Adicionar";
	private final String BTN_EDITAR = "Editar";
	private final String BTN_REMOVER = "Remover";
	private final String BTN_BUSCA = "Buscar";
>>>>>>> origin/master
	private String DICA_FIELD_BUSCAR = "Digite a descrição do cartão.";
	private String ERRO_REMOVER = "Selecione um cartão para remover.";
	private String ERRO_EDITAR = "Selecione um cartão para editar.";
	private String ATENCAO_EDITAR = "Você já está editando um cartão.\nTermine a edição do cartão atual para editar outro.";

	private JButton btnRemoverCartao;
	private JButton btnAdicionarCartao;
<<<<<<< HEAD
	private JButton btnEditarCartao;
	private JScrollPane scrollPaneTabela;
	private TableColumnModel columnModel;

	private JTextField fieldBusca;
	private JButton btnBuscar;
	private JTable table;

//	private AdicionaReceitaView addReceitaView;
//	private EditarReceitaView editReceitaView;

	protected static CartaoCreditoController controller;
	private JMenuBar menuBar;
=======
	private JButton btnEditaCartao;
	private JButton btnRemoveCartao;
	private JButton btnBusca;
	
	private JScrollPane scrollPaneTabela;
	
	private AdicionaCartao adicionaCartao;
	private EditaCartao editaCartao;
>>>>>>> origin/master
}
