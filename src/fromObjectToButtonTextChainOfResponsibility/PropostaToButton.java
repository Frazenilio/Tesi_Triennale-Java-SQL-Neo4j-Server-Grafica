package fromObjectToButtonTextChainOfResponsibility;

import strutture.Proposta;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di stringhe di testo adatte
 * per JButton a partire da una Proposta
 * ATTUALMENTE questa e' l'ultima classe della CoR
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class PropostaToButton implements ObjectToTextInterface{

	
	ObjectToTextInterface nextHandler;
	
	@Override
	public <T> String objectToTextButton(T object) {
		if(object instanceof Proposta) {
			Proposta obj = (Proposta) object;
			StringBuilder builder = new StringBuilder();
			
			String catOff = obj.getOfferta().getCategoria();
			int durOff = obj.getDurataOfferta();
			
			String catRic = obj.getRichiesta().getCategoria();
			int durRic = obj.getDurataRichiesta();
			
			builder.append("O: " + catOff + " (" + durOff + ")");
			builder.append(" - ");
			builder.append("R: " + catRic + " (" + durRic + ")");
			
			return builder.toString();
		}
		return null;
	}

}
