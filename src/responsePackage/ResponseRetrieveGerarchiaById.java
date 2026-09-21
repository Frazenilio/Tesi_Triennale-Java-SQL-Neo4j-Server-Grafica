package responsePackage;

import strutture.Gerarchia;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrieveGerarchiaById
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrieveGerarchiaById implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private Gerarchia result;
	
	public ResponseRetrieveGerarchiaById(EsitoRequest esito, Gerarchia result) {
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
	public Gerarchia getResult() {
		return result;
	}
}
