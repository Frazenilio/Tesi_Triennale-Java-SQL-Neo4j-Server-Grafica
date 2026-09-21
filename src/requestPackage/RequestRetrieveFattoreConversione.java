package requestPackage;

import strutture.Gerarchia;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * retrieveFattoreConversione
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestRetrieveFattoreConversione implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gerarchia gerStart;
	private Gerarchia gerEnd;
	
	public RequestRetrieveFattoreConversione(Gerarchia gerStart, Gerarchia gerEnd) {
		super();
		this.gerStart = gerStart;
		this.gerEnd = gerEnd;
	}

	/**
	 * @return the gerStart
	 */
	public Gerarchia getGerStart() {
		return gerStart;
	}

	/**
	 * @return the gerEnd
	 */
	public Gerarchia getGerEnd() {
		return gerEnd;
	}
}
