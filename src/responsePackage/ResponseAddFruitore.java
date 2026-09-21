package responsePackage;

import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * AddFruitore
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseAddFruitore implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	
	public ResponseAddFruitore(EsitoRequest esito) {
		this.esito = esito;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}
}
