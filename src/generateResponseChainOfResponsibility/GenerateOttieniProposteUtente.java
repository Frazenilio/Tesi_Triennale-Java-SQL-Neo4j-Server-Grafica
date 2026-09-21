package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestOttieniProposteUtente;
import responsePackage.Response;
import responsePackage.ResponseOttieniProposteUtente;
import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento delle Proposte di uno specifico Utente
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateOttieniProposteUtente implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveGerarchiasForProposta();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestOttieniProposteUtente) {
			RequestOttieniProposteUtente castRequest = (RequestOttieniProposteUtente) request;
			List<Proposta> risultato;
			try {
				risultato = model.ottieniProposteUtente(castRequest.getUser());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseOttieniProposteUtente(EsitoRequest.ERROR, null);
			}
			return new ResponseOttieniProposteUtente(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
