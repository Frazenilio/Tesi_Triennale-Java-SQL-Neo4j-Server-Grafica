package responsePackage;

import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * calcolaFattoriConversione
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseCalcolaFattoriConversione implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;

	public ResponseCalcolaFattoriConversione(EsitoRequest esito) {
		super();
		this.esito = esito;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}
	
}
