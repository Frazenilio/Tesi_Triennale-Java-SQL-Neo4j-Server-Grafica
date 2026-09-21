package utente;

import java.io.Serializable;

/**
 * @author Matteo Ghidini 736213
 * Classe che estende Utente
 * @since 1
 */
public class Configuratore extends Utente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore Di Configuratore
	 * @param username
	 * @param password
	 * @since 1
	 */
	public Configuratore(String username) {
		super(username);
	}
	
	public boolean equals(Configuratore altroConfiguratore) {
		return super.equals(altroConfiguratore);
	}
	
	public String toString() {
		return "Configuratore: " + super.toString();
	}
}
