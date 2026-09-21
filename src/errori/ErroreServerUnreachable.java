package errori;

/**
 * Errore da lanciare nel caso in cui il server non sia raggiungibile
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ErroreServerUnreachable extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_SERVER_COMMUNICATION = "Errore nella comunicazione col server.";

	public String getMessage() {
		return MSG_ERROR_SERVER_COMMUNICATION;
	}
}
