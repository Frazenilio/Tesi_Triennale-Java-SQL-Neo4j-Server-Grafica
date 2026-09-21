package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestAddProposta;
import responsePackage.Response;
import responsePackage.ResponseAddProposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'aggiunta di Proposte
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateAddProposta implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveComprensorioById();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestAddProposta) {
			RequestAddProposta castRequest = (RequestAddProposta) request;
			try {
				model.addProposta(castRequest.getPropToAdd(), castRequest.getId());
			} catch (Exception e) {
				return new ResponseAddProposta(EsitoRequest.ERROR);
			}
			return new ResponseAddProposta(EsitoRequest.SUCCESS);
		}
		return nextHandler.generateResponse(model, request);
	}

}
