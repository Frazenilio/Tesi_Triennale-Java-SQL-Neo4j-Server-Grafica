package requestPackage;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * isConfiguratoreSavedInDatabase
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestIsConfiguratoreSavedInDatabase implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	
	public RequestIsConfiguratoreSavedInDatabase(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
}
