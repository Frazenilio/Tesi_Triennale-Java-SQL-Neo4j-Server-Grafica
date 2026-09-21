package responsePackage;

import utente.Configuratore;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrieveConfiguratoreById
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrieveConfiguratoreById implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private Configuratore result;
	
	public ResponseRetrieveConfiguratoreById(EsitoRequest esito, Configuratore result) {
		super();
		this.esito = esito;
		this.result = result;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

	/**
	 * @return the result
	 */
	public Configuratore getResult() {
		return result;
	}
}
