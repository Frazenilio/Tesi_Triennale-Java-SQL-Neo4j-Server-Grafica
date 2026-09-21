package generateResponseChainOfResponsibility;

import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveRadiceOfGerarchia;
import responsePackage.Response;
import responsePackage.ResponseRetrieveRadiceOfGerarchia;
import utility.EsitoRequest;

public class GenerateRetrieveRadiceOfGerarchia implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateCleanGerarchiaDatabaseFromUncompletedGerarchias();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveRadiceOfGerarchia) {
			RequestRetrieveRadiceOfGerarchia castRequest = (RequestRetrieveRadiceOfGerarchia) request;
			try {
				UUID risultato = model.retrieveRadiceOfGerarchia(castRequest.getGerarchia());
				return new ResponseRetrieveRadiceOfGerarchia(risultato, EsitoRequest.SUCCESS);
			} catch (ErroreDatabaseNotWorking | ErroreServerUnreachable | ErroreRispostaNonConforme
					| ErroreServerReply e) {
				return new ResponseRetrieveRadiceOfGerarchia(null, EsitoRequest.ERROR);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
