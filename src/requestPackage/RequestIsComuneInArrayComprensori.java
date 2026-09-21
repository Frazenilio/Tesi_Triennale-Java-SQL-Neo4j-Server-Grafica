package requestPackage;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * isComuneInArrayComprensori
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestIsComuneInArrayComprensori implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String comune;
	
	public RequestIsComuneInArrayComprensori(String comune) {
		super();
		this.comune = comune;
	}
	
	/**
	 * @return the risultato
	 */
	public String getComune() {
		return this.comune;
	}
	
	
}
