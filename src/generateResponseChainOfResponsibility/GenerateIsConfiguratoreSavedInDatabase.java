package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestIsConfiguratoreSavedInDatabase;
import responsePackage.Response;
import responsePackage.ResponseIsConfiguratoreSavedInDatabase;
import utente.Configuratore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento del risultato alla domanda se un configuratore e' ga' presente nel Database
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateIsConfiguratoreSavedInDatabase implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateIsFruitoreEmailAlreadyTaken();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestIsConfiguratoreSavedInDatabase) {
			RequestIsConfiguratoreSavedInDatabase castRequest = (RequestIsConfiguratoreSavedInDatabase) request;
			try {
				Configuratore result = model.isConfiguratoreSavedInDatabase(castRequest.getName(), castRequest.getPassword());
				return new ResponseIsConfiguratoreSavedInDatabase(result, EsitoRequest.SUCCESS);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseIsConfiguratoreSavedInDatabase(null, EsitoRequest.ERROR);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
