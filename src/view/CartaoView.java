package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.TableColumnModel;

import util.ExibeMensagem;
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

	private void initComponents() {
		setBounds(100, 100, 630, 450);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);

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
						addCartaoView(evt);
					}
				});
		btnAdicionarCartao.setBounds(20, 42, 89, 23);

		this.btnEditarCartao = new JButton(BTN_EDITAR);
		menuBar.add(btnEditarCartao);
		btnEditarCartao.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCartaoView(evt);
			}
		});
		btnEditarCartao.setBounds(121, 42, 89, 23);

		btnDesativarCartao = new JButton(BTN_DESATIVAR);
		menuBar.add(btnDesativarCartao);
		btnDesativarCartao
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						desativarAction(evt);
					}
				});
		btnDesativarCartao.setBounds(20, 437, 89, 23);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(fieldBusca, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBuscar))
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBuscar, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
						.addComponent(fieldBusca, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneTabela, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		scrollPaneTabela.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		carregaTabela();
		addCartaoView = null;
		editCartaoView = null;
	}

	private void carregaTabela() {
		this.table.setModel(controller.getTableModel(this.fieldBusca.getText()));
		setColumnWidth();
	}

	private void setColumnWidth() {
		this.columnModel = this.table.getColumnModel();

		this.columnModel.getColumn(0).setPreferredWidth(44);
		this.columnModel.getColumn(1).setPreferredWidth(350);
		this.columnModel.getColumn(2).setPreferredWidth(100);
		this.columnModel.getColumn(3).setPreferredWidth(100);
	}

	private void buscarAction(java.awt.event.ActionEvent evt) {
		this.carregaTabela();
		setColumnWidth();
	}

	private void desativarAction(java.awt.event.ActionEvent evt) {
		boolean flag = false;

		if (table.getSelectedRow() == -1)
			ExibeMensagem.showMessage(ERRO_DESATIVAR, BTN_DESATIVAR, 0);
		else
			flag = controller.desativar(table.getValueAt(
					table.getSelectedRow(), 0));

		if (flag)
			carregaTabela();
	}

	private void addCartaoView(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itemEstoqueProdutoActionPerformed
		if (addCartaoView == null) {
			addCartaoView = new AdicionaCartaoView();
			PrincipalView.getPainel().add(addCartaoView);
			addCartaoView.setIconifiable(false);
			addCartaoView.setClosable(false);

			// METODO PARA SER EXECUTADO QUANDO A INTERNAL FRAME
			// FOR FECHADA.
			addCartaoView
					.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
						public void internalFrameClosing(InternalFrameEvent e) {
							addCartaoView = null;
							carregaTabela();
						}
					});

			addCartaoView.setPosicao();
			addCartaoView.setVisible(true);
		}

		// TRAZ A TELA DE ADICIONAR CARTAO PARA FRENTE
		try {
			addCartaoView.setSelected(true);
			addCartaoView.requestFocusInWindow();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editCartaoView(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itemEstoqueProdutoActionPerformed
		if (editCartaoView == null) {

			if (table.getSelectedRow() == -1)
				ExibeMensagem.showMessage(ERRO_EDITAR, BTN_EDITAR, 0);

			else {
				editCartaoView = controller.abrirEditarCartaoView(
						table.getSelectedRow()
						);
				PrincipalView.getPainel().add(editCartaoView);
				editCartaoView.setIconifiable(false);
				editCartaoView.setClosable(false);

				// METODO PARA SER EXECUTADO QUANDO A INTERNAL FRAME
				// FOR FECHADA.
				editCartaoView
						.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
							public void internalFrameClosing(
									InternalFrameEvent e) {
								editCartaoView = null;
								carregaTabela();
							}
						});

				editCartaoView.setPosicao();
				editCartaoView.setVisible(true);
			}
		} else if (editCartaoView != null) {
			ExibeMensagem.showMessage(ATENCAO_EDITAR, BTN_EDITAR, 2);
		}

		// TRAZ A TELA DE ADICIONAR RECEITA PARA FRENTE
		if (editCartaoView != null) {
			try {
				editCartaoView.setSelected(true);
				editCartaoView.requestFocusInWindow();
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

	private String NOME_TELA = "Cartão de Crédito";
	private String BTN_EDITAR = "Editar";
	private String BTN_ADICIONAR = "Adicionar";
	private String BTN_DESATIVAR = "Desativar";
	private String BTN_BUSCAR = "Buscar";
	private String DICA_FIELD_BUSCAR = "Digite a descrição do cartão.";
	private String ERRO_DESATIVAR = "Selecione um cartão para desativar.";
	private String ERRO_EDITAR = "Selecione um cartão para editar.";
	private String ATENCAO_EDITAR = "Você já está editando um cartão.\nTermine a edição do cartão atual para editar outro.";

	private JButton btnDesativarCartao;
	private JButton btnAdicionarCartao;
	private JButton btnEditarCartao;
	private JScrollPane scrollPaneTabela;
	private TableColumnModel columnModel;

	private JTextField fieldBusca;
	private JButton btnBuscar;
	private JTable table;

	private AdicionaCartaoView addCartaoView;
	private EditarCartaoView editCartaoView;

	protected static CartaoCreditoController controller;
	private JMenuBar menuBar;
}
