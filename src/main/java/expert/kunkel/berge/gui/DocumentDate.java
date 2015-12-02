package expert.kunkel.berge.gui;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DocumentDate extends PlainDocument {

	private static final long serialVersionUID = 317855063418956014L;

	static protected Toolkit toolkit = Toolkit.getDefaultToolkit();

	protected int countErrors = 0;

	public DocumentDate() {
	}

	@Override
	public void insertString(int offset, String string, AttributeSet attribute)
			throws BadLocationException {

		if (string != null) {
			int newOffset = offset;
			boolean inserted = false;
			char[] source = string.toCharArray();
			char[] result = new char[source.length + 2];
			int j = 0;

			for (int i = 0; i < source.length; i++) {
				if (((offset == 2) || (offset == 5))
						&& Character.isDigit(source[i])) {
					result[j++] = '.';
				}
				if ((offset == 1 || offset == 4) && source[i] == '.') {
					super.insertString(offset - 1, "0", attribute);
					inserted = true;
				}
				if (offset == 7) {
					int zahl = (Integer.valueOf(this.getText(offset - 1, 1))
							.intValue() * 10)
							+ Integer.valueOf(string).intValue();
					if ((zahl != 19) && (zahl != 20)) {
						if (zahl < 45) {
							super.insertString(offset - 1, "20", attribute);
							// super.insertString(offset - 2 , "0", attribute);
							newOffset++;
							newOffset++;
						} else {
							super.insertString(offset - 1, "19", attribute);
							// super.insertString(offset - 2, "9", attribute);
							newOffset++;
							newOffset++;
						}
					}
				}
				if (Character.isDigit(source[i]) || source[i] == '.') {
					result[j++] = source[i];
				} else {
					toolkit.beep();
					countErrors++;
					if (countErrors == 3) {
						JOptionPane.showMessageDialog(null,
								"Bitte ein Datum eingeben!");
						countErrors = 0;
					}
				}
			}

			if ((getContent().length() + j) > 11) {
				toolkit.beep();
				countErrors++;
				if (countErrors == 3) {
					JOptionPane.showMessageDialog(null,
							"Zu viele Zeichen für ein Datum!\n");
					countErrors = 0;
				}
			} else {
				if (inserted) {
					newOffset++;
				}
				super.insertString(newOffset, new String(result, 0, j),
						attribute);
			}
		} else {
			super.insertString(offset, string, attribute);
		}
	}
}
