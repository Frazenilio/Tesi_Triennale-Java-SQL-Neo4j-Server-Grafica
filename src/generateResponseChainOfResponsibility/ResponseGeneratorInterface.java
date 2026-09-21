package generateResponseChainOfResponsibility;

import clientServerApplication.MyServer;
import logica.ModelService;
import requestPackage.Request;
import responsePackage.Response;

/**
 * Interface Chain of Responsibility per la gestione delle richieste al {@link MyServer}
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public interface ResponseGeneratorInterface {

	/**
	 * Metodo per la generazione della risposta da mandare al client
	 * @param model Model da cui attingere per ottenere le informazioni ed eseguire operazioni
	 * @param request richiesta ricevuta dal client da gestire
	 * @return la risposta del server da mandare al client
	 * @since TESI CLIENT-SERVER
	 */
	Response generateResponse(ModelService model, Request request);
}
