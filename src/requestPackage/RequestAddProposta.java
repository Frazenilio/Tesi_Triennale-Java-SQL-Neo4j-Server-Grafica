package requestPackage;

import java.util.UUID;

import strutture.Proposta;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * AddProposta
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestAddProposta implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Proposta propToAdd;
	private UUID id;
	
	public RequestAddProposta(Proposta propToAdd, UUID idProp) {
		super();
		this.propToAdd = propToAdd;
		this.id = idProp;
	}

	/**
	 * @return the propToAdd
	 */
	public Proposta getPropToAdd() {
		return propToAdd;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}
}
