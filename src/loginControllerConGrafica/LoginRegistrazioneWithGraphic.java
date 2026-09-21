package loginControllerConGrafica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import graphicUI.GraphicDisplayInterface;
import graphicUI.ListSelectionButtons;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.WarnerToUser;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import indirectionAccessToGraphicPanels.MyAskMultipleData;
import logica.ModelService;
import strutture.Comprensorio;
import utente.Configuratore;
import utente.Fruitore;
import utility.CredenzialiPredefinite;
import utility.InterazioneBottoni;
import utility.ResultsConverter;

/**
 * Classe per la gestione delle registrazioni con grafica
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class LoginRegistrazioneWithGraphic {

	private static final String MSG_PLACEHOLDER_COGNOME = "Cognome";
	private static final String MSG_PLACEHOLDER_NOME = "Nome";
	private static final String MSG_CONFIRM_BUTTON_NUOVO_FRUITORE = "Crea Fruitore";
	private static final String MSG_PASSWORD_MISMATCH = "Le Password non coincidono, riprova";
	private static final String MSG_ASK_NEW_CREDENTIALS = "Inserisci le tue nuove credenziali";
	private static final String MSG_PLACEHOLDER_MAIL = "Mail";
	private static final String MSG_PLACEHOLDER_CONFIRM_PASSWORD = "Conferma Password";
	private static final String MSG_PLACEHOLDER_PASSWORD = "Password";
	private static final String MSG_PLACEHOLDER_USERNAME = "Username";
	private static final String MSG_REQUEST_COMPRENSORIO = "Inserimento Comprensorio Obbligatorio. Scegli il tuo";
	private static final String MSG_START_NEW_CONFIGURATORE = "Hai inserito le credenziali predefinite per un configuratore. Benvenuto/a!\n";
	private static final String ALREADY_TAKEN_EMAIL = "Questa mail e' gia' presa, usane un'altra\n";
	private static final String ALREADY_TAKEN_USERNAME = "Questo username e' gia' stato utilizzato o non e' valido";
	private static final String MSG_SUCCESS_NEW_ACCOUNT = "Hai creato il tuo account! Ora puoi effettuare l'accesso";
	
	private ModelService gestore;
		
	private ViewGUI gui;
		
	private MenuWithButtons menu;
	
	private WarnerToUser warner;
	
	private MyAskMultipleData askDatasFruitore;
	
	private MyAskMultipleData askDatasConfiguratore;
	
	/**
	 * Costruttore
	 * @param gestore
	 * @param gui
	 * @param graphicInputDati
	 * @param metodiDaLogin
	 * @param warner
	 * @since TESI
	 */
	public LoginRegistrazioneWithGraphic(ModelService gestore, ViewGUI gui, WarnerToUser warner) {
		this.gestore = gestore;
		this.gui = gui;
		this.warner = warner;
		this.menu = new MenuWithButtons(new ListSelectionButtons(new ArrayList<Comprensorio>(), MSG_REQUEST_COMPRENSORIO));
		
		List<String> placeholders = new ArrayList<String>();
		placeholders.add(MSG_PLACEHOLDER_USERNAME);
		placeholders.add(MSG_PLACEHOLDER_PASSWORD);
		placeholders.add(MSG_PLACEHOLDER_CONFIRM_PASSWORD);
		placeholders.add(MSG_PLACEHOLDER_NOME);
		placeholders.add(MSG_PLACEHOLDER_COGNOME);
		
		this.askDatasConfiguratore = new MyAskMultipleData(MSG_ASK_NEW_CREDENTIALS,
				placeholders,
				false,
				1, 
				placeholders.size());
		
		placeholders.add(MSG_PLACEHOLDER_MAIL);
		
		this.askDatasFruitore = new MyAskMultipleData(MSG_ASK_NEW_CREDENTIALS,
				placeholders,
				false,
				1, 
				placeholders.size());
		this.askDatasFruitore.changeConfirmMessage(MSG_CONFIRM_BUTTON_NUOVO_FRUITORE);
	}
	
	/**
	 * @return the menu
	 */
	private MenuWithButtons accediMenu() {
		return menu;
	}
	
	private WarnerToUser accediWarner() {
		return warner;
	}
	
	/**
	 * @return the gui
	 */
	private ViewGUI accediGui() {
		return gui;
	}
	
	private ModelService accediGestore() {
		return this.gestore;
	}
	
	private MyAskMultipleData accediAskDatasFruitore() {
		return this.askDatasFruitore;
	}
	
	private MyAskMultipleData accediAskDatasConfiguratore() {
		return this.askDatasConfiguratore;
	}
	
	private void refreshGUI(GraphicDisplayInterface newDisplay) {
		this.accediGui().changeToDisplay(newDisplay);
		this.accediGui().repaint();
	}
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per registrare un nuovo fruitore con grafica
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws IOException 
	 * @Precondizione arrayUtenti non deve contenere un configuratore con lo stesso nome utente
	 * @Postcondizione attayUtenti ha il nuovo fruitore
	 * @since TESI
	 */
	public void registrazioneFruitoreWithGraphic() throws
	ErroreInterruzioneOperazione, ErroreServerUnreachable, 
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking, IOException {
		
		this.accediMenu().replaceButtonContainer(new ListSelectionButtons(
				this.gestore.getArrayComprensori(), MSG_REQUEST_COMPRENSORIO));
		this.refreshGUI(this.accediMenu());
		int sceltaComprensorio = this.accediMenu().ottieniButtonPressed();
				
		if(sceltaComprensorio == InterazioneBottoni.EXIT.getIntValue()) {
			this.refreshGUI(this.accediWarner());
			this.accediWarner().warnUserOperationCancelled();
		}
		else{
			boolean corretto = false;
			List<String> credenziali = new ArrayList<String>();
			this.accediAskDatasFruitore().resetToPlaceholderAndHideWarning();
			do {
				this.refreshGUI(this.accediAskDatasFruitore());
				credenziali = ResultsConverter.converterToListSingle(this.accediAskDatasFruitore().askCredentials());
				corretto = true;
				this.accediAskDatasFruitore().hideAllWarnings();
				corretto = this.controllaCredenziali(credenziali, true);
				if(corretto) {
					if(this.accediGestore().isFruitoreEmailAlreadyTaken(credenziali.get(5))) {
						corretto = false;
						this.accediAskDatasFruitore().
						setToPlaceholderAndAddWarning(ALREADY_TAKEN_EMAIL, 5);
					}
					
				}
			}while(!corretto);
			Fruitore newFruitore = new Fruitore(credenziali.get(0), credenziali.get(5),
					this.accediGestore().getArrayComprensori().get(sceltaComprensorio-1).getId());
			newFruitore.setId(UUID.randomUUID());
			this.accediGestore().addFruitore(newFruitore, credenziali.get(3), credenziali.get(4), credenziali.get(1));
			this.refreshGUI(this.accediWarner());
			this.accediWarner().warnUserWithCustomMessage(MSG_SUCCESS_NEW_ACCOUNT);
		}
	}
	
	/**
	 * Metodo per registrare un nuovo configuratore nell'array fornito con grafica
	 * Se l'username e' gia' stato usato, richiede l'username all'utente
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws IOException 
	 * @Precondizione arrayUtenti non deve contenere un utente con lo stesso nome utente
	 * @Invariante: l'username non puo' essere l'username predefinito
	 * @Postcondizione arrayUtenti ha il nuovo configuratore
	 * @since TESI
	 */
	public void registrazioneConfiguratoreWithGraphic() throws
	ErroreInterruzioneOperazione, ErroreServerUnreachable, ErroreRispostaNonConforme, 
	ErroreServerReply, ErroreDatabaseNotWorking, IOException {
		this.refreshGUI(this.accediWarner());
		this.accediWarner().warnUserWithCustomMessage(MSG_START_NEW_CONFIGURATORE);
		List<String> credenziali = new ArrayList<String>();
		this.accediAskDatasConfiguratore().resetToPlaceholderAndHideWarning();
		boolean corretto;
			do {
				this.refreshGUI(this.accediAskDatasConfiguratore());
				credenziali = ResultsConverter.converterToListSingle(this.accediAskDatasConfiguratore().askCredentials());
				corretto = true;
				this.accediAskDatasConfiguratore().hideAllWarnings();
				corretto = this.controllaCredenziali(credenziali, false);

				String username = credenziali.get(0);

				String password = credenziali.get(1);
				if(corretto) {
					Configuratore conf = new Configuratore(username);
					conf.setId(UUID.randomUUID());
					this.accediGestore().addConfiguratore(conf, credenziali.get(3), credenziali.get(4), password);
					this.refreshGUI(this.accediWarner());
					this.accediWarner().warnUserWithCustomMessage(MSG_SUCCESS_NEW_ACCOUNT);
				}
			}while(!corretto);
		}
	
	private boolean controllaCredenziali(List<String> credenziali, boolean fruitoreOrNot) throws
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		
		MyAskMultipleData ask;
		if(fruitoreOrNot) {
			ask = this.accediAskDatasFruitore();
		}
		else {
			ask = this.accediAskDatasConfiguratore();
		}
		
		//PASSWORD NON COINCIDONO
		if(!credenziali.get(1).equals(credenziali.get(2))) {
			ask.scrollToSpecificInput(1);
			ask.setToPlaceholderAndAddWarning(MSG_PASSWORD_MISMATCH, 1);
			ask.setToPlaceholderAndAddWarning(MSG_PASSWORD_MISMATCH, 2);
			return false;
			
		}
		//NOME O COGNOME VUOTI
		if(credenziali.get(3).isBlank() || ask.isTextEqualToPlaceholder(3)) {
			ask.setToPlaceholderAndAddWarning("Nome non valido", 3);
			ask.scrollToSpecificInput(3);
			return false;
		}
		if(credenziali.get(4).isBlank() || ask.isTextEqualToPlaceholder(4)) {
			ask.setToPlaceholderAndAddWarning("Cognome non valido", 4);
			ask.scrollToSpecificInput(4);
			return false;
		}
		//USERNAME PREDEFINITO INSERITO O VUOTO
		if (credenziali.get(0).equalsIgnoreCase(CredenzialiPredefinite.PASSWORD_PREDEFINITA.getVal())
				|| ask.isTextEqualToPlaceholder(0) || credenziali.get(0).isBlank()) {
			ask.scrollToSpecificInput(0);
			ask.setToPlaceholderAndAddWarning(ALREADY_TAKEN_USERNAME, 0);
			return false;
		}
		if(this.accediGestore().isConfiguratoreNameAlreadyTaken(credenziali.get(0)) 
				|| this.accediGestore().isFruitoreNameAlreadyTaken(credenziali.get(0)) ) {
			ask.setToPlaceholderAndAddWarning(ALREADY_TAKEN_USERNAME, 0);
			
			return false;
		}
		
		return true;
	}
}
