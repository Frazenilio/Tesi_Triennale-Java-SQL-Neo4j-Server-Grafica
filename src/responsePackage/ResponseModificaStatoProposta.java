package responsePackage;

import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * modificaStatoProposta
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseModificaStatoProposta implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;

	public ResponseModificaStatoProposta(EsitoRequest esito) {
		super();
		this.esito = esito;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

}
