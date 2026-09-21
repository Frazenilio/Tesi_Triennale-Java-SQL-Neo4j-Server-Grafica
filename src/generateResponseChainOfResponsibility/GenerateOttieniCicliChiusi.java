package generateResponseChainOfResponsibility;

import java.util.List;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestOttieniCicliChiusi;
import responsePackage.Response;
import responsePackage.ResponseOttieniCicliChiusi;
import strutture.InsiemeChiuso;
import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo l'ottenimento dei cicli chiusi di un determinato Configuratore
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateOttieniCicliChiusi implements ResponseGeneratorInterface{

	ResponseGeneratorInterface nextHandler = new GenerateProposteDiFoglia();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestOttieniCicliChiusi) {
			RequestOttieniCicliChiusi castRequest = (RequestOttieniCicliChiusi) request;
			List<InsiemeChiuso> risultato;
			try {
				risultato = model.ottieniCicliChiusi(castRequest.getId());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseOttieniCicliChiusi(EsitoRequest.ERROR, null);
			}
			return new ResponseOttieniCicliChiusi(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
