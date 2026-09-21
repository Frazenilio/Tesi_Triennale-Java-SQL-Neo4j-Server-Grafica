package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrievePropostaById;
import responsePackage.Response;
import responsePackage.ResponseRetrievePropostaById;
import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di una specifica {@link Proposta} dato il suo id
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrievePropostaById implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateIsConfiguratoreNameAlreadyTaken();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrievePropostaById) {
			RequestRetrievePropostaById castRequest = (RequestRetrievePropostaById) request;
			try {
				Proposta result = model.retrievePropostabyId(castRequest.getId());
				return new ResponseRetrievePropostaById(EsitoRequest.SUCCESS, result);
			} catch (ErroreDatabaseNotWorking | ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply e) {
				return new ResponseRetrievePropostaById(EsitoRequest.ERROR, null);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
