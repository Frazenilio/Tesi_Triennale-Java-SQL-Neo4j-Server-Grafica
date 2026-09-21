package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveCicloDaChiudereById;
import responsePackage.Response;
import responsePackage.ResponseRetrieveCicloDaChiudereById;
import strutture.InsiemeChiuso;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di uno specifico {@link InsiemeChiuso} dato il suo id
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveCicloDaChiudereById implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveRadiceOfGerarchia();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveCicloDaChiudereById) {
			RequestRetrieveCicloDaChiudereById castRequest = (RequestRetrieveCicloDaChiudereById) request;
			try {
				InsiemeChiuso risultato = model.retrieveCicloDaChiudereById(castRequest.getId());
				return new ResponseRetrieveCicloDaChiudereById(EsitoRequest.SUCCESS, risultato);
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
					| ErroreDatabaseNotWorking e) {
				return new ResponseRetrieveCicloDaChiudereById(EsitoRequest.ERROR, null);
			}
			
		}
		return nextHandler.generateResponse(model, request);
	}

}
