package requestPackage;

import java.util.UUID;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * OttieniProposteUtente
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestOttieniProposteUtente implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	
	public RequestOttieniProposteUtente(UUID id) {
		super();
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public UUID getUser() {
		return id;
	}

}
