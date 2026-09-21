package logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreFDCOutOfBounds;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import gestioneDatabase.GestioneDatabase;
import strutture.*;
import utente.*;
import utility.Stato;
import utility.Tupla;

/**
 * @author Matteo Ghidini 736213
 * Classe che gestisce la logica del progetto ingegneria del SW 
 * @since 1
 */
public class Gestore implements ModificaStato, ModelService{

	private GestoreProposte gestoreProposte;
	private GestoreFattoriDiConversione gestoreFDC;
	private GestioneDatabase gestioneDB;

	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Costruttore di Gestore
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ErroreDatabaseNotWorking 
	 * @Precondizione True
	 * @Postcondizione 
	 * - Settati gli arrayGerarchie, arrayComprensori,
	 *  arrayConfiguratori, arrayFruitori, arrayProposte come i dati contenuti nei file JSON
	 *  oppure vuoti se nulla e' contenuto nel file JSON<br>
	 * - Settato arrayUtenti come unione di arrayFruitori e arrayConfiguratori
	 * @since 1 setarray[Gerarchie,Comprensori,Configuratori]
	 * @since 2 setarray[Fruitori]
	 * @since 3 setarray[Proposta]
	 * @since 4 setarray[array[Propsota]] : insiemichiusi
	 * @since TESI_DATABASE rimossi tutti i sotto gestori eccetto quello per {@link Proposta} e {@link FattoreDiConversione},
	 * tolto anche il GestioneFile vecchio in favore di un {@link GestioneDatabase}
	 */
	public Gestore() throws FileNotFoundException, IOException, ErroreDatabaseNotWorking {
		super();
		this.gestioneDB = new GestioneDatabase();
		inizializzazioneGestore();
	}

	private void inizializzazioneGestore() throws FileNotFoundException, IOException, ErroreDatabaseNotWorking {
		this.gestoreProposte = new GestoreProposte(
				this.gestioneDB.loadArrayProposta()
				,this);


		this.gestoreFDC = new GestoreFattoriDiConversione();
	}

	

	//GET SET
	//GET SET
	//GET SET
	
	
	@Override
	public synchronized List<Gerarchia> getArrayGerarchie() throws ErroreDatabaseNotWorking {
		return this.gestioneDB.loadArrayGerarchia();
	}

	@Override
	public synchronized List<Configuratore> getArrayConfiguratori() throws ErroreDatabaseNotWorking {
		return this.gestioneDB.loadArrayConfiguratore();
	}

	@Override
	public synchronized List<Utente> getArrayUtente() throws ErroreDatabaseNotWorking {
		List<Utente> listaUtenti = new ArrayList<Utente>();
		listaUtenti.addAll(this.gestioneDB.loadArrayFruitore());
		listaUtenti.addAll(this.gestioneDB.loadArrayConfiguratore());
		return listaUtenti;
	}

	@Override
	public synchronized List<Comprensorio> getArrayComprensori() throws ErroreDatabaseNotWorking {
		return this.gestioneDB.loadArrayComprensori();
	}
	
	@Override
	public synchronized boolean isComuneInArrayComprensori(String comune) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.isComuneInArrayComprensori(comune);
	}

	@Override
	public synchronized List<Fruitore> getArrayFruitori() throws ErroreDatabaseNotWorking {
		return this.gestioneDB.loadArrayFruitore();
	}

	@Override
	public synchronized List<Proposta> getArrayProposte() throws ErroreDatabaseNotWorking {
		return this.gestioneDB.loadArrayProposta();
	}

	@Override
	public synchronized List<InsiemeChiuso> getArrayInsiemiChiusi() throws ErroreDatabaseNotWorking {
		return this.gestioneDB.loadArrayCicloChiuso();
	}
	
	
	//END GET SET
	//END GET SET
	//END GET SET
	
	//ADD
	//ADD
	//ADD
	
	
	
	@Override
	public synchronized void addGerarchia(Gerarchia ger) throws IOException, ErroreDatabaseNotWorking {
		this.gestioneDB.saveGerarchia(ger);
	}

	@Override
	public synchronized void addConfiguratore(Configuratore conf, String nome, String cognome, String password) throws ErroreDatabaseNotWorking {
		this.gestioneDB.saveConfiguratore(conf, nome, cognome, password);
	}

	@Override
	public synchronized void addFruitore(Fruitore fruit, String nome, String cognome, String password) throws ErroreDatabaseNotWorking {
		this.gestioneDB.saveFruiore(fruit, nome, cognome, password);
	}

	@Override
	public synchronized void addComprensorio(Comprensorio comp) throws ErroreDatabaseNotWorking {
		this.gestioneDB.saveComprensorio(comp);
	}

	@Override
	public synchronized void addProposta(Proposta prop, UUID idComprensorio) throws ErroreDatabaseNotWorking, IOException {
		this.gestoreProposte.addProposta(prop);
		List<InsiemeChiuso> insiemiChiusi = this.gestoreProposte.chiudiProposte(idComprensorio);
		this.gestioneDB.saveProposta(prop);
		
		for(InsiemeChiuso cycle : insiemiChiusi) {
			this.gestioneDB.saveInsiemeChiuso(cycle);
		}
	}
	
	//END ADD
	//END ADD
	//END ADD
	

	@Override
	public synchronized void calcolaFattoriConversione(Gerarchia fogliaBersaglio,Gerarchia fogliaNuova,
			Double fdcDaNuovaABersaglio) 
			throws ErroreFDCOutOfBounds, ErroreDatabaseNotWorking{
		List<FattoreDiConversione> fdcsFromRichiesta = this.retrieveAllFdcsFromFoglia(fogliaBersaglio);
		List<FattoreDiConversione> FDCs = this.gestoreFDC.
				calcolaFattoriConversione(fogliaBersaglio, fogliaNuova, fdcDaNuovaABersaglio, fdcsFromRichiesta);
		this.gestioneDB.saveFattoriConversione(fogliaNuova, FDCs);
	}

	@Override
	public synchronized Tupla<Double,Double> calcolaMinMax(Gerarchia foglia1,Gerarchia foglia2) throws ErroreDatabaseNotWorking{
		List<FattoreDiConversione> fdcs = this.retrieveAllFdcsFromFoglia(foglia1);
		return this.gestoreFDC.calcolaMinMax(foglia1, foglia2, fdcs);
	}

	@Override
	public synchronized List<FattoreDiConversione> retrieveAllFdcsFromFoglia(Gerarchia foglia) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveAllFdcsFromFoglia(foglia);
	}

	@Override
	public synchronized void modificaStatoProposta(Proposta prop, Stato nuovoStato) throws IOException, ErroreDatabaseNotWorking {
		prop.setStato(nuovoStato);
		this.gestoreProposte.modificaStatoPropostaInterno(prop, nuovoStato);
		this.gestioneDB.modificaStatoProposta(prop, nuovoStato);
	}

	@Override
	public synchronized int calcolaDurataOfferta(Gerarchia richiesta, Gerarchia offerta,
			int durataRichiesta) throws ErroreDatabaseNotWorking {
		double fdc = this.retrieveFattoreConversione(richiesta, offerta).getFdc().getSecond();
		return this.gestoreFDC.calcolaDurataOfferta(richiesta,offerta,durataRichiesta, fdc);
	}

	@Override
	public synchronized List<Proposta> ottieniProposteDiFoglia(Gerarchia foglia) throws ErroreDatabaseNotWorking {
		List<UUID> idsRequired = this.gestioneDB.ottieniProposteDiFoglia(foglia);
		List<Proposta> risultato = new ArrayList<Proposta>();
		for(UUID id : idsRequired) {
			risultato.add(this.retrievePropostabyId(id));
		}
		return risultato;
	}

	
	@Override
	public synchronized List<Proposta> ottieniProposteUtente(UUID idUser) throws ErroreDatabaseNotWorking{
		List<UUID> idProposte = this.gestioneDB.ottieniIdProposteDaIdUtente(idUser);
		List<Proposta> lista = new ArrayList<Proposta>();
		for(UUID id : idProposte) {
			lista.add(this.retrievePropostabyId(id));
		}
		return lista;
	}

	@Override
	public synchronized List<InsiemeChiuso> ottieniCicliChiusi(UUID idConfiguratore) throws ErroreDatabaseNotWorking{
		return this.gestioneDB.ottieniCicliChiusiDaIdConfiguratore(idConfiguratore);
	}
	
	@Override
	public synchronized boolean isNameInLastGerarchia(String categoria) throws ErroreDatabaseNotWorking {
		List<Gerarchia> array = this.getArrayGerarchie();
		return array.get(
				array.size()-1)
				.isNameAlreadyTaken(categoria);
	}
	
	@Override
	public synchronized void cleanGerarchiaDatabaseFromUncompletedGerarchias() throws ErroreDatabaseNotWorking {
		this.gestioneDB.cleanGerarchiaDatabaseFromUncompletedGerarchias();
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////////// RETRIVE-BY-ID METHODS /////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public synchronized Proposta retrievePropostabyId(UUID id) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrievePropostabyId(id);
	}
	
	@Override
	public synchronized Gerarchia retrieveGerarchiaById(UUID id) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveGerarchiaById(id);
	}
	
	@Override
	public synchronized InsiemeChiuso retrieveCicloDaChiudereById(UUID id) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveCicloDaChiudereById(id);
	}
	
	@Override
	public synchronized Comprensorio retrieveComprensorioById(UUID id) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveComprensorioById(id);
	}
	
	@Override
	public synchronized Fruitore retrieveFruitoreById(UUID id) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveFruitoreById(id);
	}
	
	@Override
	public synchronized Configuratore retrieveConfiguratoreById(UUID id) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveConfiguratoreById(id);
	}
	
	@Override
	public synchronized List<Proposta> retrieveGerarchiasForProposta(List<Proposta> lista)
			throws ErroreDatabaseNotWorking, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		List<Proposta> listaConvertita = new ArrayList<Proposta>();
		for(int i = 0; i < lista.size(); i++) {
			if(lista.get(i).isToRetrieveGerarchias()) {
				Gerarchia offerta = this.retrieveGerarchiaById(lista.get(i).getOffertaId());
				Gerarchia richiesta = this.retrieveGerarchiaById(lista.get(i).getRichiestaId());
				lista.get(i).setOfferta(offerta);
				lista.get(i).setRichiesta(richiesta);
			}
			listaConvertita.add(lista.get(i));
		}
		return listaConvertita;
	}
	
	@Override
	public synchronized UUID retrieveRadiceOfGerarchia(Gerarchia ger) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveRadiceOfGerarchia(ger);
	}
	
	@Override
	public synchronized UUID retrieveConfiguratoreIdByComprensorioId(UUID id) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveConfiguratoreIdByComprensorioId(id);
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////////// CHECK-WITH-QUERY METHODS //////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	@Override
	public synchronized Fruitore isFruitoreSavedInDatabase(String name, String password) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.isFruitoreSavedInDatabase(name, password);
	}
	
	@Override
	public synchronized Configuratore isConfiguratoreSavedInDatabase(String name, String password) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.isConfiguratoreSavedInDatabase(name, password);
	}
	
	@Override
	public synchronized boolean isFruitoreNameAlreadyTaken(String name) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.isFruitoreNameAlreadyTaken(name);
	}
	
	@Override
	public synchronized boolean isFruitoreEmailAlreadyTaken(String email) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.isFruitoreEmailAlreadyTaken(email);
	}
	
	@Override
	public synchronized boolean isConfiguratoreNameAlreadyTaken(String name) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.isConfiguratoreNameAlreadyTaken(name);
	}

	@Override
	public synchronized FattoreDiConversione retrieveFattoreConversione(Gerarchia gerStart, Gerarchia gerEnd) throws ErroreDatabaseNotWorking {
		return this.gestioneDB.retrieveFattoreConversione(gerStart, gerEnd);
	}
}
