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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.joda.time.LocalDate;

import util.DateLabelFormatter;
import util.DecimalFieldDocument;
import util.ExibeMensagem;
import util.JDatePickerImpl;
import util.TextFieldDocument;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class EditarDespesaView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditarDespesaView() {
		initComponents();
	}

	private void initComponents() {
		setBounds(100, 100, 535, 320);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		setVisible(true);

		// ID DO TIPO
		this.DESPESA = 1;

		this.p = new Properties();
		this.p.put("text.today", "Hoje");
		this.p.put("text.month", "Mês");
		this.p.put("text.year", "Ano");

		this.modelVencimento = new UtilDateModel();
		this.panelVencimento = new JDatePanelImpl(this.modelVencimento, this.p);

		this.modelPagamento = new UtilDateModel();
		this.panelPagamento = new JDatePanelImpl(this.modelPagamento, this.p);

		this.lblValor = new JLabel(LBL_VALOR);
		this.lblCod = new JLabel(LBL_COD);
		this.fieldDescricao = new JTextField();
		this.fieldDescricao.setColumns(10);
		this.fieldValor = new JTextField();
		this.fieldValor.setColumns(10);
		this.fieldCod = new JTextField();

		this.fieldValor.setDocument(new DecimalFieldDocument());
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
								"[30.00][35.00][21.00][][44.00][25.00][21.00][20.00px][37.00px][22.00][30.00px][12][37.00][16.00][32.00px][30.00]",
								"[14px][20px][15.00][16.00px][21px][15.00px][][20px][16.00][][][16.00][]"));

		getContentPane().add(lblCod, "cell 0 0 2 1");
		this.lblDescricao = new JLabel(LBL_DESCRICAO);

		getContentPane().add(lblDescricao,
				"cell 2 0 3 1,alignx left,aligny top");

		getContentPane().add(fieldCod, "cell 0 1 2 1,alignx left");
		this.fieldCod.setColumns(5);
		getContentPane().add(fieldDescricao, "cell 2 1 14 1,growx,aligny top");
		getContentPane()
				.add(lblValor, "cell 0 3 2 1,alignx left,aligny bottom");

		this.lblComboCartao = new JLabel(LBL_CARTAO_CREDITO);
		getContentPane().add(lblComboCartao,
				"cell 4 3 5 1,alignx left,aligny bottom");
		this.lblDataVencimento = new JLabel(LBL_DATA_VENCIMENTO);
		getContentPane().add(lblDataVencimento,
				"cell 12 3 4 1,alignx left,aligny bottom");
		getContentPane().add(fieldValor,
				"cell 0 4 3 1,alignx left,aligny center");

		this.comboCartao = new JComboBox();
		getContentPane().add(comboCartao, "cell 4 4 8 1,growx");
		this.pickerVencimento = new JDatePickerImpl(panelVencimento,
				new DateLabelFormatter());
		getContentPane().add(pickerVencimento,
				"cell 12 4 4 1,alignx left,aligny center");
		this.lblDespesaFixa = new JLabel(LBL_DESPESA_FIXA);
		getContentPane().add(lblDespesaFixa,
				"cell 0 6 3 1,alignx center,aligny top");

		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(separator_1, "cell 3 6 1 2,alignx center,growy");
		this.lblPago = new JLabel(LBL_PAGO);
		getContentPane().add(lblPago, "cell 4 6,alignx center,aligny top");
		this.lblDataPagamento = new JLabel(LBL_DATA_PAGAMENTO);
		getContentPane().add(lblDataPagamento,
				"cell 6 6 4 1,alignx left,aligny top");

		this.checkDespesaFixa = new JCheckBox();
		getContentPane().add(checkDespesaFixa,
				"cell 0 7 3 1,alignx center,aligny center");
		this.checkPago = new JCheckBox();
		getContentPane().add(checkPago, "cell 4 7,alignx center,aligny center");

		this.checkPago.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkPago.isSelected()) {
					pickerPagamento.setButtonEnable(true);
					setAllEditable(false);
				} else {
					panelPagamento.getModel().setSelected(false);
					pickerPagamento.setButtonEnable(false);
					setAllEditable(true);
				}
			}
		});
		
		this.pickerPagamento = new JDatePickerImpl(panelPagamento,
				new DateLabelFormatter());
		this.pickerPagamento.setButtonEnable(false);
		getContentPane().add(pickerPagamento,
				"cell 6 7 4 1,alignx left,aligny center");
		this.lblConta = new JLabel(LBL_CONTA);
		getContentPane().add(lblConta, "cell 0 9 2 1,alignx left,aligny top");
		this.lblCategoria = new JLabel(LBL_CATEGORIA);
		getContentPane().add(lblCategoria,
				"cell 9 9 3 1,alignx left,aligny top");
		this.comboConta = new JComboBox();
		getContentPane().add(comboConta, "cell 0 10 9 1,growx");

		this.comboCategoria = new JComboBox();
		getContentPane().add(comboCategoria, "cell 9 10 7 1,growx,aligny top");

		getContentPane().add(separator, "cell 0 11 16 1,growx,aligny center");
		getContentPane().add(btnCancelar, "cell 0 12 3 1,alignx left");

		getContentPane().add(btnSalvar, "cell 12 12 4 1,alignx right");

	}

	// HABILITA OU DESABILITA A ALTERACAO DOS VALORES DOSCOMPONENTES
	private void setAllEditable(boolean flag) {
		this.fieldDescricao.setEditable(flag);
		this.fieldValor.setEditable(flag);
		this.checkDespesaFixa.setEnabled(flag);
		this.comboCategoria.setEnabled(flag);
		this.comboCartao.setEnabled(flag);
		this.comboConta.setEnabled(flag);
		this.pickerVencimento.setButtonEnable(flag);
	}

	private void cancelarAction(java.awt.event.ActionEvent evt) {
		if (cancelar() == 0) {
			closeThisFrame();
		}
	}

	private void salvarAction(java.awt.event.ActionEvent evt) {
		boolean flag = DespesaView.controllerDespesaReceita.atualizar(
				this.fieldDescricao.getText(), (Date) this.pickerVencimento
						.getModel().getValue(), (Date) this.pickerPagamento
						.getModel().getValue(), this.fieldValor.getText(),
				this.checkDespesaFixa.isSelected(), this.comboCategoria
						.getSelectedItem(), this.comboConta.getSelectedItem(),
				this.comboCartao.getSelectedItem(), this.checkPago.isSelected(), Integer
						.parseInt(this.fieldCod.getText()), DESPESA);
		if (flag)
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

	// METODOS PARA SETAR OS VALORES A SEREM EDITADOS.
	public void setCod(int cod) {
		this.fieldCod.setText(String.valueOf(cod));
		this.fieldCod.setEditable(false);
	}

	public void setDescricao(String descricao) {
		this.fieldDescricao.setText(descricao);
	}

	public void setValor(Double valor) {
		this.fieldValor.setText(valor.toString());
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.modelVencimento.setValue(dataVencimento.toDate());
		this.modelVencimento.setSelected(true);
	}

	public void setDespesaFixa(boolean flag) {
		this.checkDespesaFixa.setSelected(flag);
	}

	public void setCategoria(JComboBox comboCategoria) {
		this.comboCategoria.setModel(comboCategoria.getModel());
	}

	public void setConta(JComboBox comboConta) {
		this.comboConta.setModel(comboConta.getModel());
	}

	public void setCartao(JComboBox comboCartao) {
		this.comboCartao.setModel(comboCartao.getModel());
	}

	public void setPago(boolean flag) {
		this.checkPago.setSelected(flag);
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.modelPagamento.setValue(dataPagamento.toDate());
		this.modelPagamento.setSelected(true);
	}

	// CONSTANTES
	private String NOME_TELA = "Despesa - Editar";
	private String LBL_DESCRICAO = "*Descrição";
	private String LBL_VALOR = "*Valor";
	private String LBL_DATA_VENCIMENTO = "*Data de Vencimento";
	private String LBL_DESPESA_FIXA = "Despesa Fixa";
	private String LBL_CATEGORIA = "*Categoria";
	private String LBL_PAGO = "Pago";
	private String LBL_DATA_PAGAMENTO = "Data de Pagamento";
	private String LBL_CONTA = "*Conta";
	private String LBL_COD = "Cod.";
	private String LBL_CARTAO_CREDITO = "Cartão de crédito";
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
	private JLabel lblConta;
	private JLabel lblPago;
	private JLabel lblDataPagamento;
	private JLabel lblCod;
	private JLabel lblComboCartao;

	// FIELD
	private JTextField fieldDescricao;
	private JTextField fieldValor;
	private JTextField fieldCod;

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
	private JSeparator separator_1;
}
