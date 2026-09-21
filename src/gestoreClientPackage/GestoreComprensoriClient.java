package gestoreClientPackage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import requestPackage.RequestAddComprensorio;
import requestPackage.RequestGetArrayComprensori;
import requestPackage.RequestIsComuneInArrayComprensori;
import requestPackage.RequestRetrieveComprensorioById;
import responsePackage.ResponseAddComprensorio;
import responsePackage.ResponseGetArrayComprensori;
import responsePackage.ResponseIsComuneInArrayComprensori;
import responsePackage.ResponseRetrieveComprensorioById;
import strutture.Comprensorio;

/**
 * Classe per la gestione delle richieste al model sul server
 * per la gestione dei {@link Comprensorio}
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public class GestoreComprensoriClient {
	
	private DeliveryMethodInterface deliverer;
	
	public GestoreComprensoriClient(DeliveryMethodInterface delivere) {
		this.deliverer = delivere;
	}
	
	private DeliveryMethodInterface accediDeliverer() {
		return this.deliverer;
	}
	
	/**
	 * Metodo per ottenere l'array di {@link Comprensorio} del Server
	 * @return l'array dei Comprensori del Server
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<Comprensorio> getArrayComprensori() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestGetArrayComprensori request = new RequestGetArrayComprensori();
		ResponseGetArrayComprensori response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getLista();
	}

	/**
	 * Metodo per controllare se una data String e' gia' presente nell'array di {@link Comprensorio} del Server
	 * @param comune String da controllare
	 * @return true se e' presente, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public boolean isComuneInArrayComprensori(String comune) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestIsComuneInArrayComprensori request = new RequestIsComuneInArrayComprensori(comune);
		ResponseIsComuneInArrayComprensori response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.isRisultato();
	}

	/**
	 * Metodo per aggiungere un {@link Comprensorio} nell'array dei {@link Comprensorio} del Server
	 * @param comp nuovo {@link Comprensorio} da aggiungere
	 * @throws IOException
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public void addComprensorio(Comprensorio comp) throws IOException, ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply {
		RequestAddComprensorio request = new RequestAddComprensorio(comp);
		ResponseAddComprensorio response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
	}

	/**
	 * Metodo per ottenere un {@link Comprensorio} nel Server dato il suo id
	 * @param id id del {@link Comprensorio} ricercato
	 * @return {@link Comprensorio} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Comprensorio retrieveComprensorioById(UUID id) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestRetrieveComprensorioById request = new RequestRetrieveComprensorioById(id);
		ResponseRetrieveComprensorioById response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getResult();
	}
}
