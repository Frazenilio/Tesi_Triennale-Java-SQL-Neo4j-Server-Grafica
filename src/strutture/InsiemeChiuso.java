package strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe per la gestione di Insiemi Chiusi
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class InsiemeChiuso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Proposta> cycle;
	private UUID id;
	private List<UUID> cycleIds;
	private UUID proprietarioId;
	private UUID comprensorioId;
	private boolean areProposteToRetrieve;


	/**
	 * Costruttore 
	 * @param cycle lista di proposta che compone il ciclo chiuso
	 * @since TESI
	 */
	public InsiemeChiuso(List<Proposta> cycle) {
		super();
		this.cycle = cycle;
		this.cycleIds = new ArrayList<UUID>();
		for(Proposta p : cycle) {
			cycleIds.add(p.getId());
		}
		this.areProposteToRetrieve = false;
	}
	
	/**
	 * Costruttore per DB
	 * @param cycleIds
	 */
	public InsiemeChiuso(List<UUID> cycleIds, UUID idProp, UUID comprensorioId) {
		super();
		this.cycleIds = cycleIds;
		this.proprietarioId = idProp;
		this.comprensorioId = comprensorioId;
		this.areProposteToRetrieve = true;
		this.cycle = new ArrayList<Proposta>();
	}
	
	public List<Proposta> getCycle(){
		return this.cycle;
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	/**
	 * @return the cycleIds
	 */
	public List<UUID> getCycleIds() {
		return cycleIds;
	}

	/**
	 * @param cycleIds the cycleIds to set
	 */
	public void setCycleIds(List<UUID> cycleIds) {
		this.cycleIds = cycleIds;
	}

	/**
	 * @return the proprietarioId
	 */
	public UUID getProprietarioId() {
		return proprietarioId;
	}

	/**
	 * @param proprietarioId the proprietarioId to set
	 */
	public void setProprietarioId(UUID proprietarioId) {
		this.proprietarioId = proprietarioId;
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

	/**
	 * @return the areProposteToRetrieve
	 */
	public boolean isAreProposteToRetrieve() {
		return areProposteToRetrieve;
	}

	/**
	 * @param areProposteToRetrieve the areProposteToRetrieve to set
	 */
	public void setAreProposteToRetrieve(boolean areProposteToRetrieve) {
		this.areProposteToRetrieve = areProposteToRetrieve;
	}

	/**
	 * Metodo per convertire la propria lista di UUID in String
	 * @return la lista di UUID come String
	 * @since TESI DATABASE
	 */
	public List<String> getListIdsAsStrings(){
		List<String> ids = new ArrayList<String>();
		for(UUID id : this.getCycleIds()) {
			ids.add(id.toString());
		}
		return ids;
	}
}
