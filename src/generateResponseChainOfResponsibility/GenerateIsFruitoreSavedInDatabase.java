package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestIsFruitoreSavedInDatabase;
import responsePackage.Response;
import responsePackage.ResponseIsFruitoreSavedInDatabase;
import utente.Fruitore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento del risultato alla domanda se un comune e' gia' presente nell'array dei Comprensori
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateIsFruitoreSavedInDatabase implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateCalcolaDurataOfferta();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestIsFruitoreSavedInDatabase) {
			RequestIsFruitoreSavedInDatabase castRequest = (RequestIsFruitoreSavedInDatabase) request;
			try {
				Fruitore result = model.isFruitoreSavedInDatabase(castRequest.getUsername(), castRequest.getPassword());
				return new ResponseIsFruitoreSavedInDatabase(EsitoRequest.SUCCESS, result);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseIsFruitoreSavedInDatabase(EsitoRequest.ERROR, null);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
