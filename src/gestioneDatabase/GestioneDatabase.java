package gestioneDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import errori.ErroreDatabaseNotWorking;
import gestioneFile.LogProposteRepository;
import strutture.InsiemeChiuso;
import strutture.Comprensorio;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import strutture.Proposta;
import utente.Configuratore;
import utente.Fruitore;
import utility.FakeProposta;
import utility.Stato;

public class GestioneDatabase {

	private ComprensorioDatabaseConnection comprensorioDB;
	private ConfiguratoreDatabaseConnection configuratoreDB;
	private FruitoreDatabaseConnection fruitoreDB;
	private GerarchiaDatabaseConnect gerarchiaDB;
	private InsiemiChiusiDatabaseConnection insiemiChiusiDB;
	private PropostaDatabaseConnection propostaDB;
	private LogProposteRepository logProposteJson;
	
	private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/test_db";
	private static final String USER_MYSQL = "Fra";
    private static final String PASSWORD_MYSQL = "TesiInge2024";
    
	private static Connection conn;
	
	private static final String URI_NEO4J = "bolt://localhost:7687";
    private static final String USER_NEO4J = "neo4j"; 
    private static final String PASSWORD_NEO4J = "TesiInge2024"; 

    private static final Driver driver = GraphDatabase.driver(URI_NEO4J, AuthTokens.basic(USER_NEO4J, PASSWORD_NEO4J));	
	
	public GestioneDatabase() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL_MYSQL, USER_MYSQL, PASSWORD_MYSQL);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
        
		this.comprensorioDB = new ComprensorioDatabaseConnection(conn);
		this.configuratoreDB = new ConfiguratoreDatabaseConnection(conn);
		this.fruitoreDB = new FruitoreDatabaseConnection(conn);
		this.gerarchiaDB = new GerarchiaDatabaseConnect(driver);
		this.insiemiChiusiDB = new InsiemiChiusiDatabaseConnection(conn);
		this.propostaDB = new PropostaDatabaseConnection(conn);
		this.logProposteJson = new LogProposteRepository(FilePath.LOG_PROPOSTE.getPath());
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////     LOAD     //////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per caricare un array contenente tutti i {@link Comprensorio} presenti nel Database
	 * @return array di {@link Comprensorio} 
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Comprensorio> loadArrayComprensori() throws ErroreDatabaseNotWorking{
		List<Comprensorio> lista = this.comprensorioDB.requestArrayComprensorio();
		return lista;
	}
	
	/**
	 * Metodo per caricare un array contenente tutti i {@link Configuratore} presenti nel Database
	 * @return array di {@link Configuratore} 
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Configuratore> loadArrayConfiguratore() throws ErroreDatabaseNotWorking{
		List<Configuratore> lista = this.configuratoreDB.requestArrayConfiguratore();
		return lista;
	}
	
	/**
	 * Metodo per caricare un array contenente tutti i {@link Fruitore} presenti nel Database
	 * @return array di {@link Fruitore}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Fruitore> loadArrayFruitore() throws ErroreDatabaseNotWorking{
		List<Fruitore> lista = this.fruitoreDB.requestArrayFruitore();
		return lista;
	}
	
	/**
	 * Metodo per caricare un array contenente tutte le {@link Gerarchia} presenti nel Database
	 * @return array di {@link Gerarchia}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Gerarchia> loadArrayGerarchia() throws ErroreDatabaseNotWorking{
		List<Gerarchia> lista = this.gerarchiaDB.requestArrayGerarchia();
		return lista;
	}
	
	/**
	 * Metodo per caricare un array contenente tutti i {@link InsiemeChiuso} presenti nel Database
	 * @return array di {@link InsiemeChiuso}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<InsiemeChiuso> loadArrayCicloChiuso() throws ErroreDatabaseNotWorking{
		List<InsiemeChiuso> lista = this.insiemiChiusiDB.requestArrayInsiemeChiuso();
		return lista;
	}
	
	/**
	 * Metodo per caricare un array contenente tutti i {@link Proposta} presenti nel Database
	 * @return array di {@link Proposta}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Proposta> loadArrayProposta() throws ErroreDatabaseNotWorking{
		List<Proposta> lista = this.propostaDB.requestArrayProposta();
		return lista;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////     SAVE     //////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per salvare un nuovo {@link Comprensorio} nel database apposito
	 * @param newComprensorio nuovo {@link Comprensorio} da aggiungere
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void saveComprensorio(Comprensorio newComprensorio) throws ErroreDatabaseNotWorking {
		this.comprensorioDB.addNewComprensorio(newComprensorio);
	}
	
	/**
	 * Metodo per salvare un nuovo {@link Configuratore} nel database apposito
	 * @param newConfiguratore nuovo {@link Configuratore} da aggiungere
	 * @param nome nome del nuovo {@link Configuratore}
	 * @param cognome cognome del nuovo {@link Configuratore}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void saveConfiguratore(Configuratore newConfiguratore, String nome, String cognome, String password) throws ErroreDatabaseNotWorking {
		this.configuratoreDB.addNewConfiguratore(newConfiguratore, nome, cognome, password);
	}
	
	/**
	 * Metodo per salvare un nuovo {@link Fruitore} nel database apposito
	 * @param newFruitore nuovo {@link Fruitore} da aggiungere
	 * @param nome nome del nuovo {@link Fruitore} 
	 * @param cognome cognome del nuovo {@link Fruitore}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void saveFruiore(Fruitore newFruitore, String nome, String cognome, String password) throws ErroreDatabaseNotWorking {
		this.fruitoreDB.addNewFruitore(newFruitore, nome, cognome, password);
	}
	
	/**
	 * Metodo per salvare una nuova {@link Gerarchia} nel database apposito
	 * @param newGerarchia nuova {@link Gerarchia} da aggiungere
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void saveGerarchia(Gerarchia newGerarchia) throws ErroreDatabaseNotWorking {
		//INIZIO CON I VALORI BASE
		this.gerarchiaDB.addGerarchia(newGerarchia);
		
		//INSERIMENTO FIGLI ([:IS_PARENT])
		this.gerarchiaDB.addParentelaToFigli(newGerarchia);
	}
	
	/**
	 * Metodo per salvare i {@link FattoreDiConversione} nel database apposito
	 * @param gerStart la {@link Gerarchia} a cui aggiungere i {@link FattoreDiConversione}
	 * @param fDCs Lista di {@link FattoreDiConversione} da aggiungere
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void saveFattoriConversione(Gerarchia gerStart, List<FattoreDiConversione> fDCs) throws ErroreDatabaseNotWorking {
		this.gerarchiaDB.saveFattoriConversione(gerStart, fDCs);
	}
	
	/**
	 * Metodo per salvare un nuovo {@link InsiemeChiuso} nel database apposito
	 * @param newCycle nuovo {@link InsiemeChiuso} da aggiungere
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void saveInsiemeChiuso(InsiemeChiuso newCycle) throws ErroreDatabaseNotWorking, IOException {
		this.insiemiChiusiDB.addNewInsiemeChiuso(newCycle);
		for(UUID p : newCycle.getCycleIds()) {
			this.saveLogProposta(this.retrievePropostabyId(p));
		}
	}
	
	/**
	 * Metodo per salvare una nuova {@link Proposta} nel database apposito
	 * @param newComprensorio nuova {@link Proposta} da aggiungere
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void saveProposta(Proposta prop) throws ErroreDatabaseNotWorking, IOException {
		this.propostaDB.addNewProposta(prop);
		this.saveLogProposta(prop);
	}
	
	/**
	 * Metodo per salvare nel log il cambio di una {@link Proposta} nel database apposito
	 * @param prop {@link Proposta} con stato cambiato
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void saveLogProposta(Proposta prop) throws IOException, ErroreDatabaseNotWorking {
		FakeProposta f = new FakeProposta(prop.getId(),
				this.retrieveGerarchiaById(this.retrieveRadiceOfGerarchia(prop.getRichiesta())).getCategoria(),
				prop.getRichiesta().getCategoria(),
				this.retrieveGerarchiaById(this.retrieveRadiceOfGerarchia(prop.getOfferta())).getCategoria(), 
				prop.getOfferta().getCategoria(), 
				prop.getDurataRichiesta(), 
				prop.getDurataOfferta(), 
				prop.getStato());
		this.logProposteJson.save(f);
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////  CONDITION CHECK     //////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per controllare la presenza o meno di un comune nel database apposito
	 * @param comune String da controllare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public boolean isComuneInArrayComprensori(String comune) throws ErroreDatabaseNotWorking {
		boolean risultato = this.comprensorioDB.isComuneInArrayComprensori(comune);
		return risultato;
	}

	/**
	 * Metodo per controllare la presenza o meno di una {@link Proposta} nel database apposito dato l'id del proprietario
	 * @param idUser UUID da controllare
	 * @return Lista di ID delle Proposte il cui proprietario ha l'id inserito
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<UUID> ottieniIdProposteDaIdUtente(UUID idUser) throws ErroreDatabaseNotWorking {
		List<UUID> risultato = this.propostaDB.ottieniIdProposteDaIdUtente(idUser);
		return risultato;
	}

	/**
	 * Metodo per controllare la presenza o meno di una {@link InsiemeChiuso} nel database apposito dato l'id del proprietario
	 * @param idUser UUID da controllare
	 * @return Lista di ID delle Proposte il cui proprietario ha l'id inserito
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<InsiemeChiuso> ottieniCicliChiusiDaIdConfiguratore(UUID idConfiguratore) throws ErroreDatabaseNotWorking {
		return this.insiemiChiusiDB.ottieniCicliChiusiDaIdConfiguratore(idConfiguratore);
	}

	/**
	 * Metodo per controllare la presenza o meno di un {@link Fruitore} nel database apposito
	 * date le sue credenziali
	 * @param name username del {@link Fruitore}
	 * @param password password del {@link Fruitore}
	 * @return il {@link Fruitore} trovato
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public Fruitore isFruitoreSavedInDatabase(String name, String password) throws ErroreDatabaseNotWorking {
		return this.fruitoreDB.isFruitoreSavedInDatabase(name, password);
	}

	/**
	 * Metodo per controllare la presenza o meno di un {@link Configuratore} nel database apposito
	 * date le sue credenziali
	 * @param name username del {@link Configuratore}
	 * @param password password del {@link Configuratore}
	 * @return il {@link Configuratore} trovato
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public Configuratore isConfiguratoreSavedInDatabase(String name, String password) throws ErroreDatabaseNotWorking {
		return this.configuratoreDB.isConfiguratoreSavedInDatabase(name, password);
	}
	
	/**
	 * Metodo per controllare la presenza o meno di un username del {@link Fruitore} nel database apposito
	 * @param name username da controllare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public boolean isFruitoreNameAlreadyTaken(String name) throws ErroreDatabaseNotWorking {
		return this.fruitoreDB.isFruitoreNameAlreadyTaken(name);
	}
	
	/**
	 * Metodo per controllare la presenza o meno di una email del {@link Fruitore} nel database apposito
	 * @param email email da controllare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public boolean isFruitoreEmailAlreadyTaken(String email) throws ErroreDatabaseNotWorking {
		return this.fruitoreDB.isEmailAlreadyTaken(email);
	}
	
	/**
	 * Metodo per controllare la presenza o meno di un username del {@link Configuratore} nel database apposito
	 * @param name username da controllare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public boolean isConfiguratoreNameAlreadyTaken(String name) throws ErroreDatabaseNotWorking {
		return this.configuratoreDB.isConfiguratoreNameAlreadyTaken(name);
	}

	/**
	 * Metodo per cambiare lo {@link Stato} di una {@link Proposta} nel database apposito
	 * (con annesso salvataggio del Log)
	 * @param prop {@link Proposta} da modificare
	 * @param nuovoStato nuovo {@link Stato}
	 * @throws ErroreDatabaseNotWorking
	 * @throws IOException
	 * @since TESI DATABASE
	 */
	public void modificaStatoProposta(Proposta prop, Stato nuovoStato) throws ErroreDatabaseNotWorking, IOException {
		this.propostaDB.modificaStatoProposta(prop, nuovoStato);
		this.saveLogProposta(prop);
	}

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////   RETRIEVE-BY-X     //////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per ottenere un {@link Comprensorio} nel database apposito dato il suo Id
	 * @param id id del {@link Comprensorio}
	 * @return il {@link Comprensorio} trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public Comprensorio retrieveComprensorioById(UUID id) throws ErroreDatabaseNotWorking {
		return this.comprensorioDB.retrieveComprensorioById(id);
	}

	/**
	 * Metodo per ottenere un {@link Comprensorio} nel database apposito dato il suo Id
	 * @param id id del {@link Fruitore}
	 * @return il {@link Fruitore} trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public Fruitore retrieveFruitoreById(UUID id) throws ErroreDatabaseNotWorking {
		return this.fruitoreDB.retrieveFruitoreById(id);
	}
	
	/**
	 * Metodo per ottenere un {@link Configuratore} nel database apposito dato il suo Id
	 * @param id id del {@link Configuratore}
	 * @return il {@link Configuratore} trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public Configuratore retrieveConfiguratoreById(UUID id) throws ErroreDatabaseNotWorking {
		return this.configuratoreDB.retrieveConfiguratoreById(id);
	}

	/**
	 * Metodo per ottenere un {@link Comprensorio} nel database apposito dato il suo Id
	 * @param id id del {@link Comprensorio}
	 * @return il {@link Comprensorio} trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public UUID retrieveConfiguratoreIdByComprensorioId(UUID id) throws ErroreDatabaseNotWorking {
		return this.configuratoreDB.retrieveConfiguratoreIdByComprensorioId(id);
	}

	/**
	 * Metodo per ottenere un {@link FattoreDiConversione} nel database apposito date le due {@link Gerarchia}
	 * @param gerStart {@link Gerarchia} di partenza del {@link FattoreDiConversione}
	 * @param gerEnd {@link Gerarchia} bersaglio del {@link FattoreDiConversione}
	 * @return il {@link FattoreDiConversione} trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public FattoreDiConversione retrieveFattoreConversione(Gerarchia gerStart, Gerarchia gerEnd) throws ErroreDatabaseNotWorking {
		return this.gerarchiaDB.retrieveFattoreConversione(gerStart.getId(), gerEnd.getId());
	}

	/**
	 * Metodo per ottenere tutti i {@link FattoreDiConversione} nel database apposito data la {@link Gerarchia} di partenza
	 * @param foglia {@link Gerarchia} di partenza
	 * @return Lisat di {@link FattoreDiConversione}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<FattoreDiConversione> retrieveAllFdcsFromFoglia(Gerarchia foglia) throws ErroreDatabaseNotWorking {
		return this.gerarchiaDB.retrieveAllFdcsFromFoglia(foglia.getId());
	}
	
	/**
	 * Metodo per ottenere una {@link Gerarchia} nel database apposito dato il suo Id
	 * @param id id della {@link Gerarchia}
	 * @return la {@link Gerarchia} trovata
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public Gerarchia retrieveGerarchiaById(UUID id) throws ErroreDatabaseNotWorking {
		return this.gerarchiaDB.retrieveGerarchiaById(id);
	}

	/**
	 * Metodo per ottenere una {@link Proposta} nel database apposito dato il suo Id
	 * @param id id della {@link Proposta}
	 * @return la {@link Proposta} trovata
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public Proposta retrievePropostabyId(UUID id) throws ErroreDatabaseNotWorking {
		return this.propostaDB.retrievePropostabyId(id);
	}

	/**
	 * Metodo per ottenere un {@link InsiemeChiuso} nel database apposito dato il suo Id
	 * @param id id del {@link InsiemeChiuso}
	 * @return il {@link InsiemeChiuso} trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public InsiemeChiuso retrieveCicloDaChiudereById(UUID id) throws ErroreDatabaseNotWorking {
		return this.insiemiChiusiDB.retrieveCicloDaChiudereById(id);
	}

	/**
	 * Metodo per ottenere l'id di una {@link Gerarchia} nel database apposito data una {@link Gerarchia} figlio
	 * @param ger {@link Gerarchia} figlio della {@link Gerarchia} radice attesa
	 * @return la {@link Gerarchia} radice
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public UUID retrieveRadiceOfGerarchia(Gerarchia ger) throws ErroreDatabaseNotWorking {
		return this.gerarchiaDB.retrieveRadiceOfGerarchia(ger.getId());
	}

	/**
	 * Metodo per ottenere una lista di id di {@link Proposta} nel database apposito data una {@link Gerarchia} coinvolta 
	 * @param foglia {@link Gerarchia} coinvolta nelle {@link Proposta} cercate
	 * @return lista di id di {@link Proposta}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<UUID> ottieniProposteDiFoglia(Gerarchia foglia) throws ErroreDatabaseNotWorking {
		return this.propostaDB.ottieniProposteDiFoglia(foglia.getId());
	}
	
	/**
	 * Metodo per pulire il database da eventuali {@link Gerarchia} incomplete dei {@link FattoreDiConversione}
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI DATABASE
	 */
	public void cleanGerarchiaDatabaseFromUncompletedGerarchias() throws ErroreDatabaseNotWorking {
		this.gerarchiaDB.cleanGerarchiaDatabaseFromUncompletedGerarchias();
	}
}
