package responsePackage;

import java.util.List;

import strutture.Gerarchia;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * getArrayGerarchie
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseGetArrayGerarchie implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Gerarchia> lista;
	private EsitoRequest esito;
	
	public ResponseGetArrayGerarchie(EsitoRequest esito, List<Gerarchia> lista) {
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
	public List<Gerarchia> getLista() {
		return lista;
	}
}
