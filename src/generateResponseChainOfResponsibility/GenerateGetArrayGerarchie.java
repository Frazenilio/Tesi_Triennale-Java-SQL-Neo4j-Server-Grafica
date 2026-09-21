package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestGetArrayGerarchie;
import responsePackage.Response;
import responsePackage.ResponseGetArrayGerarchie;
import strutture.Gerarchia;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento dell'array delle Gerarchie
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateGetArrayGerarchie implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateGetArrayInsiemiChiusi();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestGetArrayGerarchie) {
			RequestGetArrayGerarchie castRequest = (RequestGetArrayGerarchie) request;
			try {
				List<Gerarchia> risultato = model.getArrayGerarchie();
				if(risultato == null) {
					return new ResponseGetArrayGerarchie(EsitoRequest.ERROR, null);
				}
				return new ResponseGetArrayGerarchie(EsitoRequest.SUCCESS, risultato);
				
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseGetArrayGerarchie(EsitoRequest.ERROR, null);
			}
			
		}
		return nextHandler.generateResponse(model, request);
	}
}
