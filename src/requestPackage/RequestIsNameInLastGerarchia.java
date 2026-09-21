package requestPackage;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * isNameInLastGerarchia
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestIsNameInLastGerarchia implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gerToCheck;
	
	public RequestIsNameInLastGerarchia(String gerToCheck) {
		super();
		this.gerToCheck = gerToCheck;
	}

	/**
	 * @return the gerToCheck
	 */
	public String getGerToCheck() {
		return gerToCheck;
	}
}
