package errori;

/**
 * Errore da lanciare in caso non si ottenga risposta dal server
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ErroreServerReply extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_SERVER_REPLY_ERROR = "Il server non ha potuto completare l'operazione, riprova.";

	
	public ErroreServerReply() {
		
	}
	
	public ErroreServerReply(Exception e) {
		super(e);
	}

	public String getMessage() {
		return MSG_ERROR_SERVER_REPLY_ERROR;
	}
}
