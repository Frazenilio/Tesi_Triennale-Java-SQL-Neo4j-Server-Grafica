package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestGetArrayProposte;
import responsePackage.Response;
import responsePackage.ResponseGetArrayProposte;
import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento dell'array delle Proposte
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateGetArrayProposte implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateGetArrayUtente();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestGetArrayProposte) {
			RequestGetArrayProposte castRequest = (RequestGetArrayProposte) request;
			List<Proposta> risultato = null;
			try {
				risultato = model.getArrayProposte();
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseGetArrayProposte(EsitoRequest.ERROR, null);
			}
			if(risultato == null) {
				return new ResponseGetArrayProposte(EsitoRequest.ERROR, null);
			}
			return new ResponseGetArrayProposte(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
