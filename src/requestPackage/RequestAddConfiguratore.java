package requestPackage;

import utente.Configuratore;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * AddConfiguratore
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestAddConfiguratore implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Configuratore configuratoreToAdd;
	private String nomeConfiguratore;
	private String cognomeConfiguratore;
	private String password;

	public RequestAddConfiguratore(Configuratore configuratoreToAdd, String nome, String cognome, String password) {
		super();
		this.configuratoreToAdd = configuratoreToAdd;
		this.nomeConfiguratore = nome;
		this.cognomeConfiguratore = cognome;
		this.password = password;
	}

	/**
	 * @return the configuratoreToAdd
	 */
	public Configuratore getConfiguratoreToAdd() {
		return configuratoreToAdd;
	}

	/**
	 * @return the nomeConfiguratore
	 */
	public String getNomeConfiguratore() {
		return nomeConfiguratore;
	}

	/**
	 * @return the cognomeConfiguratore
	 */
	public String getCognomeConfiguratore() {
		return cognomeConfiguratore;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
}
