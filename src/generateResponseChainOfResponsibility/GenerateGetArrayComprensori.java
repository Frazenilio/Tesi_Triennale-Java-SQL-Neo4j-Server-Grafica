package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestGetArrayComprensori;
import responsePackage.Response;
import responsePackage.ResponseGetArrayComprensori;
import strutture.Comprensorio;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento dell'array di comprensori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateGetArrayComprensori implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateGetArrayConfiguratori();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestGetArrayComprensori) {
			RequestGetArrayComprensori castRequest = (RequestGetArrayComprensori) request;
			List<Comprensorio> risultato = null;
			try {
				risultato = model.getArrayComprensori();
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseGetArrayComprensori(EsitoRequest.ERROR, null);
			}
			return new ResponseGetArrayComprensori(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
