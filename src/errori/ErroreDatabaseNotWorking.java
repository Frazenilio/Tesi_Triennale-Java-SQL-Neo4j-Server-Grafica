package errori;

/**
 * Eccezione lanciata riguardo al Database non funzionante
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class ErroreDatabaseNotWorking extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_DB = "Database non ha ritornato un oggetto valido, riprova.";

	public String getMessage() {
		return MSG_ERROR_DB;
	}
}
