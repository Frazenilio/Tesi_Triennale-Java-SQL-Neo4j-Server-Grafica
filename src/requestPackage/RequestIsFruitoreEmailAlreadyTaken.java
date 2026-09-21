package requestPackage;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * isFruitoreEmailAlreadyTaken
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestIsFruitoreEmailAlreadyTaken implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	
	public RequestIsFruitoreEmailAlreadyTaken(String email) {
		super();
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
}
