package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestGetArrayInsiemiChiusi;
import responsePackage.Response;
import responsePackage.ResponseGetArrayInsiemiChiusi;
import strutture.InsiemeChiuso;
import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo dell'array degli Insiemi Chiusi
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateGetArrayInsiemiChiusi implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateGetArrayProposte();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestGetArrayInsiemiChiusi) {
			RequestGetArrayInsiemiChiusi castRequest = (RequestGetArrayInsiemiChiusi) request;
			List<InsiemeChiuso> risultato = null;
			try {
				risultato = model.getArrayInsiemiChiusi();
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseGetArrayInsiemiChiusi(EsitoRequest.ERROR, null);
			}
			if(risultato == null) {
				return new ResponseGetArrayInsiemiChiusi(EsitoRequest.ERROR, null);
			}
			return new ResponseGetArrayInsiemiChiusi(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
