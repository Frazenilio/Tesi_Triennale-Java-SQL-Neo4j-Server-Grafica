package strutture;

import java.io.Serializable;
import java.util.List;

import utente.Configuratore;

/**
 * Classe Adapter per la rappresentazione grafica della classe {@link Comprensorio}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class AdapterComprensorioGraphicKit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> comuni;
	private Configuratore proprietario;
	
	public AdapterComprensorioGraphicKit(List<String> comuni, Configuratore proprietario) {
		super();
		this.comuni = comuni;
		this.proprietario = proprietario;
	}

	/**
	 * @return the comuni
	 */
	public List<String> getComuni() {
		return comuni;
	}

	/**
	 * @param comuni the comuni to set
	 */
	public void setComuni(List<String> comuni) {
		this.comuni = comuni;
	}

	/**
	 * @return the proprietario
	 */
	public Configuratore getProprietario() {
		return proprietario;
	}

	/**
	 * @param proprietario the proprietario to set
	 */
	public void setProprietario(Configuratore proprietario) {
		this.proprietario = proprietario;
	}
}
