package responsePackage;

import utility.EsitoRequest;
import utility.Tupla;

/**
 * Classe per implementare risposte del server al client
 * calcolaMinMax
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseCalcolaMinMax implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private Tupla<Double, Double> risultato;
	
	public ResponseCalcolaMinMax(EsitoRequest esito, Tupla<Double, Double> risultato) {
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
	public Tupla<Double, Double> getRisultato() {
		return risultato;
	}
	
}
