package responsePackage;

import java.util.List;

import utente.Fruitore;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * getArrayFruitori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseGetArrayFruitori implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private List<Fruitore> listaFruitori;
	
	public ResponseGetArrayFruitori(EsitoRequest esito, List<Fruitore> lista) {
		this.esito = esito;
		this.listaFruitori = lista;
	}
	
	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

	/**
	 * @return the listaFruitori
	 */
	public List<Fruitore> getListaFruitori() {
		return listaFruitori;
	}
}
