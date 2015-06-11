package view;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateLabelFormatter;
import util.DecimalFieldDocument;
import util.ExibeMensagem;
import util.JDatePickerImpl;
import util.NumberFieldDocument;
import util.TextFieldDocument;

@SuppressWarnings("rawtypes")
public class AdicionaReceitaView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdicionaReceitaView() {
		this.tipo = 2;
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 570, 240);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		setVisible(true);

		this.p = new Properties();
		this.p.put("text.today", "Hoje");
		this.p.put("text.month", "Mês");
		this.p.put("text.year", "Ano");

		this.modelVencimento = new UtilDateModel();
		this.modelPagamento = new UtilDateModel();
		this.panelVencimento = new JDatePanelImpl(this.modelVencimento, this.p);
		this.panelPagamento = new JDatePanelImpl(this.modelPagamento, this.p);
		this.pickerVencimento = new JDatePickerImpl(panelVencimento,
				new DateLabelFormatter());
		this.pickerPagamento = new JDatePickerImpl(panelPagamento,
				new DateLabelFormatter());

		this.pickerPagamento.setButtonEnable(false);

		this.lblDescricao = new JLabel(LBL_DESCRICAO);
		this.lblValor = new JLabel(LBL_VALOR);
		this.lblDataVencimento = new JLabel(LBL_DATA_VENCIMENTO);
		this.lblReceitaFixa = new JLabel(LBL_RECEITA_FIXA);
		this.lblCategoria = new JLabel(LBL_CATEGORIA);
		this.lblRepetir = new JLabel(LBL_REPETIR);
		this.lblConta = new JLabel(LBL_CONTA);
		this.lblPago = new JLabel(LBL_PAGO);
		this.lblDataPagamento = new JLabel(LBL_DATA_PAGAMENTO);

		this.checkReceitaFixa = new JCheckBox();
		this.checkPago = new JCheckBox();

		this.comboCategoria = ReceitaView.controllerDespesaReceita
				.getComboCategoriaReceita();
		this.comboConta = ReceitaView.controllerDespesaReceita.getComboConta();

		this.fieldRepetir = new JTextField();
		this.fieldRepetir.setColumns(10);
		this.fieldDescricao = new JTextField();
		this.fieldDescricao.setColumns(10);
		this.fieldValor = new JFormattedTextField();
		this.fieldValor.setColumns(10);

		this.fieldValor.setDocument(new DecimalFieldDocument());
		this.fieldRepetir.setDocument(new NumberFieldDocument());
		this.fieldDescricao.setDocument(new TextFieldDocument(150));

		this.btnCancelar = new JButton(BTN_CANCELAR);
		btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelarAction(evt);
			}
		});
		this.btnSalvar = new JButton(BTN_SALVAR);
		btnSalvar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				salvarAction(evt);
			}
		});

		this.separator = new JSeparator();

		getContentPane()
				.setLayout(
						new MigLayout(
								"",
								"[86px][12][102.00px][12.00px][65px][12][46px][12][19.00px][12][100.00px]",
								"[14px][20px][14px][21px][14px][20px][][][]"));
		getContentPane().add(fieldDescricao, "cell 0 1 11 1,growx,aligny top");
		getContentPane().add(lblDescricao, "cell 0 0,alignx left,aligny top");
		getContentPane().add(lblValor, "cell 0 2,alignx left,aligny top");
		getContentPane().add(fieldValor, "cell 0 3,alignx left,aligny center");
		getContentPane().add(pickerVencimento, "cell 2 3,growx,aligny center");
		getContentPane().add(lblDataVencimento, "cell 2 2,growx,aligny top");
		getContentPane().add(lblReceitaFixa,
				"cell 4 2,alignx center,aligny top");
		getContentPane().add(checkReceitaFixa,
				"cell 4 3,alignx center,aligny center");
		getContentPane().add(fieldRepetir, "cell 6 3,growx,aligny center");
		getContentPane().add(lblRepetir, "cell 6 2,alignx left,aligny top");
		getContentPane().add(checkPago, "cell 8 3,alignx center,aligny center");
		getContentPane().add(lblPago, "cell 8 2,alignx left,aligny top");
		getContentPane().add(lblDataPagamento,
				"cell 10 2,alignx left,aligny top");
		getContentPane().add(pickerPagamento, "cell 10 3,growx,aligny center");
		getContentPane().add(lblConta, "cell 0 4,alignx left,aligny top");
		getContentPane().add(comboConta, "cell 0 5 3 1,growx");
		getContentPane().add(comboCategoria, "cell 6 5 5 1,growx,aligny top");
		getContentPane().add(lblCategoria, "cell 6 4,alignx left,aligny top");

		getContentPane().add(separator, "cell 0 7 11 1,growx,aligny center");
		getContentPane().add(btnCancelar, "cell 0 8,alignx left");

		getContentPane().add(btnSalvar, "cell 10 8,alignx right");

		this.checkReceitaFixa.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkReceitaFixa.isSelected()) {
					fieldRepetir.setEnabled(false);
					fieldRepetir.setText("");
				} else
					fieldRepetir.setEnabled(true);
			}
		});

		this.checkPago.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkPago.isSelected()) {
					pickerPagamento.setButtonEnable(true);
				} else {
					panelPagamento.getModel().setSelected(false);
					pickerPagamento.setButtonEnable(false);
				}
			}
		});
	}

	private void cancelarAction(java.awt.event.ActionEvent evt) {
		if (cancelar() == 0) {
			closeThisFrame();
		}
	}

	private void salvarAction(java.awt.event.ActionEvent evt) {
		 boolean flag = ReceitaView.controllerDespesaReceita.salvar(
			 this.fieldDescricao.getText(),
			 (Date) this.pickerVencimento.getModel().getValue(),
			 (Date)this.pickerPagamento.getModel().getValue(),
			 this.fieldValor.getText(),
			 this.checkReceitaFixa.isSelected(),
			 this.comboCategoria.getSelectedItem(),
			 this.tipo,
			 this.comboConta.getSelectedItem(),
			 null,
			 this.fieldRepetir.getText(),
			 this.checkPago.isSelected()
		 );
		 
		 if(flag)
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

		return ExibeMensagem.showQuestionMessage(this.msg, this.title, this.type);
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
	private String NOME_TELA = "Receita - Adicionar";
	private String LBL_DESCRICAO = "*Descrição";
	private String LBL_VALOR = "*Valor";
	private String LBL_DATA_VENCIMENTO = "*Data de Vencimento";
	private String LBL_RECEITA_FIXA = "Receita Fixa";
	private String LBL_CATEGORIA = "*Categoria";
	private String LBL_PAGO = "Pago";
	private String LBL_DATA_PAGAMENTO = "Data de Pagamento";
	private String LBL_CONTA = "*Conta";
	private String LBL_REPETIR = "Repetir";
	private String BTN_CANCELAR = "Cancelar";
	private String BTN_SALVAR = "Salvar";
	private String MSG_CANCELAR = "Deseja mesmo cancelar?";
	private String TITLE_CANCELAR = "Cancelar";

	// LABEL
	private JLabel lblDescricao;
	private JLabel lblValor;
	private JLabel lblDataVencimento;
	private JLabel lblReceitaFixa;
	private JLabel lblCategoria;
	private JLabel lblRepetir;
	private JLabel lblConta;
	private JLabel lblPago;
	private JLabel lblDataPagamento;

	// FIELD
	private JTextField fieldDescricao;
	private JTextField fieldValor;
	private JTextField fieldRepetir;

	// CHECK
	private JCheckBox checkPago;
	private JCheckBox checkReceitaFixa;

	// COMBO
	private JComboBox comboCategoria;
	private JComboBox comboConta;

	// SEPARATOR
	private JSeparator separator;

	// BUTTON
	private JButton btnCancelar;
	private JButton btnSalvar;

	// OUTROS
	private String msg;
	private String title;
	private int type;
	private int tipo;

	// DATE PICKER
	private JDatePickerImpl pickerVencimento;
	private JDatePickerImpl pickerPagamento;
	private UtilDateModel modelVencimento;
	private UtilDateModel modelPagamento;
	private JDatePanelImpl panelVencimento;
	private JDatePanelImpl panelPagamento;
	private Properties p;
}
