package requestPackage;

import java.util.UUID;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * retrieveCicloDaChiudereById
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestRetrieveCicloDaChiudereById implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	
	public RequestRetrieveCicloDaChiudereById(UUID id) {
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
