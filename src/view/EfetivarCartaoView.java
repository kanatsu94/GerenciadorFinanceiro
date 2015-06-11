package view;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;

import org.joda.time.LocalDate;

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
		setBounds(100, 100, 470, 300);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		getContentPane()
				.setLayout(
						new MigLayout("", "[30][30][30][30][30][30][30][30][30][30][30][30][30]", "[25][25][25][25][25][25][25][25][25]"));

		this.lblCartaoCredito = new JLabel(LBL_CARTAO_CREDITO);
		getContentPane().add(lblCartaoCredito, "flowx,cell 0 0 7 1,growx");

		this.lblAno = new JLabel(LBL_ANO);
		getContentPane().add(lblAno, "cell 11 0 2 1");

		this.comboCartao = controller.getComboCartaoCredito();
		// this.comboCartao = new JComboBox();
		getContentPane().add(comboCartao, "cell 0 1 7 1,growx,aligny center");
		this.comboCartao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
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
		getContentPane().add(comboMes, "cell 8 1 2 1,growx");

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
		getContentPane().add(comboAno, "cell 11 1 2 1,growx");

		this.separator_1 = new JSeparator();
		getContentPane().add(separator_1, "cell 0 2 13 1,growx");

		this.lblVencimento = new JLabel("");
		getContentPane().add(lblVencimento, "cell 0 3 13 1,growx");

		this.lblFechamento = new JLabel("");
		getContentPane().add(lblFechamento, "cell 0 4 13 1,growx");

		this.lblNumeroTotal = new JLabel("");
		getContentPane().add(lblNumeroTotal, "cell 0 5 13 1,growx");

		this.lblValorTotal = new JLabel("");
		getContentPane().add(lblValorTotal, "cell 0 6 13 1,growx");

		this.separator = new JSeparator();
		getContentPane().add(separator, "cell 0 7 13 1,growx,aligny center");

		this.btnCancelar = new JButton(BTN_CANCELAR);
		getContentPane().add(btnCancelar, "cell 0 8 3 1,alignx left");
		this.btnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelarAction(evt);
			}
		});

		this.lblMes = new JLabel(LBL_MES);
		getContentPane().add(lblMes, "cell 8 0 2 1");

		this.btnEfetivar = new JButton(BTN_EFETIVAR);
		btnEfetivar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				efetivarAction(evt);
			}
		});
		getContentPane().add(btnEfetivar, "cell 10 8 3 1,alignx right");

		this.setAllVisible(false);
		setVisible(true);
	}

	private void cancelarAction(java.awt.event.ActionEvent evt) {
		closeThisFrame();
	}

	private void efetivarAction(java.awt.event.ActionEvent evt) {
		if(controller.efetivarCartao(comboCartao.getSelectedItem(), comboMes.getSelectedIndex()+1, Integer.parseInt((String)comboAno.getSelectedItem())))
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
}