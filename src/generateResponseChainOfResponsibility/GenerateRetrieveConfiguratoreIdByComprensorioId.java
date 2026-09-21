package generateResponseChainOfResponsibility;

import java.util.UUID;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveConfiguratoreIdByComprensorioId;
import responsePackage.Response;
import responsePackage.ResponseRetrieveConfiguratoreIdByComprensorioId;
import strutture.Comprensorio;
import utente.Configuratore;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di uno specifico id di un {@link Configuratore} dato l'id di un {@link Comprensorio}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveConfiguratoreIdByComprensorioId implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveFattoreConversione();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveConfiguratoreIdByComprensorioId) {
			RequestRetrieveConfiguratoreIdByComprensorioId castRequest = (RequestRetrieveConfiguratoreIdByComprensorioId) request;
			try {
				UUID result = model.retrieveConfiguratoreIdByComprensorioId(castRequest.getId());
				return new ResponseRetrieveConfiguratoreIdByComprensorioId(EsitoRequest.SUCCESS, result);
			} catch (ErroreDatabaseNotWorking | ErroreRispostaNonConforme | ErroreServerUnreachable
					| ErroreServerReply e) {
				e.printStackTrace();
				return new ResponseRetrieveConfiguratoreIdByComprensorioId(EsitoRequest.ERROR, null);
			}
			
		}
		return nextHandler.generateResponse(model, request);
	}

}
