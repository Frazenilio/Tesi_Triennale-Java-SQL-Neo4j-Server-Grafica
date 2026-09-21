package gestoreClientPackage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import requestPackage.RequestAddConfiguratore;
import requestPackage.RequestAddFruitore;
import requestPackage.RequestGetArrayConfiguratori;
import requestPackage.RequestGetArrayFruitori;
import requestPackage.RequestGetArrayUtente;
import requestPackage.RequestIsConfiguratoreNameAlreadyTaken;
import requestPackage.RequestIsConfiguratoreSavedInDatabase;
import requestPackage.RequestIsFruitoreEmailAlreadyTaken;
import requestPackage.RequestIsFruitoreNameAlreadyTaken;
import requestPackage.RequestIsFruitoreSavedInDatabase;
import requestPackage.RequestRetrieveConfiguratoreById;
import requestPackage.RequestRetrieveConfiguratoreIdByComprensorioId;
import requestPackage.RequestRetrieveFruitoreById;
import responsePackage.ResponseAddConfiguratore;
import responsePackage.ResponseAddFruitore;
import responsePackage.ResponseGetArrayConfiguratori;
import responsePackage.ResponseGetArrayFruitori;
import responsePackage.ResponseGetArrayUtente;
import responsePackage.ResponseIsConfiguratoreNameAlreadyTaken;
import responsePackage.ResponseIsConfiguratoreSavedInDatabase;
import responsePackage.ResponseIsFruitoreEmailAlreadyTaken;
import responsePackage.ResponseIsFruitoreNameAlreadyTaken;
import responsePackage.ResponseIsFruitoreSavedInDatabase;
import responsePackage.ResponseRetrieveConfiguratoreById;
import responsePackage.ResponseRetrieveConfiguratoreIdByComprensorioId;
import responsePackage.ResponseRetrieveFruitoreById;
import strutture.Comprensorio;
import utente.Configuratore;
import utente.Fruitore;
import utente.Utente;

/**
 * Classe per la gestione delle richieste al model sul server
 * riguardo la gestione di {@link Utente}
 * @author Francesco Lozio
 * @since TESI CLIENT-SERVER
 */
public class GestoreUtentiClient {

	private DeliveryMethodInterface deliverer;
	
	public GestoreUtentiClient(DeliveryMethodInterface deliverer) {
		super();
		this.deliverer = deliverer;
	}
	
	private DeliveryMethodInterface accediDeliverer() {
		return this.deliverer;
	}
	
	/**
	 * Metodo per ottenere l'array degli {@link Utente} del Server
	 * @return array degli {@link Utente} del Server
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<Utente> getArrayUtente() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestGetArrayUtente request = new RequestGetArrayUtente();
		ResponseGetArrayUtente response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getArrayToReturn();
	}

	/**
	 * Metodo per ottenere l'array dei {@link Fruitore} del Server
	 * @return array dei {@link Fruitore} del Server
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<Fruitore> getArrayFruitori() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestGetArrayFruitori request = new RequestGetArrayFruitori();
		ResponseGetArrayFruitori response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getListaFruitori();
	}

	/**
	 * Metodo per richiedere al Server di aggiungere un nuovo {@link Fruitore} al suo database
	 * @param fruit nuovo {@link Fruitore} da aggiungere
	 * @param cognome 
	 * @param nome 
	 * @param password 
	 * @throws IOException
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public void addFruitore(Fruitore fruit, String nome, String cognome, String password) throws IOException, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestAddFruitore request = new RequestAddFruitore(fruit, nome, cognome, password);
		ResponseAddFruitore response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
	}

	/**
	 * Metodo per ottenere l'array dei {@link Configuratore} del Server
	 * @return array dei {@link Configuratore} del Server
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<Configuratore> getArrayConfiguratori() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestGetArrayConfiguratori request = new RequestGetArrayConfiguratori();
		ResponseGetArrayConfiguratori response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getLista();
	}

	/**
	 * Metodo per richiedere al Server di aggiungere un nuovo {@link Configuratore} al suo database
	 * @param conf nuovo {@link Configuratore} da aggiungere
	 * @param nome nome del {@link Configuratore} 
	 * @param cognome cognome del {@link Configuratore}
	 * @param password 
	 * @throws IOException
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public void addConfiguratore(Configuratore conf, String nome, String cognome, String password) throws IOException,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestAddConfiguratore request = new RequestAddConfiguratore(conf, nome, cognome, password);
		ResponseAddConfiguratore response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
	}

	/**
	 * Metodo per controllare se il Server ha in memoria un certo {@link Fruitore}
	 * @param name username del {@link Fruitore}
	 * @param password password del {@link Fruitore}
	 * @return {@link Fruitore} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Fruitore isFruitoreSavedInDatabase(String name, String password) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestIsFruitoreSavedInDatabase request = new RequestIsFruitoreSavedInDatabase(name, password);
		ResponseIsFruitoreSavedInDatabase response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getResult();
	}

	/**
	 * Metodo per controllare se nel Server e' gia' presente un username del {@link Fruitore}
	 * @param name username da controllare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public boolean isFruitoreNameAlreadyTaken(String name) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestIsFruitoreNameAlreadyTaken request = new RequestIsFruitoreNameAlreadyTaken(name);
		ResponseIsFruitoreNameAlreadyTaken response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.isResult();
	}

	/**
	 * Metodo per controllare se nel Server e' gia' presente una mail del {@link Fruitore}
	 * @param email email da controllare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public boolean isFruitoreEmailAlreadyTaken(String email) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestIsFruitoreEmailAlreadyTaken request = new RequestIsFruitoreEmailAlreadyTaken(email);
		ResponseIsFruitoreEmailAlreadyTaken response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.isResult();
	}

	/**
	 * Metodo per ottenere un certo {@link Fruitore} dal Server dato il suo id
	 * @param id id del {@link Fruitore} ricercato
	 * @return il {@link Fruitore} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Fruitore retrieveFruitoreById(UUID id) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestRetrieveFruitoreById request = new RequestRetrieveFruitoreById(id);
		ResponseRetrieveFruitoreById response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getResult();
	}

	/**
	 * Metodo per controllare la presenza di un certo {@link Configuratore} nel Server date le sue credenziali
	 * @param name username del {@link Configuratore}
	 * @param password password del {@link Configuratore}
	 * @return il {@link Configuratore} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Configuratore isConfiguratoreSavedInDatabase(String name, String password)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestIsConfiguratoreSavedInDatabase request = new RequestIsConfiguratoreSavedInDatabase(name, password);
		ResponseIsConfiguratoreSavedInDatabase response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getConf();
	}

	/**
	 * Metodo per controllare se nel Server e' gia' presente un username del {@link Configuratore}
	 * @param name username da controllare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public boolean isConfiguratoreNameAlreadyTaken(String name)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestIsConfiguratoreNameAlreadyTaken request = new RequestIsConfiguratoreNameAlreadyTaken(name);
		ResponseIsConfiguratoreNameAlreadyTaken response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.isResult();
	}

	/**
	 * Metodo per ottenere un certo {@link Configuratore} dal Server dato il suo id
	 * @param id id del {@link Configuratore} ricercato
	 * @return il {@link Configuratore} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Configuratore retrieveConfiguratoreById(UUID id)
			throws ErroreRispostaNonConforme, ErroreServerUnreachable, ErroreServerReply {
		RequestRetrieveConfiguratoreById request = new RequestRetrieveConfiguratoreById(id);
		ResponseRetrieveConfiguratoreById response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getResult();
	}

	/**
	 * Metodo per ottenere l'id di un {@link Configuratore} associato ad un certo {@link Comprensorio}
	 * @param id id del {@link Comprensorio} associato
	 * @return l'id del {@link Configuratore} associato al {@link Comprensorio}
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerUnreachable
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public UUID retrieveConfiguratoreIdByComprensorioId(UUID id)
			throws ErroreRispostaNonConforme, ErroreServerUnreachable, ErroreServerReply {
		RequestRetrieveConfiguratoreIdByComprensorioId request = new RequestRetrieveConfiguratoreIdByComprensorioId(id);
		ResponseRetrieveConfiguratoreIdByComprensorioId response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getResult();
	}
}
