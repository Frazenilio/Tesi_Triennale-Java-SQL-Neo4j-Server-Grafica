package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestOttieniProposteDiFoglia;
import responsePackage.Response;
import responsePackage.ResponseOttieniProposteDiFoglia;
import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento delle Proposte che coinvolgono una determinata Gerarchia Foglia
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateProposteDiFoglia implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateOttieniProposteUtente();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestOttieniProposteDiFoglia) {
			RequestOttieniProposteDiFoglia castRequest = (RequestOttieniProposteDiFoglia) request;
			List<Proposta> risultato;
			try {
				risultato = model.ottieniProposteDiFoglia(castRequest.getFoglia());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseOttieniProposteDiFoglia(EsitoRequest.ERROR, null);
			}
			return new ResponseOttieniProposteDiFoglia(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
