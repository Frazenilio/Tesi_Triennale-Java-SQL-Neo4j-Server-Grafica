package logica;

import java.util.List;

import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import utility.Tupla;

public class GestoreFDCLimiti {

	public Tupla<Double,Double> calcolaMinMax(Gerarchia foglia1,Gerarchia foglia2, List<FattoreDiConversione> allFdcs){
		Double maxFDC = calcolaLimiteMax(allFdcs,Limits.MAX_FDC.getValue());
		Double minFDC = calcolaLimiteMin(allFdcs,Limits.MIN_FDC.getValue());
		return new Tupla<Double,Double>(minFDC,maxFDC);
	}

	private Double calcolaLimiteMax(List<FattoreDiConversione> tuttiIFattoriUscentiDaFoglia1, Double maxFdc) {
		Double max=maxFdc;
		for(FattoreDiConversione x:tuttiIFattoriUscentiDaFoglia1) {
			max=Math.min(max, maxFdc/x.getFdc().getSecond());
		}
		return max;
	}

	private Double calcolaLimiteMin(List<FattoreDiConversione> tuttiIFattoriUscentiDaFoglia1, Double minFdc) {
		Double min=minFdc;
		for(FattoreDiConversione x:tuttiIFattoriUscentiDaFoglia1) {
			min=Math.max(min, minFdc/x.getFdc().getSecond());
		}
		return min;

	}
}
