package personalGraphicElements;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import utility.Colori;

/**
 * Classe per un {@link JButton} personalizzato
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class MyJButton extends JButton {

	private int value;

	/**
	 * Costruttore 
	 * @param value valore corrispondente al bottone
	 * @since TESI
	 */
	public MyJButton(int value) {
		super();
		initialize(value);
	}
	
	/**
	 * Costruttore con messaggio personalizzato sul bottone ed immagine
	 * @param text messaggio del bottone
	 * @param value valore corrispondente al bottone
	 * @param newImage immagine da usare per il bottone
	 * @since TESI
	 */
	public MyJButton(int value, ImageIcon newImage) {
		super(newImage);
		initialize(value);
	}
	
	/**
	 * Costruttore con messaggio personalizzato sul bottone ed immagine
	 * @param text messaggio del bottone
	 * @param value valore corrispondente al bottone
	 * @since TESI
	 */
	public MyJButton(String text, int value) {
		super(text);
		initialize(value);
	}

	/**
	 * Metodo lanciato alla costruzione del bottone per settarlo
	 * @param value valore del bottone 
	 */
	public void initialize(int value) {
		this.value = value;
		super.setBorder(null);
		super.setBorder(new LineBorder(Colori.NERO.getVal()));
	}
	
	public int getValueWhenPressed() {
		return value;
	}
}
