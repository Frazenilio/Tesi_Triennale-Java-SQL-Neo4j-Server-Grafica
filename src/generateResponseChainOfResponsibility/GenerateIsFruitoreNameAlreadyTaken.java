package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestIsFruitoreNameAlreadyTaken;
import responsePackage.Response;
import responsePackage.ResponseIsFruitoreNameAlreadyTaken;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento del risultato alla domanda se l'username di un Fruitore e' gia' presente nel Database
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateIsFruitoreNameAlreadyTaken implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateIsFruitoreSavedInDatabase();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestIsFruitoreNameAlreadyTaken) {
			RequestIsFruitoreNameAlreadyTaken castRequest = (RequestIsFruitoreNameAlreadyTaken) request;
			try {
				boolean result = model.isFruitoreNameAlreadyTaken(castRequest.getName());
				return new ResponseIsFruitoreNameAlreadyTaken(EsitoRequest.SUCCESS, result);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseIsFruitoreNameAlreadyTaken(EsitoRequest.ERROR, false);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
