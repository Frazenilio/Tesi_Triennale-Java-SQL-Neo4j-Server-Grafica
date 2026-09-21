package strutture;

import java.io.Serializable;

import utente.Fruitore;

/**
 * Classe Adapter per la rappresentazione Grafica di {@link Proposta}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class AdapterPropostaGraphicKit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Proposta proposta;
	private Fruitore proprietario;
	
	public AdapterPropostaGraphicKit(Proposta proposta, Fruitore proprietario) {
		super();
		this.proposta = proposta;
		this.proprietario = proprietario;
	}
	/**
	 * @return the proposta
	 */
	public Proposta getProposta() {
		return proposta;
	}
	/**
	 * @param proposta the proposta to set
	 */
	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}
	/**
	 * @return the proprietario
	 */
	public Fruitore getProprietario() {
		return proprietario;
	}
	/**
	 * @param proprietario the proprietario to set
	 */
	public void setProprietario(Fruitore proprietario) {
		this.proprietario = proprietario;
	}
}
