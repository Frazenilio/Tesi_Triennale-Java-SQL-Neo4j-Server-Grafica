package requestPackage;

import strutture.Gerarchia;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * retrieveAllFdcsFromFoglia
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestRetrieveAllFdcsFromFoglia implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gerarchia foglia;
	
	public RequestRetrieveAllFdcsFromFoglia(Gerarchia foglia) {
		super();
		this.foglia = foglia;
	}

	/**
	 * @return the foglia
	 */
	public Gerarchia getFoglia() {
		return foglia;
	}
}
