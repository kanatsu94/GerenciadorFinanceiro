package view;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class DespesaView extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String NOME_TELA = "Despesa";

	/**
	 * Create the frame.
	 */
	public DespesaView() {
		initComponents();
	}
	
	private void initComponents(){
		setBounds(100, 100, 700, 500);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
		);
		
		JLabel lblTeste = new JLabel("TESTE");
		panel.add(lblTeste);
		getContentPane().setLayout(groupLayout);
	}
	
	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
	}
}
