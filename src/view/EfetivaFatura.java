package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class EfetivaFatura extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUI = 1L;
	
	private String NOME_TELA = "Efetivar Fatura";
	private JTextField textField;
	
	public EfetivaFatura() {
		getContentPane().setForeground(Color.BLACK);
		initComponents();
	}
	
	private void initComponents() {
		setBounds(100, 100, 370, 210);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		getContentPane().setLayout(null);
		
		JLabel lblTemCertezaQue = new JLabel("*Selecione o cart\u00E3o de cr\u00E9dito");
		lblTemCertezaQue.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTemCertezaQue.setBounds(21, 25, 189, 14);
		getContentPane().add(lblTemCertezaQue);
		
		JButton btnCancelar = new JButton("Efetivar");
		btnCancelar.setBounds(246, 102, 89, 31);
		getContentPane().add(btnCancelar);
		
		JButton btnRemover = new JButton("Cancelar");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnRemover.setBounds(246, 136, 89, 31);
		getContentPane().add(btnRemover);
		
		String[] message = {"Selecione..."}; 
		JComboBox comboBox = new JComboBox(message);
		comboBox.setBounds(21, 50, 177, 31);
		getContentPane().add(comboBox);
		
		JLabel lblinformeAData = new JLabel("*Informe a data de pagamento");
		lblinformeAData.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblinformeAData.setBounds(21, 105, 189, 14);
		getContentPane().add(lblinformeAData);
		
		textField = new JTextField();
		textField.setBounds(21, 137, 177, 30);
		getContentPane().add(textField);
		textField.setColumns(10);
	}
	
	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 3,
				(d.height - this.getSize().height) / 4);
	}
}
