package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestRetrieveGerarchiasForProposta;
import responsePackage.Response;
import responsePackage.ResponseRetrieveGerarchiasForProposta;
import strutture.Gerarchia;
import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento di una lista di {@link Proposta} data una {@link Gerarchia}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GenerateRetrieveGerarchiasForProposta implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateRetrieveConfiguratoreIdByComprensorioId();
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestRetrieveGerarchiasForProposta) {
			RequestRetrieveGerarchiasForProposta castRequest = (RequestRetrieveGerarchiasForProposta) request;
			try {
				List<Proposta> risultato = model.retrieveGerarchiasForProposta(castRequest.getLista());
				return new ResponseRetrieveGerarchiasForProposta(EsitoRequest.SUCCESS, risultato);
			} catch (ErroreDatabaseNotWorking | ErroreServerUnreachable | ErroreRispostaNonConforme
					| ErroreServerReply e) {
				return new ResponseRetrieveGerarchiasForProposta(EsitoRequest.ERROR, null);
			}
		}
		return nextHandler.generateResponse(model, request);
	}

}
