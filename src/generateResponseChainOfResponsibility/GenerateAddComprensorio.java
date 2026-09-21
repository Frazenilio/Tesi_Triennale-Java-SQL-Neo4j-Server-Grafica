package generateResponseChainOfResponsibility;

import java.io.IOException;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestAddComprensorio;
import responsePackage.Response;
import responsePackage.ResponseAddComprensorio;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'aggiunta di comprensori. Attualmente e' la prima classe della Chain of Responsibility
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateAddComprensorio implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateAddConfiguratore();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestAddComprensorio) {
			RequestAddComprensorio castRequest = (RequestAddComprensorio) request;
			try {
				model.addComprensorio(castRequest.getComprensorioToAdd());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme
					| ErroreServerReply | ErroreDatabaseNotWorking | IOException e) {
				return new ResponseAddComprensorio(EsitoRequest.ERROR);
			} 
			return new ResponseAddComprensorio(EsitoRequest.SUCCESS);
		}
		return nextHandler.generateResponse(model, request);
	}

}
