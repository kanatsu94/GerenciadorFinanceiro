package view;

import javax.swing.GroupLayout;
import javax.swing.JInternalFrame;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import java.awt.ScrollPane;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.GroupLayout.Alignment;

import teste.Testee;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

public class ReceitaView extends JInternalFrame {
	private JTextField campoBuscaReceita;
	private Vector<String> vector = new Vector<String>();
	
	public ReceitaView() {
		initComponents();

	}
	private void initComponents() {
		setBounds(100, 100, 700, 500);
		setTitle("Receita");
		setClosable(true);
		setIconifiable(true);
		setVisible(true);
		getContentPane().setLayout(null);
		
		campoBuscaReceita = new JTextField();
		campoBuscaReceita.setToolTipText("");
		campoBuscaReceita.setBounds(502, 11, 172, 20);
		getContentPane().add(campoBuscaReceita);
		campoBuscaReceita.setColumns(10);
		
		JButton btnAdicionarReceita = new JButton("Adicionar");
		btnAdicionarReceita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdicionaReceita a = new AdicionaReceita();
				add(a);
				try {
					setIcon(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdicionarReceita.setBounds(20, 42, 89, 23);
		getContentPane().add(btnAdicionarReceita);
		
		JButton btnEditarReceita = new JButton("Editar");
		btnEditarReceita.setBounds(121, 42, 89, 23);
		getContentPane().add(btnEditarReceita);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(10, 71, 664, 360);
		getContentPane().add(scrollPane);
		
		JButton btnRemoverReceita = new JButton("Remover");
		btnRemoverReceita.setBounds(20, 437, 89, 23);
		getContentPane().add(btnRemoverReceita);
		
		vector.addElement("Pago e Não Pago");
		vector.addElement("Não Pago");
		vector.addElement("Pago");
		
		JComboBox comboBox = new JComboBox(vector);
		comboBox.setBounds(20, 11, 190, 20);
		getContentPane().add(comboBox);
		
	}
}
