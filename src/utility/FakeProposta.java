package utility;

import java.util.UUID;

import strutture.InsiemeChiuso;


/**
 * Classe per salvare le proposte in json<br>
 * Jackson puo' trasformare un oggetto in json e viceversa facilmente. Invece di parsare posso usare un Oggetto apposta
 * @author Matteo Ghidini 736213
 * @since TESI_DATABSE nuovo utilizzo: grafica per {@link InsiemeChiuso}
 */
public class FakeProposta {
	
	/**
	 * La radice serve per identificare univocamente una Gerarchia
	 */
	private String radiceRichiesta;
	
	/**
	 * Nome (==Categoria) della Gerarchia richiesta
	 */
	private String richiesta;
	
	/**
	 * La radice serve per identificare univocamente una Gerarchia
	 */
	private String radiceOfferta;
	
	private UUID idProprietario;
	
	/**
	 * Nome (==Categoria) della Gerarchia offerta
	 */
	private String offerta;
	private int durataRichiesta;
	private int durataOfferta;
	private Stato stato;
	
	public FakeProposta(UUID id, String radiceRichiesta, String richiesta, String radiceOfferta, String offerta,
			int durataRichiesta, int durataOfferta, Stato stato) {
		super();
		this.idProprietario = id;
		this.radiceRichiesta = radiceRichiesta;
		this.richiesta = richiesta;
		this.radiceOfferta = radiceOfferta;
		this.offerta = offerta;
		this.durataRichiesta = durataRichiesta;
		this.durataOfferta = durataOfferta;
		this.stato = stato;
	}
	
	
	private FakeProposta() {
		super();
	}
	public String getRadiceRichiesta() {
		return radiceRichiesta;
	}
	public void setRadiceRichiesta(String radiceRichiesta) {
		this.radiceRichiesta = radiceRichiesta;
	}
	public String getRichiesta() {
		return richiesta;
	}
	public void setRichiesta(String richiesta) {
		this.richiesta = richiesta;
	}
	public String getRadiceOfferta() {
		return radiceOfferta;
	}
	public void setRadiceOfferta(String radiceOfferta) {
		this.radiceOfferta = radiceOfferta;
	}
	public String getOfferta() {
		return offerta;
	}
	public void setOfferta(String offerta) {
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
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	/**
	 * @return the idProprietario
	 */
	public UUID getIdProprietario() {
		return idProprietario;
	}

	/**
	 * @param idProprietario the idProprietario to set
	 */
	public void setIdProprietario(UUID idProprietario) {
		this.idProprietario = idProprietario;
	}
}
