package view;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JTable;

import java.awt.Color;
import java.beans.PropertyVetoException;

public class DespesaView extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUI = 1L;	

	/**
	 * Create the frame.
	 */
	public DespesaView() {
		initComponents();
	}
	
	private void initComponents(){
		setBounds(100, 100, 691, 509);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 684, 480);
		panel.setLayout(null);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
		);

		
		vector.addElement("Pago e Não Pago");
		vector.addElement("Não Pago");
		vector.addElement("Pago");

		JComboBox comboBox = new JComboBox(vector);
		comboBox.setBounds(36, 14, 188, 26);
		panel.add(comboBox);
		
		txtBuscar = new JTextField();
		txtBuscar.setText(" Buscar");
		txtBuscar.setBounds(459, 14, 188, 31);
		panel.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				AdicionaDespesa objTela = new AdicionaDespesa();
				
				getDesktopPane().add(objTela);
				//panel.setVisible(false);
				objTela.setVisible(true);
				objTela.setPosicao();
				try {
					setIcon(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdicionar.setBounds(36, 61, 89, 30);
		panel.add(btnAdicionar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditaDespesa objTela = new EditaDespesa();
				getContentPane().add(objTela);
				panel.setVisible(false);
				objTela.setVisible(true);
				objTela.setPosicao();
			}
		});
		btnEditar.setBounds(135, 61, 89, 30);
		panel.add(btnEditar);
		
		JButton btnEfetivarFaturaDo = new JButton("Efetivar Fatura do Cart\u00E3o");
		btnEfetivarFaturaDo.setBounds(459, 61, 188, 30);
		btnEfetivarFaturaDo.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				EfetivaFatura objTela = new EfetivaFatura();
				getContentPane().add(objTela);
				panel.setVisible(false);
				objTela.setVisible(true);
				objTela.setPosicao();
			}
		});
		panel.add(btnEfetivarFaturaDo);
		
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setBounds(36, 109, 608, 317);/*
		table.setBorder(UIManager.getBorder("Button.border"));
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", "Conta de \u00C1gua", "R$ 75,80", "Moradia", "15/04/2015", "14/04/2015"},
				{"2", "Conta de Energia", "R$ 143,60", "Moradia", "17/04/2015", "-"},
				{"3", "Camiseta", "R$ 100,00", "Roupa", "12/04/2015", "-"},
			},
			new String[] {
				"C\u00F3d.", "Descri\u00E7\u00E3o", "Valor", "Categoria", "Vencimento", "Pago"
			}
		));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(115);
		*/
		panel.add(table);
		
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.setBounds(36, 437, 89, 30);
		btnRemover.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				RemoveDespesa objTela = new RemoveDespesa();
				getContentPane().add(objTela);
				panel.setVisible(false);
				objTela.setVisible(true);
				objTela.setPosicao();
			}
		});
		panel.add(btnRemover);
		
		getContentPane().setLayout(null);
		getContentPane().setLayout(groupLayout);
		getContentPane().add(panel);
	}
	

	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
	}
	
	private String NOME_TELA = "Despesa";
	private JTextField txtBuscar;
	private JTable table;
	private Vector<String> vector = new Vector<String>();
}