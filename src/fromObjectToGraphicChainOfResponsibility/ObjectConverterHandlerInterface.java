package fromObjectToGraphicChainOfResponsibility;

import javax.swing.JPanel;

import errori.ErroreDatiAssenti;

/**
 * Interface per pattern Chain of Responsibilty 
 * per la creazione di JPanel usato per rappresentare
 * un oggetto
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public interface ObjectConverterHandlerInterface {
	
	/**
	 * Metodo per ottenere la rappresentazione grafica di un oggetto
	 * @param <T> Tipo dell'oggetto da rappresentare
	 * @param object oggetto da rappresentare
	 * @return JPanel contenente l'oggetto rappresentato
	 */
	<T> JPanel objectToGraphic(T object) throws ErroreDatiAssenti;
}
