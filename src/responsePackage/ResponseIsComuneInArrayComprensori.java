package responsePackage;

import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * isComuneInArrayComprensori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseIsComuneInArrayComprensori implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private boolean risultato;

	public ResponseIsComuneInArrayComprensori(EsitoRequest esito, boolean risultato) {
		super();
		this.esito = esito;
		this.risultato = risultato;
	}

	/**
	 * @return the risultato
	 */
	public boolean isRisultato() {
		return risultato;
	}

	/**
	 * @return the esito
	 */
	public EsitoRequest getEsito() {
		return esito;
	}
	
}
