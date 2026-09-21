package responsePackage;

import java.util.List;

import strutture.FattoreDiConversione;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrieveAllFdcsFromFoglia
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrieveAllFdcsFromFoglia implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private List<FattoreDiConversione> risultato;

	public ResponseRetrieveAllFdcsFromFoglia(EsitoRequest esito, List<FattoreDiConversione> risultato) {
		super();
		this.esito = esito;
		this.risultato = risultato;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

	/**
	 * @return the risultato
	 */
	public List<FattoreDiConversione> getRisultato() {
		return risultato;
	}
}
