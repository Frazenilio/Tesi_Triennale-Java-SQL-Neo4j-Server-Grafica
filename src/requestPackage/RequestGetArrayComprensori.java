package requestPackage;

import java.util.List;

import strutture.Comprensorio;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * GetArrayComprensori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestGetArrayComprensori implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Comprensorio> lista;
	
	public RequestGetArrayComprensori() {
		
	}

	/**
	 * @return the lista
	 */
	public List<Comprensorio> getLista() {
		return lista;
	}

}
