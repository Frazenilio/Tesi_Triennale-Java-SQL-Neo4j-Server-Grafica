package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestIsNameInLastGerarchia;
import responsePackage.Response;
import responsePackage.ResponseIsNameInLastGerarchia;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento della risposta alla domanda se il nome inserito e' gia' presente nell'ultima Gerarchia
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateIsNameInLastGerarchia implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateModificaStatoProposta();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestIsNameInLastGerarchia) {
			RequestIsNameInLastGerarchia castRequest = (RequestIsNameInLastGerarchia) request;
			boolean risultato;
			try {
				risultato = model.isNameInLastGerarchia(castRequest.getGerToCheck());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseIsNameInLastGerarchia(EsitoRequest.ERROR, false);
			}
			return new ResponseIsNameInLastGerarchia(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
