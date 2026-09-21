package requestPackage;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * isFruitoreSavedInDatabase
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestIsFruitoreSavedInDatabase implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	public RequestIsFruitoreSavedInDatabase(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	

}
