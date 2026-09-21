package responsePackage;

import java.util.List;

import utente.Utente;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * getArrayUtente
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseGetArrayUtente implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Utente> arrayToReturn;
	private EsitoRequest esito;

	public ResponseGetArrayUtente(EsitoRequest esito, List<Utente> arrayToReturn) {
		super();
		this.arrayToReturn = arrayToReturn;
		this.esito = esito;
	}

	/**
	 * @return the arrayToReturn
	 */
	public List<Utente> getArrayToReturn() {
		return arrayToReturn;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}
}
