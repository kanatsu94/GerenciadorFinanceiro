package view;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class AdicionaDespesa extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUI = 1L;

	private String NOME_TELA = "Despesa - Adicionar";
	private JTextField descricao;
	private JTextField valor;
	private JTextField vencimento;
	private JLabel lblvalor;
	private JLabel lblNewLabel;
	private JLabel lbldespesaFixa;
	private JComboBox categoria;
	private JLabel lblcategoria;
	private JComboBox conta;
	private JComboBox cartao;
	private JTextField repetir;
	private JComboBox pago;
	private JTextField dataPgto;
	private JLabel lblNewLabel_1;
	private JLabel lblCartoDeCrdito;
	private JLabel lblRepetir;
	private JLabel lblNewLabel_2;
	private JLabel lbldataDePagamento;
	private JLabel lblconta;
	private Vector<String> vectorSimNao = new Vector<String>();
	
	public AdicionaDespesa() {
		vectorSimNao.add("Não");
		vectorSimNao.add("Sim");
		initComponents();
	}
	
	private void initComponents() {
		setBounds(100, 100, 663, 464);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBounds(0, 0, 647, 435);
		panel.setLayout(null);
		
		JLabel lbldescrio = new JLabel("*Descri\u00E7\u00E3o");
		lbldescrio.setBounds(10, 24, 74, 14);
		lbldescrio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lbldescrio);
		
		descricao = new JTextField();
		descricao.setBounds(10, 50, 628, 31);
		descricao.setColumns(15);
		panel.add(descricao);
		
		valor = new JTextField();
		valor.setBounds(10, 129, 102, 31);
		valor.setColumns(15);
		panel.add(valor);
		
		vencimento = new JTextField();
		vencimento.setBounds(140, 129, 120, 31);
		vencimento.setColumns(15);
		panel.add(vencimento);
		
		JComboBox despesaFixa = new JComboBox(vectorSimNao);
		despesaFixa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		despesaFixa.setBounds(301, 128, 74, 31);
		panel.add(despesaFixa);
		
		lblvalor = new JLabel("*Valor");
		lblvalor.setBounds(10, 104, 46, 14);
		lblvalor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblvalor);
		
		lblNewLabel = new JLabel("*Data de Vencimento");
		lblNewLabel.setBounds(140, 104, 131, 14);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblNewLabel);
		
		lbldespesaFixa = new JLabel("*Despesa Fixa");
		lbldespesaFixa.setBounds(291, 104, 111, 14);
		lbldespesaFixa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lbldespesaFixa);
		
		categoria = new JComboBox();
		categoria.setBounds(423, 129, 215, 31);
		panel.add(categoria);
		
		lblcategoria = new JLabel("*Categoria");
		lblcategoria.setBounds(423, 96, 74, 31);
		lblcategoria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblcategoria);
		
		conta = new JComboBox();
		conta.setBounds(10, 212, 222, 31);
		panel.add(conta);
		
		cartao = new JComboBox();
		cartao.setBounds(291, 212, 225, 31);
		panel.add(cartao);
		
		repetir = new JTextField();
		repetir.setBounds(562, 212, 76, 31);
		repetir.setColumns(15);
		panel.add(repetir);
		
		pago = new JComboBox(vectorSimNao);
		pago.setFont(new Font("Tahoma", Font.PLAIN, 13));
		pago.setBounds(10, 311, 74, 31);
		pago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pago.getSelectedItem().equals("Não")){
					dataPgto.setEnabled(false);
				}
				else{
					dataPgto.setEnabled(true);
				}
			}
		});
		panel.add(pago);
		
		dataPgto = new JTextField();
		dataPgto.setBounds(125, 311, 107, 31);
		dataPgto.setColumns(15);
		panel.add(dataPgto);
		
		lblNewLabel_1 = new JLabel("*Conta");
		lblNewLabel_1.setBounds(30, 186, 46, 14);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblNewLabel);
		
		lblCartoDeCrdito = new JLabel("Cart\u00E3o de Cr\u00E9dito");
		lblCartoDeCrdito.setBounds(291, 187, 120, 14);
		lblCartoDeCrdito.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblCartoDeCrdito);
		
		lblRepetir = new JLabel("Repetir");
		lblRepetir.setBounds(562, 187, 46, 14);
		lblRepetir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblRepetir);
		
		lblNewLabel_2 = new JLabel("Pago");
		lblNewLabel_2.setBounds(10, 285, 46, 15);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblNewLabel_2);
		
		lbldataDePagamento = new JLabel("*Data de Pagamento");
		lbldataDePagamento.setBounds(115, 277, 146, 31);
		lbldataDePagamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lbldataDePagamento);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 362, 627, 14);
		separator.setForeground(Color.DARK_GRAY);
		panel.add(separator);		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(10, 387, 89, 30);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnCancelar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(549, 387, 89, 30);
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
		
		lblconta = new JLabel("*Conta");
		lblconta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblconta.setBounds(10, 188, 120, 14);
		panel.add(lblconta);
	}
	
	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 11,
				(d.height - this.getSize().height) / 11);
	}
}
