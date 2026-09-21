package requestPackage;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * isFruitoreNameAlreadyTaken
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestIsFruitoreNameAlreadyTaken implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public RequestIsFruitoreNameAlreadyTaken(String name) {
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
