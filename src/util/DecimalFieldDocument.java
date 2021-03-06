package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Restringe a digita��o de apenas numeros em um componentes de texto como o
 * JTextField Uso: setDocument(new OnlyNumberField());
 * 
 * @author Eduardo Costa - www.dimensaotech.com
 *
 */
public class DecimalFieldDocument extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxlength;

	public DecimalFieldDocument() {
	}

	public DecimalFieldDocument(int maxlength) {
		super();
		this.maxlength = maxlength;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException ex) {
			if (!str.equals(","))
				str = "";
		}
		try {
			boolean fixedLengh = (!((getLength() + str.length()) > maxlength));

			if (maxlength == 0 || fixedLengh && !str.isEmpty()) {
				str = str.replace(".", ",");
				super.insertString(offs, str, a);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
