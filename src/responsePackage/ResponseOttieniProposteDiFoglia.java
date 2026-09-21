package responsePackage;

import java.util.List;

import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * ottieniProposteDiFoglia
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseOttieniProposteDiFoglia implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private List<Proposta> risultato;

	public ResponseOttieniProposteDiFoglia(EsitoRequest esito, List<Proposta> risultato) {
		super();
		this.esito = esito;
		this.risultato = risultato;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

	/**
	 * @return the risultato
	 */
	public List<Proposta> getRisultato() {
		return risultato;
	}

}
