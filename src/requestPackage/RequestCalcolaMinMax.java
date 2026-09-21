package requestPackage;

import strutture.Gerarchia;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * CalcolaMinMax
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestCalcolaMinMax implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gerarchia foglia1;
	private Gerarchia foglia2;
	
	public RequestCalcolaMinMax(Gerarchia foglia1, Gerarchia foglia2) {
		super();
		this.foglia1 = foglia1;
		this.foglia2 = foglia2;
	}

	/**
	 * @return the foglia1
	 */
	public Gerarchia getFoglia1() {
		return foglia1;
	}

	/**
	 * @return the foglia2
	 */
	public Gerarchia getFoglia2() {
		return foglia2;
	}
	
}
