package responsePackage;

import java.util.UUID;

import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * retrieveConfiguratoreIdByComprensorioId
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseRetrieveConfiguratoreIdByComprensorioId implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID result;
	private EsitoRequest esito;

	public ResponseRetrieveConfiguratoreIdByComprensorioId(EsitoRequest esito, UUID result) {
		super();
		this.result = result;
		this.esito = esito;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

	/**
	 * @return the result
	 */
	public UUID getResult() {
		return result;
	}

}
