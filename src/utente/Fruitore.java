package utente;

import java.io.Serializable;
import java.util.UUID;

import strutture.Comprensorio;

/**
 * Classe per il fruitore
 * @author Francesco Lozio 737664
 * @since 2
 */
public class Fruitore extends Utente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * email del fruitore
	 * @since 2
	 */
	String email;
	
	/**
	 * Comprensorio di appartenenza del fruitore
	 * @since 2
	 * @since TESI_DATABASE e' un UUID, non l'oggetto {@link Comprensorio}
	 */	
	private UUID comprensorioId;

	/**
	 * Costruttore senza email e comprensorio
	 * @param username Username del fruitore
	 * @param password password del fruitore
	 * @since 2
	 */
	public Fruitore(String username) {
		super(username);
	}
	
	public Fruitore(String username, String email, UUID compId) {
		super(username);
		this.email = email;
		this.comprensorioId = compId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the comprensorioId
	 */
	public UUID getComprensorioId() {
		return comprensorioId;
	}

	/**
	 * @param comprensorioId the comprensorioId to set
	 */
	public void setComprensorioId(UUID comprensorioId) {
		this.comprensorioId = comprensorioId;
	}
	
	public boolean equals(Fruitore altroFruitore) {
		return super.equals(altroFruitore);
	}
	
	public String toString() {
		return "Fruitore: " + super.toString();
	}
}
