package responsePackage;

import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * isConfiguratoreEmailAlreadyTaken
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseIsFruitoreEmailAlreadyTaken implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private boolean result;
	
	public ResponseIsFruitoreEmailAlreadyTaken(EsitoRequest esito, boolean result) {
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
	public boolean isResult() {
		return result;
	}
}
