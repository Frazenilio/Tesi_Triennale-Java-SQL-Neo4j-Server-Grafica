package responsePackage;

import strutture.FattoreDiConversione;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrieveFattoreConversione
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrieveFattoreConversione implements Response {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private FattoreDiConversione risultato;

	public ResponseRetrieveFattoreConversione(EsitoRequest esito, FattoreDiConversione risultato) {
		super();
		this.esito = esito;
		this.risultato = risultato;
	}

	/**
	 * @return the risultato
	 */
	public FattoreDiConversione getRisultato() {
		return risultato;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

}
