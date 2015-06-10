package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import controller.MainController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrincipalView extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/* Set the Nimbus look and feel */
					// <editor-fold defaultstate="collapsed"
					// desc=" Look and feel setting code (optional) ">
					/*
					 * If Nimbus (introduced in Java SE 6) is not available,
					 * stay with the default look and feel. For details see
					 * http:
					 * //download.oracle.com/javase/tutorial/uiswing/lookandfeel
					 * /plaf.html
					 */
					try {
						for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
								.getInstalledLookAndFeels()) {
							if ("Nimbus".equals(info.getName())) {
								javax.swing.UIManager.setLookAndFeel(info
										.getClassName());
								break;
							}
						}
					} catch (ClassNotFoundException ex) {
						java.util.logging.Logger.getLogger(
								PrincipalView.class.getName()).log(
								java.util.logging.Level.SEVERE, null, ex);
					} catch (InstantiationException ex) {
						java.util.logging.Logger.getLogger(
								PrincipalView.class.getName()).log(
								java.util.logging.Level.SEVERE, null, ex);
					} catch (IllegalAccessException ex) {
						java.util.logging.Logger.getLogger(
								PrincipalView.class.getName()).log(
								java.util.logging.Level.SEVERE, null, ex);
					} catch (javax.swing.UnsupportedLookAndFeelException ex) {
						java.util.logging.Logger.getLogger(
								PrincipalView.class.getName()).log(
								java.util.logging.Level.SEVERE, null, ex);
					}

					PrincipalView window = new PrincipalView();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		MainController.initFactory();
		lblCarregando.setVisible(false);
	}

	public static JDesktopPane getPainel() {
		return telaPrincipal;
	}

	/**
	 * Create the application.
	 */
	public PrincipalView() {
		initialize();
	}

	/**
	 * Initialize the contents of the telaPrincipal.
	 */
	private void initialize() {
		telaPrincipal = new javax.swing.JDesktopPane();
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// SIM = 0, NAO = 1
				if (JOptionPane.showConfirmDialog(null,
						"Deseja fechar o sistema?", "Finalizar",
						JOptionPane.YES_NO_OPTION) == 0) {
					// FECHA AS CONEXOES COM O BANCO
					MainController.closeConnections();
					System.exit(0);
				}

			}
		});

		this.despesaView = null;
		this.receitaView = null;
		this.cartaoView = null;

		setResizable(false);
		setTitle(NOME_SISTEMA);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		btnDespesa = new JButton(DESPESA_BTN_MENU);
		btnDespesa.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				despesaView(evt);
			}
		});
		menuBar.add(btnDespesa);

		btnReceita = new JButton(RECEITA_BTN_MENU);
		btnReceita.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				receitaView(evt);
			}
		});

		menuBar.add(btnReceita);

		btnCategoria = new JButton(CATEGORIA_BTN_MENU);
		menuBar.add(btnCategoria);

		btnCartaoCredito = new JButton(CARTAO_BTN_MENU);
		btnCartaoCredito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cartaoView(evt);
			}
		});
		menuBar.add(btnCartaoCredito);

		btnConta = new JButton(CONTA_BTN_MENU);
		menuBar.add(btnConta);

		btnRelatorios = new JButton(RELATORIOS_BTN_MENU);
		menuBar.add(btnRelatorios);

		lblCarregando.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarregando.setForeground(Color.WHITE);
		lblCarregando.setFont(new Font("Arial", Font.BOLD, 26));
		
		GroupLayout groupLayout = new GroupLayout(telaPrincipal);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(342)
					.addComponent(lblCarregando)
					.addContainerGap(348, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(256)
					.addComponent(lblCarregando)
					.addContainerGap(264, Short.MAX_VALUE))
		);
		telaPrincipal.setLayout(groupLayout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				telaPrincipal));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				telaPrincipal));

		setSize(new java.awt.Dimension(800, 600));
		setLocationRelativeTo(null);
	}

	private void despesaView(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itemEstoqueProdutoActionPerformed
		if (despesaView == null) {
			despesaView = new DespesaView();
			PrincipalView.getPainel().add(despesaView);
			despesaView.setClosable(false);
			despesaView.setPosicao();
			despesaView.setVisible(true);
		} else {
			try {
				despesaView.setIcon(false);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			despesaView.setSelected(true);
			despesaView.requestFocusInWindow();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void receitaView(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_itemEstoqueProdutoActionPerformed
		if (receitaView == null) {
			receitaView = new ReceitaView();
			PrincipalView.getPainel().add(receitaView);
			receitaView.setClosable(false);
			receitaView.setPosicao();
			receitaView.setVisible(true);
		} else {
			try {
				receitaView.setIcon(false);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			receitaView.setSelected(true);
			receitaView.requestFocusInWindow();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void cartaoView(java.awt.event.ActionEvent evt){
		if(cartaoView == null){
			cartaoView = new CartaoView();
			PrincipalView.getPainel().add(cartaoView);
			cartaoView.setClosable(false);
			cartaoView.setPosicao();
			cartaoView.setVisible(true);
		}
		else{
			try {
				cartaoView.setIcon(true);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		try {
			cartaoView.setSelected(true);
			cartaoView.requestFocusInWindow();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private JMenuBar menuBar;
	private JButton btnReceita;
	private JButton btnDespesa;
	private JButton btnCategoria;
	private JButton btnCartaoCredito;
	private JButton btnConta;
	private JButton btnRelatorios;
	private static javax.swing.JDesktopPane telaPrincipal;
	private String NOME_SISTEMA = "Gerenciador Financeiro";
	private String RECEITA_BTN_MENU = "Receita";
	private String DESPESA_BTN_MENU = "Despesa";
	private String CATEGORIA_BTN_MENU = "Categoria";
	private String CONTA_BTN_MENU = "Conta";
	private String RELATORIOS_BTN_MENU = "Relatórios";
	private String CARTAO_BTN_MENU = "Cartão de Crédito";
	private DespesaView despesaView;
	private ReceitaView receitaView;
	private CartaoView cartaoView;
	private static JLabel lblCarregando = new JLabel("Aguarde...");
}
