package personalGraphicElements;

import java.util.List;

/**
 * interface per implementare una classe
 * che si occupi di contenere le informazioni
 * necessarie per un MenuWithButtons
 * @author Francesco Lozio 737664
 * @since TESI
 */
public interface ButtonsContainer {
	
	/**
	 * Metodo per ottenere i MyJButton associati
	 * @return una List di MyJButton associata
	 * @since TESI
	 */
	List<MyJButton> getButtons();
	
	/**
	 * Metodo per ottenere il testo da mostrare
	 * @return String per il messaggio da mostrare
	 * @since TESI
	 */
	String getText();
	
	/**
	 * Metodo per settare il testo da mostrare
	 * @param newText nuova Stringa da impostare
	 * @since TESI
	 */
	void setText(String newText);
}
