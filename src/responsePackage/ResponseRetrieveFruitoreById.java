package responsePackage;

import utente.Fruitore;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrieveFruitoreById
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrieveFruitoreById implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private Fruitore result;
	
	public ResponseRetrieveFruitoreById(EsitoRequest esito, Fruitore result) {
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
	public Fruitore getResult() {
		return result;
	}
}
