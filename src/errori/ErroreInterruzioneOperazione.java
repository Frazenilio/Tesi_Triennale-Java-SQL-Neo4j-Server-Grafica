package errori;

/**
 * Exeception lanciata quando viene interrotta un'operazione
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class ErroreInterruzioneOperazione extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Operazione Interrotta";
	
	public String getMessage() {
		return MESSAGE;
	}
}
