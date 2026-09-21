package loginControllerConGrafica;

import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import graphicUI.GraphicDisplayInterface;
import graphicUI.GraphicImplementer;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.WarnerToUser;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import indirectionAccessToGraphicPanels.MyAskMultipleData;
import logica.ModelService;
import personalGraphicElements.ButtonsContainer;
import personalGraphicElements.ChooseLoginButtons;
import personalGraphicElements.MyJTextArea;
import utente.Configuratore;
import utente.Fruitore;
import utente.Utente;
import utility.InterazioneBottoni;
import utility.InterazioneOperazioni;
import utility.ResultsConverter;
import utility.UsefulPathEnum;

/**
 * Classe CONTROLLER per la gestione del login con grafica
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class LoginGrafico implements MetodiDaLoginGrafici, GraphicImplementer{

	private static final String MSG_ERROR_LETTURA_FILE = "Errore nella Lettura della informazioni. Riprova";
	private static final String MSG_INVALID_CREDENTIALS = "Username e/o password non valido, riprova";
	private static final String MSG_FUNZIONAMENTO_APPLICATIVO = "Funzionamento Applicativo";
	private static final String MSG_ASK_CREDENTIALS = "Inserisci le credenziali";
	
	private static final int INFO_APPLICATION_OPTION = 4;
	private static final int FRUITORE_REGISTRAZIONE_OPTION = 3;
	private static final int FRUITORE_LOGIN_OPTION = 2;
	private static final int CONFIGURATORE_LOGIN_OPTION = 1;
	
	/**
	 * gestore da cui prendere i dati
	 * @since 1
	 */
	private ModelService gestore;
	
	private LoginAccessoWithGraphic loginAccesso;
	
	private LoginRegistrazioneWithGraphic loginRegistrazione;
	
	private ButtonsContainer buttonsContainer;
	
	private ViewGUI gui;
	
	private MenuWithButtons menu;
		
	private WarnerToUser warner;
	
	private MyAskMultipleData askCredentials;
	
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END VARIABLES /////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Costruttore
	 * @param gestore
	 * @param view
	 * @param graphID
	 * @param warner
	 * @since TESI
	 */
	public LoginGrafico(ModelService gestore, ViewGUI view, WarnerToUser warner) {
		this.gestore = gestore;
		this.buttonsContainer = new ChooseLoginButtons();
		this.menu = new MenuWithButtons(buttonsContainer);
		this.gui = view;
		this.warner = warner;
		
		List<String> listaPlaceholders = new ArrayList<String>();
		listaPlaceholders.add("Username");
		listaPlaceholders.add("Password");
		this.askCredentials = new MyAskMultipleData(MSG_ASK_CREDENTIALS, listaPlaceholders,
				false, 1, 2);
		
		this.gui.changeToDisplay(menu);
		
		loginAccesso = new LoginAccessoWithGraphic(gestore, this.gui, this, this.warner);
		loginRegistrazione = new LoginRegistrazioneWithGraphic(gestore, this.gui, this.warner);
	}
	
	private ViewGUI accediGUI() {
		return this.gui;
	}
	
	private MenuWithButtons accediMenu() {
		return this.menu;
	}
	
	private WarnerToUser accediWarner() {
		return warner;
	}
	
	private void refreshGUI(GraphicDisplayInterface newDisplay) {
		this.accediGUI().changeToDisplay(newDisplay);
		this.accediGUI().repaint();
	}
	
	@Override
	public ViewGUI returnView() {
		return this.gui;
	}
	
	private LoginAccessoWithGraphic accediLoginAccesso() {
		return this.loginAccesso;
	}
	
	private LoginRegistrazioneWithGraphic accediLoginRegistrazione() {
		return this.loginRegistrazione;
	}
	
	private ModelService accediGestore() {
		return this.gestore;
	}
	
	private MyAskMultipleData accediAskCredentials() {
		return this.askCredentials;
	}
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per interagire con questo controller
	 * @return Utente loggato o null se si esce senza loggare
	 * @since TESI
	 */
	public Utente azioniLoginWithGraphic() {
		Utente user = null;
		int scelta = InterazioneOperazioni.NO_EVENT.getVal();
		do {
			this.refreshGUI(this.accediMenu());
			try {
				scelta = this.accediMenu().ottieniButtonPressed();
			} catch (ErroreInterruzioneOperazione e) {
				scelta = InterazioneBottoni.EXIT.getIntValue();
			}
			if(scelta != InterazioneOperazioni.NO_EVENT.getVal()) {
					
				switch(scelta){
					case CONFIGURATORE_LOGIN_OPTION: 
					try {
						user = this.loginConfiguratoreWithGraphic();
					} catch (ErroreInterruzioneOperazione | ErroreServerUnreachable |
							ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking | IOException e) {
						this.refreshGUI(this.accediWarner());
						this.accediWarner().warnUserException(e);
						break;
					}
						if(user != null) {
							return user;
						}
						break;
							
					case FRUITORE_LOGIN_OPTION:
					try {
						user = this.loginFruitoreWithGraphic();
					} catch (ErroreInterruzioneOperazione | ErroreServerUnreachable |
							ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
						this.refreshGUI(this.accediWarner());
						this.accediWarner().warnUserException(e);
						break;
					} 
						if(user != null) {
							return user;
						}
						break;
								
					case FRUITORE_REGISTRAZIONE_OPTION:
					try {
						this.registrazioneFruitoreWithGraphic();
					} catch (ErroreInterruzioneOperazione | ErroreServerUnreachable |
							ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking | IOException e) {
						this.refreshGUI(this.accediWarner());
						this.accediWarner().warnUserException(e);
					}
						break;
					case INFO_APPLICATION_OPTION:
					try {
						this.readInfoApplication();
					} catch (IOException e) {
						this.refreshGUI(this.accediWarner());
						this.accediWarner().warnUserWithCustomMessage(MSG_ERROR_LETTURA_FILE);
					}
						break;
					}
				}
		}while(scelta!=InterazioneBottoni.EXIT.getIntValue());
		
		return user;
	}
	
	/**
	 * Metodo per fare login come configuratore
	 * @return Configuratore loggato
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws IOException 
	 * @since TESI
	 */
	public Configuratore loginConfiguratoreWithGraphic() throws ErroreInterruzioneOperazione,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking, IOException {
		return this.accediLoginAccesso().loginConfiguratoreWithGraphic();
	}
	
	/**
	 * Metodo  per fare login come Fruitore
	 * @return Fruitore loggato
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public Fruitore loginFruitoreWithGraphic() throws ErroreInterruzioneOperazione,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.accediLoginAccesso().loginFruitoreWithGraphic();
	}
	
	/**
	 * Metodo per registrare un nuovo Fruitore nel sistema
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws IOException 
	 * @since TESI
	 */
	public void registrazioneFruitoreWithGraphic() throws ErroreInterruzioneOperazione,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking, IOException {
		this.accediLoginRegistrazione().registrazioneFruitoreWithGraphic();
	}
	
	/**
	 * Metodo per registrare un nuovo configuratore nel sistema
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws IOException 
	 * @since TESI
	 */
	@Override
	public void registrazioneConfiguratoreWithGraphic() throws ErroreInterruzioneOperazione,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking, IOException {
		this.accediLoginRegistrazione().registrazioneConfiguratoreWithGraphic();
	}
	
	private void readInfoApplication() throws IOException {
		
		this.refreshGUI(this.accediWarner());
		String descrizione = Files.readString(Path.of(UsefulPathEnum.APP_DESCRIPTION.getPath()));
		MyJTextArea area = new MyJTextArea(descrizione);
		area.setFont(area.getFont().deriveFont(46f));
		area.setFont(area.getFont().deriveFont(Font.ITALIC));
		this.accediWarner().warnUserWithMessageAndAdditionalComponent(area, MSG_FUNZIONAMENTO_APPLICATIVO);
	}
	
	/**
	 * Metodo per richiedere credenziali base (username, password) all'utente
	 * @param isDuplicatedBanned true se gli username duplicati non sono ammessi, false altrimenti. Il
	 * controllo sulla duplicazione avviene con gli username già esistenti
	 * @param message messaggio da inserire, se "" viene usato quello di default
	 * @return una List con primo elemento l'username e come secondo la password inserita
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	@Override
	public List<String> ritornaCredenzialiBaseWithGraphic(String message)
			throws ErroreInterruzioneOperazione, ErroreServerUnreachable,
			ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		List<String> credenziali = new ArrayList<String>();
		if(message.equalsIgnoreCase("")) {
			this.accediAskCredentials().setMessage(MSG_ASK_CREDENTIALS);
		}
		else {
			this.accediAskCredentials().setMessage(message);
		}
		this.refreshGUI(this.accediAskCredentials());
		credenziali = ResultsConverter.converterToListSingle(
				this.accediAskCredentials().askCredentials());
		return credenziali;
	}
	
	@Override
	public void hideWarningsAndResetToPlaceholderForCredentials() {
		this.askCredentials.resetToPlaceholderAndHideWarning();
	}
	
	@Override
	public void warnInvalidCredentials() {
		this.accediAskCredentials().resetAllToPlaceholder();
		this.accediAskCredentials().addWarningUnderAllInputArea(MSG_INVALID_CREDENTIALS);
	}
}
