package requestPackage;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * isConfiguratoreNameAlreadyTaken
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestIsConfiguratoreNameAlreadyTaken implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public RequestIsConfiguratoreNameAlreadyTaken(String name) {
		super();
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
