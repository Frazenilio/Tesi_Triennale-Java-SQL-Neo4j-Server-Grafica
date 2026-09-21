package requestPackage;

import java.util.List;

import strutture.Proposta;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * retrieveGerarchiasForProposta
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestRetrieveGerarchiasForProposta implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Proposta> lista;
	
	public RequestRetrieveGerarchiasForProposta(List<Proposta> lista) {
		super();
		this.lista = lista;
	}

	/**
	 * @return the lista
	 */
	public List<Proposta> getLista() {
		return lista;
	}
}
