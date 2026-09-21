package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveAllFdcsFromFoglia;
import responsePackage.Response;
import responsePackage.ResponseRetrieveAllFdcsFromFoglia;
import strutture.FattoreDiConversione;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di tutti i {@link FattoreDiConversione} legati ad una specifica foglia
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveAllFdcsFromFoglia implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveCicloDaChiudereById();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveAllFdcsFromFoglia) {
			RequestRetrieveAllFdcsFromFoglia castRequest = (RequestRetrieveAllFdcsFromFoglia) request;
			List<FattoreDiConversione> risultato;
			try {
				risultato = model.retrieveAllFdcsFromFoglia(castRequest.getFoglia());
				return new ResponseRetrieveAllFdcsFromFoglia(EsitoRequest.SUCCESS, risultato);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				e.printStackTrace();
				return new ResponseRetrieveAllFdcsFromFoglia(EsitoRequest.ERROR, null);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
