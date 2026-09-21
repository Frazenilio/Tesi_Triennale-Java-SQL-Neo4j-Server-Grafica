package strutture;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import utente.Configuratore;

/**
 * Classe Comprensorio: ArrayList di zone geografiche
 * @author Francesco Lozio 737664
 * @since 1
 */
public class Comprensorio implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Creatore del Comprensorio
	 * @since 1
	 * @since TESI_DATABASE e' un UUID, non piu' l'oggetto {@link Configuratore}
	 */
	private UUID proprietarioId;


	/**
	 * Array delle zone geografiche del comprensorio corrente
	 * @since 1
	 */
	private List<String> arrayComuni;
	
	private UUID id;
	
	/**
	 * Costruttore alternativo per sposare i valori del DB
	 * @param prop_id id del proprietario
	 * @param arrayComuni array dei comuni
	 */
	public Comprensorio(UUID prop_id, List<String> arrayComuni) {
		super();
		this.proprietarioId = prop_id;
		this.arrayComuni = arrayComuni;
	}

	public List<String> getArrayComuni() {
		return arrayComuni;
	}

	public void setArrayComuni(List<String> arrayComuni) {
		this.arrayComuni = arrayComuni;
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	/**
	 * @return the proprietario_id
	 */
	public UUID getProprietarioId() {
		return proprietarioId;
	}
	/**
	 * @param proprietario_id the proprietario_id to set
	 */
	public void setProprietarioId(UUID proprietario_id) {
		this.proprietarioId = proprietario_id;
	}

	
	/**
	 * Metodo equals che fa affidamento sul fatto che lo stesso comune non puo' essere in piu' Comprensori
	 * @param altroComprensorio comprensorio da confrontare
	 * @Precondizioni Un comune puo' essere in un comprensorio solo
	 * @return true se i comprensori sono uguali
	 */
	public boolean equals(Comprensorio altroComprensorio) {
		if(this.getId().equals(altroComprensorio.getId())) {
			return true;
		}
		for(String c1 : this.getArrayComuni()) {
			for(String c2 : altroComprensorio.getArrayComuni()) {
				if(c1.equals(c2)) {
					return true;
				}
			}
		}
		return false;
	}
}
