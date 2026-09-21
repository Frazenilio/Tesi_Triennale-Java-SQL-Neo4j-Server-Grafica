package strutture;

import java.io.Serializable;
import java.util.UUID;

import utility.Tupla;

/**
 * Classe per contenere un Fattore di Conversione
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class FattoreDiConversione implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tupla<UUID, Double> fdc;

	/**
	 * Costruttore
	 * @param tupla tupla del fattore di conversione
	 * 
	 * @since TESI
	 */
	public FattoreDiConversione(Tupla<UUID, Double> tupla) {
		super();
		this.fdc = tupla;
	}

	/**
	 * @return the fdc
	 */
	public Tupla<UUID, Double> getFdc() {
		return fdc;
	}

	/**
	 * @param fdc the fdc to set
	 */
	public void setFdc(Tupla<UUID, Double> fdc) {
		this.fdc = fdc;
	}
}
