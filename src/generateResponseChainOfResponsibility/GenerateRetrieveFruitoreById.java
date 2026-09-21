package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveFruitoreById;
import responsePackage.Response;
import responsePackage.ResponseRetrieveFruitoreById;
import utente.Fruitore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di uno specifico {@link Fruitore} dato il suo id
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveFruitoreById implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveGerarchiaById();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveFruitoreById) {
			RequestRetrieveFruitoreById castRequest = (RequestRetrieveFruitoreById) request;
			try {
				Fruitore result = model.retrieveFruitoreById(castRequest.getId());
				return new ResponseRetrieveFruitoreById(EsitoRequest.SUCCESS, result);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseRetrieveFruitoreById(EsitoRequest.ERROR, null);
			}
			
		}
		return nextHandler.generateResponse(model, request);
	}

}
