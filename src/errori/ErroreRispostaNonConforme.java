package errori;

/**
 * Errore da lanciare nel caso in cui la risposta non sia conforme da quella attesa
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ErroreRispostaNonConforme extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_RISPOSTA_NON_CONFORME = "Risposta del Server non verificabile, ritenta l'operazione";

	public String getMessage() {
		return MSG_ERROR_RISPOSTA_NON_CONFORME;
	}
}
