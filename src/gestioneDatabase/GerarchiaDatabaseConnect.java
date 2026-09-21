package gestioneDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.Node;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import errori.ErroreDatabaseNotWorking;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import utility.LabelsAndRelationshipsUsedForGerarchiaDB;
import utility.NomiDiEPerDatabase;
import utility.ResultsConverter;
import utility.Tupla;

import org.neo4j.driver.Record;

/**
 * Sotto classe per la gestione del database {@link Gerarchia}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class GerarchiaDatabaseConnect {
    
    private Driver driver;
    
    public GerarchiaDatabaseConnect(Driver driver) {
    	this.driver = driver;
    }
    
    /**
     * Metodo per ottenere un array con tutte le {@link Gerarchia} nel database
     * @return array con tutte le {@link Gerarchia}
     * @throws ErroreDatabaseNotWorking
     * @since TESI DATABASE
     */
	public List<Gerarchia> requestArrayGerarchia() throws ErroreDatabaseNotWorking {
		
		List<Gerarchia> lista = new ArrayList<Gerarchia>();
		List<Gerarchia> tempLista = new ArrayList<Gerarchia>();
		
		try (Session session = driver.session(SessionConfig.forDatabase(NomiDiEPerDatabase.GERARCHIA_DB_NOMEDB.getVal()))) {
            String cypher = "MATCH(n) RETURN n";
            Result result = session.run(cypher);
            ObjectMapper mapper = new ObjectMapper();
            
            while(result.hasNext()) {
            	Record record = result.next();
            	Node nodo = record.get("n").asNode();
            	
            	String nodeIdString = nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal()).asString();
            	UUID nodeId = UUID.fromString(nodeIdString);
            	
            	String name = nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_NOMECATEGORIA.getVal()).asString();
            	String campo = nodo.containsKey(NomiDiEPerDatabase.GERARCHIA_DB_CAMPO.getVal()) ? 
            			nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_CAMPO.getVal()).asString() : "";
            	
            	List<Tupla<String, String>> nodoDominio;
				if (nodo.containsKey(NomiDiEPerDatabase.GERARCHIA_DB_DOMINIO.getVal())) {
					List<List<String>> temp = ResultsConverter.convertDoubleArrayInListList(
							mapper.readValue(nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_DOMINIO.getVal()).asString(),
									String[][].class));
					nodoDominio = ResultsConverter.converterToListTupla(temp);
					Collections.reverse(nodoDominio);
				}		
				else {
					nodoDominio = new ArrayList<Tupla<String, String>>();
				}
				UUID proprietario = UUID.fromString(nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_PROPRIETARIOID.getVal()).asString());
								
				//CREAZIONE SENZA PADRE, FIGLI E FATTORI DI CONVERSIONE
				Gerarchia ger = new Gerarchia(proprietario,
						name, campo, nodoDominio, nodeId);
				if(nodo.hasLabel(LabelsAndRelationshipsUsedForGerarchiaDB.RADICE.getValue())) {
					lista.add(ger);
				}
				tempLista.add(ger);
            }
            
            Record record;
            
            cypher = "MATCH (n:" + LabelsAndRelationshipsUsedForGerarchiaDB.FOGLIA.getValue() + ") "
        			+ "WHERE NOT (n)-[:" + LabelsAndRelationshipsUsedForGerarchiaDB.HAS_FATTORE_CONVERSIONE.getValue() + "]->() "
        			+ "RETURN n." + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + " AS nodeId";
            result = session.run(cypher);
        	List<UUID> idsFogliaWithNoFDC = new ArrayList<UUID>();
        	while(result.hasNext()) {
        		record = result.next();
        		idsFogliaWithNoFDC.add(UUID.fromString(record.get("nodeId").asString()));
        	}
            
            for(int i = 0; i < tempLista.size(); i++) {
            	cypher = "MATCH (m)-[r:" + LabelsAndRelationshipsUsedForGerarchiaDB.IS_PARENT.getValue() + "]->(n) "
            			+ "WHERE n." + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + " = $nodeId "
            			+ "RETURN m." + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + " AS fatherId "
            			+ "LIMIT 1 ";
            	result = session.run(cypher, Values.parameters("nodeId", tempLista.get(i).getId().toString()));
            	
            	String idString;
            	UUID id;
            	
            	if(result.hasNext()) {
            		record = result.next();
                	idString = record.get("fatherId").asString();
                	id = UUID.fromString(idString);
                	//PADRE SETTATO
                	for(Gerarchia ger : tempLista) {
                		if(ger.getId().equals(id)) {
                			tempLista.get(i).setPadre(ger);
                		}
                	}
            	}
            	
            	cypher = "MATCH (n)-[r:" + LabelsAndRelationshipsUsedForGerarchiaDB.IS_PARENT.getValue() + "]->(m) "
            			+ "WHERE n." + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + " = $nodeId "
            			+ "RETURN m." + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + " AS nodeId";
            	result = session.run(cypher, Values.parameters("nodeId", tempLista.get(i).getId().toString()));
            	while(result.hasNext()) {
            		record = result.next();
            		idString = record.get("nodeId").asString();
            		id = UUID.fromString(idString);
            		for(Gerarchia ger : tempLista) {
                		if(ger.getId().equals(id)) {
                			//FIGLIO SETTATO
                			tempLista.get(i).addFiglio(ger);
                		}
                	}
            	}
            	
            	for(UUID idsForFdc : idsFogliaWithNoFDC) {
            		if(tempLista.get(i).getId().equals(idsForFdc)) {
            			//ToSetFDC IMPOSTATO 
            			tempLista.get(i).setToSetFDC(true);
            		}
            	}
            	
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new ErroreDatabaseNotWorking();
        } 
		
		return lista;
	}
	
	/**
	 * Metodo per aggiungere una {@link Gerarchia} E TUTTI I SUOI FIGLI nel database
	 * senza pero' aggiungere relazioni di parentela ed fdc
	 * @param radice {@link Gerarchia} da salvare
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public void addGerarchia(Gerarchia radice) throws ErroreDatabaseNotWorking {
		String propId = radice.getProprietarioId().toString();
		String categoria = radice.getCategoria();
		String campo = radice.getCampo();
		List<Tupla<String, String>> dominioTemp = radice.getDominio();
		String uuid = radice.getId().toString();
		
		ObjectMapper mapper = new ObjectMapper();
		
		String[][] dominio = ResultsConverter.convertListTo2DArray(
				ResultsConverter.convertFromTuplasToLists(dominioTemp), String.class);
		String dominioString = "";
		
		try {
			dominioString = mapper.writeValueAsString(dominio);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try(Session session = driver.session(SessionConfig.forDatabase(NomiDiEPerDatabase.GERARCHIA_DB_NOMEDB.getVal()))){
			StringBuilder cypher = new StringBuilder();
			String label = LabelsAndRelationshipsUsedForGerarchiaDB.NODO.getValue();
			if(radice.getPadre() == null) {
				label = LabelsAndRelationshipsUsedForGerarchiaDB.RADICE.getValue();
			}
			else if(radice.getFigli().isEmpty()) {
				label = LabelsAndRelationshipsUsedForGerarchiaDB.FOGLIA.getValue();
			}
			
			cypher.append("CREATE (n:" + label);
			cypher.append(" {" + NomiDiEPerDatabase.GERARCHIA_DB_NOMECATEGORIA.getVal()+ ": $nome, " 
			+ NomiDiEPerDatabase.GERARCHIA_DB_PROPRIETARIOID.getVal() + ": $proprietario, " 
			+ NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": $uuid");
			
			if(!radice.getFigli().isEmpty()) {
				cypher.append(", " + NomiDiEPerDatabase.GERARCHIA_DB_CAMPO.getVal() + ": $campo, "
						+ NomiDiEPerDatabase.GERARCHIA_DB_DOMINIO.getVal() + ": $dominio");
			}
			cypher.append("})");
			
			if(radice.getFigli().isEmpty()) {
				session.run(cypher.toString(), Values.parameters(
						NomiDiEPerDatabase.GERARCHIA_DB_NOMECATEGORIA.getVal(), categoria,
						NomiDiEPerDatabase.GERARCHIA_DB_PROPRIETARIOID.getVal(), propId,
						NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal(), uuid));
			}
			else {
				session.run(cypher.toString(), Values.parameters(
						NomiDiEPerDatabase.GERARCHIA_DB_NOMECATEGORIA.getVal(), categoria,
						NomiDiEPerDatabase.GERARCHIA_DB_PROPRIETARIOID.getVal(), propId,
						NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal(), uuid,
						NomiDiEPerDatabase.GERARCHIA_DB_CAMPO.getVal(), campo,
						NomiDiEPerDatabase.GERARCHIA_DB_DOMINIO.getVal(), dominioString));
			}
		} catch (Exception e) {
			e.printStackTrace();
            throw new ErroreDatabaseNotWorking();
        } 
		
		for(Gerarchia ger : radice.getFigli()) {
			this.addGerarchia(ger);
		}
	}
	
	public void addParentelaToFigli(Gerarchia radice) throws ErroreDatabaseNotWorking {
		if(!radice.isFoglia()) {
			String parentId = radice.getId().toString();
			for(Gerarchia ger : radice.getFigli()) {
				String childId = ger.getId().toString();
				StringBuilder query = new StringBuilder();
				query.append("MATCH (parent {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": '").append(parentId).append("'}) ");
				query.append("MATCH (child {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": '").append(childId).append("'}) ");
				query.append("CREATE (parent)-[:" + LabelsAndRelationshipsUsedForGerarchiaDB.IS_PARENT.getValue() + "]->(child) ");
				try(Session session = driver.session(SessionConfig.forDatabase(NomiDiEPerDatabase.GERARCHIA_DB_NOMEDB.getVal()))){
					session.run(query.toString());
				}
				catch (Exception e) {
		            throw new ErroreDatabaseNotWorking();
		        } 
				if(!ger.isFoglia()) {
					this.addParentelaToFigli(ger);
				}
			}
		}
	}

	/**
	 * Metodo per aggiungere la relazione HAS_FATTORE_CONVERSIONE di una {@link Gerarchia}
	 * Non e' necessario usarla anche sulle altre foglie poiche' usa automaticamente poi l'inverso per creare 
	 * la relazione nel verso opposto
	 * @param gerStart {@link Gerarchia} a cui aggiungere la relazione nel Database
	 * @param fdcs Lista di {@link FattoreDiConversione} da inserire per questa {@link Gerarchia}
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI DATABASE
	 */
	public void saveFattoriConversione(Gerarchia gerStart, List<FattoreDiConversione> fdcs) throws ErroreDatabaseNotWorking {
		if(gerStart.getFigli().isEmpty()) {
			String fogliaId = gerStart.getId().toString();
			for(FattoreDiConversione tupla : fdcs) {
				String altraFogliaId = tupla.getFdc().getFirst().toString();
				double fdc = tupla.getFdc().getSecond();
				StringBuilder query = new StringBuilder();
				query.append("MATCH (foglia1 {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": '").append(fogliaId).append("'}) ");
				query.append("MATCH (foglia2 {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": '").append(altraFogliaId).append("'}) ");
				query.append("MERGE (foglia1)-[r:" +
				LabelsAndRelationshipsUsedForGerarchiaDB.HAS_FATTORE_CONVERSIONE.getValue() + "]->(foglia2) ");
				query.append("ON CREATE SET r." + NomiDiEPerDatabase.GERARCHIA_DB_FATTOREDICONVERSIONE.getVal() + " = ");
				query.append(fdc);
				query.append(" MERGE (foglia2)-[t:" + 
				LabelsAndRelationshipsUsedForGerarchiaDB.HAS_FATTORE_CONVERSIONE.getValue() + "]->(foglia1) ");
				query.append("ON CREATE SET t." + NomiDiEPerDatabase.GERARCHIA_DB_FATTOREDICONVERSIONE.getVal() + " = ");
				query.append(1/fdc);
				try(Session session = driver.session(SessionConfig.forDatabase(NomiDiEPerDatabase.GERARCHIA_DB_NOMEDB.getVal()))){
					session.run(query.toString());
				}
				catch (Exception e) {
		            throw new ErroreDatabaseNotWorking();
		        } 
			}
		}
	}

	/**
	 * Metodo per ottenere il {@link FattoreDiConversione} tra due {@link Gerarchia}
	 * @param gerStart {@link Gerarchia} di partenza 
	 * @param gerEnd {@link Gerarchia} bersaglio del {@link FattoreDiConversione}
	 * @return il {@link FattoreDiConversione} associato
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public FattoreDiConversione retrieveFattoreConversione(UUID gerStart, UUID gerEnd) throws ErroreDatabaseNotWorking {
		try (Session session = driver.session(SessionConfig.forDatabase("gerarchia"))){
			StringBuilder query = new StringBuilder();
			query.append("MATCH (n {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": '").append(gerStart.toString()).append("'})");
			query.append("-[r:" + LabelsAndRelationshipsUsedForGerarchiaDB.HAS_FATTORE_CONVERSIONE.getValue() + "]->");
			query.append("(s {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": '").append(gerEnd.toString()).append("'}) ");
			query.append("RETURN r." + NomiDiEPerDatabase.GERARCHIA_DB_FATTOREDICONVERSIONE.getVal() + " AS fattore");
			Record record;
            Result result = session.run(query.toString());
            if(result.hasNext()) {
            	record = result.next();
            	double fdc = record.get("fattore").asDouble();
            	return new FattoreDiConversione(new Tupla<UUID, Double>(gerEnd, fdc));
            }
		} catch(Exception e) {
			throw new ErroreDatabaseNotWorking();
		}
		throw new ErroreDatabaseNotWorking();
	}

	/**
	 * Metodo per ottenere una lista di {@link FattoreDiConversione} che partono da una certa {@link Gerarchia} 
	 * @param foglia id della {@link Gerarchia} da cui partono i {@link FattoreDiConversione} desiderati
	 * @return Lista di {@link FattoreDiConversione}
	 * @throws ErroreDatabaseNotWorking
	 * @since TESI DATABASE
	 */
	public List<FattoreDiConversione> retrieveAllFdcsFromFoglia(UUID foglia) throws ErroreDatabaseNotWorking {
		List<FattoreDiConversione> fdcs = new ArrayList<FattoreDiConversione>();
		try (Session session = driver.session(SessionConfig.forDatabase(NomiDiEPerDatabase.GERARCHIA_DB_NOMEDB.getVal()))){
			StringBuilder query = new StringBuilder();
			query.append("MATCH (n {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": '").append(foglia.toString()).append("'})");
			query.append("-[r:" + LabelsAndRelationshipsUsedForGerarchiaDB.HAS_FATTORE_CONVERSIONE.getValue() + "]->");
			query.append("(s) ");
			query.append("RETURN s." + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + " AS idBersaglio, "
					+ "r." + NomiDiEPerDatabase.GERARCHIA_DB_FATTOREDICONVERSIONE.getVal() + " AS fattore");
			Record record;
            Result result = session.run(query.toString());
            while(result.hasNext()) {
            	record = result.next();
            	double fdc = record.get("fattore").asDouble();
            	UUID idBersaglio = UUID.fromString(record.get("idBersaglio").asString());
            	fdcs.add(new FattoreDiConversione(new Tupla<UUID, Double>(idBersaglio, fdc)));
            }
            return fdcs;
		} catch(Exception e) {
			e.printStackTrace();
			throw new ErroreDatabaseNotWorking();
		}
	}

	/**
	 * Metodo per ottenere una {@link Gerarchia} dato il suo id 
	 * @param id id della {@link Gerarchia} desiderata
	 * @return {@link Gerarchia} trovata senza padre, figli e {@link FattoreDiConversione}
	 * @throws ErroreDatabaseNotWorking lanciata se non viene trovato niente
	 * @since TESI DATABASE
	 */
	public Gerarchia retrieveGerarchiaById(UUID id) throws ErroreDatabaseNotWorking {
		try (Session session = driver.session(SessionConfig.forDatabase(NomiDiEPerDatabase.GERARCHIA_DB_NOMEDB.getVal()))) {
            String cypher = "MATCH(n {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": $id}) RETURN n";
            Result result = session.run(cypher, Values.parameters("id", id.toString()));
            ObjectMapper mapper = new ObjectMapper();
            
            if(result.hasNext()) {
            	Record record = result.next();
            	Node nodo = record.get("n").asNode();
            	
            	UUID nodeId = id;
            	
            	String name = nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_NOMECATEGORIA.getVal()).asString();
            	String campo = nodo.containsKey(NomiDiEPerDatabase.GERARCHIA_DB_CAMPO.getVal()) 
            			? nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_CAMPO.getVal()).asString() : "";
            	
            	List<Tupla<String, String>> nodoDominio;
				if (nodo.containsKey(NomiDiEPerDatabase.GERARCHIA_DB_DOMINIO.getVal())) {
					List<List<String>> temp = ResultsConverter.convertDoubleArrayInListList(
							mapper.readValue(nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_DOMINIO.getVal()).asString(),
									String[][].class));
					nodoDominio = ResultsConverter.converterToListTupla(temp);
				}		
				else {
					nodoDominio = new ArrayList<Tupla<String, String>>();
				}
				UUID proprietario = UUID.fromString(nodo.get(NomiDiEPerDatabase.GERARCHIA_DB_PROPRIETARIOID.getVal()).asString());
								
				//CREAZIONE SENZA PADRE, FIGLI E FATTORI DI CONVERSIONE
				Gerarchia ger = new Gerarchia(proprietario,
						name, campo, nodoDominio, nodeId);
				return ger;
            }
		} catch(Exception e) {
			throw new ErroreDatabaseNotWorking();
		}
		throw new ErroreDatabaseNotWorking();
	}

	public UUID retrieveRadiceOfGerarchia(UUID id) throws ErroreDatabaseNotWorking {
		try (Session session = driver.session(SessionConfig.forDatabase(NomiDiEPerDatabase.GERARCHIA_DB_NOMEDB.getVal()))){
			StringBuilder query = new StringBuilder();
			query.append("MATCH (n {" + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + ": '").append(id.toString()).append("'}) ");
			query.append("MATCH p = (root: " + LabelsAndRelationshipsUsedForGerarchiaDB.RADICE.getValue()
			+ ")-[r:" + LabelsAndRelationshipsUsedForGerarchiaDB.IS_PARENT.getValue() +"*]->");
			query.append("(n)");
			query.append("RETURN root." + NomiDiEPerDatabase.GERARCHIA_DB_ID.getVal() + " AS rootId");
			Record record;
            Result result = session.run(query.toString());
            if(result.hasNext()) {
            	record = result.next();
            	UUID rootId = UUID.fromString(record.get("rootId").asString());
            	return rootId;
            }
		} catch(Exception e) {
			throw new ErroreDatabaseNotWorking();
		}
		throw new ErroreDatabaseNotWorking();
	}

	/**
	 * Metodo per pulire il database da eventuali {@link Gerarchia} incomplete dei {@link FattoreDiConversione}
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI DATABASE
	 */
	public void cleanGerarchiaDatabaseFromUncompletedGerarchias() throws ErroreDatabaseNotWorking {
		try(Session session = driver.session(SessionConfig.forDatabase(NomiDiEPerDatabase.GERARCHIA_DB_NOMEDB.getVal()))){
			String cypher = "MATCH (leaf:" + LabelsAndRelationshipsUsedForGerarchiaDB.FOGLIA.getValue() + ")"
					+ " WHERE NOT (leaf)-[:" + LabelsAndRelationshipsUsedForGerarchiaDB.HAS_FATTORE_CONVERSIONE.getValue() + "]->()"
					+ " WITH leaf"
					+ " MATCH path = (root:" + LabelsAndRelationshipsUsedForGerarchiaDB.RADICE.getValue() 
					+ ")-[:" + LabelsAndRelationshipsUsedForGerarchiaDB.IS_PARENT.getValue() + "*0..]->(leaf)"
					+ "WITH DISTINCT root "
					+ " CALL {"
					+ " WITH root "
					+ " MATCH (root)-[:" + LabelsAndRelationshipsUsedForGerarchiaDB.IS_PARENT.getValue() + "*0..]->(child) "
					+ " DETACH DELETE child}"
					+ " DETACH DELETE root;";
			session.run(cypher.toString());
		} catch(Exception e) {
			throw new ErroreDatabaseNotWorking();
		}
	}
}
