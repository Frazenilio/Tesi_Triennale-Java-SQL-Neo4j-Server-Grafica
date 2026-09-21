package gestioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import utente.Fruitore;
import utility.NomiDiEPerDatabase;

/**
 * Sotto classe per la gestione del Database dei {@link Fruitore}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class FruitoreDatabaseConnection {


	private Connection connectionToDB;

	public FruitoreDatabaseConnection(Connection connectionToDB) {
		super();
		this.connectionToDB = connectionToDB;
	}
	
	/**
	 * Metodo per ottenere un array con tutti i {@link Fruitore} nel Database
	 * @return Lista di {@link Fruitore} 
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Fruitore> requestArrayFruitore() throws ErroreDatabaseNotWorking {
		List<Fruitore> lista = new ArrayList<Fruitore>();
		try {
            Statement stmt = this.connectionToDB.createStatement();

            String query = "SELECT "
            		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_COMPRENSORIOID.getVal() + " AS fruit_comprensorio, "
            		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_USERNAME.getVal() + " AS fruit_username, "
            		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_EMAIL.getVal() + " AS fruit_email, "
            		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal() + " AS fruit_id "
            		+ "FROM "
            		+ NomiDiEPerDatabase.FRUITORI_DB_NOMEDB.getVal() + " AS fruit;";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
            	
            	String usernameFruit = rs.getString("fruit_username");
                String emailFruit = rs.getString("fruit_email");
                UUID comprensorioId = UUID.fromString(rs.getString("fruit_comprensorio"));
                
                Fruitore fruit = new Fruitore(usernameFruit, emailFruit, comprensorioId);
                
                fruit.setId(UUID.fromString(rs.getString("fruit_id")));
                
                lista.add(fruit);
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new ErroreDatabaseNotWorking();
        }
		return lista;
	}
	
	/**
	 * Metodo per salvare un nuovo {@link Fruitore} nel database
	 * @param fruit nuovo {@link Fruitore} da salvare
	 * @param nome Nome del {@link Fruitore}
	 * @param cognome Cognome del {@link Fruitore}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void addNewFruitore(Fruitore fruit, String nome, String cognome, String password) throws ErroreDatabaseNotWorking {
		String username = fruit.getUsername();
		String email = fruit.getEmail();
		String compId = fruit.getComprensorioId().toString();
		String id = fruit.getId().toString();
		
		String query = "INSERT INTO " + NomiDiEPerDatabase.FRUITORI_DB_NOMEDB.getVal() + 
				" (" + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal() + ", " + NomiDiEPerDatabase.FRUITORI_DB_USERNAME.getVal() + 
				", " + NomiDiEPerDatabase.FRUITORI_DB_PASSWORD.getVal() + 
				", " + NomiDiEPerDatabase.FRUITORI_DB_EMAIL.getVal() + ", " + NomiDiEPerDatabase.FRUITORI_DB_COMPRENSORIOID.getVal() +
				", " + NomiDiEPerDatabase.FRUITORI_DB_NOME.getVal() + ", " + NomiDiEPerDatabase.FRUITORI_DB_COGNOME.getVal() + ") "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, id);
			pstmt.setString(2, username);
			pstmt.setString(3, password);
			pstmt.setString(4, email);
			pstmt.setString(5, compId);
			pstmt.setString(6, nome);
			pstmt.setString(7, cognome);
			
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
	}

	/**
	 * Metodo per controllare la presenza di un {@link Fruitore} nel database
	 * @param name username del {@link Fruitore}
	 * @param password password del {@link Fruitore}
	 * @return {@link Fruitore} trovato con le credenziali passate
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public Fruitore isFruitoreSavedInDatabase(String name, String password) throws ErroreDatabaseNotWorking {

		String query = "SELECT "
        		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_COMPRENSORIOID.getVal() + " AS fruit_comprensorio, "
        		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_USERNAME.getVal() + " AS fruit_username, "
        		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal() + " AS fruit_id, "
        		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_EMAIL.getVal() + " AS fruit_email "
        		+ "FROM "
        		+ NomiDiEPerDatabase.FRUITORI_DB_NOMEDB.getVal() + " AS fruit "
        		+ " WHERE fruit." + NomiDiEPerDatabase.FRUITORI_DB_USERNAME.getVal()
        		+ " = ? AND fruit." + NomiDiEPerDatabase.FRUITORI_DB_PASSWORD.getVal() + " = ?";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.setString(2, password);
						
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					String usernameFruit = rs.getString("fruit_username");
	                String emailFruit = rs.getString("fruit_email");
	                UUID comprensorioId = UUID.fromString(rs.getString("fruit_comprensorio"));
	                
	                Fruitore fruit = new Fruitore(usernameFruit, emailFruit, comprensorioId);
	                
	                fruit.setId(UUID.fromString(rs.getString("fruit_id")));
	                
	                return fruit;
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
	 * Metodo per controllare la presenza di un username del {@link Fruitore} nel database
	 * @param name username da cercare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public boolean isFruitoreNameAlreadyTaken(String name) throws ErroreDatabaseNotWorking {
		String query = "SELECT EXISTS ( "
				+ "SELECT 1 "
				+ "FROM " + NomiDiEPerDatabase.FRUITORI_DB_NOMEDB.getVal()
				+ " WHERE " + NomiDiEPerDatabase.FRUITORI_DB_USERNAME.getVal() + " = ? "
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
	 * Metodo per controllare la presenza di una mail del {@link Fruitore} nel database
	 * @param email mail da cercare
	 * @return true se e' stato trovato un doppione, false altrimenti
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public boolean isEmailAlreadyTaken(String email) throws ErroreDatabaseNotWorking {
		String query = "SELECT EXISTS ( "
				+ "SELECT 1 "
				+ "FROM " + NomiDiEPerDatabase.FRUITORI_DB_NOMEDB.getVal()
				+ " WHERE " + NomiDiEPerDatabase.FRUITORI_DB_EMAIL.getVal() + " = ? "
				+ ") AS result";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, email);
			
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
	 * Metodo per controllare la presenza di un {@link Fruitore} nel database dato il suo id
	 * @param id id del {@link Fruitore} ricercato
	 * @return {@link Fruitore} trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato il fruitore
	 * @since TESI DATABASE
	 */
	public Fruitore retrieveFruitoreById(UUID id) throws ErroreDatabaseNotWorking {
		String query = "SELECT "
        		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_COMPRENSORIOID.getVal() + " AS fruit_comprensorio, "
        		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_USERNAME.getVal() + " AS fruit_username, "
        		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal() + " AS fruit_id,"
        		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_EMAIL.getVal() + " AS fruit_email "
        		+ "FROM "
        		+ NomiDiEPerDatabase.FRUITORI_DB_NOMEDB.getVal() + " AS fruit "
        		+ " WHERE fruit." + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal() + " = ?";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, id.toString());
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					String usernameFruit = rs.getString("fruit_username");
	                String emailFruit = rs.getString("fruit_email");
	                UUID comprensorioId = UUID.fromString(rs.getString("fruit_comprensorio"));
	                UUID fruitId = UUID.fromString(rs.getString("fruit_id"));
	                if(!fruitId.equals(id)) {
	                	throw new ErroreDatabaseNotWorking(); 
	                }
	                Fruitore fruit = new Fruitore(usernameFruit, emailFruit, comprensorioId);
	                
	                fruit.setId(fruitId);
	                
	                return fruit;
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
