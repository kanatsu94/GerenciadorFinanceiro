package util;

import javax.swing.JOptionPane;

public abstract class ExibeMensagem {
	// EXIBE MENSAGENS
	public static void showMessage(String msg, String title, int type) {
		JOptionPane.showMessageDialog(null, msg, title, type);
	}

	// EXIBE UMA JANELA DE PERGUNTA
	public static int showQuestionMessage(String msg, String title, int type) {

		// 0 = SIM
		// 1 = NAO
		// 2 = CANCELAR
		return JOptionPane.showConfirmDialog(null, msg, title, type);
	}
}
