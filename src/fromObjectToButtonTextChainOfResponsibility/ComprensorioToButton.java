package fromObjectToButtonTextChainOfResponsibility;

import strutture.Comprensorio;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di stringhe di testo adatte
 * per JButton a partire da un Comprensorio
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class ComprensorioToButton implements ObjectToTextInterface{

	ObjectToTextInterface nextHandler = new GerarchiaToButton();
	
	public ComprensorioToButton() {
		
	}
	
	@Override
	public <T> String objectToTextButton(T object) {
		if(object instanceof Comprensorio) {
			return "" + ((Comprensorio) object).getArrayComuni();
		}
		return nextHandler.objectToTextButton(object);
	}
}
