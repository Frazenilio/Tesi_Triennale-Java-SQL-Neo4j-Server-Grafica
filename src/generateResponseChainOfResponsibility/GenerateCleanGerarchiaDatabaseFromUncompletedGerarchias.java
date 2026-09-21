package generateResponseChainOfResponsibility;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestCleanGerarchiaDatabaseFromUncompletedGerarchias;
import responsePackage.Response;
import responsePackage.ResponseCleanGerarchiaDatabaseFromUncompletedGerarchias;
import utility.EsitoRequest;

public class GenerateCleanGerarchiaDatabaseFromUncompletedGerarchias implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler;
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestCleanGerarchiaDatabaseFromUncompletedGerarchias) {
			try {
				model.cleanGerarchiaDatabaseFromUncompletedGerarchias();
				return new ResponseCleanGerarchiaDatabaseFromUncompletedGerarchias(EsitoRequest.SUCCESS);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseCleanGerarchiaDatabaseFromUncompletedGerarchias(EsitoRequest.ERROR);
			}
		}
		System.out.println("fail response");
		return new ResponseCleanGerarchiaDatabaseFromUncompletedGerarchias(EsitoRequest.ERROR);
	}

}
