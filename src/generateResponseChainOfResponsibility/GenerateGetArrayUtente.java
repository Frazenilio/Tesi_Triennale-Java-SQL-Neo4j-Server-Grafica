package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestGetArrayUtente;
import responsePackage.Response;
import responsePackage.ResponseGetArrayUtente;
import utente.Utente;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento dell'array degli Utenti
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateGetArrayUtente implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateIsComuneInArrayComprensorio();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestGetArrayUtente) {
			RequestGetArrayUtente castRequest = (RequestGetArrayUtente) request;
			List<Utente> risultato = null;
			try {
				risultato = model.getArrayUtente();
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseGetArrayUtente(EsitoRequest.ERROR, null);
			}
			if(risultato == null) {
				return new ResponseGetArrayUtente(EsitoRequest.ERROR, null);
			}
			return new ResponseGetArrayUtente(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
