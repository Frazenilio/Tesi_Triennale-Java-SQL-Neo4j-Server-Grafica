package responsePackage;

import java.util.List;

import utente.Configuratore;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * getArrayConfiguratori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseGetArrayConfiguratori implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private List<Configuratore> lista;

	public ResponseGetArrayConfiguratori(EsitoRequest esito, List<Configuratore> lista) {
		this.esito = esito;
		this.lista = lista;
	}
	
	/**
	 * @return the lista
	 */
	public List<Configuratore> getLista() {
		return lista;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}
}
