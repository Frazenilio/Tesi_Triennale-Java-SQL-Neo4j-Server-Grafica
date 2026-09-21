package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestIsFruitoreEmailAlreadyTaken;
import responsePackage.Response;
import responsePackage.ResponseIsFruitoreEmailAlreadyTaken;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento del risultato alla domanda se la mail di un fruitore e' gia' presente nel Database
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateIsFruitoreEmailAlreadyTaken implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateIsFruitoreNameAlreadyTaken();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestIsFruitoreEmailAlreadyTaken) {
			RequestIsFruitoreEmailAlreadyTaken castRequest = (RequestIsFruitoreEmailAlreadyTaken) request;
			try {
				boolean result = model.isFruitoreEmailAlreadyTaken(castRequest.getEmail());
				return new ResponseIsFruitoreEmailAlreadyTaken(EsitoRequest.SUCCESS, result);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseIsFruitoreEmailAlreadyTaken(EsitoRequest.ERROR, false);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
