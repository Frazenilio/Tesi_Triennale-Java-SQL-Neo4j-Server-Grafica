package requestPackage;

import strutture.Gerarchia;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * CalcolaDurataOfferta
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestCalcolaDurataOfferta implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gerarchia gerOfferta;
	private Gerarchia gerRichiesta;
	private int durataRichiesta;
	
	public RequestCalcolaDurataOfferta(Gerarchia gerOfferta, Gerarchia gerRichiesta, int durataRichiesta) {
		super();
		this.gerOfferta = gerOfferta;
		this.gerRichiesta = gerRichiesta;
		this.durataRichiesta = durataRichiesta;
	}

	/**
	 * @return the gerOfferta
	 */
	public Gerarchia getGerOfferta() {
		return gerOfferta;
	}

	/**
	 * @return the gerRichiesta
	 */
	public Gerarchia getGerRichiesta() {
		return gerRichiesta;
	}

	/**
	 * @return the durataRichiesta
	 */
	public int getDurataRichiesta() {
		return durataRichiesta;
	}

}
