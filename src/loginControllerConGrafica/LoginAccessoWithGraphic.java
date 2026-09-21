package loginControllerConGrafica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.ModelService;
import utente.Configuratore;
import utente.Fruitore;
import utility.CredenzialiPredefinite;

/**
 * Classe per la gestione degli accessi al login con grafica
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class LoginAccessoWithGraphic {

	private static final String MSG_ASK_CREDENTIALS_FRUITORE = "Inserisci le credenziali"
			+ " per effettuare l'accesso come Fruitore";
	private static final String MSG_ASK_CREDENTIALS_CONFIGURATORE = "Inserisci le credenziali. Se invece"
			+ " vuoi creare un nuovo account Configuratore, inserisci le credenziali predefinite fornite";
	private static final String MSG_ERROR_LOGIN = "Username e/o passowrd non corretti, riprova";
	private static final String MSG_SUCCESS_LOGIN = "Accesso effettuato, bentornato/a %s";

	
	private ModelService gestore;
	
	private MetodiDaLoginGrafici metodiDaLogin;
	
	private ViewGUI gui;
		
	private WarnerToUser warner;
	
	/**
	 * Costruttore
	 * @param gestore 
	 * @param gui
	 * @param GUIInputDati
	 * @param metodiDaLogin
	 * @param warner
	 * @since TESI
	 */
	public LoginAccessoWithGraphic(ModelService gestore, ViewGUI gui,
			MetodiDaLoginGrafici metodiDaLogin, WarnerToUser warner) {
		this.gestore = gestore;
		this.gui = gui;
		this.metodiDaLogin = metodiDaLogin;
		this.warner = warner;
	}
	
	/**
	 * @return the gui
	 */
	private ViewGUI accediGui() {
		return gui;
	}

	private WarnerToUser accediWarner() {
		if(this.accediGui().getToDisplay() != this.warner) {
			this.accediGui().changeToDisplay(warner);
		}
		return warner;
	}
	
	private ModelService accediGestore() {
		return gestore;
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per fare login del fruitore 
	 * @Precondizione l'arrayFruitori non deve essere null
	 * @Postcondizione
	 * @return il fruitore loggato
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public Fruitore loginFruitoreWithGraphic() 
			throws ErroreInterruzioneOperazione, ErroreServerUnreachable, 
			ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		boolean loggato = false;
		metodiDaLogin.hideWarningsAndResetToPlaceholderForCredentials();
		while(!loggato) {
			
			List<String> credenziali = metodiDaLogin.ritornaCredenzialiBaseWithGraphic(
					MSG_ASK_CREDENTIALS_FRUITORE);
			Fruitore userTrovato = null;
			try {
				userTrovato = this.accediGestore().isFruitoreSavedInDatabase(
					credenziali.get(0), credenziali.get(1));
			} catch(ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				metodiDaLogin.warnInvalidCredentials();
			}
			if(userTrovato != null) {
				loggato = true;
				this.accediWarner().warnUserWithCustomMessage(String.format(MSG_SUCCESS_LOGIN, userTrovato.getUsername()));
				return userTrovato;
			}
		}
		this.accediWarner().warnUserWithCustomMessage(MSG_ERROR_LOGIN);
		return null;
	}
	
	
	/**
	 * Metodo per far loggare un Configuratore: Prende da input i dati richiesti (username, password) tramite scan
	 * e controlla la presenza della tupla fornita nell'array di paramentro
	 * I messaggi di successo o fallimento login sono gia' inclusi qui
	 * @Precondizione l'arrayConfiguratoori non deve essere null
	 * @Postcondizione
	 * @return il configuratore trovato. altrimenti ritorna null
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws IOException 
	 * @since TESI
	 */
	public Configuratore loginConfiguratoreWithGraphic() throws
	ErroreInterruzioneOperazione, ErroreServerUnreachable, ErroreRispostaNonConforme,
	ErroreServerReply, ErroreDatabaseNotWorking, IOException {
		boolean loggato = false;
		List<String> credenziali = new ArrayList<String>();
		metodiDaLogin.hideWarningsAndResetToPlaceholderForCredentials();
		while(!loggato) {

			credenziali = metodiDaLogin.ritornaCredenzialiBaseWithGraphic( MSG_ASK_CREDENTIALS_CONFIGURATORE);

			if(credenziali.get(0).equalsIgnoreCase(CredenzialiPredefinite.USERNAME_PREDEFINITO.getVal()) &&
					credenziali.get(1).equals(CredenzialiPredefinite.PASSWORD_PREDEFINITA.getVal())) {
				
				metodiDaLogin.registrazioneConfiguratoreWithGraphic();
				return null;
			}
			Configuratore userTrovato = null;
			
			try{
				userTrovato = this.accediGestore().isConfiguratoreSavedInDatabase(credenziali.get(0), credenziali.get(1));
			} catch(ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				metodiDaLogin.warnInvalidCredentials();
			}			
			
			if(userTrovato != null) {
				loggato = true;
				this.accediWarner().warnUserWithCustomMessage(String.format(MSG_SUCCESS_LOGIN, userTrovato.getUsername()));
				return userTrovato;
			}
		}
		this.accediWarner().warnUserWithCustomMessage(MSG_ERROR_LOGIN);
		return null;
	}
	
}
