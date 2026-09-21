package requestPackage;

import java.util.UUID;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * retrievePropostaById
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestRetrievePropostaById implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID id;
	
	public RequestRetrievePropostaById(UUID id) {
		super();
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}
}
