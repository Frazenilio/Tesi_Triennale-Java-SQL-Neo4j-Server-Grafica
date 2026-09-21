package personalGraphicElements;

import javax.swing.JTextArea;

/**
 * Classe per una {@link JTextArea} personalizzata
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class MyJTextArea extends JTextArea {
	
	
	/**
	 * Costruttore
	 * @param text testo della JTextArea
	 * @since TESI
	 */
	public MyJTextArea(String text) {
		initialize(text);
	}

	/**
	 * Metodo invocato alla costruzione
	 * @param text testo da settare
	 * @since TESI
	 */
	public void initialize(String text) {
		super.setText(text);
		super.setAlignmentX(CENTER_ALIGNMENT);
		super.setAlignmentY(CENTER_ALIGNMENT);
		super.setLineWrap(true);
		super.setWrapStyleWord(true);
		this.setEditable(false);
	}
}
