package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveFattoreConversione;
import responsePackage.Response;
import responsePackage.ResponseRetrieveFattoreConversione;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di uno specifico {@link FattoreDiConversione} date due {@link Gerarchia} 
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveFattoreConversione implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveAllFdcsFromFoglia();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveFattoreConversione) {
			RequestRetrieveFattoreConversione castRequest = (RequestRetrieveFattoreConversione) request;
			FattoreDiConversione risultato;
			try {
				risultato = model.retrieveFattoreConversione(castRequest.getGerStart(), castRequest.getGerEnd());
				return new ResponseRetrieveFattoreConversione(EsitoRequest.SUCCESS, risultato);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseRetrieveFattoreConversione(EsitoRequest.ERROR, null);
			}
			
		}
		return nextHandler.generateResponse(model, request);
	}

}
