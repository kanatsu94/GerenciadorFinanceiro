package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SalvaDespesa extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUI = 1L;
	
	private String NOME_TELA = "Salvar";
	
	public SalvaDespesa() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().setForeground(Color.BLACK);
		initComponents();
	}
	
	private void initComponents() {
		setBounds(100, 100, 376, 114);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		getContentPane().setLayout(null);
		
		JLabel lblTemCertezaQue = new JLabel("Tem certeza que deseja salvar?");
		lblTemCertezaQue.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTemCertezaQue.setBounds(68, 0, 245, 31);
		getContentPane().add(lblTemCertezaQue);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(39, 36, 89, 31);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancelar);
		
		JButton btnRemover = new JButton("OK");
		btnRemover.setBounds(207, 36, 89, 31);
		getContentPane().add(btnRemover);
	}
	
	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 3,
				(d.height - this.getSize().height) / 4);
	}
}
