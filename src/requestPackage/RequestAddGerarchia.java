package requestPackage;

import strutture.Gerarchia;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * AddGerarchia
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestAddGerarchia implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gerarchia gerarchiaToAdd;
	
	public RequestAddGerarchia(Gerarchia gerarchiaToAdd) {
		super();
		this.gerarchiaToAdd = gerarchiaToAdd;
	}

	/**
	 * @return the gerarchiaToAdd
	 */
	public Gerarchia getGerarchiaToAdd() {
		return gerarchiaToAdd;
	}
}
