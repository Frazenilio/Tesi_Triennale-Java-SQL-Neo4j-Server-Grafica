package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestCalcolaFattoriConversione;
import responsePackage.Response;
import responsePackage.ResponseCalcolaFattoriConversione;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo il calcolo di un Fattore di Conversione
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateCalcolaFattoriConversione implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateMinMax();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestCalcolaFattoriConversione) {
			RequestCalcolaFattoriConversione castRequest = (RequestCalcolaFattoriConversione) request;
			try {
				model.calcolaFattoriConversione(castRequest.getFogliaBersaglio(),
						castRequest.getFogliaNuova(), castRequest.getFdcBersaglio());
				return new ResponseCalcolaFattoriConversione(EsitoRequest.SUCCESS);
			} catch (Exception e) {
				return new ResponseCalcolaFattoriConversione(EsitoRequest.ERROR);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
