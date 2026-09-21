package strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import utente.Configuratore;
import utility.Tupla;

/**
 * 
 * @author Francesco Lozio 737664
 * @since 1
 * Classe Gerarchia 
 */
public class Gerarchia implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Proprietario, e' un configuratore
	 * @since 1
	 * @since TESI_DATABASE e' un UUID, non piu' l'oggetto {@link Configuratore}
	 */	
	private UUID proprietarioId;

	/**
	 * categoria, e' il nome nello schemo ad albero
	 * @since 1
	 */
	private String categoria;

	/**
	 * Dominio, insieme di valori con cui scorrere una gerarchia. 
	 * contiene anche una descrizione opzionale (stringa vuota se non c'e')
	 * @since 1
	 */
	private List<Tupla<String,String>> dominio;

	/**
	 * Campo, consente di distinguere un figlio da un altro ed e' proprio della categoria.
	 * I possibili valori sono salvati in dominio
	 * @since 1
	 */
	private String campo;

	/**
	 * figlia, array di nodi figli del nodo considerato. 
	 * Se e' vuoto, la categoria e' una categoria foglia
	 * @since 1
	 */
	private List<Gerarchia> figli;

	/**
	 * Gerarchia padre, indica di chi e' figlia la categoria corrente
	 * Se nullo, la categoria e' radice
	 * @since 1
	 */
	private Gerarchia padre;

	/**
	 * Boolean per controllare se e' necessario
	 * aggiungere figli o meno alla categoria corrente
	 */
	private boolean toSetFigli = false;

	/**
	 * Boolean per controllare se e' necessario
	 * aggiungere FDC o meno alla categoria corrente
	 */
	private boolean toSetFDC = false;
	
	private UUID id;
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isToSetFigli() {
		return toSetFigli;
	}

	public void setToSetFigli(boolean toSetFigli) {
		this.toSetFigli = toSetFigli;
	}

	public boolean isToSetFDC() {
		return toSetFDC;
	}

	public void setToSetFDC(boolean toSetFDC) {
		this.toSetFDC = toSetFDC;
	}

	/**
	 * Costruttore per ricostruzione da DB
	 * @param proprietario
	 * @param categoria
	 * @param campo
	 * @param dominio
	 */
	public Gerarchia(UUID prop_id, String categoria, String campo, List<Tupla<String, String>> dominio, UUID id) {
		this.proprietarioId = prop_id;
		this.categoria = categoria;
		this.dominio = dominio;
		this.campo = campo;
		this.id = id;
		this.padre = null;
		this.figli = new ArrayList<>();
	}
	
	public Gerarchia(UUID prop_id, 
			String categoria, UUID id) {
		this.categoria = categoria;
		this.dominio = new ArrayList<>();
		this.campo = "";
		this.id = id;
		this.figli = new ArrayList<>();
		this.proprietarioId = prop_id;
	}


	///
	///GET E SET
	///

	/**
	 * getter del nome della Gerarchia
	 * @return il nome della categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<Tupla<String, String>> getDominio() {
		return dominio;
	}

	public void setDominio(List<Tupla<String, String>> dominio) {
		this.dominio = dominio;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public List<Gerarchia> getFigli() {
		return figli;
	}

	public void setFigli(ArrayList<Gerarchia> figli) {
		this.figli = figli;
	}

	public Gerarchia getPadre() {
		return padre;
	}

	public void setPadre(Gerarchia padre) {
		this.padre = padre;
	}

	/**
	 * Metodo per ottenere le foglie sottostanti a questa gerarchia
	 * @Precondizione 
	 * @Postcondizione l'array foglie deve essere correttamente riempito
	 * @param foglie array di foglie da riempire: alla prima invocazione bisognera' passare un array vuoto
	 * @return array di foglie, contiene solo la gerarchia stessa se questa e' una foglia
	 */
	public List<Gerarchia> getFoglie(List<Gerarchia> foglie){
		if(this.isFoglia() && !this.toSetFigli) {
			if(foglie == null) {
				foglie = new ArrayList<>();
			}
			foglie.add(this);
		}
		else{
			for(Gerarchia f : this.getFigli()) {
				f.getFoglie(foglie);
			}
		}
		return foglie;
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the proprietario_id
	 */
	public UUID getProprietarioId() {
		return proprietarioId;
	}

	/**
	 * @param proprietario_id the proprietario_id to set
	 */
	public void setProprietarioId(UUID proprietario_id) {
		this.proprietarioId = proprietario_id;
	}
	
	//FINE GETTER E SETTER
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * Metodo per controllare se la categoria corrente e' una foglia:
	 * viene controllato se l'array di figli e' vuoto/nullo o meno
	 * @Precondizione l'arrayFigli non deve essere null
	 * @Postcondizione
	 * @return true se l'array di figli e' vuoto, false altrimenti
	 * @since 1
	 */
	public boolean isFoglia() {
		return (this.getFigli().isEmpty() 
				&& !this.isToSetFigli() && 
				!this.isToSetFDC())
				? true : false;
	}

	/**
	 * Metodo per sapere se una Gerarchia ha delle foglie sottostanti
	 * @param esiti array dove salvare se viene trovato una foglia
	 * @Precondizioni 
	 * @Postcondizioni
	 * @return true se ha foglie, altrimenti false
	 * @since 1
	 */
	public boolean hasFoglie(List<Boolean> esiti) {
		if(esiti == null) {
			esiti = new ArrayList<Boolean>();
		}
		if(this.isFoglia()) {
			esiti.add(true);
			return true;
		}
		else{
			for(Gerarchia f : this.getFigli()) {
				if(f.isFoglia()) {
					esiti.add(true);
					return true;
				}
				else {
					f.hasFoglie(esiti);
				}
			}
		}
		return this.extractEsito(esiti);
	}

	/**
	 * Aggiunge un nuovo figlio alla categoria corrente
	 * @Precondizione nuovoFiglio non deve essere null
	 * @Postocondizione l'arrayFigli della Gerarchia ha il nuovoFiglio
	 * @param nuovoFiglio nuovo figlio da aggiungere
	 * @since 1
	 */
	public void addFiglio(Gerarchia nuovoFiglio) {
		this.getFigli().add(nuovoFiglio);
	}

	/**
	 * Metodo ausiliario al metodo hasFoglie per estrarre il risultato dall'array passato
	 * @Precondizioni l'array non deve essere null
	 * @Postcondizioni
	 * @param esiti array da controllare
	 * @return false se l'array � vuoto, true altrimenti
	 * @since 1
	 */
	private boolean extractEsito(List<Boolean> esiti) {
		if(esiti.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo per sapere un nome e' gia' stato usato dalla categoria corrente e dalle categorie sottostanti
	 * @Precondizioni nameToCheck!=null
	 * @Postcondizioni
	 * @param nameToCheck nome da controllare
	 * @return true se c'e' effettivamente un doppione, altrimenti false
	 * @since 1
	 */
	public boolean isNameAlreadyTaken(String nameToCheck) {
		if(this.isFoglia()) {
			if(this.getCategoria().toUpperCase()
					.equals(nameToCheck.toUpperCase())) {
				return true;
			}
		}
		if(this.getCategoria().toUpperCase().equals(nameToCheck.toUpperCase())) {
			return true;
		}
		else {
			for(int i = 0; i < this.getFigli().size(); i++) {
				if(this.getFigli().get(i).getCategoria().toUpperCase()
						.equals(nameToCheck.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * Metodo per comparare due Gerarchia
	 * @param gerToCompare altra Gerarchia da confrontare
	 * @return true se la Gerarchia corrente e' la stessa di quella passaat come parametro
	 * @Precondizione gerToCompare non deve essere null
	 * @Postcondizione
	 * @since 3
	 */
	public boolean equals(Gerarchia gerToCompare) {
		if(this.getId().equals(gerToCompare.getId())) {
			return true;
		}
		if(!this.getCategoria().equals(gerToCompare.getCategoria())) {
			return false;
		}
		if((this.getPadre() == null && gerToCompare.getPadre() != null) ||
				(this.getPadre() != null && gerToCompare.getPadre() == null)) {
			return false;
		}
		if(this.getPadre() != null && gerToCompare != null) {
			return this.getPadre().equals(gerToCompare.getPadre());
		}
		return true;
	}
	
	/**
	 * Metodo che ritorna la tupla Elemento-Descrizione del padre associata a questa {@link Gerarchia}
	 * @return 
	 * @since TESI
	 */
	public Tupla<String, String> getElementoFromPadre() {
		if(this.getPadre() == null) {
			return new Tupla<String, String>("", "");
		}
		int index = 0;
		for(int i = 0; i < this.getPadre().getFigli().size(); i++) {
			if(this.getPadre().getFigli().get(i).equals(this)) {
				index = i;
			}
		}
		return this.getPadre().getDominio().get(index);
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(this.getCategoria());
		if(this.isFoglia()) {
			Tupla<String, String> elemento = this.getElementoFromPadre();
			string.append(", Elemento: " + elemento.getFirst());
			if(elemento.getSecond() != "") {
				string.append(" (" + elemento.getSecond() + ")");
			}
		}
		else {
			if(this.isToSetFDC()) {
				string.append(" (Fattore di Conversione da inserire)");
			}
			else{
				string.append(", Campo: " + getCampo());
				if(isToSetFigli() && this.getFigli().size() != this.getDominio().size()){
					string.append(" (Sotto Categorie da inserire)");
				}
			}
		}

		return string.toString();
	}
}
