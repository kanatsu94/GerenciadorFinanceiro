package view;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JTable;

import controller.DespesaReceitaController;

public class ReceitaView extends JInternalFrame {
	/*
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ReceitaView() {
		initComponents();
	}

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
		btnEditarReceita.setBounds(121, 42, 89, 23);

		vector.addElement("Pago e Não Pago");
		vector.addElement("Não Pago");
		vector.addElement("Pago");

		comboFiltro = new JComboBox(vector);
		comboFiltro.setBounds(20, 11, 190, 20);

		btnRemoverReceita = new JButton(BTN_REMOVER);
		btnRemoverReceita.setBounds(20, 437, 89, 23);

		fieldBusca = new JTextField();
		fieldBusca.setToolTipText(DICA_FIELD_BUSCAR);
		fieldBusca.setBounds(502, 11, 172, 20);
		fieldBusca.setColumns(10);

		scrollPaneTabela = new JScrollPane();
		
		table = new JTable();
		
		carregaTabela();

		btnBuscar = new JButton(BTN_BUSCAR);

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
	}
	
	private void carregaTabela(){
		this.table.setModel(controllerDespesaReceita.getReceitaTableModel());
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
	private JButton btnRemoverReceita;
	private JScrollPane scrollPaneTabela;
	private JComboBox comboFiltro;
	private Vector<String> vector = new Vector<String>();
	private AdicionaReceitaView addReceitaView;
	private JTextField fieldBusca;
	private JButton btnBuscar;
	private JTable table;
	
	private DespesaReceitaController controllerDespesaReceita;
}
