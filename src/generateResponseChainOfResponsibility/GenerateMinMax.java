package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestCalcolaMinMax;
import responsePackage.Response;
import responsePackage.ResponseCalcolaMinMax;
import utility.EsitoRequest;
import utility.Tupla;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo la richiesta del minimo e massimo del range per il Fattore di Conversione
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateMinMax implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateGetArrayComprensori();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestCalcolaMinMax) {
			RequestCalcolaMinMax castRequest = (RequestCalcolaMinMax) request;
			Tupla<Double, Double> risultato;
			try {
				risultato = model.calcolaMinMax(castRequest.getFoglia1(), castRequest.getFoglia2());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseCalcolaMinMax(EsitoRequest.ERROR, null);
			}
			return new ResponseCalcolaMinMax(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
