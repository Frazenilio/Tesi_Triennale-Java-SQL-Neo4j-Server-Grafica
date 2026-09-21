package gestioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import errori.ErroreDatabaseNotWorking;
import strutture.Comprensorio;
import utility.NomiDiEPerDatabase;

/**
 * Sotto Classe per la gestione di query al database dei {@link Comprensorio}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class ComprensorioDatabaseConnection {

	private Connection connectionToDB;
	private Gson gson;

	public ComprensorioDatabaseConnection(Connection connectionToDB) {
		super();
		this.connectionToDB = connectionToDB;
		this.gson = new Gson();
	}
	
	/**
	 * Metodo per ottenere un array contenente tutti i {@link Comprensorio} nel database
	 * @return array con tutti i comprensori nel database
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Comprensorio> requestArrayComprensorio() throws ErroreDatabaseNotWorking {
		List<Comprensorio> lista = new ArrayList<Comprensorio>();
		
		try {
            Statement stmt = this.connectionToDB.createStatement();

            String query = "SELECT comp." + NomiDiEPerDatabase.COMPRENSORI_DB_LISTACOMUNI.getVal()
            		+ ", comp." + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal()
            		+ ", comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal()
            		+ " FROM " + NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal() + " AS comp;";
            ResultSet rs = stmt.executeQuery(query);

            ObjectMapper objectMapper = new ObjectMapper();
            
            while (rs.next()) {
            	
            	String listaComuniJson = rs.getString(NomiDiEPerDatabase.COMPRENSORI_DB_LISTACOMUNI.getVal());
            	UUID propId = UUID.fromString(rs.getString(NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal()));
                UUID id = UUID.fromString(rs.getString(NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal()));
                
            	List<String> listaComuni = objectMapper.readValue(listaComuniJson, new TypeReference<List<String>>() {});
            	
            	Comprensorio comp = new Comprensorio(propId, listaComuni);
            	comp.setId(id);
            	
            	lista.add(comp);  	 
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new ErroreDatabaseNotWorking();
        }
		return lista;
	}
	
	/**
	 * Metodo per salvare un nuovo {@link Comprensorio} nel Database
	 * @param comp comprensorio da salvare
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void addNewComprensorio(Comprensorio comp) throws ErroreDatabaseNotWorking {
		List<String> listaComuni = comp.getArrayComuni();
		String configuratoreId = comp.getProprietarioId().toString();
		String uuidComp = comp.getId().toString();
		
		String listaComuniJson = this.gson.toJson(listaComuni);
		
		String query = "INSERT INTO " + NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal()
				+ " (" + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal() + ", " + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal()
				+ ", " + NomiDiEPerDatabase.COMPRENSORI_DB_LISTACOMUNI.getVal() + ") VALUES (?, ?, ?)";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, uuidComp);
			pstmt.setString(2, configuratoreId);
			pstmt.setString(3, listaComuniJson);
			
			pstmt.executeUpdate();
			
			pstmt.close();
        } catch (SQLException e) {
        	throw new ErroreDatabaseNotWorking();
		}
	}

	/**
	 * Metodo per controllare che un certo comune non sia presente nel database
	 * @param comune comune da controllare
	 * @return true se viene trovato il duplicato, false altrimenti
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public boolean isComuneInArrayComprensori(String comune) throws ErroreDatabaseNotWorking {
		
		String query = "SELECT EXISTS ("
				+ "    SELECT 1 "
				+ "    FROM " + NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal()
				+ "    WHERE JSON_CONTAINS(" + NomiDiEPerDatabase.COMPRENSORI_DB_LISTACOMUNI.getVal() + ", ?, '$') "
				+ ") AS result;";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, "\"" + comune + "\"");
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return rs.getBoolean("result");
				}
			}
			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
		
		throw new ErroreDatabaseNotWorking();
	}

	/**
	 * Metodo per ottenere uno specifico {@link Comprensorio} dato il suo id
	 * @param id
	 * @return
	 * @throws ErroreDatabaseNotWorking
	 */
	public Comprensorio retrieveComprensorioById(UUID id) throws ErroreDatabaseNotWorking {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
            String query = "SELECT comp." + NomiDiEPerDatabase.COMPRENSORI_DB_LISTACOMUNI.getVal()
            		+ ", comp." + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal()
            		+ ", comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal()
            		+ " FROM "
            		+ NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal() + " AS comp "
            		+ " WHERE "
            		+ " comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal() + " = ?";
            try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
    			pstmt.setString(1, id.toString());
    			
    			try(ResultSet rs = pstmt.executeQuery()){
    				if(rs.next()) {
    					UUID propId = UUID.fromString(rs.getString("comp." + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal()));
    					UUID compId = UUID.fromString(rs.getString("comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal()));
    					String listaComuniJson = rs.getString("comp." + NomiDiEPerDatabase.COMPRENSORI_DB_LISTACOMUNI.getVal());
    					
    					if(!compId.equals(id)) {
    						throw new ErroreDatabaseNotWorking();
    					}
    					
    					List<String> listaComuni = 
    							objectMapper.readValue(listaComuniJson, new TypeReference<List<String>>() {});
    					
    					Comprensorio comp = new Comprensorio(propId, listaComuni);
    	            	comp.setId(compId);
    	            	
    	            	return comp;
    				}
    				rs.close();
    			}
                pstmt.close();
            } catch (SQLException e) {
    			throw new ErroreDatabaseNotWorking();
    		}
        } catch (Exception e) {
        	throw new ErroreDatabaseNotWorking();
        }
		throw new ErroreDatabaseNotWorking();
	}
}
