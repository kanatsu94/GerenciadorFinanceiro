package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CartaoView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CartaoView() {
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 700, 500);
		setTitle(NOME_TELA);
		setIconifiable(true);
		
		btnAdicionarCartao = new JButton(BTN_ADICIONAR);
		btnAdicionarCartao
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						//--
						
					}
				});
		btnAdicionarCartao.setBounds(20, 42, 89, 23);
		
		btnEditaCartao = new JButton(BTN_EDITAR);
		btnEditaCartao.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//--
			}
		});
		btnEditaCartao.setBounds(121, 42, 89, 23);
		
		btnRemoveCartao = new JButton(BTN_REMOVER);
		btnRemoveCartao
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						//--
					}
				});
		btnRemoveCartao.setBounds(20, 437, 89, 23);
		
		btnBusca = new JButton(BTN_BUSCA);
		btnBusca.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//--
			}
		});
		
		fieldBusca = new JTextField();
		fieldBusca.setToolTipText(DICA_FIELD_BUSCAR);
		fieldBusca.setBounds(502, 11, 172, 20);
		fieldBusca.setColumns(10);
		fieldBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//--
			}
		});
		
		scrollPaneTabela = new JScrollPane();
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
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
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(fieldBusca, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBusca, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAdicionarCartao, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
						.addComponent(btnEditaCartao, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRemoveCartao, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneTabela, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		setVisible(true);
		
	}
	
	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
	}
	
	private final String NOME_TELA = "Cartão de crédito";
	private final String BTN_ADICIONAR = "Adicionar";
	private final String BTN_EDITAR = "Editar";
	private final String BTN_REMOVER = "Remover";
	private final String BTN_BUSCA = "Buscar";
	private String DICA_FIELD_BUSCAR = "Digite a descrição do cartão.";
	
	private JTextField fieldBusca;
	
	private JButton btnAdicionarCartao;
	private JButton btnEditaCartao;
	private JButton btnRemoveCartao;
	private JButton btnBusca;
	
	private JScrollPane scrollPaneTabela;
	
	private AdicionaCartao adicionaCartao;
}
