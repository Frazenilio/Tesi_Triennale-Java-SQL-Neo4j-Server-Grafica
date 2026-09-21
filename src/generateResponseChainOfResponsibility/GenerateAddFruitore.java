package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestAddFruitore;
import responsePackage.Response;
import responsePackage.ResponseAddFruitore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'aggiunta di fruitori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateAddFruitore implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateAddGerarchia();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestAddFruitore) {
			RequestAddFruitore castRequest = (RequestAddFruitore) request;
			try {
				model.addFruitore(castRequest.getFruitoreToAdd(),
						castRequest.getNomeFruitore(), castRequest.getCognomeFruitore(),
						castRequest.getPassword());
			} catch (Exception e) {
				return new ResponseAddFruitore(EsitoRequest.ERROR);
			} 
			return new ResponseAddFruitore(EsitoRequest.SUCCESS);
		}
		return nextHandler.generateResponse(model, request);
	}

}
