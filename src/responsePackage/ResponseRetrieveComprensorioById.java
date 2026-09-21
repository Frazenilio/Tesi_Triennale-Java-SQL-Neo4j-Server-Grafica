package responsePackage;

import strutture.Comprensorio;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrieveComprensorioById
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrieveComprensorioById implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private Comprensorio result;	
	
	public ResponseRetrieveComprensorioById(EsitoRequest esito, Comprensorio result) {
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
	public Comprensorio getResult() {
		return result;
	}
}
