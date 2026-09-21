package gestoreClientPackage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import requestPackage.RequestAddProposta;
import requestPackage.RequestCalcolaDurataOfferta;
import requestPackage.RequestGetArrayProposte;
import requestPackage.RequestModificaStatoProposta;
import requestPackage.RequestOttieniProposteDiFoglia;
import requestPackage.RequestOttieniProposteUtente;
import requestPackage.RequestRetrieveGerarchiasForProposta;
import requestPackage.RequestRetrievePropostaById;
import responsePackage.ResponseAddProposta;
import responsePackage.ResponseCalcolaDurataOfferta;
import responsePackage.ResponseGetArrayProposte;
import responsePackage.ResponseModificaStatoProposta;
import responsePackage.ResponseOttieniProposteDiFoglia;
import responsePackage.ResponseOttieniProposteUtente;
import responsePackage.ResponseRetrieveGerarchiasForProposta;
import responsePackage.ResponseRetrievePropostaById;
import strutture.Gerarchia;
import strutture.Proposta;
import utility.Stato;

/**
 * Classe per la gestione delle richieste al model sul server
 * riguardo la gestione delle {@link Proposta}
 * @author Francesco Lozio
 * @since TESI CLIENT-SERVER
 */
public class GestorePropostaClient {

	private DeliveryMethodInterface deliverer;
	
	public GestorePropostaClient(DeliveryMethodInterface deliverer) {
		this.deliverer = deliverer;
	}
	
	private DeliveryMethodInterface accediDeliverer() {
		return this.deliverer;
	}
	
	/**
	 * Metodo per ottenere l'array di {@link Proposta} del Server
	 * @return l'array di {@link Proposta} del Server
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<Proposta> getArrayProposte() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestGetArrayProposte request = new RequestGetArrayProposte();
		ResponseGetArrayProposte response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getLista();
	}

	/**
	 * Metodo per aggiungere una {@link Proposta} all'array del Server
	 * @param prop nuova {@link Proposta} da aggiungere
	 * @param idProp 
	 * @throws IOException
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public void addProposta(Proposta prop, UUID idProp) throws IOException, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestAddProposta request = new RequestAddProposta(prop, idProp);
		ResponseAddProposta response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
	}

	/**
	 * Metodo per modificare lo {@link Stato} di una {@link Proposta} del Server
	 * @param prop {@link Proposta} da modificare
	 * @param nuovoStato nuovo {@link Stato}
	 * @throws IOException
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public void modificaStatoProposta(Proposta prop, Stato nuovoStato) throws IOException, ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply{
		RequestModificaStatoProposta request = new RequestModificaStatoProposta(prop, nuovoStato);
		ResponseModificaStatoProposta response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
	}
	
	/**
	 * Metodo per calcolare la durata di un'offerta
	 * @param richiesta {@link Gerarchia} richesta
	 * @param offerta {@link Gerarchia} offerta
	 * @param durataRichiesta durata della richiesta in questione
	 * @return la durata dell'offerta
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public int calcolaDurataOfferta(Gerarchia richiesta, Gerarchia offerta, int durataRichiesta) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestCalcolaDurataOfferta request = new RequestCalcolaDurataOfferta(richiesta,
				offerta, durataRichiesta);
		ResponseCalcolaDurataOfferta response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}

	/**
	 * Metodo per ottenere le {@link Proposta} di una data {@link Gerarchia} foglia
	 * @param foglia {@link Gerarchia} considerata
	 * @return lista delle {@link Proposta} che coinvolgono la Gerarchia foglia passata
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<Proposta> ottieniProposteDiFoglia(Gerarchia foglia) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestOttieniProposteDiFoglia request = new RequestOttieniProposteDiFoglia(foglia);
		ResponseOttieniProposteDiFoglia response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}

	/**
	 * Metodo per ottenere le {@link Proposta} associate ad un Utente
	 * @param user utente da considerare
	 * @return lista di {@link Proposta} legate ad un Utente
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI CLIENT-SERVER
	 */
	public List<Proposta> ottieniProposteUtente(UUID user) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestOttieniProposteUtente request = new RequestOttieniProposteUtente(user);
		ResponseOttieniProposteUtente response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}

	/**
	 * Metodo per ottenere una {@link Proposta} dal Server dato il suo id 
	 * @param id id della {@link Proposta} ricercata
	 * @return {@link Proposta} trovata
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Proposta retrievePropostaById(UUID id) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		RequestRetrievePropostaById request = new RequestRetrievePropostaById(id);
		ResponseRetrievePropostaById response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getResult();
	}

	/**
	 * Metodo per ottenere le {@link Gerarchia} che coinvolgono una lista di {@link Proposta}
	 * @param lista lista di {@link Proposta} con offerte e richieste non impostate
	 * @return lista di {@link Proposta} con offerte e richieste non impostate
	 * @throws ErroreServerReply
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @since TESI DATABASE
	 */
	public List<Proposta> retrieveGerarchiasForProposta(List<Proposta> lista) 
			throws ErroreServerReply, ErroreServerUnreachable, ErroreRispostaNonConforme {
		RequestRetrieveGerarchiasForProposta request = new RequestRetrieveGerarchiasForProposta(lista);
		ResponseRetrieveGerarchiasForProposta response = this.accediDeliverer().deliveryRequestAndGetResponse(request);
		return response.getRisultato();
	}
}
