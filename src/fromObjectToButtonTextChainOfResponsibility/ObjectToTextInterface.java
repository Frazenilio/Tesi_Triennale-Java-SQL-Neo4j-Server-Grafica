package fromObjectToButtonTextChainOfResponsibility;

/**
 * Interface per pattern Chain of Responsibilty 
 * per la creazione di stringhe di testo adatte
 * per JButton
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public interface ObjectToTextInterface {

	/**
	 * Metodo per convertire un oggetto in stringa
	 * @param <T> tipo dell'oggetto da convertire
	 * @param object oggetto da convertire
	 * @return stringa ottenuta partendo da object
	 * @since TESI
	 */
	<T> String objectToTextButton(T object);
}
