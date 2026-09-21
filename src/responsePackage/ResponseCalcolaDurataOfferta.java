package responsePackage;

import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * calcolaDurataOfferta
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseCalcolaDurataOfferta implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private int risultato;
	
	public ResponseCalcolaDurataOfferta(EsitoRequest esito, int risultato) {
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
	public int getRisultato() {
		return risultato;
	}

}
