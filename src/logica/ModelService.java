package logica;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreFDCOutOfBounds;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import strutture.InsiemeChiuso;
import strutture.Comprensorio;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import strutture.Proposta;
import utente.Configuratore;
import utente.Fruitore;
import utente.Utente;
import utility.Stato;
import utility.Tupla;

public interface ModelService {

	/////////////////////////////////////////////////////////////////////////
	/////////////////////////// UTENTE METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	List<Utente> getArrayUtente() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	List<Fruitore> getArrayFruitori() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Aggiunge all'arrayFruitori il {@link Fruitore} fruit
	 * @param fruit {@link Fruitore} aggiunto a arrayFruitore
	 * @param nome Nome del {@link Fruitore}
	 * @param cognome Cognome del {@link Fruitore}
	 * @param password Password del {@link Fruitore}
	 * @throws IOException 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @Precondizioni fruit!=null
	 * @Postcondizioni
	 * - aggiunto fruit:Configuratore a arrayConfiguratori<br>
	 * - aggiunto a Fruitori.json il Fruitore
	 * - aggiunto a arrayUtente l'utente fruit:Fruitore (estende Utente)
	 * @since 2
	 * @since TESI_DATABASE il {@link Fruitore} viene salvato sul database
	 */
	void addFruitore(Fruitore fruit, String nome, String cognome, String password) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking, IOException;
	
	/**
	 * Metodo per controllare se un certo {@link Fruitore} si trova nel database
	 * @param name username del {@link Fruitore}
	 * @param password password del {@link Fruitore}
	 * @return il {@link Fruitore} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	Fruitore isFruitoreSavedInDatabase(String name, String password) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per controllare se il nome di un {@link Fruitore} e' gia' presente nel database
	 * @param name username da cercare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	boolean isFruitoreNameAlreadyTaken(String name) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per controllare se la mail di un {@link Fruitore} e' gia' presente nel database
	 * @param email mil da cercare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	boolean isFruitoreEmailAlreadyTaken(String email) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere un {@link Fruitore} dato il suo id
	 * @param id id del {@link Fruitore} ricercato
	 * @return il {@link Fruitore} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 */
	Fruitore retrieveFruitoreById(UUID id) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	
	List<Configuratore> getArrayConfiguratori() throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Aggiunge all'arrayConfiguratori il {@link Configuratore} conf
	 * @param conf {@link Configuratore} aggiunto a arrayConfiguratore
	 * @param nome Nome del {@link Configuratore}
	 * @param cognome Cognome del {@link Configuratore}
	 * @param password Password del {@link Configuratore}
	 * @throws IOException 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @Precondizioni conf!=null
	 * @Postcondizioni
	 * - aggiunto conf:Configuratore a arrayConfiguratori<br>
	 * - aggiunto a Configuratori.json il configuratore
	 * - aggiunto a arrayUtente l'utente conf:Configuratore (estende Utente)
	 * @since 1
	 * @since TESI_DATABASE il {@link Configuratore} viene salvato nel Database
	 */
	void addConfiguratore(Configuratore conf, String nome, String cognome, String password) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking, IOException;
	
	/**
	 * Metodo per controllare se un certo {@link Configuratore} e' salvato nel database
	 * @param name username del {@link Configuratore}
	 * @param password password del {@link Configuratore}
	 * @return il {@link Configuratore} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	Configuratore isConfiguratoreSavedInDatabase(String name, String password) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per controllare se il nome di un {@link Configuratore} e' gia' presente nel database
	 * @param name username da cercare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	boolean isConfiguratoreNameAlreadyTaken(String name) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere un {@link Configuratore} dato il suo id
	 * @param id id del {@link Configuratore} ricercato
	 * @return il {@link Configuratore} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	Configuratore retrieveConfiguratoreById(UUID id) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere l'id di un {@link Configuratore} associato all'id di un {@link Comprensorio}
	 * @param id id del {@link Comprensorio} associato
	 * @return id del {@link Configuratore} associato
	 * @throws ErroreDatabaseNotWorking
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerUnreachable
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	UUID retrieveConfiguratoreIdByComprensorioId(UUID id) throws ErroreDatabaseNotWorking,
	ErroreRispostaNonConforme, ErroreServerUnreachable, ErroreServerReply;
	
	/////////////////////////////////////////////////////////////////////////
	////////////////////// COMPRENSORI METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	List<Comprensorio> getArrayComprensori() throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per controllare se un certo comune (String) e' gia' presente nel database
	 * @param comune comune da cercare
	 * @return true se e' gia' presente, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 */
	boolean isComuneInArrayComprensori(String comune) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo che aggiunge il parametro comp:Comprensorio all'arrayComprensori e al file Comprensori.json
	 * @param comp :Comprensorio da aggiungere all'arrayComprensori
	 * @throws IOException 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @Precondizioni
	 * comp!=null
	 * @Postcondizioni
	 * Aggiunto comp all'arrayComprensori e scritto su json
	 * @since 1
	 * @since TESI_DATABASE il {@link Comprensorio} viene salvato su Database
	 */
	void addComprensorio(Comprensorio comp) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking, IOException;
	
	/**
	 * Metodo per ottenere un {@link Comprensorio} dato il suo id
	 * @param id id del {@link Comprensorio}
	 * @return il {@link Comprensorio}
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	Comprensorio retrieveComprensorioById(UUID id) throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	
	/////////////////////////////////////////////////////////////////////////
	//////////////////////// GERARCHIE METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	List<Gerarchia> getArrayGerarchie() 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;

	/**
	 * Aggiunge ad arrayGerarchie il parametro ger:Gerarchia
	 * @param ger :Gerarchia aggiunta all'arrayGerarchie
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @Precondizioni ger!=null
	 * @Postcondizioni
	 * - Se la gerarchia aggiunta e' una foglia: modificato arrayFoglieDiRadici (aggiornato prima di restituirlo)<br>
	 * - Scritto sul file json l'arrayGerarchie
	 * @since 1
	 * @since TESI_DATABASE la {@link Gerarchia} viene aggiunta al Database invece e non c'e' piu' IOException
	 */
	void addGerarchia(Gerarchia ger) throws IOException, ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per controllare se un certo nome si trova nell'ultima {@link Gerarchia} inserita
	 * @param categoria Stringa da cercare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	boolean isNameInLastGerarchia(String categoria)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere una {@link Gerarchia} dato il suo id
	 * @param id id della {@link Gerarchia} ricercata
	 * @return la {@link Gerarchia} trovata
	 * @throws ErroreDatabaseNotWorking
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Gerarchia retrieveGerarchiaById(UUID id) throws ErroreDatabaseNotWorking, 
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply;

	
	/////////////////////////////////////////////////////////////////////////
	///////////////////////// PROPOSTE METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	List<Proposta> getArrayProposte() throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Aggiunge una proposta all'arrayProposte
	 * @param prop
	 * @return un array di array: sono tutti i cicli chiusi creati grazie all'aggiunta della proposta 
	 * @throws IOException 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @Precondizioni prop!=null
	 * @Postcondizioni aggiunto a arrayProposte prop, e anche al fileProposte (JSON)
	 * @since 3
	 * @since TESI_DATABASE la {@link Proposta} viene salvata su Database
	 */
	void addProposta(Proposta prop, UUID idProprietarioComprensorio) throws ErroreServerUnreachable, ErroreRispostaNonConforme,
	ErroreServerReply, ErroreDatabaseNotWorking, IOException;
	
	/**
	 * Metodo per modificare lo stato di una proposta<br>
	 * Usa gli stati disponibili in proposte
	 * @param prop
	 * @param nuovoStato
	 * @throws IOException 
	 * @throws ErroreDatabaseNotWorking 
	 * @Precondizioni prop!=null, nuovoStato deve essere uno stato valido
	 * @Postcondizione aggiorna Json di Proposte con i nuovi stati
	 * @since 4
	 * @since TESI_DATABASE la modifica avviene nel Database
	 */
	void modificaStatoProposta(Proposta prop, Stato nuovoStato) throws IOException, ErroreServerUnreachable, ErroreRispostaNonConforme,
	ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Ritorna la durata dell'offerta calcolata
	 * @param richiesta
	 * @param offerta
	 * @param durataRichiesta
	 * @param retrievedFdcsRichiesta 
	 * @return intero approssimato all'intero piu' vicino
	 * @throws ErroreDatabaseNotWorking 
	 * @Precondizioni richiesta!=null, offerta!=null, durataRichiesta>=1
	 * @Postcondizioni 
	 * - la durata offerta e' uguale al FDC(richiesta,Offerta)*durataRichiesta approssimato all'intero più vicino<br>
	 * - se non esiste FDC ritornato valore -1
	 * @since 3
	 */
	int calcolaDurataOfferta(Gerarchia richiesta, Gerarchia offerta, int durataRichiesta)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere tutte le Proposta che contengono (come richiesta o offerta) la Gerarchia foglia specificata
	 * @param foglia {@link Gerarchia} di cui si vuole ottenere le Proposte associate
	 * @return ArrayList di Proposta che contengono foglia in Richiesta o offerta
	 * @throws ErroreDatabaseNotWorking 
	 * @Precondizioni foglia!=null
	 * @Postcondizioni 
	 * @since 4
	 */
	List<Proposta> ottieniProposteDiFoglia(Gerarchia foglia)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere le {@link Proposta} associate all'id di un utente
	 * @param idUser id dell'utente
	 * @return Lista di {@link Proposta}
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	List<Proposta> ottieniProposteUtente(UUID idUser) throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere una {@link Proposta} dato il suo id
	 * @param id id della {@link Proposta} ricercata
	 * @return la {@link Proposta} trovata
	 * @throws ErroreDatabaseNotWorking
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public Proposta retrievePropostabyId(UUID id) throws ErroreDatabaseNotWorking,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply;
	
	/**
	 * Metodo per fornire {@link Gerarchia} di offerta e richiesta di una lista di {@link Proposta}
	 * @param lista lista da sistemare
	 * @return la lista con {@link Proposta} con offerta e richiesta
	 * @throws ErroreDatabaseNotWorking
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @since TESI DATABASE
	 */
	public List<Proposta> retrieveGerarchiasForProposta(List<Proposta> lista)
			throws ErroreDatabaseNotWorking, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply;
	
	
	/////////////////////////////////////////////////////////////////////////
	//////////////////// FATTORI DI CONVERSIONE METHODS /////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per calcolare la lista di fattori di conversione legati ad una nuova foglia
	 * MODIFICA i fattori di conversione di tutte le gerarchie foglia
	 * @param fogliaBersaglio : Foglia con tutti i FDC
	 * @param fogliaNuova : Foglia nuova:
	 * @param fdcDaNuovaABersaglio :fattore di conversione da foglia1 a foglia2
	 * @param retrievedFdcsRichiesta Lista {@link FattoreDiConversione} della fogliaBersaglio
	 * @throws ErroreFDCOutOfBounds 
	 * @throws ErroreDatabaseNotWorking 
	 * @since 1
	 * @since TESI_DATABASE aggiunto il parametro retrievedFdcsRichiesta ed il salvataggio avviene direttamente sul database
	 */
	void calcolaFattoriConversione(Gerarchia fogliaBersaglio, Gerarchia fogliaNuova,
			Double fdcDaNuovaABersaglio)
			throws ErroreFDCOutOfBounds, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Ritorna il minimo e massimo valore che si puo' dare al fattore di conversione tra due foglie
	 * @param foglia1 Foglia non nuova, da qui si prendono i fattori di conversione
	 * @param foglia2 
	 * @return tupla(min,max)
	 * @throws ErroreDatabaseNotWorking 
	 * @Precondizioni foglia1!=null, foglia2!=null
	 * @Postcondizioni i limiti che permettono agli FDC di stare nei limiti prestabiliti
	 */
	Tupla<Double,Double> calcolaMinMax(Gerarchia foglia1, Gerarchia foglia2)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;

	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////////// CICLI CHIUSI METHODS //////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	List<InsiemeChiuso> getArrayInsiemiChiusi() throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere una Lista di {@link InsiemeChiuso} relativi ad un certo id {@link Configuratore}
	 * @param IdConfiguratore id del {@link Configuratore} associato
	 * @return Lista di {@link InsiemeChiuso}
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	List<InsiemeChiuso> ottieniCicliChiusi(UUID IdConfiguratore) throws ErroreServerUnreachable, 
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per ottenere un {@link InsiemeChiuso} attraverso il suo id
	 * @param id id del {@link InsiemeChiuso} ricercato
	 * @return il {@link InsiemeChiuso} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	InsiemeChiuso retrieveCicloDaChiudereById(UUID id) throws ErroreServerUnreachable, 
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;

	/**
	 * Metodo per ottenere il {@link FattoreDiConversione} tra due {@link Gerarchia}
	 * @param gerStart {@link Gerarchia} di partenza
	 * @param gerEnd {@link Gerarchia} bersaglio del {@link FattoreDiConversione}
	 * @return il {@link FattoreDiConversione} trovato
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	FattoreDiConversione retrieveFattoreConversione(Gerarchia gerStart, Gerarchia gerEnd)
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;

	/**
	 * Metodo per avere tutti i {@link FattoreDiConversione} di una {@link Gerarchia}
	 * @param foglia1 {@link Gerarchia} di cui ottenere i {@link FattoreDiConversione}
	 * @return Lista dei {@link FattoreDiConversione} della {@link Gerarchia} passata come parametro
	 * @throws ErroreServerUnreachable
	 * @throws ErroreRispostaNonConforme
	 * @throws ErroreServerReply
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	List<FattoreDiConversione> retrieveAllFdcsFromFoglia(Gerarchia foglia1) 
			throws ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;

	/**
	 * Metodo per ottenere la {@link Gerarchia} radice di una data {@link Gerarchia}
	 * @param ger {@link Gerarchia} di cui cercare la radice
	 * @return id della {@link Gerarchia} radice
	 * @throws ErroreDatabaseNotWorking
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @since TESI DATABASE
	 */
	UUID retrieveRadiceOfGerarchia(Gerarchia ger)
			throws ErroreDatabaseNotWorking, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply;

	/**
	 * Metodo per pulire il database da eventuali {@link Gerarchia} incomplete dei {@link FattoreDiConversione}
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI DATABASE
	 */
	void cleanGerarchiaDatabaseFromUncompletedGerarchias() throws ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
}
