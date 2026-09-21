package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestGetArrayFruitori;
import responsePackage.Response;
import responsePackage.ResponseGetArrayFruitori;
import utente.Fruitore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento dell'array dei Fruitori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateGetArrayFruitori implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateGetArrayGerarchie();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestGetArrayFruitori) {
			RequestGetArrayFruitori castRequest = (RequestGetArrayFruitori) request;
			List<Fruitore> risultato = null;
			try {
				risultato = model.getArrayFruitori();
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseGetArrayFruitori(EsitoRequest.ERROR, null);
			}
			if(risultato == null) {
				return new ResponseGetArrayFruitori(EsitoRequest.ERROR, null);
			}
			return new ResponseGetArrayFruitori(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}

}
