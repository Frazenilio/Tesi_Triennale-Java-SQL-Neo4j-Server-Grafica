package errori;

/**
 * Errore per FDC fuori dai limiti
 * @author Francesco Lozio 737664
 * @since Parte B
 */
public class ErroreFDCOutOfBounds extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_FDC_OUT_OF_BOUNDS = "Fattore di Conversione fuori dai limiti.\n";

	public String getMessage() {
		return MSG_ERROR_FDC_OUT_OF_BOUNDS;
	}
}
