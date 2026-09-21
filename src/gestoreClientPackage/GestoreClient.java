package gestoreClientPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreFDCOutOfBounds;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import logica.ModelService;
import requestPackage.Request;
import responsePackage.Response;
import strutture.InsiemeChiuso;
import strutture.Comprensorio;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import strutture.Proposta;
import utente.Configuratore;
import utente.Fruitore;
import utente.Utente;
import utility.EsitoRequest;
import utility.Stato;
import utility.Tupla;

/**
 * Classe per la gestione delle richieste al model sul server
 * @author Francesco Lozio
 * @since TESI CLIENT-SERVER
 */
public class GestoreClient implements ModelService, DeliveryMethodInterface{

	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	private GestoreUtentiClient gestoreUtenti;
	private GestoreComprensoriClient gestoreComprensori;
	private GestoreGerarchieClient gestoreGerarchie;
	private GestorePropostaClient gestoreProposte;
	private GestoreFattoriDiConversioneClient gestoreFDC;
	private GestoreCicliChiusiClient gestoreCicliChiusi;
	
	public GestoreClient(ObjectInputStream input, ObjectOutputStream output) {
		super();
		this.input = input;
		this.output = output;
		
		this.gestoreUtenti = new GestoreUtentiClient(this);
		this.gestoreComprensori = new GestoreComprensoriClient(this);
		this.gestoreGerarchie = new GestoreGerarchieClient(this);
		this.gestoreProposte = new GestorePropostaClient(this);
		this.gestoreFDC = new GestoreFattoriDiConversioneClient(this);
		this.gestoreCicliChiusi = new GestoreCicliChiusiClient(this);
	}

	
	/////////////////////////////////////////////////////////////////////////
	/////////////////////////// UTENTE METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public List<Utente> getArrayUtente() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreUtenti.getArrayUtente();
	}

	@Override
	public List<Fruitore> getArrayFruitori() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreUtenti.getArrayFruitori();
	}

	@Override
	public void addFruitore(Fruitore fruit, String nome, String cognome, String password)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, IOException {
		this.gestoreUtenti.addFruitore(fruit, nome, cognome, password);
	}

	@Override
	public Fruitore isFruitoreSavedInDatabase(String name, String password)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreUtenti.isFruitoreSavedInDatabase(name, password);
	}

	@Override
	public boolean isFruitoreNameAlreadyTaken(String name)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreUtenti.isFruitoreNameAlreadyTaken(name);
	}

	@Override
	public boolean isFruitoreEmailAlreadyTaken(String email)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreUtenti.isFruitoreEmailAlreadyTaken(email);
	}

	@Override
	public Fruitore retrieveFruitoreById(UUID id)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreUtenti.retrieveFruitoreById(id);
	}

	@Override
	public List<Configuratore> getArrayConfiguratori() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreUtenti.getArrayConfiguratori();
	}

	@Override
	public void addConfiguratore(Configuratore conf, String nome, String cognome, String password)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, IOException {
		this.gestoreUtenti.addConfiguratore(conf, nome, cognome, password);
	}
	
	@Override
	public Configuratore isConfiguratoreSavedInDatabase(String name, String password)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreUtenti.isConfiguratoreSavedInDatabase(name, password);
	}

	@Override
	public boolean isConfiguratoreNameAlreadyTaken(String name)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreUtenti.isConfiguratoreNameAlreadyTaken(name);
	}

	@Override
	public Configuratore retrieveConfiguratoreById(UUID id)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreUtenti.retrieveConfiguratoreById(id);
	}	
	
	
	/////////////////////////////////////////////////////////////////////////
	////////////////////// COMPRENSORI METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	

	@Override
	public List<Comprensorio> getArrayComprensori() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreComprensori.getArrayComprensori();
	}

	@Override
	public boolean isComuneInArrayComprensori(String comune) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreComprensori.isComuneInArrayComprensori(comune);
	}

	@Override
	public void addComprensorio(Comprensorio comp) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, IOException {
		this.gestoreComprensori.addComprensorio(comp);
	}
	
	@Override
	public Comprensorio retrieveComprensorioById(UUID id)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreComprensori.retrieveComprensorioById(id);
	}

	
	/////////////////////////////////////////////////////////////////////////
	//////////////////////// GERARCHIE METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	

	@Override
	public List<Gerarchia> getArrayGerarchie() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreGerarchie.getArrayGerarchie();
	}

	@Override
	public void addGerarchia(Gerarchia ger) throws IOException,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		this.gestoreGerarchie.addGerarchia(ger);
	}

	@Override
	public boolean isNameInLastGerarchia(String categoria) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreGerarchie.isNameInLastGerarchia(categoria);
	}
	
	@Override
	public Gerarchia retrieveGerarchiaById(UUID id)
			throws ErroreDatabaseNotWorking, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreGerarchie.retrieveGerarchiaById(id);
	}
	
	@Override
	public UUID retrieveRadiceOfGerarchia(Gerarchia ger) 
			throws ErroreDatabaseNotWorking, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreGerarchie.retrieveRadiceOfGerarchia(ger);
	}
	
	@Override
	public void cleanGerarchiaDatabaseFromUncompletedGerarchias() throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply {
		this.gestoreGerarchie.cleanGerarchiaDatabaseFromUncompletedGerarchias();
	}

	
	/////////////////////////////////////////////////////////////////////////
	///////////////////////// PROPOSTE METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	@Override
	public List<Proposta> getArrayProposte() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreProposte.getArrayProposte();
	}

	@Override
	public void addProposta(Proposta prop, UUID idProp) throws IOException, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		this.gestoreProposte.addProposta(prop, idProp);
	}

	@Override
	public void modificaStatoProposta(Proposta prop, Stato nuovoStato) throws IOException, ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply{
		this.gestoreProposte.modificaStatoProposta(prop, nuovoStato);
	}

	@Override
	public int calcolaDurataOfferta(Gerarchia richiesta, Gerarchia offerta, int durataRichiesta) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreProposte.calcolaDurataOfferta(richiesta, offerta, durataRichiesta);
	}

	@Override
	public List<Proposta> ottieniProposteDiFoglia(Gerarchia foglia) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreProposte.ottieniProposteDiFoglia(foglia);
	}
	
	@Override
	public List<Proposta> ottieniProposteUtente(UUID idUser)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreProposte.ottieniProposteUtente(idUser);
	}


	@Override
	public Proposta retrievePropostabyId(UUID id) throws ErroreDatabaseNotWorking,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreProposte.retrievePropostaById(id);
	}

	@Override
	public List<Proposta> retrieveGerarchiasForProposta(List<Proposta> lista)
			throws ErroreDatabaseNotWorking, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreProposte.retrieveGerarchiasForProposta(lista);
	}


	/////////////////////////////////////////////////////////////////////////
	//////////////////// FATTORI DI CONVERSIONE METHODS /////////////////////
	/////////////////////////////////////////////////////////////////////////
	


	@Override
	public void calcolaFattoriConversione(Gerarchia fogliaBersaglio, Gerarchia fogliaNuova, 
			Double fdcDaNuovaABersaglio)
			throws ErroreFDCOutOfBounds, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		this.gestoreFDC.calcolaFattoriConversione(fogliaBersaglio, fogliaNuova,
				fdcDaNuovaABersaglio);
	}

	@Override
	public Tupla<Double, Double> calcolaMinMax(Gerarchia foglia1, Gerarchia foglia2) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply{
		return this.gestoreFDC.calcolaMinMax(foglia1, foglia2);
	}

	@Override
	public FattoreDiConversione retrieveFattoreConversione(Gerarchia gerStart, Gerarchia gerEnd) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreFDC.retrieveFattoreConversione(gerStart, gerEnd);
	}
	
	@Override
	public List<FattoreDiConversione> retrieveAllFdcsFromFoglia(Gerarchia foglia1)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreFDC.retrieveAllFdcsFromFoglia(foglia1);
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////////// CICLI CHIUSI METHODS //////////////////////////
	/////////////////////////////////////////////////////////////////////////
	

	@Override
	public List<InsiemeChiuso> getArrayInsiemiChiusi() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		return this.gestoreCicliChiusi.getArrayInsiemiChiusi();
	}


	@Override
	public List<InsiemeChiuso> ottieniCicliChiusi(UUID IdConfiguratore)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreCicliChiusi.ottieniCicliChiusi(IdConfiguratore);
	}

	@Override
	public InsiemeChiuso retrieveCicloDaChiudereById(UUID id)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.gestoreCicliChiusi.retrieveCicloDaChiudereById(id);
	}

	@Override
	public UUID retrieveConfiguratoreIdByComprensorioId(UUID id) 
			throws ErroreDatabaseNotWorking, ErroreRispostaNonConforme, ErroreServerUnreachable, ErroreServerReply {
		return this.gestoreUtenti.retrieveConfiguratoreIdByComprensorioId(id);
	}

	@Override
	public <R extends Response> R deliveryRequestAndGetResponse(Request request) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {

		try {
			this.output.writeObject(request);
			this.output.flush();
			this.output.reset();
		} catch (IOException e) {
			throw new ErroreServerUnreachable();
		}
		
		R response;
		try {
			response = (R) this.input.readObject();
			
		}catch(Exception e) {
			throw new ErroreRispostaNonConforme();
		}
		if(response.getEsito() == EsitoRequest.ERROR) {
			throw new ErroreServerReply();
		}
		return response;
	}
}
