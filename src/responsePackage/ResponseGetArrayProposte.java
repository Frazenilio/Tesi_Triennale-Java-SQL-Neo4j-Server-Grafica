package responsePackage;

import java.util.List;

import strutture.Proposta;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * getArrayProposte
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseGetArrayProposte implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Proposta> lista;
	private EsitoRequest esito;
	
	public ResponseGetArrayProposte(EsitoRequest esito, List<Proposta> lista) {
		super();
		this.lista = lista;
		this.esito = esito;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

	/**
	 * @return the lista
	 */
	public List<Proposta> getLista() {
		return lista;
	}

}
