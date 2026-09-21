package personalGraphicElements;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MyJTextPane extends JTextPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyJTextPane() {
		super();
		initialize();
	}
	
	/**
	 * Costruttore
	 * @param text testo della JTextArea
	 * @since TESI
	 */
	public MyJTextPane(String text) {
		super.setText(text);
		initialize();
	}

	/**
	 * Metodo invocato alla costruzione
	 * @param text testo da settare
	 * @since TESI
	 */
	public void initialize() {
		this.setEditable(false);
		StyledDocument doc = super.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}
	
	public void addStyle(ImageIcon icon) {
		Style style = super.addStyle("icon", null);
		StyleConstants.setIcon(style, icon);
		StyledDocument doc = super.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), " ", style);
		} catch (BadLocationException e) {
			
		}
	}
}
