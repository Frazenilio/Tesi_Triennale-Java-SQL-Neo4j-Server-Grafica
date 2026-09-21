package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestAddConfiguratore;
import responsePackage.Response;
import responsePackage.ResponseAddConfiguratore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'aggiunta di Configuratori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateAddConfiguratore implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateAddFruitore();

	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestAddConfiguratore) {
			RequestAddConfiguratore castRequest = (RequestAddConfiguratore) request;
			try {
				model.addConfiguratore(castRequest.getConfiguratoreToAdd(),
						castRequest.getNomeConfiguratore(), castRequest.getCognomeConfiguratore(),
						castRequest.getPassword());
			} catch (Exception e) {
				return new ResponseAddConfiguratore(EsitoRequest.ERROR);
			}
			return new ResponseAddConfiguratore(EsitoRequest.SUCCESS);
		}
		return nextHandler.generateResponse(model, request);
	}
	
	
}
