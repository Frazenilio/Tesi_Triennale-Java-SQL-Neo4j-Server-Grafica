package fromObjectToGraphicChainOfResponsibility;

import javax.swing.JPanel;

import errori.ErroreDatiAssenti;

/**
 * Classe usata per accedere al CoR
 * per la graficazione di oggetti
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class ObjectListGraphic {

	private static ObjectConverterHandlerInterface handler = new TextToGraphic();
	
	/**
	 * Metodo per ottenere un oggetto 
	 * rappresentato con grafica
	 * @param <T> Tipo dell'oggetto da trasformare
	 * @param object oggetto da trasformare
	 * @return JPanel contenente l'oggetto graficato
	 * @throws ErroreDatiAssenti 
	 */
	public static <T> JPanel objConverterToGraphic(T object) throws ErroreDatiAssenti {
		JPanel container = new JPanel();
		try {
			container = handler.objectToGraphic(object);
		} catch (ErroreDatiAssenti e) {
			throw new ErroreDatiAssenti();
		}
		return container;
	}
}
