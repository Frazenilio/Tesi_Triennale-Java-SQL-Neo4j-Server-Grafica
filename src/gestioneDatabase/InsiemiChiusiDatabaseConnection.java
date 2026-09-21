package gestioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import errori.ErroreDatabaseNotWorking;
import strutture.InsiemeChiuso;
import utente.Configuratore;
import utility.NomiDiEPerDatabase;

/**
 * Sotto classe per la gestione del Database dei {@link InsiemeChiuso}
 * @author Francesco Lozio
 * @since TESI DATABASE
 */
public class InsiemiChiusiDatabaseConnection {

	private Connection connectionToDB;
	private Gson gson;

	public InsiemiChiusiDatabaseConnection(Connection connectionToDB) {
		super();
		this.connectionToDB = connectionToDB;
		this.gson = new Gson();
	}
	
	/**
	 * Metodo per ottenere un array con tutti i {@link InsiemeChiuso} salvati nel database
	 * @return array con i {@link InsiemeChiuso}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<InsiemeChiuso> requestArrayInsiemeChiuso() throws ErroreDatabaseNotWorking {
		List<InsiemeChiuso> lista = new ArrayList<InsiemeChiuso>();
		try {

            Statement stmt = this.connectionToDB.createStatement();

            String query = "SELECT ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_ID.getVal() + " AS ins_id "
            		+ ", ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_LISTAPROPOSTE.getVal() + " AS ins_lista "
            		+ ", ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_COMPRENSORIOID.getVal() + " AS ins_comp "
            		+ ", comp." + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal() + " AS ins_prop "
            		+ " FROM " + NomiDiEPerDatabase.INSIEMICHIUSI_DB_NOMEDB.getVal() + " AS ins "
            		+ " JOIN " + NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal() + " AS comp"
            		+ " ON ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_COMPRENSORIOID.getVal()
            		+ " = comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal();
            ResultSet rs = stmt.executeQuery(query);

            ObjectMapper objectMapper = new ObjectMapper();
            
            while (rs.next()) { 
            	UUID id = UUID.fromString(rs.getString("ins_id"));
            	UUID propId = UUID.fromString(rs.getString("ins_prop"));
            	String listaProposteJson = rs.getString("ins_lista");
            	UUID compId = UUID.fromString(rs.getString("ins_comp"));
            	
            	List<UUID> proposteId = objectMapper.readValue(listaProposteJson,
            			new TypeReference<List<UUID>>() {});
            	
            	InsiemeChiuso ciclo = new InsiemeChiuso(proposteId, propId, compId);
            	ciclo.setId(id);
            	
            	lista.add(ciclo);
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new ErroreDatabaseNotWorking();
        }
		return lista;
	}
	
	/**
	 * Metodo per aggiungere un nuovo {@link InsiemeChiuso} nel database
	 * @param cycle nuovo {@link InsiemeChiuso} 
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void addNewInsiemeChiuso(InsiemeChiuso cycle) throws ErroreDatabaseNotWorking {
		String listaProposteGson = this.gson.toJson(cycle.getListIdsAsStrings());
		String uuid = cycle.getId().toString();
		String compId = cycle.getComprensorioId().toString();
		
		String query = "INSERT INTO " + NomiDiEPerDatabase.INSIEMICHIUSI_DB_NOMEDB.getVal()
				+ " ( " + NomiDiEPerDatabase.INSIEMICHIUSI_DB_ID.getVal()
				+ ", " + NomiDiEPerDatabase.INSIEMICHIUSI_DB_LISTAPROPOSTE.getVal()
				+ ", " + NomiDiEPerDatabase.INSIEMICHIUSI_DB_COMPRENSORIOID.getVal() + ")"
				+ " VALUES (?, ?, ?)";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, uuid);
			pstmt.setString(2, listaProposteGson);
			pstmt.setString(3, compId);
			
			pstmt.executeUpdate();

			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
	}

	/**
	 * Metodo per ottenere i {@link InsiemeChiuso} associati ad uno specifico {@link Configuratore} ottenuto con il suo id
	 * @param idConfiguratore id del {@link Configuratore} associato 
	 * @return Lista di {@link InsiemeChiuso} di cui il {@link Configuratore} passato e' proprietario
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<InsiemeChiuso> ottieniCicliChiusiDaIdConfiguratore(UUID idConfiguratore) throws ErroreDatabaseNotWorking {
		List<InsiemeChiuso> listaCicliChiusi = new ArrayList<InsiemeChiuso>();

		String query = "SELECT ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_ID.getVal() + " AS ins_id "
        		+ ", ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_LISTAPROPOSTE.getVal() + " AS ins_lista "
        		+ ", ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_COMPRENSORIOID.getVal() + " AS ins_comp "
        		+ ", comp." + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal() + " AS ins_prop "
        		+ " FROM " + NomiDiEPerDatabase.INSIEMICHIUSI_DB_NOMEDB.getVal() + " AS ins "
        		+ " JOIN " + NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal() + " AS comp"
        		+ " ON ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_COMPRENSORIOID.getVal()
        		+ " = comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal()
				+ " WHERE comp." + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal() + " = ?";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, idConfiguratore.toString());

			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					ObjectMapper objectMapper = new ObjectMapper();
					
					UUID id = UUID.fromString(rs.getString("ins_id"));
	            	UUID propId = UUID.fromString(rs.getString("ins_prop"));
	            	String listaProposteJson = rs.getString("ins_lista");
	            	UUID compId = UUID.fromString(rs.getString("ins_comp"));
	            	
	            	if(!propId.equals(idConfiguratore)) {
	            		throw new ErroreDatabaseNotWorking();
	            	}
	            	
	            	List<UUID> proposteId = objectMapper.readValue(listaProposteJson,
	            			new TypeReference<List<UUID>>() {});
	            	
					InsiemeChiuso cycle = new InsiemeChiuso(proposteId,
							propId, compId);
					cycle.setId(id);
					listaCicliChiusi.add(cycle);
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException | JsonProcessingException e) {
			throw new ErroreDatabaseNotWorking();
		}
		return listaCicliChiusi;
	}

	/**
	 * Metodo per ottenere un {@link InsiemeChiuso} dato il suo id
	 * @param id id del {@link InsiemeChiuso} ricercato
	 * @return {@link InsiemeChiuso} trovato
	 * @throws ErroreDatabaseNotWorking lanciata se non e' stato trovato niente
	 * @since TESI DATABASE
	 */
	public InsiemeChiuso retrieveCicloDaChiudereById(UUID id) throws ErroreDatabaseNotWorking {		
		
		String query = "SELECT ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_ID.getVal() + " AS ins_id "
        		+ ", ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_LISTAPROPOSTE.getVal() + " AS ins_lista "
        		+ ", ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_COMPRENSORIOID.getVal() + " AS ins_prop "
        		+ ", comp." + NomiDiEPerDatabase.COMPRENSORI_DB_CONFIGURATOREID.getVal() + " AS ins_prop "
        		+ " FROM " + NomiDiEPerDatabase.INSIEMICHIUSI_DB_NOMEDB.getVal() + " AS ins "
        		+ " JOIN " + NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal() + " AS comp"
        		+ " ON ins." + NomiDiEPerDatabase.INSIEMICHIUSI_DB_COMPRENSORIOID.getVal()
        		+ " = comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal()
        		+ " WHERE " + NomiDiEPerDatabase.INSIEMICHIUSI_DB_ID.getVal() + " = ?";
				
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, id.toString());

			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					ObjectMapper objectMapper = new ObjectMapper();
					
					UUID cycleId = UUID.fromString(rs.getString("ins_id"));
	            	UUID propId = UUID.fromString(rs.getString("ins_prop"));
	            	String listaProposteJson = rs.getString("ins_lista");
	            	UUID compId = UUID.fromString(rs.getString("ins_comp"));
	            	
	            	if(!cycleId.equals(id)) {
	            		throw new ErroreDatabaseNotWorking();
	            	}
	            	
	            	List<UUID> proposteId = objectMapper.readValue(listaProposteJson,
	            			new TypeReference<List<UUID>>() {});
	            	
					InsiemeChiuso cycle = new InsiemeChiuso(proposteId,
							propId, compId);
					cycle.setId(cycleId);
					
					return cycle;
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException | JsonProcessingException e) {
			throw new ErroreDatabaseNotWorking();
		}
		throw new ErroreDatabaseNotWorking();
	}
}
