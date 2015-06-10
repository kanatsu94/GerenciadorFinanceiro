package view;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.miginfocom.swing.MigLayout;

public class AdicionaCartao extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	
	public AdicionaCartao() {
		initComnponents();
	}

	
	
	private void initComnponents() {
		setBounds(100, 100, 621, 208);
		setTitle(NOME_TELA);
		setClosable(true);
		setIconifiable(true);
		
		getContentPane().setLayout(new MigLayout("", "[86px][][][12][102.00px][12.00px][65px][12][46px][][][12][19.00px][12][100.00px]", "[14px][][20px][][][][][][14px][21px][][][][14px][][][20px][][][]"));
		
		lblDescrição = new JLabel(LBL_DESCRICAO);
		getContentPane().add(lblDescrição,"cell 0 0,growx,aligny top");
		getContentPane().add(edtDescricao,"cell 0 0,growx,aligny top");
		getContentPane().add(edtFechamento,"cell 0 0,growx,aligny top");
		getContentPane().add(edtVencimento,"cell 0 0,growx,aligny top");
		
		edtDescricao = new JTextField();
		getContentPane().add(edtDescricao,"cell 0 1 15 1,growx,aligny top");
		lblVencimento = new JLabel(LBL_VENCIMENTO);
		getContentPane().add(lblVencimento,"cell 0 3,growx,aligny top");
		lblFechaento = new JLabel(LBL_FECHAMENTO);
		getContentPane().add(lblFechaento,"cell 14 3,growx,aligny center");
		
		edtVencimento = new JTextField();
		getContentPane().add(edtVencimento,"cell 0 4,growx,aligny top");
		
		edtFechamento = new JTextField();
		getContentPane().add(edtFechamento,"cell 14 4,growx,aligny top");
		
		separador = new JSeparator();
		getContentPane().add(separador,"cell 0 6 15 1,growx,aligny top");
		
		btnSalvar = new JButton(BTN_SALVAR);
		getContentPane().add(btnSalvar,"cell 4 11,growx,aligny top");
		
		btnCancelar = new JButton(BTN_CANCELAR);
		getContentPane().add(btnCancelar,"cell 9 11,growx,aligny top");
	}
	
	public void setPosicao() {
		Dimension d = this.getDesktopPane().getSize();
		this.setLocation((d.width - this.getSize().width) / 2,
				(d.height - this.getSize().height) / 2);
	}


	private final String NOME_TELA = "Adicionar Cartão de Crédito";
	private final String LBL_DESCRICAO = "Descrição";
	private final String LBL_VENCIMENTO = "Vencimento (Dia de Vencimento)";
	private final String LBL_FECHAMENTO = "Fechamento (Dia do Fechamento da Fatura)";
	private final String BTN_CANCELAR = "Cancelar";
	private final String BTN_SALVAR = "Salvar";
	
	private JLabel lblDescrição;
	private JLabel lblVencimento;
	private JLabel lblFechaento;
	
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	private JTextField edtDescricao;
	private JTextField edtVencimento;
	private JTextField edtFechamento;
			
	private JSeparator separador;
}
