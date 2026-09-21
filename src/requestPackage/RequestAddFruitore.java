package requestPackage;

import utente.Fruitore;

/**
 * Classe di Richiesta per chiedere al server di eseguire
 * AddFruitore
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class RequestAddFruitore implements Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Fruitore fruitoreToAdd;
	private String nomeFruitore;
	private String cognomeFruitore;
	private String password;

	public RequestAddFruitore(Fruitore fruitoreToAdd, String nome, String cognome, String password) {
		super();
		this.fruitoreToAdd = fruitoreToAdd;
		this.nomeFruitore = nome;
		this.cognomeFruitore = cognome;
		this.password = password;
	}

	/**
	 * @return the fruitoreToAdd
	 */
	public Fruitore getFruitoreToAdd() {
		return fruitoreToAdd;
	}
	
	/**
	 * @return the nomeConfiguratore
	 */
	public String getNomeFruitore() {
		return nomeFruitore;
	}

	/**
	 * @return the cognomeConfiguratore
	 */
	public String getCognomeFruitore() {
		return cognomeFruitore;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}
