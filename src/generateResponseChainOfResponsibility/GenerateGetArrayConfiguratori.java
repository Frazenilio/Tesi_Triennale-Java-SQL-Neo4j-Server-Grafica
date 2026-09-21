package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestGetArrayConfiguratori;
import responsePackage.Response;
import responsePackage.ResponseGetArrayConfiguratori;
import utente.Configuratore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento dell'array dei Configuratori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateGetArrayConfiguratori implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateGetArrayFruitori();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestGetArrayConfiguratori) {
			RequestGetArrayConfiguratori castRequest = (RequestGetArrayConfiguratori) request;
			List<Configuratore> risultato = null;
			try {
				risultato = model.getArrayConfiguratori();
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseGetArrayConfiguratori(EsitoRequest.ERROR, null);
			}
			if(risultato == null) {
				return new ResponseGetArrayConfiguratori(EsitoRequest.ERROR, null);
			}
			return new ResponseGetArrayConfiguratori(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
