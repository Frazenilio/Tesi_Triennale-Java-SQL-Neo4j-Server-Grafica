package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestAddGerarchia;
import responsePackage.Response;
import responsePackage.ResponseAddGerarchia;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'aggiunta di Gerarchie
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateAddGerarchia implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateAddProposta();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestAddGerarchia) {
			RequestAddGerarchia castRequest = (RequestAddGerarchia) request;
			try {
				model.addGerarchia(castRequest.getGerarchiaToAdd());
			} catch (Exception e) {
				return new ResponseAddGerarchia(EsitoRequest.ERROR);
			} 
			return new ResponseAddGerarchia(EsitoRequest.SUCCESS);
		}
		return nextHandler.generateResponse(model, request);
	}

}
