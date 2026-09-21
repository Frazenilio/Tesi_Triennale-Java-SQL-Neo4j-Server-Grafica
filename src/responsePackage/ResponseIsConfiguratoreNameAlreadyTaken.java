package responsePackage;

import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * isConfiguratoreNameAlreadyTaken
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseIsConfiguratoreNameAlreadyTaken implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean result;
	private EsitoRequest esito;

	public ResponseIsConfiguratoreNameAlreadyTaken(boolean result, EsitoRequest esito) {
		super();
		this.result = result;
		this.esito = esito;
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
