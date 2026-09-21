package requestPackage;

import strutture.Proposta;
import utility.Stato;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * modificaStatoProposta
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestModificaStatoProposta implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Proposta proposta;
	private Stato nuovoStato;
	
	public RequestModificaStatoProposta(Proposta proposta, Stato nuovoStato) {
		super();
		this.proposta = proposta;
		this.nuovoStato = nuovoStato;
	}

	/**
	 * @return the proposta
	 */
	public Proposta getProposta() {
		return proposta;
	}

	/**
	 * @return the nuovoStato
	 */
	public Stato getNuovoStato() {
		return nuovoStato;
	}
	
}
