package gestoreClientPackage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import requestPackage.RequestAddGerarchia;
import requestPackage.RequestCleanGerarchiaDatabaseFromUncompletedGerarchias;
import requestPackage.RequestGetArrayGerarchie;
import requestPackage.RequestIsNameInLastGerarchia;
import requestPackage.RequestRetrieveGerarchiaById;
import requestPackage.RequestRetrieveRadiceOfGerarchia;
import responsePackage.ResponseAddGerarchia;
import responsePackage.ResponseCleanGerarchiaDatabaseFromUncompletedGerarchias;
import responsePackage.ResponseGetArrayGerarchie;
import responsePackage.ResponseIsNameInLastGerarchia;
import responsePackage.ResponseRetrieveGerarchiaById;
import responsePackage.ResponseRetrieveRadiceOfGerarchia;
import strutture.Gerarchia;

/**
 * Classe per la gestione delle richieste al model sul server
 * per la gestione delle {@link Gerarchia}
 * @author Francesco Lozio
 * @since TESI CLIENT-SERVER
 */
public class GestoreGerarchieClient {

	
	private DeliveryMethodInterface deliverer;
	
	
	public GestoreGerarchieClient(DeliveryMethodInterface deliverer) {
		this.deliverer = deliverer;
	}
	
	private DeliveryMethodInterface accediDeliverer() {
		return this.deliverer;
	}
	
	/**
	 * Metodo per ottenere l'array di {@link Gerarchia} del Server
	 * @return array delle {@link Gerarchia} del Server
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<Gerarchia> getArrayGerarchie() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestGetArrayGerarchie request = new RequestGetArrayGerarchie();
		ResponseGetArrayGerarchie response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getLista();
	}

	/**
	 * Metodo per aggiungere una Gerarchia all'array di {@link Gerarchia} del Server
	 * @param ger nuova gerarchia da aggiungere
	 * @throws IOException
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public void addGerarchia(Gerarchia ger) throws IOException,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestAddGerarchia request = new RequestAddGerarchia(ger);
		ResponseAddGerarchia response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
	}

	/**
	 * Metodo per controllare se una String e' gia' usata nell'ultima {@link Gerarchia}
	 * @param categoria stringa da controllare
	 * @return true se e' presente, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public boolean isNameInLastGerarchia(String categoria) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestIsNameInLastGerarchia request = new RequestIsNameInLastGerarchia(categoria);
		ResponseIsNameInLastGerarchia response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.isRisultato();
	}

	/**
	 * Metodo per ottenere una {@link Gerarchia} dal Server dato il suo id
	 * @param id id della {@link Gerarchia} ricercata
	 * @return {@link Gerarchia} trovata
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Gerarchia retrieveGerarchiaById(UUID id) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestRetrieveGerarchiaById request = new RequestRetrieveGerarchiaById(id);
		ResponseRetrieveGerarchiaById response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getResult();
	}

	/**
	 * Metodo per ottenere un id al Server di una {@link Gerarchia} radice della {@link Gerarchia} passata
	 * @param ger {@link Gerarchia} di cui trovare la radice
	 * @return id della {@link Gerarchia} radice
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABSE
	 */
	public UUID retrieveRadiceOfGerarchia(Gerarchia ger) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestRetrieveRadiceOfGerarchia request = new RequestRetrieveRadiceOfGerarchia(ger);
		ResponseRetrieveRadiceOfGerarchia response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}

	public void cleanGerarchiaDatabaseFromUncompletedGerarchias() throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply {
		RequestCleanGerarchiaDatabaseFromUncompletedGerarchias request = new RequestCleanGerarchiaDatabaseFromUncompletedGerarchias();
		ResponseCleanGerarchiaDatabaseFromUncompletedGerarchias response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
	}
}
