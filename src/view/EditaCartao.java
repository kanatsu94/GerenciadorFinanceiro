package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EditaCartao extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	
	public EditaCartao() {
		initComnponents();
	}

	
	
	private void initComnponents() {
		setBounds(100, 100, 520, 198);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		
		lblDescrição = new JLabel(LBL_DESCRICAO);
		lblVencimento = new JLabel(LBL_VENCIMENTO);		
		lblFechaento = new JLabel(LBL_FECHAMENTO);
		lblCodigo = new JLabel(LBL_CODIGO);
		edtDescricao = new JTextField();
		edtDescricao.requestFocus();
		edtVencimento = new JTextField();
		edtFechamento = new JTextField();
		edtCodigo = new JTextField();
		edtCodigo.setEnabled(false);
		separador = new JSeparator();	
		btnSalvar = new JButton(BTN_SALVAR);
		btnCancelar = new JButton(BTN_CANCELAR);
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int escolha = JOptionPane.showConfirmDialog(null, "Deseja mesmo cancelar esta operação?","Cancelar", 0);
				if(escolha == 0){
					EditaCartao.this.dispose();
				}
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(105)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSalvar)
							.addGap(150)
							.addComponent(btnCancelar)
							.addGap(111))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblFechaento)
							.addContainerGap())))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(separador, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblVencimento)
					.addContainerGap(334, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addComponent(edtVencimento, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
					.addComponent(edtFechamento, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(88))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCodigo)
						.addComponent(edtCodigo, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addGap(39)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDescrição)
						.addComponent(edtDescricao, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescrição)
						.addComponent(lblCodigo))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtDescricao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtCodigo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVencimento)
						.addComponent(lblFechaento))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(edtFechamento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edtVencimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addComponent(separador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancelar)
						.addComponent(btnSalvar))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		setVisible(true);
	}
	
	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
	}


	private final String NOME_TELA = "Editar Cartão de Crédito";
	private final String LBL_CODIGO = "Cod.";
	private final String LBL_DESCRICAO = "*Descrição";
	private final String LBL_VENCIMENTO = "*Vencimento (Dia de Vencimento)";
	private final String LBL_FECHAMENTO = "*Fechamento (Dia do Fechamento da Fatura)";
	private final String BTN_CANCELAR = "Cancelar";
	private final String BTN_SALVAR = "Salvar";
	
	private JLabel lblDescrição;
	private JLabel lblVencimento;
	private JLabel lblFechaento;
	private JLabel lblCodigo;
	
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	private JTextField edtDescricao;
	private JTextField edtVencimento;
	private JTextField edtFechamento;
	private JTextField edtCodigo;
			
	private JSeparator separador;
}

