package view;

import java.awt.Color;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class SalvaDespRepetida extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUI = 1L;
	
	private String NOME_TELA = "Salvar";
	
	public SalvaDespRepetida() {
		getContentPane().setForeground(Color.BLACK);
		initComponents();
	}
	
	private void initComponents() {
		setBounds(100, 100, 376, 118);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		getContentPane().setLayout(null);
		
		JLabel lblTemCertezaQue = new JLabel("<html>Essa \u00E9 uma despesa repetida. <br>Deseja aplicar esta a\u00E7\u00E3o \u00E0 todas as outras despesas?</html>");
		lblTemCertezaQue.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTemCertezaQue.setBounds(18, 0, 332, 48);
		getContentPane().add(lblTemCertezaQue);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(33, 47, 89, 31);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancelar);
		
		
		JButton btnRemover = new JButton("OK");
		btnRemover.setBounds(228, 47, 89, 31);
		getContentPane().add(btnRemover);
	}
}
