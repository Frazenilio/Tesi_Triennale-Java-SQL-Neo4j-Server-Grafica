package utente;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Matteo Ghidini 736213
 * Classe Utente 
 * @since 1
 */
public abstract class Utente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private UUID id;
	
	/**
	 * Costruttore Utente
	 * @param username
	 * @param password
	 * @since 1
	 */
	public Utente(String username) {
		super();
		this.username = username;
	}
	
	public Utente(String username, String password, UUID uuid) {
		super();
		this.username = username;
		this.id = uuid;
	}
	
	protected Utente() {};

//get e set
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
//get e set end
	@Override
	public String toString() {
		return String.format("username = %s", this.getUsername());
	}
	
	/**
	 * Metodo per controllare 2 utenti siano uguali
	 * @Precondizione this ed altroUtente non devono essere nulli
	 * @param altroUtente l'altro utente da controllare
	 * @return true se hanno lo stesso username essendo univoco. false altrimenti
	 * @since 4
	 */
	public boolean equals(Utente altroUtente) {
		if(this.getUsername().equals(altroUtente.getUsername())) {
			return true;
		}
		return false;
	}
	
}
