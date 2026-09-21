package menuControllerConGrafica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import graphicUI.GraphicImplementer;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.MyScorrimentoTree;
import indirectionAccessToGraphicPanels.WarnerToUser;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import logica.ModelService;
import personalGraphicElements.ButtonsContainer;
import strutture.Gerarchia;
import strutture.Proposta;
import utente.Utente;
import utility.Stato;

/**
 * Classe CONTROLLER per i vari menu utente
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuGrafico implements GraphicImplementer{

	
	private static final String MSG_SCORRIMENTO_GERARCHIA = "Scorri le Gerarchie. Fai doppio click per selezionare la"
			+ " categoria che preferisci o per vederne le sottocategorie. Quando selezionerai una foglia"
			+ " o premerai il pulsante d'uscita, finirai lo scorrimento";
	
	private ModelService gestore;
		
	private ViewGUI gui;
	
	private MenuWithButtons menu;
	
	private MyInputDati GUIInputDati;
	
	private WarnerToUser warner;
		
	private Utente user;	
	
	private MyScorrimentoTree<Gerarchia, Gerarchia> scorrimentoTree;
	
	/**
	 * Costruttore
	 * @param user
	 * @param gestore
	 * @param gui
	 * @param GUIInputDati
	 * @param warner
	 * @since TESI
	 */
	public MenuGrafico(Utente user, ModelService gestore, ViewGUI gui, MyInputDati GUIInputDati,
			WarnerToUser warner) {
		super();
		this.gestore = gestore;
		this.gui = gui;
		this.GUIInputDati = GUIInputDati;
		this.warner = warner;
		this.user = user;
		this.scorrimentoTree = new MyScorrimentoTree<Gerarchia, Gerarchia>("Scorrimento Gerarchie");
	}


	@Override
	public ViewGUI returnView() {
		return this.gui;
	}

	/**
	 * @return the gestore
	 */
	protected ModelService accediGestore() {
		return gestore;
	}

	/**
	 * @return the gui
	 */
	protected ViewGUI getGui() {
		return gui;
	}

	protected Utente ottieniUtente() {
		return this.user;
	}
	
	/**
	 * @return the menu
	 */
	protected MenuWithButtons accediMenu() {
		if(this.getGui().getToDisplay() != this.menu) {
			this.getGui().changeToDisplay(menu);
		}
		return menu;
	}
	
	protected void setButtonsForMenu(ButtonsContainer newContainer) {
		this.menu.replaceButtonContainer(newContainer);
	}
	/**
	 * @param menu the menu to set
	 */
	protected void setMenu(MenuWithButtons menu) {
		this.menu = menu;
	}

	/**
	 * @return the gUIInputDati
	 */
	protected MyInputDati accediGUIInputDati() {
		if(this.getGui().getToDisplay() != this.GUIInputDati) {
			this.getGui().changeToDisplay(GUIInputDati);
		}
		return GUIInputDati;
	}

	protected WarnerToUser accediWarner() {
		if(this.getGui().getToDisplay() != this.warner) {
			this.getGui().changeToDisplay(warner);
		}
		return warner;
	}
	
	protected MyScorrimentoTree<Gerarchia, Gerarchia> accediScorrimentoTree(){
		if(this.getGui().getToDisplay() != this.scorrimentoTree) {
			this.getGui().changeToDisplay(scorrimentoTree);
		}
		return scorrimentoTree;
	}

	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	/**
	 * Metodo to-Override per gestire l'interazione con l'utente
	 * e modificare il comportamento in base all'azione scelta. Se
	 * usato con MenuGrafico ritorna un messaggio di errore per dati non sufficienti
	 */
	public void azioniMenuGrafico() {
		this.accediWarner().warnUserInsufficentData();
	}
	
	/**
	 * Metodo per dividere un array in altri array differenziati dallo stato con grafica
	 * @Precondizione la lista da dividere non deve essere null
	 * @Postcondizione
	 * @param gruppo Array di proposte da suddividere
	 * @return HashMap di array di proposte divise in base allo stato
	 * @since TESI
	 */
	protected HashMap<Stato,List<Proposta>> dividiArrayPerStato(List<Proposta> gruppo){
		HashMap<Stato,List<Proposta>> map = new HashMap<Stato, List<Proposta>>();
		for(Stato s : Stato.values()) {
			map.put(s, new ArrayList<>());
		}
		for(Proposta p : gruppo) {
			map.get(p.getStato()).add(p);
		}
		return map;
	}
	
	/**
	 * Metodo per selezionare una foglia scorrendo per intero l'albero
	 * @param lista Lista di gerarchie da cui attingere
	 * @param message messaggio da mostrare durante lo scorrimento. se vale "", mostrerà un messaggio standard
	 * @return Categoria scelta
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreDatiAssenti 
	 * @since TESI
	 */
	protected Gerarchia scegliFogliaWithGraphic(List<Gerarchia> lista, String message, boolean selectableLeafs) throws ErroreInterruzioneOperazione, ErroreDatiAssenti{
		if(message == "") {
			message = MSG_SCORRIMENTO_GERARCHIA;
		}
		this.scorrimentoTree.setMessageText(message);
		this.scorrimentoTree.setToBeSelcted(selectableLeafs);
		this.scorrimentoTree.setObjectsToScroll(lista);
		Gerarchia gerScelta = this.accediScorrimentoTree().scorriListaDiAlberi();
		return gerScelta;
	}
}
