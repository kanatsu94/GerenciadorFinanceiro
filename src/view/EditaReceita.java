package view;

import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class EditaReceita extends JInternalFrame {
	private Vector<String> vectorSimNao = new Vector<String>();
	private JTextField codigo;
	
	public EditaReceita() {
		vectorSimNao.add("Não");
		vectorSimNao.add("Sim");
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 600, 300);
		setTitle("Receita - Adicionar");
		setClosable(true);
		setIconifiable(true);
		setVisible(true);
		getContentPane().setLayout(null);
		
		Label label = new Label("*Descri\u00E7\u00E3o");
		label.setBounds(95, 8, 62, 22);
		getContentPane().add(label);
		
		TextField textField = new TextField();
		textField.setBounds(95, 36, 479, 22);
		getContentPane().add(textField);
		
		TextField textField_1 = new TextField();
		textField_1.setBounds(10, 110, 87, 22);
		getContentPane().add(textField_1);		
		
		TextField textField_2 = new TextField();
		textField_2.setBounds(126, 110, 121, 22);
		getContentPane().add(textField_2);
		
		JComboBox receitaFixa = new JComboBox(vectorSimNao);
		receitaFixa.setBounds(284, 110, 77, 20);
		getContentPane().add(receitaFixa);
		
		JComboBox categoria = new JComboBox();
		categoria.setEditable(true);
		categoria.setBounds(383, 110, 191, 20);
		getContentPane().add(categoria);
		
		Label label_1 = new Label("*Valor");
		label_1.setBounds(10, 82, 62, 22);
		getContentPane().add(label_1);
		
		Label label_2 = new Label("*Data de Vencimento");
		label_2.setBounds(126, 82, 121, 22);
		getContentPane().add(label_2);
		
		Label label_3 = new Label("*Receita Fixa");
		label_3.setBounds(284, 82, 77, 22);
		getContentPane().add(label_3);
		
		Label label_4 = new Label("*Categoria");
		label_4.setBounds(383, 82, 62, 22);
		getContentPane().add(label_4);
		
		JComboBox conta = new JComboBox();
		conta.setEditable(true);
		conta.setBounds(10, 185, 237, 20);
		getContentPane().add(conta);
		
		Label label_5 = new Label("*Conta");
		label_5.setBounds(10, 157, 62, 22);
		getContentPane().add(label_5);
		
		Label label_6 = new Label("Repetir");
		label_6.setBounds(284, 157, 62, 22);
		getContentPane().add(label_6);
		
		TextField textField_3 = new TextField();
		textField_3.setBounds(284, 185, 54, 22);
		getContentPane().add(textField_3);
		
		TextField textField_4 = new TextField();
		textField_4.setBounds(446, 183, 128, 22);
		textField_4.setEnabled(false);
		getContentPane().add(textField_4);
		
		JComboBox pago = new JComboBox(vectorSimNao);
		pago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pago.getSelectedItem().equals("Não")){
					textField_4.setEnabled(false);
				}
				else{
					textField_4.setEnabled(true);
				}
			}
		});
		pago.setBounds(359, 185, 62, 20);
		getContentPane().add(pago);
		
		Label label_7 = new Label("Pago");
		label_7.setBounds(359, 157, 62, 22);
		getContentPane().add(label_7);
		
		Label label_8 = new Label("*Data de Pagamento");
		label_8.setBounds(446, 157, 128, 22);
		getContentPane().add(label_8);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 216, 564, 2);
		getContentPane().add(separator);
		
		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(10, 229, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(485, 229, 89, 23);
		getContentPane().add(btnSalvar);
		
		codigo = new JTextField();
		codigo.setEditable(false);
		codigo.setBounds(10, 36, 79, 20);
		getContentPane().add(codigo);
		codigo.setColumns(10);
		
		Label label_9 = new Label("Cod.");
		label_9.setBounds(10, 8, 62, 22);
		getContentPane().add(label_9);
	}
}
