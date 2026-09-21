package requestPackage;

import strutture.Comprensorio;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * AddComprensorio
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestAddComprensorio implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Comprensorio comprensorioToAdd;
	
	public RequestAddComprensorio(Comprensorio comprensorioToAdd) {
		super();
		this.comprensorioToAdd = comprensorioToAdd;
	}

	/**
	 * @return the comprensorioToAdd
	 */
	public Comprensorio getComprensorioToAdd() {
		return comprensorioToAdd;
	}
}
