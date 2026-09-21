package errori;

/**
 * Errore di Selezione errata
 * @author Francesco Lozio 737664
 * @since Parte B
 */
public class ErroreSelezione extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_SELEZIONE = "Errore nella selezione, riprova.\n";

	public String getMessage() {
		return MSG_ERROR_SELEZIONE;
	}
}
