package responsePackage;

import strutture.InsiemeChiuso;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrieveCicloDaChiudereById
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrieveCicloDaChiudereById implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private InsiemeChiuso result;
	
	public ResponseRetrieveCicloDaChiudereById(EsitoRequest esito, InsiemeChiuso result) {
		super();
		this.esito = esito;
		this.result = result;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

	/**
	 * @return the result
	 */
	public InsiemeChiuso getResult() {
		return result;
	}
}
