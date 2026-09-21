package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreFDCOutOfBounds;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import utility.Tupla;

public class GestoreFDCCalcolo {
	

	public int calcolaDurataOfferta(Gerarchia richiesta, Gerarchia offerta, int durataRichiesta, 
			double fdc) {
		int durataOfferta;
		durataOfferta = (int) Math.round(fdc*durataRichiesta);
		return durataOfferta;
	}

	/**
	 * I limiti sui fdc sono controllati dal Controller
	 */
	public List<FattoreDiConversione> calcolaFattoriConversione(Gerarchia fogliaBersaglio,Gerarchia fogliaNuova,
			Double fdcDaNuovaABersaglio, List<FattoreDiConversione> retrievedFDCs) throws ErroreFDCOutOfBounds{

		List<FattoreDiConversione> risultato = new ArrayList<FattoreDiConversione>();
		requireFDCwithinBounds(fdcDaNuovaABersaglio);

		risultato.add(new FattoreDiConversione(new Tupla<UUID,Double>(fogliaBersaglio.getId(),1/fdcDaNuovaABersaglio)));
		
		for(FattoreDiConversione tupla: retrievedFDCs) {
			assegnazioneAFogliaDINuoviFattoriConversione(fogliaNuova, fdcDaNuovaABersaglio, tupla.getFdc(), risultato);
		}
		return risultato;
	}
	
	private void assegnazioneAFogliaDINuoviFattoriConversione(Gerarchia fogliaNuova, Double fdcDaNuovaABersaglio,
			Tupla<UUID, Double> tupla, List<FattoreDiConversione> risultato) {
		Double fattoreTemp=tupla.getSecond();
		UUID gerBersaglio = tupla.getFirst();
		if(gerBersaglio.equals(fogliaNuova.getId())) {}
		else {
			risultato.add(new FattoreDiConversione(new Tupla<UUID,Double>(gerBersaglio,fdcDaNuovaABersaglio*fattoreTemp)));
		}
	}

	/**
	 * Metodo che trows illegalArguemtExeption se il fdc e' fuori dai limiti
	 * @param fdc
	 */
	private void requireFDCwithinBounds(Double fdc) throws ErroreFDCOutOfBounds {
		if(fdc > Limits.MAX_FDC.getValue() || fdc < Limits.MIN_FDC.getValue() ) { ///CASO ERRORE
			throw new ErroreFDCOutOfBounds();
		}
	}
}
