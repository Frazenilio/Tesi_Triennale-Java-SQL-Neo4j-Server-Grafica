package gestoreClientPackage;

import java.util.List;
import java.util.UUID;

import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import requestPackage.RequestGetArrayInsiemiChiusi;
import requestPackage.RequestOttieniCicliChiusi;
import requestPackage.RequestRetrieveCicloDaChiudereById;
import responsePackage.ResponseGetArrayInsiemiChiusi;
import responsePackage.ResponseOttieniCicliChiusi;
import responsePackage.ResponseRetrieveCicloDaChiudereById;
import strutture.InsiemeChiuso;
import utente.Configuratore;

/**
 * Classe per la gestione delle richieste al model sul server
 * per i cicli chiusi
 * @author Francesco Lozio
 * @since TESI CLIENT-SERVER
 */
public class GestoreCicliChiusiClient {

	private DeliveryMethodInterface deliverer;
	
	public GestoreCicliChiusiClient(DeliveryMethodInterface deliverer) {
		this.deliverer = deliverer;
	}
	
	private DeliveryMethodInterface accediDeliverer() {
		return this.deliverer;
	}
	
	/**
	 * Metodo per ottenere l'array di {@link InsiemeChiuso} del Server
	 * @return array dei {@link InsiemeChiuso}
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<InsiemeChiuso> getArrayInsiemiChiusi() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestGetArrayInsiemiChiusi request = new RequestGetArrayInsiemiChiusi();
		ResponseGetArrayInsiemiChiusi response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}

	/**
	 * Metodo per ottenere i {@link InsiemeChiuso} del Server specificando un {@link Configuratore}
	 * @param c {@link Configuratore} a cui associare i Cicli Chiusi
	 * @return insieme dei {@link InsiemeChiuso} del Configuratore specificato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<InsiemeChiuso> ottieniCicliChiusi(UUID c) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestOttieniCicliChiusi request = new RequestOttieniCicliChiusi(c);
		ResponseOttieniCicliChiusi response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getLista();
	}

	/**
	 * Metodo per ottenere un {@link InsiemeChiuso} dal Server da un certo id
	 * @param id id del {@link InsiemeChiuso}
	 * @return il {@link InsiemeChiuso} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public InsiemeChiuso retrieveCicloDaChiudereById(UUID id) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestRetrieveCicloDaChiudereById request = new RequestRetrieveCicloDaChiudereById(id);
		ResponseRetrieveCicloDaChiudereById response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getResult();
	}
}
