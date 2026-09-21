package errori;

/**
 * Errore nel caso in cui i parametri non siano conformi
 * @author Francesco Lozio 737664
 * @since Parte B
 */
public class ErroreParametriNonConformi extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_PARAMETRI_NON_CONFORMI = "Errore nell'inserimento dei parametri. Potrebbero essere non sufficienti o non conformi, riprova.\n";

	public String getMessage() {
		return MSG_ERROR_PARAMETRI_NON_CONFORMI;
	}
}
