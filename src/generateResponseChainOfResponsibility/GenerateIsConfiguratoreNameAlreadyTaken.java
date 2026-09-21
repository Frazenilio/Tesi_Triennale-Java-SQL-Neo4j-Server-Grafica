package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestIsConfiguratoreNameAlreadyTaken;
import responsePackage.Response;
import responsePackage.ResponseIsConfiguratoreNameAlreadyTaken;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento del risultato alla domanda se l'username di un configuratore e' gia' presente nel database
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateIsConfiguratoreNameAlreadyTaken implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateIsConfiguratoreSavedInDatabase();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestIsConfiguratoreNameAlreadyTaken) {
			RequestIsConfiguratoreNameAlreadyTaken castRequest = (RequestIsConfiguratoreNameAlreadyTaken) request;
			try {
				boolean result = model.isConfiguratoreNameAlreadyTaken(castRequest.getName());
				return new ResponseIsConfiguratoreNameAlreadyTaken(result, EsitoRequest.SUCCESS);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseIsConfiguratoreNameAlreadyTaken(false, EsitoRequest.ERROR);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
