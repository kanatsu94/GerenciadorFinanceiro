package view;

import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import util.ExibeMensagem;
import util.NumberFieldDocument;
import util.TextFieldDocument;

public class EditarCartaoView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditarCartaoView() {
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 475, 195);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		setVisible(true);
		
		getContentPane().setLayout(new MigLayout("", "[30][30][30][30][30][30][30][30][30][30][30][30][30]", "[][][15.00][][][15.00][]"));
		
		this.lblCod = new JLabel(LBL_COD);
		getContentPane().add(lblCod, "cell 0 0 2 1");
		
		lblDescricao = new JLabel(LBL_DESCRICAO);
		getContentPane().add(lblDescricao, "cell 3 0 3 1");
		
		fieldCod = new JTextField();
		getContentPane().add(fieldCod, "cell 0 1 2 1,growx");
		fieldCod.setColumns(5);
		
		fieldDescricao = new JTextField();
		getContentPane().add(fieldDescricao, "cell 3 1 10 1,growx");
		fieldDescricao.setColumns(10);
		
		lblDiaVencimento = new JLabel(LBL_DIA_VENCIMENTO);
		getContentPane().add(lblDiaVencimento, "cell 0 3 5 1");
		
		lblDiasFechamento = new JLabel(LBL_DIAS_FECHAMENTO);
		getContentPane().add(lblDiasFechamento, "cell 6 3 5 1");
		
		fieldVencimento = new JTextField();
		getContentPane().add(fieldVencimento, "cell 0 4 2 1,growx");
		fieldVencimento.setColumns(5);
		
		fieldFechamento = new JTextField();
		getContentPane().add(fieldFechamento, "cell 6 4 2 1,growx");
		fieldFechamento.setColumns(5);
		
		separator = new JSeparator();
		getContentPane().add(separator, "cell 0 5 13 1,growx,aligny center");

		this.btnCancelar = new JButton(BTN_CANCELAR);
		getContentPane().add(this.btnCancelar, "cell 0 6 3 1,alignx left,growy");
		
		this.btnSalvar = new JButton(BTN_SALVAR);
		getContentPane().add(this.btnSalvar, "cell 10 6 3 1,alignx right,growy");
		
		this.btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelarAction(evt);
			}
		});
		
		this.btnSalvar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				salvarAction(evt);
			}
		});
		
		// O TAMANHO MAXIMO DA STRING E 150 [ VARCHAR(150) ]
		this.fieldDescricao.setDocument(new TextFieldDocument(150));
		this.fieldVencimento.setToolTipText(DICA_VENCIMENTO);
		this.fieldVencimento.setDocument(new NumberFieldDocument());
		this.fieldFechamento.setToolTipText(DICA_FECHAMENTO);
		this.fieldFechamento.setDocument(new NumberFieldDocument());
	}
	
	private void cancelarAction(java.awt.event.ActionEvent evt){
		if(cancelar() == 0){
			closeThisFrame();
		}
	}
	
	private void salvarAction(java.awt.event.ActionEvent evt){
		boolean flag = CartaoView.controller.atualizar(
				fieldDescricao.getText(),
				fieldVencimento.getText(),
				fieldFechamento.getText(),
				Integer.parseInt(fieldCod.getText())
				);
		if(flag)
			closeThisFrame();
	}

	public int cancelar() {
		// TYPE EM QUESTION MESSAGE DEFINE AS OPCOES
		// QUE APARECEM.
		this.msg = MSG_CANCELAR;
		this.title = TITLE_CANCELAR;
		// TYPE 0 INDICA QUE SOMENTE TEREMOS AS
		// OPCOES SIM E NAO.
		this.type = 0;

		return ExibeMensagem.showQuestionMessage(this.msg, this.title, this.type);
	}
	
	private void closeThisFrame(){
		try {
			this.setClosed(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
		this.toFront();
	}
	
	
	// METODOS PARA SETAR OS VALORES A SEREM EDITADOS.
	public void setCod(int cod){
		this.fieldCod.setText(String.valueOf(cod));
		this.fieldCod.setEditable(false);
	}
	public void setDescricao(String descricao){
		this.fieldDescricao.setText(descricao);
	}
	public void setDiaVencimento(String diaVencimento){
		this.fieldVencimento.setText(diaVencimento);
	}
	public void setDiasFechamento(String diasFechamento){
		this.fieldFechamento.setText(diasFechamento);
	}

	// CONSTANTES
	private String NOME_TELA = "Cartão de Crédito - Editar";
	private String LBL_DESCRICAO = "*Descrição";
	private String LBL_COD = "Cod.";
	private String LBL_DIAS_FECHAMENTO = "*Dias para fechamento da fatura";
	private String LBL_DIA_VENCIMENTO = "*Dia do vencimento da fatura";
	private String DICA_VENCIMENTO = "Informe o dia em que vence a fatura do seu cartão.";
	private String DICA_FECHAMENTO = "Informe quantos dias antes do vencimento, a fatura do seu cartão fecha.";
	private String BTN_CANCELAR = "Cancelar";
	private String BTN_SALVAR = "Salvar";
	private String MSG_CANCELAR = "Deseja mesmo cancelar?";
	private String TITLE_CANCELAR = "Cancelar";

	// LABEL
	private JLabel lblCod;
	private JLabel lblDescricao;
	private JLabel lblDiaVencimento;
	private JLabel lblDiasFechamento;

	// FIELD
	private JTextField fieldCod;
	private JTextField fieldDescricao;
	private JTextField fieldVencimento;
	private JTextField fieldFechamento;

	// SEPARATOR
	private JSeparator separator;

	// BUTTON
	private JButton btnCancelar;
	private JButton btnSalvar;

	// OUTROS
	private String msg;
	private String title;
	private int type;
}
