package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveGerarchiaById;
import responsePackage.Response;
import responsePackage.ResponseRetrieveGerarchiaById;
import strutture.Gerarchia;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di una specifica {@link Gerarchia} dato il suo id
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveGerarchiaById implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrievePropostaById();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveGerarchiaById) {
			RequestRetrieveGerarchiaById castRequest = (RequestRetrieveGerarchiaById) request;
			try {
				Gerarchia result = model.retrieveGerarchiaById(castRequest.getId());
				return new ResponseRetrieveGerarchiaById(EsitoRequest.SUCCESS, result);
			} catch (ErroreDatabaseNotWorking | ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply e) {
				return new ResponseRetrieveGerarchiaById(EsitoRequest.ERROR, null);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
