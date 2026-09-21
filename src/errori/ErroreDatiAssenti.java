package errori;

/**
 * Errore lanciato in caso di dati assenti (mancanti)
 * @author Francesco Lozio 737664
 * @since Parte B
 */
public class ErroreDatiAssenti extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_DATI_INSUFFICIENTI = "Dati Insufficienti per questa operazione, riprova.\n";

	public String getMessage() {
		return MSG_ERROR_DATI_INSUFFICIENTI;
	}
}
