package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveComprensorioById;
import responsePackage.Response;
import responsePackage.ResponseRetrieveComprensorioById;
import strutture.Comprensorio;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di uno specifico {@link Comprensorio} dato il suo id
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveComprensorioById implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveConfiguratoreById();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveComprensorioById) {
			RequestRetrieveComprensorioById castRequest = (RequestRetrieveComprensorioById) request;
			try {
				Comprensorio result = model.retrieveComprensorioById(castRequest.getId());
				return new ResponseRetrieveComprensorioById(EsitoRequest.SUCCESS, result);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseRetrieveComprensorioById(EsitoRequest.ERROR, null);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
