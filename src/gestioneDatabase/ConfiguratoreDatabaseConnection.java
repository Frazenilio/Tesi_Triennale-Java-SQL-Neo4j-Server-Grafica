package gestioneDatabase;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import strutture.Comprensorio;
import utente.Configuratore;
import utility.NomiDiEPerDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Sotto classe per gestire il Database contenente tutti i {@link Configuratore}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class ConfiguratoreDatabaseConnection {

	
	private Connection connectionToDB;

	public ConfiguratoreDatabaseConnection(Connection connectionToDB) {
		super();
		this.connectionToDB = connectionToDB;
	}
	
	/**
	 * Metodo per ottenere un array di tutti i {@link Configuratore} salvati nel Database
	 * @return array con tutti i {@link Configuratore}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Configuratore> requestArrayConfiguratore() throws ErroreDatabaseNotWorking {
		List<Configuratore> lista = new ArrayList<Configuratore>();
		
		try {
            Statement stmt = this.connectionToDB.createStatement();

            String query = "SELECT " + NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal() + 
            		", " + NomiDiEPerDatabase.CONFIGURATORI_DB_USERNAME.getVal() + 
            		" FROM " + NomiDiEPerDatabase.CONFIGURATORI_DB_NOMEDB.getVal();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
            	Configuratore conf = new Configuratore(rs.getString(NomiDiEPerDatabase.CONFIGURATORI_DB_USERNAME.getVal()));
            	conf.setId(UUID.fromString(rs.getString(NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal())));
            	lista.add(conf);
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new ErroreDatabaseNotWorking();
        }
		return lista;
	}
	
	/**
	 * Metodo per aggiungere un nuovo {@link Configuratore} al database
	 * @param conf configuratore da salvare
	 * @param nome nome del Configuratore
	 * @param cognome Cognome del Configuratore
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void addNewConfiguratore(Configuratore conf, String nome, String cognome, String password) throws ErroreDatabaseNotWorking {
		String username = conf.getUsername();
		String uuid = conf.getId().toString();
		
		String query = "INSERT INTO " + NomiDiEPerDatabase.CONFIGURATORI_DB_NOMEDB.getVal() + 
				" (" + NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal() + ", " + NomiDiEPerDatabase.CONFIGURATORI_DB_USERNAME.getVal() + 
				", " + NomiDiEPerDatabase.CONFIGURATORI_DB_PASSWORD.getVal() + 
				", " + NomiDiEPerDatabase.CONFIGURATORI_DB_NOME.getVal() + 
				", " + NomiDiEPerDatabase.CONFIGURATORI_DB_COGNOME.getVal() + ") "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, uuid);
			pstmt.setString(2, username);
			pstmt.setString(3, password);
			pstmt.setString(4, nome);
			pstmt.setString(5, cognome);
			
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ErroreDatabaseNotWorking();
		}
	}

	/**
	 * Metodo per controllare se un {@link Configuratore} e' presente nel database
	 * @param name username del {@link Configuratore}
	 * @param password password del {@link Configuratore}
	 * @return il Configuratore trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato alcun Database
	 * @since TESI DATABASE
	 */
	public Configuratore isConfiguratoreSavedInDatabase(String name, String password) throws ErroreDatabaseNotWorking {

		String query = "SELECT " + NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal() + 
				", " + NomiDiEPerDatabase.CONFIGURATORI_DB_USERNAME.getVal() + 
				" FROM " + NomiDiEPerDatabase.CONFIGURATORI_DB_NOMEDB.getVal() + 
				" WHERE " + NomiDiEPerDatabase.CONFIGURATORI_DB_USERNAME.getVal() + " = ? AND "
						+ NomiDiEPerDatabase.CONFIGURATORI_DB_PASSWORD.getVal() + " = ?";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Configuratore conf = new Configuratore(rs.getString(NomiDiEPerDatabase.CONFIGURATORI_DB_USERNAME.getVal()));
	            	conf.setId(UUID.fromString(rs.getString(NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal())));
	            	return conf;
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
		
		throw new ErroreDatabaseNotWorking();
	}
	
	/**
	 * Metodo per controllare se un username e' gia' presente in questo database
	 * @param name username da controllare
	 * @return true se viene trovato un duplicato, false altrimenti
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public boolean isConfiguratoreNameAlreadyTaken(String name) throws ErroreDatabaseNotWorking {
		String query = "SELECT EXISTS ( "
				+ "SELECT 1 "
				+ "FROM " + NomiDiEPerDatabase.CONFIGURATORI_DB_NOMEDB.getVal()
				+ " WHERE " + NomiDiEPerDatabase.CONFIGURATORI_DB_USERNAME.getVal() + " = ? "
				+ ") AS result";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, name);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return rs.getBoolean("result");
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
		
		throw new ErroreDatabaseNotWorking();
	}

	/**
	 * Metodo per ottenere un {@link Configuratore} attraverso il suo id
	 * @param id id del {@link Configuratore} ricercato
	 * @return il {@link Configuratore} ritrovato
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public Configuratore retrieveConfiguratoreById(UUID id) throws ErroreDatabaseNotWorking {
		String query = "SELECT " + NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal() + 
				", " + NomiDiEPerDatabase.CONFIGURATORI_DB_USERNAME.getVal() + 
				" FROM " + NomiDiEPerDatabase.CONFIGURATORI_DB_NOMEDB.getVal() + 
				" WHERE " + NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal() + " = ?";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, id.toString());
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Configuratore conf = new Configuratore(rs.getString("username"));
	            	UUID confId = UUID.fromString(rs.getString("id"));
	            	if(!confId.equals(id)) {
	            		throw new ErroreDatabaseNotWorking();
	            	}
					conf.setId(confId);
	            	return conf;
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
		
		throw new ErroreDatabaseNotWorking();
	}

	/**
	 * Metodo per ottenere l'id di un certo {@link Configuratore} dato l'id di un {@link Comprensorio}
	 * @param id id del {@link Comprensorio} associato al {@link Configuratore}
	 * @return id del {@link Configuratore} trovato
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public UUID retrieveConfiguratoreIdByComprensorioId(UUID id) throws ErroreDatabaseNotWorking {
		String query = "SELECT conf." + NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal() + " AS conf_id "
				+ "FROM " + NomiDiEPerDatabase.CONFIGURATORI_DB_NOMEDB.getVal() + " AS conf "
				+ "JOIN " + NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal() + " AS comp "
				+ "ON comp." + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal() + " = conf."
				+ NomiDiEPerDatabase.CONFIGURATORI_DB_ID.getVal()
				+ " WHERE comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal() + " = ?";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, id.toString());
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
	            	UUID confId = UUID.fromString(rs.getString("conf_id"));
	            	return confId;
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
		throw new ErroreDatabaseNotWorking();
	}
}
