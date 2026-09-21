package responsePackage;

import utente.Configuratore;
import utility.EsitoRequest;

/**
 * Classe per implementare risposte del server al client
 * isConfiguratoreSavedInDatabase
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class ResponseIsConfiguratoreSavedInDatabase implements Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Configuratore conf;
	private EsitoRequest esito;
	
	public ResponseIsConfiguratoreSavedInDatabase(Configuratore conf, EsitoRequest esito) {
		super();
		this.conf = conf;
		this.esito = esito;
	}
	
	/**
	 * @return the conf
	 */
	public Configuratore getConf() {
		return conf;
	}
	/**
	 * @return the esito
	 */
	public EsitoRequest getEsito() {
		return esito;
	}
	
}
