package strutture;

import java.io.Serializable;
import java.util.UUID;

import utility.Stato;

/**
 * Classe Proposta
 * @author Francesco Lozio 737664
 * @since 3
 */
public class Proposta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID proprietarioId;

	private Stato stato;
	
	private Gerarchia richiesta;
	
	private UUID richiestaId;

	private Gerarchia offerta;
	
	private UUID offertaId;
	
	private int durataRichiesta;
	
	private int durataOfferta;

	private UUID id;
	
	private UUID comprensorioId;
	
	private boolean toRetrieveGerarchias;
	
	/**
	 * Costruttore Proposta per il DB
	 * @param prop_id
	 * @param richiestaId
	 * @param offertaId
	 * @param durataRichiesta
	 * @param durataOfferta
	 * @param stato
	 */
	public Proposta(UUID prop_id,  UUID richiestaId, UUID offertaId, int durataRichiesta,
			int durataOfferta, Stato stato, UUID compId) {
		this.proprietarioId = prop_id;
		this.richiestaId = richiestaId;
		this.offertaId = offertaId;
		this.durataRichiesta = durataRichiesta;
		this.durataOfferta = durataOfferta;
		this.stato = stato;
		this.comprensorioId = compId;
		this.toRetrieveGerarchias = true;
	}
	
	public Proposta(UUID prop_id,  Gerarchia richiesta, Gerarchia offerta, int durataRichiesta,
			int durataOfferta, Stato stato, UUID compId) {
		this.proprietarioId = prop_id;
		this.richiesta = richiesta;
		this.offerta = offerta;
		this.durataRichiesta = durataRichiesta;
		this.durataOfferta = durataOfferta;
		this.stato = stato;
		this.comprensorioId = compId;
		this.richiestaId = richiesta.getId();
		this.offertaId = offerta.getId();
		this.toRetrieveGerarchias = false;
	}

	/**
	 * Costruttore per json
	 * @since 3
	 */
	public Proposta() {
		super();
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public Gerarchia getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(Gerarchia richiesta) {
		this.richiesta = richiesta;
	}

	public Gerarchia getOfferta() {
		return offerta;
	}

	public void setOfferta(Gerarchia offerta) {
		this.offerta = offerta;
	}

	public int getDurataRichiesta() {
		return durataRichiesta;
	}

	public void setDurataRichiesta(int durataRichiesta) {
		this.durataRichiesta = durataRichiesta;
	}

	public int getDurataOfferta() {
		return durataOfferta;
	}

	public void setDurataOfferta(int durataOfferta) {
		this.durataOfferta = durataOfferta;
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
	 * @return the richiestaId
	 */
	public UUID getRichiestaId() {
		return richiestaId;
	}

	/**
	 * @param richiestaId the richiestaId to set
	 */
	public void setRichiestaId(UUID richiestaId) {
		this.richiestaId = richiestaId;
	}

	/**
	 * @return the offertaId
	 */
	public UUID getOffertaId() {
		return offertaId;
	}

	/**
	 * @param offertaId the offertaId to set
	 */
	public void setOffertaId(UUID offertaId) {
		this.offertaId = offertaId;
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
	 * @return the toRetrieveGerarchias
	 */
	public boolean isToRetrieveGerarchias() {
		return toRetrieveGerarchias;
	}

	/**
	 * @param toRetrieveGerarchias the toRetrieveGerarchias to set
	 */
	public void setToRetrieveGerarchias(boolean toRetrieveGerarchias) {
		this.toRetrieveGerarchias = toRetrieveGerarchias;
	}

	public boolean equals(Proposta altraProposta) {
		if(this.getId().equals(altraProposta.getId())) {
			return true;
		}
		if(this.getRichiesta().equals(altraProposta.getRichiesta())
				&& this.getOfferta().equals(altraProposta.getOfferta())
				&& this.getDurataOfferta() == altraProposta.getDurataOfferta()
				&& this.getDurataRichiesta() == altraProposta.getDurataRichiesta()
				) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		String catOff = this.getOfferta().getCategoria();
		int durOff = this.getDurataOfferta();
		
		String catRic = this.getRichiesta().getCategoria();
		int durRic = this.getDurataRichiesta();
		
		builder.append("Offerta: " + catOff + " (" + durOff + ")");
		builder.append(" - ");
		builder.append("Richiesta: " + catRic + " (" + durRic + ")");
		
		return builder.toString();
	}
}
