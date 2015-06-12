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

public class AdicionaCartaoView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdicionaCartaoView() {
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 400, 200);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		getContentPane().setLayout(
				new MigLayout("", "[30][30][30][30][30][30][30][30][30][30][30]", "[][][15.00][][][24.00][15.00]"));

		this.lblDescricao = new JLabel(LBL_DESCRICAO);
		getContentPane().add(lblDescricao, "cell 0 0 2 1");

		this.fieldDescricao = new JTextField();
		// O TAMANHO MAXIMO DA STRING E 150 [ VARCHAR(150) ]
		this.fieldDescricao.setDocument(new TextFieldDocument(150));
		getContentPane().add(fieldDescricao, "cell 0 1 11 1,growx");
		this.fieldDescricao.setColumns(10);

		this.lblVencimento = new JLabel(LBL_DIA_VENCIMENTO);
		getContentPane().add(lblVencimento, "cell 0 3 5 1");

		this.lblFechamento = new JLabel(LBL_DIAS_FECHAMENTO);
		getContentPane().add(lblFechamento, "cell 6 3 5 1");

		this.fieldVencimento = new JTextField();
		this.fieldVencimento.setToolTipText(DICA_VENCIMENTO);
		this.fieldVencimento.setDocument(new NumberFieldDocument());
		getContentPane().add(this.fieldVencimento, "cell 0 4 3 1,alignx left");
		this.fieldVencimento.setColumns(5);

		this.fieldFechamento = new JTextField();
		this.fieldFechamento.setToolTipText(DICA_FECHAMENTO);
		this.fieldFechamento.setDocument(new NumberFieldDocument());
		getContentPane().add(this.fieldFechamento, "cell 6 4 3 1,alignx left");
		this.fieldFechamento.setColumns(5);

		this.separator = new JSeparator();
		getContentPane().add(separator, "cell 0 5 11 1,growx");

		this.btnCancelar = new JButton(BTN_CANCELAR);
		getContentPane().add(btnCancelar,
				"cell 0 6 3 1,alignx left,aligny center");
		this.btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelarAction(evt);
			}
		});

		this.btnSalvar = new JButton(BTN_SALVAR);
		getContentPane().add(btnSalvar,
				"cell 8 6 3 1,alignx right,aligny center");
		this.btnSalvar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				salvarAction(evt);
			}
		});

		setVisible(true);
	}

	private void cancelarAction(java.awt.event.ActionEvent evt) {
		if (cancelar() == 0) {
			closeThisFrame();
		}
	}

	private void salvarAction(java.awt.event.ActionEvent evt) {
		boolean flag = CartaoView.controller.salvar(
					this.fieldDescricao.getText(),
					this.fieldVencimento.getText(),
					this.fieldFechamento.getText()
				);

		if (flag)
			this.closeThisFrame();
	}

	public int cancelar() {
		// TYPE EM QUESTION MESSAGE DEFINE AS OPCOES
		// QUE APARECEM.
		this.msg = MSG_CANCELAR;
		this.title = TITLE_CANCELAR;
		// TYPE 0 INDICA QUE SOMENTE TEREMOS AS
		// OPCOES SIM E NAO.
		this.type = 0;

		return ExibeMensagem.showQuestionMessage(this.msg, this.title,
				this.type);
	}

	private void closeThisFrame() {
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

	// CONSTANTES
	private String NOME_TELA = "Cartão de Crédito - Adicionar";
	private String LBL_DESCRICAO = "*Descrição";
	private String LBL_DIAS_FECHAMENTO = "*Dias para fechamento da fatura";
	private String LBL_DIA_VENCIMENTO = "*Dia do vencimento da fatura";
	private String DICA_VENCIMENTO = "Informe o dia em que vence a fatura do seu cartão.";
	private String DICA_FECHAMENTO = "Informe quantos dias antes do vencimento, a fatura do seu cartão fecha.";
	private String BTN_CANCELAR = "Cancelar";
	private String BTN_SALVAR = "Salvar";
	private String MSG_CANCELAR = "Deseja mesmo cancelar?";
	private String TITLE_CANCELAR = "Cancelar";

	// LABEL
	private JLabel lblDescricao;
	private JLabel lblVencimento;
	private JLabel lblFechamento;

	// FIELD
	private JTextField fieldDescricao;
	private JTextField fieldVencimento;
	private JTextField fieldFechamento;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JSeparator separator;

	// OUTROS
	private String msg;
	private String title;
	private int type;
}
