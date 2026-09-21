package menuControllerConGrafica;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreParametriNonConformi;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import graphicUI.GraphicDisplayInterface;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import indirectionAccessToGraphicPanels.MyAskMultipleData;
import indirectionAccessToGraphicPanels.MyScorrimentoRisultati;
import logica.ModelService;
import personalGraphicElements.YesOrNoButtons;
import strutture.AdapterComprensorioGraphicKit;
import strutture.Comprensorio;
import utente.Configuratore;
import utility.InterazioneBottoni;
import utility.ResultsConverter;

/**
 * Classe per la gestione dei Comprensori dal menu Configuratore
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuConfiguratoreComprensoriGrafico {

	private static final String MSG_CONFIRM_CREAZIONE_COMPRENSORIO = "Confermi la creazione del Comprensorio? Hai inserito questi comuni:\n %s";
	private static final String MSG_NUOVO_COMUNE = "Nuovo Comune ";
	private static final String MSG_ERROR_COMUNE_NOT_VALID = "Comune gia' presente o non valido. Riprova.";
	private static final String MSG_ASK_NEW_COMPRENSORIO_WITH_EXIT = "Inserisci un nuovo comune per il comprensorio. Se vuoi annullare"
			+ " l'operazione, premi il bottone d'uscita. Quando avrai finito di inserire i dati, premi il bottone di conferma dati."
			+ " Se vuoi aggiungere o rimuovere (non sotto al minimo consentito) nuove aree di input, usa i bottoni Aggiungi e Rimuovi";
	
	private MyScorrimentoRisultati<AdapterComprensorioGraphicKit> scorrimento;
	
	private ViewGUI gui;
	
	private ModelService gestore;
	
	private MyAskMultipleData askDatas;
	
	private MenuWithButtons menu;

	/**
	 * Costruttore
	 * @param gUIInputDati
	 * @param warner
	 * @param gui
	 * @param gestore
	 * @since TESI
	 */
	public MenuConfiguratoreComprensoriGrafico(ViewGUI gui,
			ModelService gestore) {

		this.scorrimento = new MyScorrimentoRisultati<AdapterComprensorioGraphicKit>("Lista Comprensori");
		this.gui = gui;
		this.gestore = gestore;
		
		List<String> lista = new ArrayList<String>();
		lista.add(MSG_NUOVO_COMUNE);
		lista.add(MSG_NUOVO_COMUNE);
		this.askDatas = new MyAskMultipleData(MSG_ASK_NEW_COMPRENSORIO_WITH_EXIT, 
				lista, true, 1, 2);
		
		this.menu = new MenuWithButtons(new YesOrNoButtons());
	}
	
	private ViewGUI accediGUI() {
		return this.gui;
	}
	
	private ModelService accediGestore() {
		return this.gestore;
	}
	
	private MyScorrimentoRisultati<AdapterComprensorioGraphicKit> accediScorrimento() {
		return this.scorrimento;
	}
	
	private MyAskMultipleData accediAskDatas() {
		return this.askDatas;
	}
	
	private MenuWithButtons accediMenu() {
		return this.menu;
	}
	
	private void refreshGui(GraphicDisplayInterface newToDisplay) {
		this.accediGUI().changeToDisplay(newToDisplay);
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per la creazione di un comprensorio con l'uso di grafica
	 * @param proprietario Configuratore che sta creando il comprensorio (di cui ne diventera' proprietario)
	 * @return Comprensorio creato
	 * @Precondizione il configuratore non deve essere null
	 * @throws ErroreParametriNonConformi
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public Comprensorio creazioneComprensorioWithGraphic(Configuratore proprietario)
			throws ErroreParametriNonConformi, ErroreInterruzioneOperazione, ErroreDatiAssenti,
			ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		List<String> arrayComuni = new ArrayList<>();
		boolean corretto;
		corretto = false;
		boolean firstErrorToPositionate;
		int indexFirstError = 0;
		
		this.accediAskDatas().setPlaceholderMessageForAdded(ResultsConverter.converterToListFromSingle(MSG_NUOVO_COMUNE));
		this.accediAskDatas().resetToPlaceholderAndHideWarning();
		this.accediAskDatas().resetCounterForAddedAreas();
		
		do {
			this.refreshGui(this.accediAskDatas());
			arrayComuni = ResultsConverter.converterToListSingle(this.accediAskDatas().askCredentials());
			corretto = true;
			firstErrorToPositionate  = true;
			indexFirstError = 0;
			this.accediAskDatas().hideAllWarnings();
			if(arrayComuni.isEmpty()) {
				corretto = false;
				throw new ErroreDatiAssenti();
			}
			else {
				
				for(int i = 0; i < arrayComuni.size(); i++) {
					
					if(this.accediAskDatas().isTextEqualToPlaceholder(i)
							|| arrayComuni.get(i).equalsIgnoreCase("")) {
						corretto = false;
						this.accediAskDatas().setToPlaceholderAndAddWarning(
								MSG_ERROR_COMUNE_NOT_VALID, i);
						if(firstErrorToPositionate) {
							indexFirstError = i;
							firstErrorToPositionate = false;
						}
					}
					
					if(this.accediGestore().isComuneInArrayComprensori(arrayComuni.get(i))) {
						corretto = false;
						this.accediAskDatas().setToPlaceholderAndAddWarning(
								MSG_ERROR_COMUNE_NOT_VALID, i);
						if(firstErrorToPositionate) {
							indexFirstError = i;
							firstErrorToPositionate = false;
						}
					}
					
					for(int j = 0; j < arrayComuni.size(); j++) {
							
						if((i != j && arrayComuni.get(i).
								equalsIgnoreCase(arrayComuni.get(j)))) {
							
							corretto = false;
							this.accediAskDatas().setToPlaceholderAndAddWarning(
									MSG_ERROR_COMUNE_NOT_VALID, i);
							if(firstErrorToPositionate) {
								indexFirstError = i;
								firstErrorToPositionate = false;
							}
							
						}
					}
				}
			}
			
			if(!corretto) {
				this.accediAskDatas().scrollToSpecificInput(indexFirstError);
			}
			else {
				this.accediMenu().setMessage(String.format(MSG_CONFIRM_CREAZIONE_COMPRENSORIO, arrayComuni));
				this.refreshGui(this.accediMenu());
				int scelta = this.accediMenu().ottieniButtonPressed();
				if(scelta == InterazioneBottoni.CONFIRM_DATA.getIntValue()) {
					Comprensorio comprensorio = new Comprensorio(proprietario.getId(), arrayComuni);
					comprensorio.setId(UUID.randomUUID());
					return comprensorio;
				}
				else {
					corretto = false;
				}
			}
		}while(!corretto);
		return null;
	}
	
	/**
	 * Metodo per stampare a video tutti i comprensori con grafica
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @Precondizione L'Array dei Comprensori non e' vuoto
	 * @Postcondizione
	 * @since TESI
	 */
	public void letturaComprensori(List<Comprensorio> comprensori) throws
	ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		List<AdapterComprensorioGraphicKit> adapterGrafici = new ArrayList<AdapterComprensorioGraphicKit>();
		for(Comprensorio c : comprensori) {
			adapterGrafici.add(new AdapterComprensorioGraphicKit(c.getArrayComuni(),
					this.accediGestore().retrieveConfiguratoreById(c.getProprietarioId())));
		}
		this.accediScorrimento().setObjectsToScroll(adapterGrafici);
		this.refreshGui(this.accediScorrimento());
		this.accediScorrimento().scorriLista();
	}
}
