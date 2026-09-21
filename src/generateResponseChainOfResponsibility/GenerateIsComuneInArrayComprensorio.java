package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestIsComuneInArrayComprensori;
import responsePackage.Response;
import responsePackage.ResponseIsComuneInArrayComprensori;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento del risultato alla domanda se un comune e' gia' presente nel database dei Comprensori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateIsComuneInArrayComprensorio implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateIsNameInLastGerarchia();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestIsComuneInArrayComprensori) {
			RequestIsComuneInArrayComprensori castRequest = (RequestIsComuneInArrayComprensori) request;
			boolean risultato;
			try {
				risultato = model.isComuneInArrayComprensori(castRequest.getComune());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseIsComuneInArrayComprensori(EsitoRequest.ERROR, false);
			}
			return new ResponseIsComuneInArrayComprensori(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
