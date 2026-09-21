package responsePackage;

import utility.EsitoRequest;

public class ResponseCleanGerarchiaDatabaseFromUncompletedGerarchias implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EsitoRequest esito;

	public ResponseCleanGerarchiaDatabaseFromUncompletedGerarchias(EsitoRequest esito) {
		super();
		this.esito = esito;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

}
