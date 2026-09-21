package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveConfiguratoreById;
import responsePackage.Response;
import responsePackage.ResponseRetrieveConfiguratoreById;
import utente.Configuratore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di uno specifico {@link Configuratore} dato il suo Id
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveConfiguratoreById implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveFruitoreById();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveConfiguratoreById) {
			RequestRetrieveConfiguratoreById castRequest = (RequestRetrieveConfiguratoreById) request;
			try {
				Configuratore result = model.retrieveConfiguratoreById(castRequest.getId());
				return new ResponseRetrieveConfiguratoreById(EsitoRequest.SUCCESS, result);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseRetrieveConfiguratoreById(EsitoRequest.ERROR, null);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
