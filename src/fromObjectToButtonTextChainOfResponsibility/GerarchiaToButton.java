package fromObjectToButtonTextChainOfResponsibility;

import strutture.Gerarchia;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di stringhe di testo adatte
 * per JButton a partire da una Gerarchia
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class GerarchiaToButton implements ObjectToTextInterface{

	ObjectToTextInterface nextHandler = new PropostaToButton();
	
	public GerarchiaToButton() {
		
	}
	
	@Override
	public <T> String objectToTextButton(T object) {
		if(object instanceof Gerarchia) {
			StringBuilder builder = new StringBuilder();
			Gerarchia ger = (Gerarchia) object;
			if(ger.getPadre() != null) {
				for(int i = 0; i < ger.getPadre().getFigli().size(); i++) {
					if(ger.getPadre().getFigli().get(i).equals(ger)) {
						builder.append("Elemento Dominio: ");
						builder.append(ger.getPadre().getDominio().get(i).getFirst());
						if(ger.getPadre().getDominio().get(i).getSecond() != "") {
							builder.append(" (" + ger.getPadre().getDominio().get(i).getSecond() + ")");
						}
						builder.append(", ");
					}
				}
			}
			builder.append(ger.getCategoria());
			if(ger.isFoglia()) {
				builder.append(", Foglia");
			}
			else {
				builder.append(", Campo: ");
				builder.append(ger.getCampo());
			}
			return builder.toString();
		}
		return nextHandler.objectToTextButton(object);
	}

}
