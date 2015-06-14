package view;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;

import org.jdatepicker.impl.UtilDateModel;
import org.joda.time.LocalDate;

import util.DateLabelFormatter;
import util.JDatePanelImpl;
import util.JDatePickerImpl;
import controller.CartaoCreditoController;

@SuppressWarnings("rawtypes")
public class EfetivarCartaoView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EfetivarCartaoView() {
		this.vetorMes = new Vector<String>();
		this.vetorAno = new Vector<String>();

		this.vetorMes.addElement("Jan");
		this.vetorMes.addElement("Fev");
		this.vetorMes.addElement("Mar");
		this.vetorMes.addElement("Abr");
		this.vetorMes.addElement("Mai");
		this.vetorMes.addElement("Jun");
		this.vetorMes.addElement("Jul");
		this.vetorMes.addElement("Ago");
		this.vetorMes.addElement("Set");
		this.vetorMes.addElement("Out");
		this.vetorMes.addElement("Nov");
		this.vetorMes.addElement("Dez");

		for (int i = 2010; i <= new LocalDate().getYear(); i++) {
			vetorAno.addElement("" + i);
		}

		this.controller = new CartaoCreditoController();

		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		setBounds(100, 100, 490, 295);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		
		this.p = new Properties();
		this.p.put("text.today", "Hoje");
		this.p.put("text.month", "Mês");
		this.p.put("text.year", "Ano");
		
		this.modelPagamento = new UtilDateModel();
		this.panelPagamento = new JDatePanelImpl(this.modelPagamento, this.p);
		this.panelPagamento.setShowYearButtons(true);
		
		getContentPane()
				.setLayout(
						new MigLayout("", "[30][30][30][30][30][30][30][20][30][15][25][25][20][25][25]", "[25][25][13.00][25][25][25][25][25][25]"));

		this.lblCartaoCredito = new JLabel(LBL_CARTAO_CREDITO);
		getContentPane().add(lblCartaoCredito, "flowx,cell 0 0 9 1,growx");

		this.lblAno = new JLabel(LBL_ANO);
		getContentPane().add(lblAno, "cell 13 0 2 1,alignx left");

		this.comboCartao = controller.getComboCartaoCredito(true);
		// this.comboCartao = new JComboBox();
		getContentPane().add(comboCartao, "cell 0 1 9 1,growx,aligny center");
		this.comboCartao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				panelPagamento.getModel().setSelected(false);
				if (comboCartao.getSelectedIndex() == 0) {
					setAllVisible(false);
				} else {
					setResumo();
					setAllVisible(true);
				}
			}
		});

		this.comboMes = new JComboBox(vetorMes);
		comboMes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (comboCartao.getSelectedIndex() > 0) {
					setResumo();
				}
			}
		});
		this.comboMes.setToolTipText(DICA_MES);
		this.comboMes.setSelectedIndex(new LocalDate().getMonthOfYear() - 1);
		getContentPane().add(comboMes, "cell 10 1 2 1,growx");

		this.comboAno = new JComboBox(vetorAno);
		this.comboAno.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (comboCartao.getSelectedIndex() > 0) {
					setResumo();
				}
			}
		});
		this.comboAno.setSelectedIndex(this.vetorAno.size() - 1);
		this.comboAno.setToolTipText(DICA_ANO);
		getContentPane().add(comboAno, "cell 13 1 2 1,growx");

		this.separator_1 = new JSeparator();
		getContentPane().add(separator_1, "cell 0 2 15 1,growx");
		Dimension d = new Dimension();

		this.lblVencimento = new JLabel("ven");
		getContentPane().add(lblVencimento, "flowx,cell 0 3 9 1,growx");
		
		this.lblDataPagamento = new JLabel(LBL_DATA_PAGAMENTO);
		getContentPane().add(lblDataPagamento, "cell 11 3 4 1,alignx left,aligny center");

		this.lblFechamento = new JLabel("fec");
		getContentPane().add(lblFechamento, "cell 0 4 9 1,growx");
		
		this.pickerPagamento = new JDatePickerImpl(panelPagamento,
				new DateLabelFormatter());
		d.setSize(this.pickerPagamento.getPreferredSize().getWidth()/1.5, this.pickerPagamento.getPreferredSize().getHeight());
		this.pickerPagamento.setPreferredSize(d);
		
		getContentPane().add(this.pickerPagamento, "cell 11 4 4 1,alignx left,aligny center");

		this.lblNumeroTotal = new JLabel("tot");
		getContentPane().add(lblNumeroTotal, "cell 0 5 15 1,growx");

		this.lblValorTotal = new JLabel("tot");
		getContentPane().add(lblValorTotal, "cell 0 6 15 1,growx");

		this.separator = new JSeparator();
		getContentPane().add(separator, "cell 0 7 15 1,growx,aligny center");

		this.btnCancelar = new JButton(BTN_CANCELAR);
		getContentPane().add(btnCancelar, "cell 0 8 3 1,alignx left");
		this.btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelarAction(evt);
			}
		});

		this.lblMes = new JLabel(LBL_MES);
		getContentPane().add(lblMes, "cell 10 0 2 1,alignx left");

		this.btnEfetivar = new JButton(BTN_EFETIVAR);
		btnEfetivar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				efetivarAction(evt);
			}
		});
		getContentPane().add(btnEfetivar, "cell 12 8 3 1,alignx right");

		this.setAllVisible(false);
		setVisible(true);
	}

	private void cancelarAction(java.awt.event.ActionEvent evt) {
		closeThisFrame();
	}

	private void efetivarAction(java.awt.event.ActionEvent evt) {
		if(controller.efetivarCartao(comboCartao.getSelectedItem(), comboMes.getSelectedIndex()+1, Integer.parseInt((String)comboAno.getSelectedItem()), (Date) this.pickerPagamento
				.getModel().getValue()))
			setResumo();
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

	private void setAllVisible(boolean aFlag) {
		this.lblFechamento.setVisible(aFlag);
		this.lblNumeroTotal.setVisible(aFlag);
		this.lblValorTotal.setVisible(aFlag);
		this.lblVencimento.setVisible(aFlag);
		this.btnEfetivar.setEnabled(aFlag);
		this.pickerPagamento.setButtonEnable(aFlag);
	}

	private void setResumo() {
		ArrayList<String> resumo = controller.getResumoCartaoCredito(
				comboCartao.getSelectedItem(), comboMes.getSelectedIndex() + 1,
				Integer.parseInt((String) comboAno.getSelectedItem()));

		lblVencimento.setText(resumo.get(0));
		lblFechamento.setText(resumo.get(1));
		lblNumeroTotal.setText(resumo.get(2));
		lblValorTotal.setText(resumo.get(3));
	}

	private String NOME_TELA = "Efetivar Cartão de Crédito";
	private String LBL_CARTAO_CREDITO = "Cartão de crédito";
	private String BTN_CANCELAR = "Cancelar";
	private String BTN_EFETIVAR = "Efetivar";
	private String LBL_MES = "Mês";
	private String LBL_ANO = "Ano";
	private String DICA_MES = "Selecione o mês da fatura.";
	private String DICA_ANO = "Selecione o ano da fatura.";
	private String LBL_DATA_PAGAMENTO = "*Data do pagamento";

	private JComboBox comboCartao;

	private JLabel lblVencimento;
	private JLabel lblCartaoCredito;
	
	private CartaoCreditoController controller;

	private JButton btnCancelar;
	private JButton btnEfetivar;
	private JSeparator separator;
	private JLabel lblFechamento;
	private JLabel lblNumeroTotal;
	private JLabel lblValorTotal;
	private JSeparator separator_1;
	private JLabel lblMes;
	private JLabel lblAno;
	private JComboBox comboMes;
	private Vector<String> vetorMes;
	private Vector<String> vetorAno;
	private JComboBox comboAno;
	private JLabel lblDataPagamento;
	
	// DATE PICKER
	private JDatePickerImpl pickerPagamento;
	private UtilDateModel modelPagamento;
	private JDatePanelImpl panelPagamento;
	private Properties p;
}