package requestPackage;

import strutture.Gerarchia;

public class RequestRetrieveRadiceOfGerarchia implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gerarchia gerarchia;
	
	public RequestRetrieveRadiceOfGerarchia(Gerarchia gerarchia) {
		super();
		this.gerarchia = gerarchia;
	}

	/**
	 * @return the gerarchia
	 */
	public Gerarchia getGerarchia() {
		return gerarchia;
	}
}
