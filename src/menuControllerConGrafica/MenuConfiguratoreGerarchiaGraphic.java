package menuControllerConGrafica;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import fromObjectToTreeChainOfResponsibility.GerarchiaToTree;
import fromObjectToTreeChainOfResponsibility.TreeConverterHandlerInterface;
import graphicUI.GraphicDisplayInterface;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import indirectionAccessToGraphicPanels.MyAskMultipleData;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.ModelService;
import personalGraphicElements.ButtonsContainer;
import personalGraphicElements.MyJTree;
import personalGraphicElements.YesOrNoButtons;
import strutture.Gerarchia;
import utility.InterazioneOperazioni;
import utility.ResultsConverter;
import utility.TreeDefaultTextEnum;
import utility.Tupla;

/**
 * Classe per la gestione dei Cicli Chiusi dal menu Configuratore
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuConfiguratoreGerarchiaGraphic {

	private static final String MSG_CAMPO_CATEGORIA = "Campo Categoria";
	private static final String MSG_NOME_CATEGORIA = "Nome Categoria";
	private static final String MSG_WARN_PROGRESS_CREATION_GERARCHIA = "Stato attuale della creazione della Gerarchia";
	private static final String MSG_WARN_RADICE_CREATION = "Inizi ora la creazione della Categoria Radice, quella da cui poi "
			+ "seguono le altre sotto categorie collegate alla radice stessa";
	private static final String MSG_ERROR_ELEMENTO_DOMINIO_NON_VALIDO = "Elemento non valido, prova con un altro";
	private static final String MSG_ASK_ELEMENTO_DOMINIO = "Inserisci un elemento per il dominio (ossia cio' che differenzia una sottocategoria"
			+ " da un'altra nello stesso campo. Da es. precedente: I Secolo, III Secolo ecc...). Ne servono minimo 2 e le descrizioni sono opzionali";
	private static final String MSG_ASK_FOGLIA_OR_NOT = "Vuoi creare una foglia? Se scegli di si, la creazione di questa categoria (%s) si interrompera', altrimenti passerai"
			+ " alla richiesta del suo campo";
	private static final String MSG_ASK_CAMPO = "Inserisci il campo della Categoria %s. Il campo di una categoria indica quale aspetto identifica"
			+ " una sotto categoria piuttosto che un'altra. Es.: per Lezioni di Storia, un possibile campo e' \"Secolo\" ";
	private static final String MSG_NAME_ALREADY_TAKEN = "Nome gia' inserito. Riprova";
	private static final String MSG_ASK_NAME_CATEGORIA = "Inserisci il nome di questa categoria";
	private static final String MSG_CREAZIONE_FIGLIO = "Creazione della sottocategoria con categoria soprastante: %s, campo: %s, elemento del dominio: %s";
	private static final String MSG_FIGLIO_NON_FOGLIA = "Il figlio %s (categoria soprastante: %s, Campo soprastante: %s, Elemento del Dominio Associato: %s)"
    		+ " che hai inserito non e' una foglia, crea le sue sotto-categorie";
	
	private static final String ADDED_INPUT_PLACEHOLDER_FIRST = "Elemento Dominio ";
	private static final String ADDED_INPUT_PLACEHOLDER_SECOND = "Descrizione (opzionale) ";
	
	private List<String> tuplaPlaceholder;

	private MyInputDati inputDati;
	
	private MenuWithButtons menu;
	
	private ViewGUI gui;
		
	private WarnerToUser warner;
		
	private MyAskMultipleData askData;
	
	private TreeConverterHandlerInterface treeConverter;
	
	private ModelService gestore;
	
	private Gerarchia tempGerarchiaScorrimento;

	/**
	 * Costruttore
	 * @param inputDati
	 * @param warner
	 * @param gui
	 * @param gestore
	 * @since TESI
	 */
	public MenuConfiguratoreGerarchiaGraphic(MyInputDati inputDati, WarnerToUser warner, ViewGUI gui, ModelService model) {
		super();
		this.inputDati = inputDati;
		this.gui = gui;
		this.warner = warner;
		this.gestore = model;
		List<String> placeholdersIniziali = new ArrayList<String>();
		placeholdersIniziali.add(ADDED_INPUT_PLACEHOLDER_FIRST);
		placeholdersIniziali.add(ADDED_INPUT_PLACEHOLDER_SECOND);
		placeholdersIniziali.add(ADDED_INPUT_PLACEHOLDER_FIRST);
		placeholdersIniziali.add(ADDED_INPUT_PLACEHOLDER_SECOND);
		
		this.askData = new MyAskMultipleData(MSG_ASK_ELEMENTO_DOMINIO,
				placeholdersIniziali, true, 2, 4);
		this.askData.setRelevantControlForValidity(0);
		
		this.tuplaPlaceholder = new ArrayList<String>();
		this.tuplaPlaceholder.add(ADDED_INPUT_PLACEHOLDER_FIRST);
		this.tuplaPlaceholder.add(ADDED_INPUT_PLACEHOLDER_SECOND);
		
		this.treeConverter = new GerarchiaToTree();
	}
	
	private ViewGUI accediGUI() {
		return this.gui;
	}

	private ModelService accediGestore() {
		return this.gestore;
	}
	
	private void setMenuButtons(ButtonsContainer container) {
		if(this.menu == null) {
			menu = new MenuWithButtons(container);
		}
		else {
			menu.replaceButtonContainer(container);
		}
	}
	
	private MyInputDati accediInputDati() {
		return this.inputDati;
	}
	
	private WarnerToUser accediWarner() {
		return this.warner;
	}
	
	private void refreshGUI(GraphicDisplayInterface newToDisplay) {
		this.accediGUI().changeToDisplay(newToDisplay);
	}
	
	private MenuWithButtons accediMenu() {
		return this.menu;
	}
	
	private MyAskMultipleData accediAskDatas() {
		return this.askData;
	}

	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	
	/**
	 * Metodo per creare una categoria
	 * @param listaPerDoppioni lista di string per controllare nomi doppioni. Per creare una radice, sara' la lista dei nomi delle altre radici
	 * mentre per una categoria non radice sara' la lista dei nomi di tutti i figli della radice della categoria che si sta creando
	 * @param radiceOrNot true per dire che la categoria che si sta creando e' una radice, false altrimenti (se true consente di saltare
	 * la parte in cui si chiede all'utente se vuole inserire una foglia o meno, impedendo di creare radici foglia)
	 * @param proprietarioId Configuratore che sta creando la Categoria
	 * @param padre padre della categoria che si sta creando. Se e' una radice, padre vale null
	 * @param elementoDominio elemento del dominio associato alla categoria che si vuole creare. Vale null se si crea una radice
	 * @return Categoria creata
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public Gerarchia creazioneCategoriaWithGraphic(List<String> listaPerDoppioni,
			boolean radiceOrNot, UUID proprietarioId, Gerarchia padre, Tupla<String, String> elementoDominio)
					throws ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreDatabaseNotWorking,
					ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		this.refreshGUI(this.accediWarner());
		if(radiceOrNot) {
			this.accediWarner().warnUserWithCustomMessage(MSG_WARN_RADICE_CREATION);
		}
		else {
			MyJTree<Gerarchia> tree = this.treeConverter.treeToGraphic(this.tempGerarchiaScorrimento);
			tree.addNewTextNodeToSpecificNode(TreeDefaultTextEnum.YOU_ARE_HERE.getVal(), padre);
			tree.expandTree();
			this.accediWarner().warnUserWithMessageAndAdditionalComponent(tree, MSG_WARN_PROGRESS_CREATION_GERARCHIA);
			
		}
		boolean corretto = false;
		boolean fogliaOrNot = false;
		String categoria = "";
		this.accediAskDatas().hideAllWarnings();
		this.accediInputDati().hideWarning();
		while(!corretto) {

			StringBuilder builder = new StringBuilder();
			builder.append(MSG_ASK_NAME_CATEGORIA);
			
			if(!radiceOrNot && elementoDominio != null) {
				builder.append(String.format(". Padre: %s con campo %s, Elemento Dominio: %s",
						padre.getCategoria(), padre.getCampo(), elementoDominio.getFirst()));
				if(!elementoDominio.getSecond().equalsIgnoreCase("")) {
					builder.append(" (" + elementoDominio.getSecond() + ")");
				}
			}
			this.refreshGUI(this.accediInputDati());
			this.accediInputDati().setPlaceholder(MSG_NOME_CATEGORIA);
			categoria = this.accediInputDati().setRequestMessageAndAskInput(builder.toString());
			corretto = true;
			
			if((padre != null && categoria.equalsIgnoreCase(padre.getCategoria()) || listaPerDoppioni.contains(categoria))) {
				corretto = false;
				this.accediInputDati().warnUserWithInputDati(MSG_NAME_ALREADY_TAKEN);
			}
		}

		String campo = "";
		this.accediInputDati().hideWarning();
		if(!radiceOrNot) {
			this.setMenuButtons(new YesOrNoButtons(String.format(MSG_ASK_FOGLIA_OR_NOT, categoria)));
			this.refreshGUI(this.accediMenu());
			int scelta = this.accediMenu().ottieniButtonPressed();
			
			if(scelta == InterazioneOperazioni.CONFIRM_OPERATION.getVal()) {
				fogliaOrNot = true;
			}
		}
		
		if(radiceOrNot || !fogliaOrNot) {
			this.refreshGUI(this.accediInputDati());
			this.accediInputDati().setPlaceholder(MSG_CAMPO_CATEGORIA);
			campo = this.accediInputDati().setRequestMessageAndAskInput(String.format(MSG_ASK_CAMPO, categoria));
			
			List<Tupla<String, String>> dominio = this.chiediDominio();
			
			Gerarchia gerResult = new Gerarchia(proprietarioId, categoria, campo, dominio, UUID.randomUUID());
			gerResult.setPadre(padre);
			gerResult.setToSetFigli(true);
			
			if(radiceOrNot) {
				this.tempGerarchiaScorrimento = gerResult;
			}
			
			return gerResult;
		}
		
		Gerarchia gerResult = new Gerarchia(proprietarioId, categoria, UUID.randomUUID());
		gerResult.setPadre(padre);
		gerResult.setToSetFDC(true);
		
		return gerResult;
	}
	
	/**
	 * Metodo per l'acquisizione di un dominio con grafica
	 * Extract Method
	 * @param exitInput input per annullare/chiudere un'operazione
	 * @return un nuovo dominio
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreDatiAssenti 
	 * @since TESI
	 */
	private List<Tupla<String, String>> chiediDominio() throws ErroreInterruzioneOperazione, ErroreDatiAssenti{
		List<Tupla<String, String>> dominio = new ArrayList<>();
		boolean uscita = false;
		this.accediAskDatas().resetToPlaceholderAndHideWarning();
		this.accediAskDatas().resetCounterForAddedAreas();
		while(!uscita) {
			this.refreshGUI(this.accediAskDatas());
			this.accediAskDatas().setPlaceholderMessageForAdded(tuplaPlaceholder);
			dominio = ResultsConverter.converterToListTupla(this.accediAskDatas().askCredentials());
			uscita = true;
			if(dominio.isEmpty()) {
				throw new ErroreDatiAssenti();
			}
			else {
				for(int i = 0; i < dominio.size(); i++) {
					if(this.accediAskDatas().isTextEqualToPlaceholder(i * 2) || dominio.get(i).getFirst().equalsIgnoreCase("")){
						uscita = false;
						this.accediAskDatas().setToPlaceholderAndAddWarning(
								MSG_ERROR_ELEMENTO_DOMINIO_NON_VALIDO, i * 2);
					}
					if(uscita) {
						for(int j = 0; j < dominio.size(); j++) {
							
							if((i != j && dominio.get(i).getFirst().
									equalsIgnoreCase(dominio.get(j).getFirst()))
									) {
								uscita = false;
								this.accediAskDatas().setToPlaceholderAndAddWarning(
										MSG_ERROR_ELEMENTO_DOMINIO_NON_VALIDO, i * 2);
							}
						}
						
					}
					if(this.accediAskDatas().isTextEqualToPlaceholder((i * 2) + 1)) {
						dominio.get(i).setSecond("");
					}
				}
			}
		}
		return dominio;
	}
	
	/**
	 * Metodo per creare figli con uso di grafica
	 * @param padre Categoria padre dei figli che si stanno creando
	 * @param listaPerDoppioni lista da cui attingere per evitare doppioni
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public void creazioneFigliWithGraphic(Gerarchia padre, List<String> listaPerDoppioni) 
			throws ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreDatabaseNotWorking,
			ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		
		int segnale = 0;
		while(segnale < padre.getDominio().size()) {
			StringBuffer str = new StringBuffer();
			Tupla<String, String> elementoDominio = padre.getDominio().get(segnale);
			
			str.append(String.format(MSG_CREAZIONE_FIGLIO, padre.getCategoria(), padre.getCampo(), elementoDominio.getFirst()));
			
			if(!elementoDominio.getSecond().equalsIgnoreCase("")) {
				str.append(" (" + elementoDominio.getSecond() + ")");
			}
			this.refreshGUI(this.accediWarner());
			this.accediWarner().warnUserWithCustomMessage(str.toString());
			
			Gerarchia figlio = this.creazioneCategoriaWithGraphic(listaPerDoppioni, false,
					padre.getProprietarioId(), padre, elementoDominio);

			padre.addFiglio(figlio);
			listaPerDoppioni.add(figlio.getCategoria());
			segnale++;
		}
		
		for(int scorriFigli = 0; scorriFigli < padre.getFigli().size(); scorriFigli++) {
			if(padre.getFigli().get(scorriFigli).isToSetFigli()) {
				this.refreshGUI(this.accediWarner());
				this.accediWarner().warnUserWithCustomMessage(String.format(MSG_FIGLIO_NON_FOGLIA, 
						padre.getFigli().get(scorriFigli).getCategoria(),
						padre.getCategoria(),
						padre.getCampo(),
						padre.getDominio().get(scorriFigli).getFirst()));
				
						creazioneFigliWithGraphic(padre.getFigli().get(scorriFigli), listaPerDoppioni);
			}
		}
		padre.setToSetFigli(false);
	}
	
//	private UUID findRootWithIds(Gerarchia ger) {
//		if(ger.getPadre() == null) {
//			return ger.getId();
//		}
//		else {
//			return this.findRootWithIds(ger.getPadre());
//		}
//	}
}
