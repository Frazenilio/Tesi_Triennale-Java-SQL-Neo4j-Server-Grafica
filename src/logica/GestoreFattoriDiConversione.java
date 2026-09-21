package logica;

import java.util.List;

import errori.ErroreFDCOutOfBounds;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import utility.Tupla;

public class GestoreFattoriDiConversione {

	private GestoreFDCCalcolo gestoreFDCCalcolo;
	private GestoreFDCLimiti gestoreFDCLimiti;

	public GestoreFattoriDiConversione() {
		super();
		this.gestoreFDCCalcolo = new GestoreFDCCalcolo();
		this.gestoreFDCLimiti= new GestoreFDCLimiti();
	}

	
	public GestoreFDCCalcolo getGestoreFDCCalcolo() {
		return gestoreFDCCalcolo;
	}
	public GestoreFDCLimiti getGestoreFDCLimiti() {
		return gestoreFDCLimiti;
	}

	public int calcolaDurataOfferta(Gerarchia richiesta, Gerarchia offerta, int durataRichiesta, double fdc) {
		return this.gestoreFDCCalcolo.calcolaDurataOfferta(richiesta, offerta, durataRichiesta, fdc);
	}

	/**
	 * I limiti sui fdc sono controllati dal Controller
	 * @param retrievedFdcsRichiesta 
	 * @return 
	 */
	public List<FattoreDiConversione> calcolaFattoriConversione(Gerarchia fogliaBersaglio,Gerarchia fogliaNuova,
			Double fdcDaNuovaABersaglio, List<FattoreDiConversione> retrievedFdcsRichiesta) throws ErroreFDCOutOfBounds{
		return this.gestoreFDCCalcolo.calcolaFattoriConversione(fogliaBersaglio, fogliaNuova, fdcDaNuovaABersaglio, retrievedFdcsRichiesta);
	}

	public Tupla<Double,Double> calcolaMinMax(Gerarchia foglia1,Gerarchia foglia2, List<FattoreDiConversione> allFdcsFromFirst){
		return this.getGestoreFDCLimiti().calcolaMinMax(foglia1, foglia2, allFdcsFromFirst);
	}

}
