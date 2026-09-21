package responsePackage;

import java.util.List;

import strutture.InsiemeChiuso;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * getArrayInsiemiChiusi
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseGetArrayInsiemiChiusi implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private List<InsiemeChiuso> risultato;
	
	public ResponseGetArrayInsiemiChiusi(EsitoRequest esito, List<InsiemeChiuso> risultato) {
		super();
		this.esito = esito;
		this.risultato = risultato;
	}

	/**
	 * @return the esito
	 */
	public EsitoRequest getEsito() {
		return esito;
	}

	/**
	 * @return the risultato
	 */
	public List<InsiemeChiuso> getRisultato() {
		return risultato;
	}
	
}
