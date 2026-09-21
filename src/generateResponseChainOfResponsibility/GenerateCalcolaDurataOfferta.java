package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import errori.ErroreDatabaseNotWorking;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import requestPackage.RequestCalcolaDurataOfferta;
import responsePackage.Response;
import responsePackage.ResponseAddComprensorio;
import responsePackage.ResponseCalcolaDurataOfferta;
import utility.EsitoRequest;

/**
 * Classe Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * riguardo il calcolo della durata dell'offerta di una Proposta
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GenerateCalcolaDurataOfferta implements ResponseGeneratorInterface {

	ResponseGeneratorInterface nextHandler = new GenerateCalcolaFattoriConversione();
	
	@Override
	public Response generateResponse(ModelService model, Request request) {
		if(request instanceof RequestCalcolaDurataOfferta) {
			RequestCalcolaDurataOfferta castRequest = (RequestCalcolaDurataOfferta) request;
			int risultato;
			try {
				risultato = model.calcolaDurataOfferta(castRequest.getGerRichiesta(),
						castRequest.getGerOfferta(), castRequest.getDurataRichiesta());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				return new ResponseAddComprensorio(EsitoRequest.ERROR);
			} 
			return new ResponseCalcolaDurataOfferta(EsitoRequest.SUCCESS, risultato);
		}
		return nextHandler.generateResponse(model, request);
	}
}
