package utility;

import java.awt.event.KeyEvent;

import personalGraphicElements.MyJButton;

/**
 * Enum per contenere alcuni valori di {@link MyJButton} standard
 * @author Francesco Lozio 737664
 * @since TESI
 */
public enum InterazioneBottoni {

	EXIT("", InterazioneOperazioni.CANCEL_OPERATION.getVal()),
	AVANTI("↓", 1),
	INDIETRO("↑", -1),
	ENTER("↲", KeyEvent.VK_ENTER),
	CONFIRM_DATA("Conferma Dati", InterazioneOperazioni.CONFIRM_OPERATION.getVal()),
	ADD_SECTION("+ Aggiungi", InterazioneOperazioni.NO_EVENT.getVal()),
	REMOVE_SECTION("- Rimuovi", InterazioneOperazioni.NO_EVENT.getVal());
	
	
	
	String value;
	int intValue;
	
	InterazioneBottoni(String value, int intValue) {
		this.value = value;
		this.intValue = intValue;
	}
	
	/**
	 * Ritorna il valore in Stringa dell'enum
	 * @return String associata
	 * @since TESI
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Ritorna il valore int dell'enum
	 * @return int associato
	 * @since TESI
	 */
	public int getIntValue() {
		return this.intValue;
	}
}
