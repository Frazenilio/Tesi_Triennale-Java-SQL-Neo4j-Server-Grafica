package requestPackage;

import java.util.List;

import strutture.FattoreDiConversione;
import strutture.Gerarchia;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * CalcolaFattoriConversione
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestCalcolaFattoriConversione implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gerarchia fogliaBersaglio;
	private Gerarchia fogliaNuova;
	private Double fdcBersaglio;
	
	public RequestCalcolaFattoriConversione(Gerarchia fogliaBersaglio, Gerarchia fogliaNuova,
			Double fdcBersaglio) {
		super();
		this.fogliaBersaglio = fogliaBersaglio;
		this.fogliaNuova = fogliaNuova;
		this.fdcBersaglio = fdcBersaglio;
	}

	/**
	 * @return the gerOfferta
	 */
	public Gerarchia getFogliaBersaglio() {
		return fogliaBersaglio;
	}

	/**
	 * @return the gerRichiesta
	 */
	public Gerarchia getFogliaNuova() {
		return fogliaNuova;
	}

	/**
	 * @return the fdcBersaglio
	 */
	public Double getFdcBersaglio() {
		return fdcBersaglio;
	}
}
