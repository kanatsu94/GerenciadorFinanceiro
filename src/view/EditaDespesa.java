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

import javax.swing.JTable;

import java.awt.Font;

import javax.swing.JSeparator;

import java.awt.Color;
import java.util.Vector;

public class EditaDespesa extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUI = 1L;
	
	private String NOME_TELA = "Despesa - Editar";
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JComboBox comboBox;
	private JLabel lblValor;
	private JLabel lbldata;
	private JTextField textField_4;
	private JLabel lblDespesaFixa;
	private JLabel lblconta;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JLabel lblcartoDeCrdito;
	private JComboBox pago;
	private JLabel lblPago;
	private JLabel lblDataDePagamento;
	private JLabel lblCategoria;
	private JTextField vencimento;
	private Vector<String> vectorSimNao = new Vector<String>();

	/**
	 * Create the frame.
	 */
	public EditaDespesa() {
		vectorSimNao.add("Não");
		vectorSimNao.add("Sim");
		initComponents();
	}
	
	private void initComponents(){
		setBounds(100, 100, 644, 440);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 633, 413);
		panel.setLayout(null);		
		
		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCdigo.setBounds(10, 16, 46, 31);
		panel.add(lblCdigo);
		
		textField = new JTextField();
		textField.setBounds(10, 43, 75, 31);
		panel.add(textField);
		textField.setColumns(15);
		textField.setEnabled(false);
		
		JLabel lbldescrio = new JLabel("*Descri\u00E7\u00E3o");
		lbldescrio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbldescrio.setBounds(104, 20, 69, 23);
		panel.add(lbldescrio);
		
		textField_1 = new JTextField();
		textField_1.setColumns(15);
		textField_1.setBounds(104, 43, 514, 31);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(15);
		textField_2.setBounds(10, 128, 75, 31);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(15);
		textField_3.setBounds(104, 128, 124, 31);
		panel.add(textField_3);
		
		comboBox = new JComboBox();
		comboBox.setBounds(356, 128, 262, 30);
		panel.add(comboBox);
		
		lblValor = new JLabel("*Valor");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblValor.setBounds(10, 94, 46, 14);
		panel.add(lblValor);
		
		lbldata = new JLabel("*Data de Vencimento");
		lbldata.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbldata.setBounds(104, 94, 146, 14);
		panel.add(lbldata);
		
		textField_4 = new JTextField();
		textField_4.setColumns(15);
		textField_4.setBounds(244, 128, 89, 31);
		panel.add(textField_4);
		
		lblDespesaFixa = new JLabel("*Despesa Fixa");
		lblDespesaFixa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDespesaFixa.setBounds(241, 94, 120, 14);
		panel.add(lblDespesaFixa);
		
		lblconta = new JLabel("*Conta");
		lblconta.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblconta.setBounds(10, 170, 46, 14);
		panel.add(lblconta);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(10, 195, 220, 30);
		panel.add(comboBox_1);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(356, 195, 262, 30);
		panel.add(comboBox_2);
		
		lblcartoDeCrdito = new JLabel("*Cart\u00E3o de Cr\u00E9dito");
		lblcartoDeCrdito.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblcartoDeCrdito.setBounds(356, 170, 120, 14);
		panel.add(lblcartoDeCrdito);
		
		pago = new JComboBox(vectorSimNao);
		pago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pago.getSelectedItem().equals("Não")){
					vencimento.setEnabled(false);
				}
				else{
					vencimento.setEnabled(true);
				}
			}
		});
		pago.setBounds(10, 285, 89, 30);
		
		panel.add(pago);
		
		lblPago = new JLabel("Pago");
		lblPago.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPago.setBounds(10, 250, 46, 24);
		panel.add(lblPago);
		
		lblDataDePagamento = new JLabel("*Data de Pagamento");
		lblDataDePagamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDataDePagamento.setBounds(130, 255, 120, 19);
		panel.add(lblDataDePagamento);
		
		lblCategoria = new JLabel("*Categoria");
		lblCategoria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCategoria.setBounds(356, 86, 120, 31);
		panel.add(lblCategoria);
		
		vencimento = new JTextField();
		vencimento.setColumns(15);
		vencimento.setBounds(130, 285, 98, 31);
		panel.add(vencimento);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.DARK_GRAY);
		separator.setBounds(10, 338, 608, 30);
		panel.add(separator);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(10, 358, 89, 30);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCancelar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(529, 356, 89, 30);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SalvaDespesa objTela = new SalvaDespesa();
				getContentPane().add(objTela);
				panel.setVisible(false);
				objTela.setVisible(true);
				objTela.setPosicao();
			}
		});
		panel.add(btnSalvar);
		
		getContentPane().setLayout(null);
		getContentPane().add(panel);
	}
	

	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 11,
				(d.height - this.getSize().height) / 11);
	}
}