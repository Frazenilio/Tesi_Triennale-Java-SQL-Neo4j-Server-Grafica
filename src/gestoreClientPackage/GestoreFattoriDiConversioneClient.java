package gestoreClientPackage;

import java.util.List;

import errori.ErroreFDCOutOfBounds;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import requestPackage.RequestCalcolaFattoriConversione;
import requestPackage.RequestCalcolaMinMax;
import requestPackage.RequestRetrieveAllFdcsFromFoglia;
import requestPackage.RequestRetrieveFattoreConversione;
import responsePackage.ResponseCalcolaFattoriConversione;
import responsePackage.ResponseCalcolaMinMax;
import responsePackage.ResponseRetrieveAllFdcsFromFoglia;
import responsePackage.ResponseRetrieveFattoreConversione;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import utility.Tupla;

/**
 * Classe per la gestione delle richieste al model sul server
 * per la gestione dei {@link FattoreDiConversione}
 * @author Francesco Lozio
 * @since TESI CLIENT-SERVER
 */
public class GestoreFattoriDiConversioneClient {

	private DeliveryMethodInterface deliverer;

	
	public GestoreFattoriDiConversioneClient(DeliveryMethodInterface deliverer) {
		this.deliverer = deliverer;
	}
	
	private DeliveryMethodInterface accediDeliverer() {
		return this.deliverer;
	}

	/**
	 * Metodo per calcolare i {@link FattoreDiConversione} di gerarchie 
	 * @param fogliaBersaglio {@link Gerarchia} bersaglio del {@link FattoreDiConversione} iniziale
	 * @param fogliaNuova nuova {@link Gerarchia} di cui si calcolano i {@link FattoreDiConversione}
	 * @param fdcDaNuovaABersaglio il {@link FattoreDiConversione} con fogliaBersaglio
	 * @throws ErroreFDCOutOfBounds
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public void calcolaFattoriConversione(Gerarchia fogliaBersaglio, Gerarchia fogliaNuova,
			Double fdcDaNuovaABersaglio)
			throws ErroreFDCOutOfBounds, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestCalcolaFattoriConversione request = new RequestCalcolaFattoriConversione(fogliaBersaglio,
				fogliaNuova, fdcDaNuovaABersaglio);
		ResponseCalcolaFattoriConversione response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
	}

	/**
	 * Metodo per richiedere al Server di calcolare il minimo ed il massimo del range del {@link FattoreDiConversione}
	 *  tra due {@link Gerarchia} foglie
	 * @param foglia1 prima foglia
	 * @param foglia2 seconda foglia
	 * @return Tupla con i valori di minimo e massimo rispettivamente
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public Tupla<Double, Double> calcolaMinMax(Gerarchia foglia1, Gerarchia foglia2) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply{
		RequestCalcolaMinMax request = new RequestCalcolaMinMax(foglia1, foglia2);
		ResponseCalcolaMinMax response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}

	/**
	 * Metodo per ottenere un {@link FattoreDiConversione} dal Server tra due {@link Gerarchia}
	 * @param gerStart {@link Gerarchia} di partenza del {@link FattoreDiConversione} ricercato
	 * @param gerEnd {@link Gerarchia} di destinazione del {@link FattoreDiConversione} ricercato
	 * @return il {@link FattoreDiConversione} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public FattoreDiConversione retrieveFattoreConversione(Gerarchia gerStart, Gerarchia gerEnd)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestRetrieveFattoreConversione request = new RequestRetrieveFattoreConversione(gerStart, gerEnd);
		ResponseRetrieveFattoreConversione response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}

	/**
	 * Metodo per ottenere tutti i {@link FattoreDiConversione} dal Server relativi ad una specifica {@link Gerarchia}
	 * @param foglia1 {@link Gerarchia} di cui si vuole ottenere tutti i {@link FattoreDiConversione}
	 * @return Lista di {@link FattoreDiConversione}
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public List<FattoreDiConversione> retrieveAllFdcsFromFoglia(Gerarchia foglia1)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestRetrieveAllFdcsFromFoglia request = new RequestRetrieveAllFdcsFromFoglia(foglia1);
		ResponseRetrieveAllFdcsFromFoglia response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}
}
