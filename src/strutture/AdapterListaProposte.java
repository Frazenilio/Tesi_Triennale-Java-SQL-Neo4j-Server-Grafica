package strutture;

import java.io.Serializable;
import java.util.List;

/**
 * Classe adapter per gestire liste di Proposta
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class AdapterListaProposte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Proposta> lista;

	public AdapterListaProposte(List<Proposta> lista) {
		super();
		this.lista = lista;
	}

	/**
	 * @return the lista
	 */
	public List<Proposta> ottieniLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(List<Proposta> lista) {
		this.lista = lista;
	}

}
