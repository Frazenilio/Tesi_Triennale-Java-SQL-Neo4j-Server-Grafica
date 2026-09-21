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
import strutture.Proposta;
import utility.NomiDiEPerDatabase;
import utility.Stato;

/**
 * Sotto classe per la gestione del database di {@link Proposta}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class PropostaDatabaseConnection {

	private Connection connectionToDB;

	public PropostaDatabaseConnection(Connection connectionToDB) {
		super();
		this.connectionToDB = connectionToDB;
	}
	
	/**
	 * Metodo per ottenere un array di tutte le {@link Proposta} salvate nel database
	 * @return array con {@link Proposta}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<Proposta> requestArrayProposta() throws ErroreDatabaseNotWorking {
		List<Proposta> lista = new ArrayList<Proposta>();
		
		try {
            Statement stmt = this.connectionToDB.createStatement();

            String sql = "SELECT "
            		+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal() + " AS prop_id, "
            		+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_RICHIESTAID.getVal() + " AS prop_uuid_richiesta, "
            		+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_OFFERTAID.getVal() + " AS prop_uuid_offerta, "
            		+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal() + " AS fruit_id, "
            		+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_DURATAOFFERTA.getVal() + " AS prop_durata_offerta, "
            		+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_DURATARICHIESTA.getVal() + " AS prop_durata_richiesta,"
            		+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_STATO.getVal() + " AS prop_stato, "
            		+ "comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal() + " AS comprensorio_id "
            		
            		+ "FROM "
            		+ NomiDiEPerDatabase.PROPOSTA_DB_NOMEDB.getVal() + " AS prop "
            		+ "JOIN "
            		+ NomiDiEPerDatabase.FRUITORI_DB_NOMEDB.getVal() + " AS fruit "
            		+ "ON "
            		+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_PROPRIETARIOID.getVal()
            		+ " = fruit." + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal()
            		
            		+ " JOIN "
            		+ NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal() + " AS comp "
            		+ "ON "
            		+ "comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal()
            		+ " = fruit." + NomiDiEPerDatabase.FRUITORI_DB_COMPRENSORIOID.getVal();
            
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
            	UUID id = UUID.fromString(rs.getString("prop_id"));
            	UUID uuidRichiesta = UUID.fromString(rs.getString("prop_uuid_richiesta"));
            	UUID uuidOfferta = UUID.fromString(rs.getString("prop_uuid_offerta"));
            	UUID fruitId = UUID.fromString(rs.getString("fruit_id"));
            	int durataOfferta = rs.getInt("prop_durata_offerta");
            	int durataRichiesta = rs.getInt("prop_durata_richiesta");
            	Stato stato = Stato.fromString(rs.getString("prop_stato"));
            	UUID compId = UUID.fromString(rs.getString("comprensorio_id"));
            	
            	Proposta prop = new Proposta(fruitId, uuidRichiesta, uuidOfferta,
            			durataRichiesta, durataOfferta, stato, compId);
            	prop.setId(id);
            	
            	lista.add(prop);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        	throw new ErroreDatabaseNotWorking();
        }
		return lista;
	}
	
	/**
	 * Metodo per aggiungere una nuova {@link Proposta} al database
	 * @param prop nuova {@link Proposta} da salvare
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void addNewProposta(Proposta prop) throws ErroreDatabaseNotWorking {
		String id = prop.getId().toString();
		String uuidRichiesta = prop.getRichiestaId().toString();
		String uuidOfferta = prop.getOffertaId().toString();
		String proprietarioId = prop.getProprietarioId().toString();
		int durataOfferta = prop.getDurataOfferta();
		int durataRichiesta = prop.getDurataRichiesta();
		String stato = prop.getStato().toString();
		
		String query = "INSERT INTO " + NomiDiEPerDatabase.PROPOSTA_DB_NOMEDB.getVal()
				+ " (" + NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal() + ", " + NomiDiEPerDatabase.PROPOSTA_DB_RICHIESTAID.getVal()
				+ ", " + NomiDiEPerDatabase.PROPOSTA_DB_OFFERTAID.getVal() + ", " + NomiDiEPerDatabase.PROPOSTA_DB_PROPRIETARIOID.getVal()
				+ ", " + NomiDiEPerDatabase.PROPOSTA_DB_DURATAOFFERTA.getVal() + ", " + NomiDiEPerDatabase.PROPOSTA_DB_DURATARICHIESTA.getVal()
				+ ", " + NomiDiEPerDatabase.PROPOSTA_DB_STATO.getVal() + ") "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, id);
			pstmt.setString(2, uuidRichiesta);
			pstmt.setString(3, uuidOfferta);
			pstmt.setString(4, proprietarioId);
			pstmt.setInt(5, durataOfferta);
			pstmt.setInt(6, durataRichiesta);
			pstmt.setString(7, stato);
			
			pstmt.executeUpdate();

			pstmt.close();
        } catch (SQLException e) {
        	e.printStackTrace();
			throw new ErroreDatabaseNotWorking();
		}
	}

	/**
	 * Metodo per ottenere l'id di una {@link Proposta} da un certo id di un utente
	 * @param idUser id dell'utente associato alla {@link Proposta} ricercata
	 * @return {@link Proposta} trovata
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato nulla
	 * @since TESI DATABASE
	 */
	public List<UUID> ottieniIdProposteDaIdUtente(UUID idUser) throws ErroreDatabaseNotWorking {
		List<UUID> listaProposte = new ArrayList<UUID>();
		String query = "SELECT " + NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal()
				+ "		FROM " + NomiDiEPerDatabase.PROPOSTA_DB_NOMEDB.getVal()
				+ "		WHERE " + NomiDiEPerDatabase.PROPOSTA_DB_PROPRIETARIOID.getVal() + " = ?";
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, idUser.toString());

			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					listaProposte.add(UUID.fromString(rs.getString(NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal())));
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException e) {
        	e.printStackTrace();
			throw new ErroreDatabaseNotWorking();
		}
		return listaProposte;
	}

	/**
	 * Metodo per modificare lo {@link Stato} di una {@link Proposta} nel Database
	 * @param prop {@link Proposta} da modificare
	 * @param nuovoStato nuovo {@link Stato}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void modificaStatoProposta(Proposta prop, Stato nuovoStato) throws ErroreDatabaseNotWorking {
		String query = "UPDATE " + NomiDiEPerDatabase.PROPOSTA_DB_NOMEDB.getVal()
				+ " SET " + NomiDiEPerDatabase.PROPOSTA_DB_STATO.getVal() + " = ? "
				+ "WHERE " + NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal() + " = ?;";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, nuovoStato.toString());
			pstmt.setString(2, prop.getId().toString());		

			pstmt.executeUpdate();

			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
		
	}

	/**
	 * Metodo per ottenere una {@link Proposta} dato il suo id
	 * @param id id della {@link Proposta} ricercata
	 * @return {@link Proposta} trovata
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public Proposta retrievePropostabyId(UUID id) throws ErroreDatabaseNotWorking {
		String query = "SELECT "
				+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal() + " AS prop_id, "
				+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_RICHIESTAID.getVal() + " AS prop_uuid_richiesta, "
				+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_OFFERTAID.getVal() + " AS prop_uuid_offerta, "
				+ "fruit." + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal() + " AS fruit_id, "
				+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_DURATAOFFERTA.getVal() + " AS prop_durata_offerta, "
				+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_DURATARICHIESTA.getVal() + " AS prop_durata_richiesta,"
				+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_STATO.getVal() + " AS prop_stato, "
				+ "comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal() + " AS comprensorio_id "
				+ "FROM "
				+ NomiDiEPerDatabase.PROPOSTA_DB_NOMEDB.getVal() + " AS prop "
				+ "JOIN "
				+ NomiDiEPerDatabase.FRUITORI_DB_NOMEDB.getVal() + " AS fruit "
				+ "ON "
				+ "prop." + NomiDiEPerDatabase.PROPOSTA_DB_PROPRIETARIOID.getVal()
				+ " = fruit." + NomiDiEPerDatabase.FRUITORI_DB_ID.getVal()
				+ " JOIN "
				+ NomiDiEPerDatabase.COMPRENSORI_DB_NOMEDB.getVal() + " AS comp "
				+ "ON "
				+ "comp." + NomiDiEPerDatabase.COMPRENSORI_DB_ID.getVal()
				+ " = fruit." + NomiDiEPerDatabase.FRUITORI_DB_COMPRENSORIOID.getVal()
				+ " WHERE " + NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal() + " = ?";
		
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, id.toString());

			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					UUID propostaId = UUID.fromString(rs.getString("prop_id"));
					if(!propostaId.equals(id)) {
						throw new ErroreDatabaseNotWorking();
					}
	            	UUID uuidRichiesta = UUID.fromString(rs.getString("prop_uuid_richiesta"));
	            	UUID uuidOfferta = UUID.fromString(rs.getString("prop_uuid_offerta"));
	            	UUID fruitId = UUID.fromString(rs.getString("fruit_id"));
	            	int durataOfferta = rs.getInt("prop_durata_offerta");
	            	int durataRichiesta = rs.getInt("prop_durata_richiesta");
	            	Stato stato = Stato.fromString(rs.getString("prop_stato"));
	            	UUID compId = UUID.fromString(rs.getString("comprensorio_id"));
	            	
	            	Proposta prop = new Proposta(fruitId, uuidRichiesta, uuidOfferta,
	            			durataRichiesta, durataOfferta, stato, compId);
	            	prop.setId(propostaId);
	            	
	            	return prop;
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
		throw new ErroreDatabaseNotWorking();
	}

	public List<UUID> ottieniProposteDiFoglia(UUID id) throws ErroreDatabaseNotWorking {
		List<UUID> listaProposte = new ArrayList<UUID>();
		String query = "SELECT " + NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal()
						+ "	FROM " + NomiDiEPerDatabase.PROPOSTA_DB_NOMEDB.getVal()
						+ "	WHERE " + NomiDiEPerDatabase.PROPOSTA_DB_RICHIESTAID.getVal() + " = ? "
						+ "OR " + NomiDiEPerDatabase.PROPOSTA_DB_OFFERTAID.getVal() + " = ?";
		try (PreparedStatement pstmt = this.connectionToDB.prepareStatement(query)) {
			pstmt.setString(1, id.toString());
			pstmt.setString(2, id.toString());

			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					listaProposte.add(UUID.fromString(rs.getString(NomiDiEPerDatabase.PROPOSTA_DB_ID.getVal())));
				}
				rs.close();
			}
			pstmt.close();
        } catch (SQLException e) {
			throw new ErroreDatabaseNotWorking();
		}
		return listaProposte;
	}
}
