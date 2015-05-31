package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Restringe a digitação de apenas numeros em um componentes de texto como o
 * JTextField Uso: setDocument(new OnlyNumberField());
 * 
 * @author Eduardo Costa - www.dimensaotech.com
 *
 */
public class TextFieldDocument extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxlength;

	public TextFieldDocument() {
	}

	public TextFieldDocument(int maxlength) {
		super();
		this.maxlength = maxlength;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) {
		try {
			boolean fixedLengh = (!((getLength() + str.length()) > maxlength));
			if (maxlength == 0 || fixedLengh)
				super.insertString(offs, str, a);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
