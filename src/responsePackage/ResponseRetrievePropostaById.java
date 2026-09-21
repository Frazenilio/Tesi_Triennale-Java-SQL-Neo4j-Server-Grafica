package responsePackage;

import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrievePropostaById
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrievePropostaById implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private Proposta result;
	
	public ResponseRetrievePropostaById(EsitoRequest esito, Proposta result) {
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
	public Proposta getResult() {
		return result;
	}
}
