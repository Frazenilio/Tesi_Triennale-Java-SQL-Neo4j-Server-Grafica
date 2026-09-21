package generateResponseChainOfResponsibility;

import java.io.IOException;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestModificaStatoProposta;
import responsePackage.Response;
import responsePackage.ResponseModificaStatoProposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo la modifica di uno stato di una Proposta
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateModificaStatoProposta implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateOttieniCicliChiusi();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestModificaStatoProposta) {
			RequestModificaStatoProposta castRequest = (RequestModificaStatoProposta) request;
			try {
				model.modificaStatoProposta(castRequest.getProposta(), castRequest.getNuovoStato());
			} catch (IOException | ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseModificaStatoProposta(EsitoRequest.ERROR);
			}
			return new ResponseModificaStatoProposta(EsitoRequest.SUCCESS);
		}
		return nextHandler.generateResponse(model, request);
	}
}
