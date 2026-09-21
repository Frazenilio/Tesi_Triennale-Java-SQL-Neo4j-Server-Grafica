package requestPackage;

import java.util.UUID;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * retrieveConfiguratoreIdByComprensorioId
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestRetrieveConfiguratoreIdByComprensorioId implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	
	public RequestRetrieveConfiguratoreIdByComprensorioId(UUID id) {
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
