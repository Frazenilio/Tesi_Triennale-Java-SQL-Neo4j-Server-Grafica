package responsePackage;

import java.util.UUID;

import utility.EsitoRequest;

public class ResponseRetrieveRadiceOfGerarchia implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID risultato;
	private EsitoRequest esito;
	
	public ResponseRetrieveRadiceOfGerarchia(UUID risultato, EsitoRequest esito) {
		super();
		this.risultato = risultato;
		this.esito = esito;
	}

	/**
	 * @return the risultato
	 */
	public UUID getRisultato() {
		return risultato;
	}



	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

}
