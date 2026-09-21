package responsePackage;

import java.util.List;

import strutture.Comprensorio;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * getArrayComprensori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseGetArrayComprensori implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EsitoRequest esito;
	private List<Comprensorio> lista;

	public ResponseGetArrayComprensori(EsitoRequest esito, List<Comprensorio> lista) {
		super();
		this.esito = esito;
		this.lista = lista;
	}

	@Override
	public EsitoRequest getEsito() {
		return this.esito;
	}

	/**
	 * @return the lista
	 */
	public List<Comprensorio> getLista() {
		return lista;
	}
}
