package fromObjectToButtonTextChainOfResponsibility;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di stringhe di testo adatte
 * per JButton partendo da una stringa
 * ATTUALMENTE, questa e' la prima classe della CoR
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class TextToButtonText implements ObjectToTextInterface{

	ObjectToTextInterface nextHandler = new ComprensorioToButton();
	
	public TextToButtonText() {
	}
	
	@Override
	public <T> String objectToTextButton(T object) {
		if(object instanceof String) {
			return (String) object;
		}
		return nextHandler.objectToTextButton(object);
	}

}
