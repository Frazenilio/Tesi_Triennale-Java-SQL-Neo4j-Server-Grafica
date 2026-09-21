package responsePackage;

import java.util.List;

import strutture.InsiemeChiuso;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * ottieniCicliChiusi
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseOttieniCicliChiusi implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private List<InsiemeChiuso> lista;
	
	public ResponseOttieniCicliChiusi(EsitoRequest esito, List<InsiemeChiuso> lista) {
		super();
		this.esito = esito;
		this.lista = lista;
	}

	/**
	 * @return the esito
	 */
	public EsitoRequest getEsito() {
		return esito;
	}

	/**
	 * @return the lista
	 */
	public List<InsiemeChiuso> getLista() {
		return lista;
	}
	
}
