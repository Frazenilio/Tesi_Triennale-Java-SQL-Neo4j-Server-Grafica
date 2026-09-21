package requestPackage;

import java.util.UUID;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * OttieniCicliChiusi
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestOttieniCicliChiusi implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;

	public RequestOttieniCicliChiusi(UUID id) {
		super();
		this.id = id;
	}

	/**
	 * @return the c
	 */
	public UUID getId() {
		return this.id;
	}

}
