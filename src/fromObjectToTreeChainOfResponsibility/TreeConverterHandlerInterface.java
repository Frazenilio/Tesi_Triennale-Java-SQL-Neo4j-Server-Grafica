package fromObjectToTreeChainOfResponsibility;

import errori.ErroreDatiAssenti;
import personalGraphicElements.MyJTree;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di MyJTree usato per rappresentare
 * oggetti, genericamente una lista o una struttura ad albero
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public interface TreeConverterHandlerInterface {

	/**
	 * Metodo per l'effettiva trasformazione in MyJTree
	 * @param <T> tipo dell'oggetto da convertire
	 * @param object oggetto da convertire
	 * @return MyJTree associato all'oggetto
	 * @throws ErroreDatiAssenti
	 * @since TESI
	 */
	<T> MyJTree treeToGraphic(T object) throws ErroreDatiAssenti;
}
