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

import util.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import util.DateLabelFormatter;
import util.DecimalFieldDocument;
import util.ExibeMensagem;
import util.JDatePickerImpl;
import util.NumberFieldDocument;
import util.TextFieldDocument;

@SuppressWarnings("rawtypes")
public class AdicionaDespesaView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdicionaDespesaView() {
		this.DESPESA = 1;
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 585, 300);
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

		this.panelVencimento.setShowYearButtons(true);
		this.panelPagamento.setShowYearButtons(true);

		this.lblDescricao = new JLabel(LBL_DESCRICAO);
		this.lblValor = new JLabel(LBL_VALOR);

		this.comboCategoria = DespesaView.controllerDespesaReceita
				.getComboCategoriaDespesa();
		this.comboConta = DespesaView.controllerDespesaReceita.getComboConta();

		this.fieldDescricao = new JTextField();
		this.fieldDescricao.setColumns(10);
		this.fieldValor = new JFormattedTextField();
		this.fieldValor.setColumns(10);

		this.fieldValor.setDocument(new DecimalFieldDocument());
		// O TAMANHO MAXIMO DA STRING E 150 [ VARCHAR(150) ]
		this.fieldDescricao.setDocument(new TextFieldDocument(150));

		getContentPane()
				.setLayout(
						new MigLayout(
								"",
								"[20][20][20][20][20][20][20][20][20][20][20][20][20][20][20][20][20][20][20][20][20][20][20]",
								"[14px][20px][14px][21px][11.00px][18.00px][][11.00][23.00][][13.00][]"));
		getContentPane().add(fieldDescricao, "cell 0 1 23 1,growx,aligny top");
		getContentPane().add(lblDescricao,
				"cell 0 0 3 1,alignx left,aligny top");
		getContentPane().add(lblValor, "cell 0 2 2 1,alignx left,aligny top");

		lblCartaoCredito = new JLabel(LBL_CARTAO);
		getContentPane().add(lblCartaoCredito, "cell 5 2 4 1,alignx left");
		this.lblDataVencimento = new JLabel(LBL_DATA_VENCIMENTO);
		getContentPane().add(lblDataVencimento,
				"cell 14 2 5 1,alignx left,aligny center");
		this.lblDespesaFixa = new JLabel(LBL_DESPESA_FIXA);
		getContentPane().add(lblDespesaFixa,
				"cell 20 2 3 1,alignx center,aligny center");
		getContentPane().add(fieldValor,
				"cell 0 3 4 1,alignx left,aligny center");
		this.lblPago = new JLabel(LBL_PAGO);
		getContentPane().add(lblPago,
				"cell 4 5 2 1,alignx center,aligny center");
		this.lblDataPagamento = new JLabel(LBL_DATA_PAGAMENTO);
		getContentPane().add(lblDataPagamento,
				"cell 7 5 5 1,alignx left,aligny center");
		this.checkPago = new JCheckBox();
		getContentPane().add(checkPago,
				"cell 4 6 2 1,alignx center,aligny center");

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
		this.pickerPagamento = new JDatePickerImpl(panelPagamento,
				new DateLabelFormatter());

		this.pickerPagamento.setButtonEnable(false);
		getContentPane().add(pickerPagamento,
				"cell 7 6 5 1,alignx left,aligny center");
		this.lblCategoria = new JLabel(LBL_CATEGORIA);
		getContentPane().add(lblCategoria,
				"cell 12 8 3 1,alignx left,aligny center");
		getContentPane().add(comboConta, "cell 0 9 11 1,growx,aligny center");
		getContentPane().add(comboCategoria,
				"cell 12 9 11 1,growx,aligny center");

		this.comboCartao = DespesaView.controllerDespesaReceita
				.getComboCartaoCredito(true);
		comboCartao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(comboCartao.getSelectedIndex() > 0){
					panelVencimento.getModel().setSelected(false);
					pickerVencimento.setButtonEnable(false);
				}
				else
					pickerVencimento.setButtonEnable(true);
			}
		});
		getContentPane().add(comboCartao, "cell 5 3 8 1,growx,aligny center");
		this.pickerVencimento = new JDatePickerImpl(panelVencimento,
				new DateLabelFormatter());
		getContentPane().add(pickerVencimento,
				"cell 14 3 5 1,alignx left,aligny center");

		this.checkDespesaFixa = new JCheckBox();
		getContentPane().add(checkDespesaFixa,
				"cell 20 3 3 1,alignx center,aligny center");

		this.checkDespesaFixa.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkDespesaFixa.isSelected()) {
					fieldRepetir.setEnabled(false);
					fieldRepetir.setText("");
				} else
					fieldRepetir.setEnabled(true);
			}
		});

		this.btnCancelar = new JButton(BTN_CANCELAR);
		btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelarAction(evt);
			}
		});
		this.lblRepetir = new JLabel(LBL_REPETIR);
		getContentPane().add(lblRepetir,
				"cell 0 5 2 1,alignx left,aligny bottom");

		this.fieldRepetir = new JTextField();
		this.fieldRepetir.setColumns(5);
		this.fieldRepetir.setDocument(new NumberFieldDocument());
		getContentPane().add(fieldRepetir,
				"cell 0 6 3 1,alignx left,aligny center");
		this.lblConta = new JLabel(LBL_CONTA);
		getContentPane()
				.add(lblConta, "cell 0 8 2 1,alignx left,aligny center");

		this.separator = new JSeparator();

		getContentPane().add(separator, "cell 0 10 23 1,growx,aligny center");
		getContentPane().add(btnCancelar, "cell 0 11 4 1,alignx left");
		this.btnSalvar = new JButton(BTN_SALVAR);
		btnSalvar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				salvarAction(evt);
			}
		});

		getContentPane().add(btnSalvar, "cell 20 11 3 1,alignx right");
	}

	private void cancelarAction(java.awt.event.ActionEvent evt) {
		if (cancelar() == 0) {
			closeThisFrame();
		}
	}

	private void salvarAction(java.awt.event.ActionEvent evt) {
		boolean flag = DespesaView.controllerDespesaReceita.salvar(
				this.fieldDescricao.getText(), (Date) this.pickerVencimento
						.getModel().getValue(), (Date) this.pickerPagamento
						.getModel().getValue(), this.fieldValor.getText(),
				this.checkDespesaFixa.isSelected(), this.comboCategoria
						.getSelectedItem(), this.DESPESA, this.comboConta
						.getSelectedItem(), this.getItemComboCartao(),
				this.fieldRepetir.getText(), this.checkPago.isSelected());

		if (flag)
			this.closeThisFrame();
	}
	
	private Object getItemComboCartao(){
		if(this.comboCartao.getSelectedIndex() == 0)
			return null;
		else
			return this.comboCartao.getSelectedItem();
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
	private String NOME_TELA = "Despesa - Adicionar";
	private String LBL_DESCRICAO = "*Descrição";
	private String LBL_VALOR = "*Valor";
	private String LBL_DATA_VENCIMENTO = "*Data de Vencimento";
	private String LBL_DESPESA_FIXA = "Despesa Fixa";
	private String LBL_CATEGORIA = "*Categoria";
	private String LBL_PAGO = "Pago";
	private String LBL_DATA_PAGAMENTO = "Data de Pagamento";
	private String LBL_CONTA = "*Conta";
	private String LBL_REPETIR = "Repetir";
	private String LBL_CARTAO = "Cartão de crédito";
	private String BTN_CANCELAR = "Cancelar";
	private String BTN_SALVAR = "Salvar";
	private String MSG_CANCELAR = "Deseja mesmo cancelar?";
	private String TITLE_CANCELAR = "Cancelar";

	// LABEL
	private JLabel lblDescricao;
	private JLabel lblValor;
	private JLabel lblDataVencimento;
	private JLabel lblDespesaFixa;
	private JLabel lblCategoria;
	private JLabel lblRepetir;
	private JLabel lblConta;
	private JLabel lblPago;
	private JLabel lblDataPagamento;
	private JLabel lblCartaoCredito;

	// FIELD
	private JTextField fieldDescricao;
	private JTextField fieldValor;
	private JTextField fieldRepetir;

	// CHECK
	private JCheckBox checkPago;
	private JCheckBox checkDespesaFixa;

	// COMBO
	private JComboBox comboCategoria;
	private JComboBox comboConta;
	private JComboBox comboCartao;

	// SEPARATOR
	private JSeparator separator;

	// BUTTON
	private JButton btnCancelar;
	private JButton btnSalvar;

	// OUTROS
	private String msg;
	private String title;
	private int type;
	private int DESPESA;

	// DATE PICKER
	private JDatePickerImpl pickerVencimento;
	private JDatePickerImpl pickerPagamento;
	private UtilDateModel modelVencimento;
	private UtilDateModel modelPagamento;
	private JDatePanelImpl panelVencimento;
	private JDatePanelImpl panelPagamento;
	private Properties p;
}
