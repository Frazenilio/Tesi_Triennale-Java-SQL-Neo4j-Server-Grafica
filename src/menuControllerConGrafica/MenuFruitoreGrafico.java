package menuControllerConGrafica;

import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.ModelService;
import personalGraphicElements.ButtonsContainer;
import personalGraphicElements.ChooseMenuFruitoreOptionButtons;
import personalGraphicElements.MyJTextArea;
import strutture.Gerarchia;
import strutture.Proposta;
import utente.Fruitore;
import utente.Utente;
import utility.InterazioneBottoni;
import utility.InterazioneOperazioni;
import utility.Stato;
import utility.UsefulPathEnum;

/**
 * Classe CONTROLLER per il menu del Fruitore con grafica
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuFruitoreGrafico extends MenuGrafico{

	private static final String MSG_ERROR_LOADING = "Errore nel caricamento, Riprova.";
	private static final String MSG_INFO_OPZIONI_FRUITORE = "Info Opzioni Fruitore";
	private static final String MSG_WARNER_ASK_RICHIESTA = "Ora prosegui per selezionare la Richiesta";
	private static final String MSG_WARNER_START_PROPOSTA_ASK_OFFERTA = "Stai iniziando la creazione della Proposta. Premi Prosegui per passare alla"
			+ " selezione della categoria che vuoi offrire";
	private static final String MSG_NO_OPEN_PROPOSTA_TO_WITHDRAW = "Non hai Proposte aperte da ritirare";
	private static final String MSG_ERROR_RITIRO_PROPOSTA = "Errore nel ritiro della Proposta. Riprova";
	private static final String MSG_ERROR_SAME_GERARCHIA_FOR_PROPOSTA = "Non puoi inserire offerta e richiesta uguali. riprova";
	private static final String MSG_ASK_GERARCHIA_RICHIESTA = "Adesso scegli la categoria richiesta. Le Categorie disponibili sono segnate in verde";
	private static final String MSG_ASK_GERARCHIA_OFFERTA = "Ora sceglierai la categoria d'offerta. Le Categorie disponibili sono segnate in verde";
	
	private static final int CASE_READ_INFO_OPZIONI = 5;
	private static final int CASE_READ_OWN_PROPOSTE = 4;
	private static final int CASE_RITIRA_PROPOSTA = 3;
	private static final int CASE_CREA_PROPOSTA = 2;
	private static final int CASE_READ_GERARCHIA = 1;
	
	private ButtonsContainer fruitoreButtons;
	private MenuWithButtons menu;
	
	private MenuFruitorePropostaGraphic menuProposte;
	
	/**
	 * Costruttore
	 * @param user
	 * @param gestore
	 * @param gui
	 * @param GUIInputDati
	 * @param warner
	 * @since TESI
	 */
	public MenuFruitoreGrafico(Utente user, ModelService gestore, ViewGUI gui, MyInputDati GUIInputDati,
			WarnerToUser warner) {
		
		super(user, gestore, gui, GUIInputDati, warner);
		
		this.menuProposte = new MenuFruitorePropostaGraphic(GUIInputDati, warner, gui, gestore);
		
		this.fruitoreButtons = new ChooseMenuFruitoreOptionButtons();
		
		this.menu = new MenuWithButtons(fruitoreButtons);
		
		super.setMenu(menu);
	}

	private Fruitore ottieniFruitore() {
		return (Fruitore) super.ottieniUtente();
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	@Override
	public void azioniMenuGrafico() {
		int scelta = InterazioneOperazioni.NO_EVENT.getVal();
		do {
			super.setMenu(menu);
			
			try {
				scelta = super.accediMenu().ottieniButtonPressed();
			} catch (ErroreInterruzioneOperazione e) {
				scelta = InterazioneBottoni.EXIT.getIntValue();
			}
			
			switch(scelta) {
			case CASE_READ_GERARCHIA:
				try {
					this.letturaGerarchie(this.accediGestore().getArrayGerarchie());
				} catch (ErroreInterruzioneOperazione | ErroreDatiAssenti | ErroreServerUnreachable
						| ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				} 
				break;
				
			case CASE_CREA_PROPOSTA:
				try {
					this.creazioneProposta(this.accediGestore().getArrayGerarchie(), this.ottieniFruitore());
					this.accediWarner().warnUserSuccessOperation();
				} catch (ErroreInterruzioneOperazione | ErroreDatiAssenti | ErroreServerUnreachable 
						| ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
					this.accediWarner().warnUserException(e);
				} 
				break;
				
			case CASE_RITIRA_PROPOSTA:
				
				
				try {
					List<Proposta> lista = this.accediGestore().ottieniProposteUtente(this.ottieniFruitore().getId());
					HashMap<Stato, List<Proposta>> mappa = super.dividiArrayPerStato(lista);
					this.ritiraProposta(mappa.get(Stato.APERTO));
					this.accediWarner().warnUserSuccessOperation();
				} catch (IOException e) {
					this.accediWarner().warnUserWithCustomMessage(MSG_ERROR_RITIRO_PROPOSTA);
				} catch (ErroreInterruzioneOperazione | ErroreDatiAssenti | ErroreServerUnreachable 
						| ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
					this.accediWarner().warnUserException(e);
				} 
				
				break;
				
			case CASE_READ_OWN_PROPOSTE:
				
				try {
					List<Proposta> proposte = this.accediGestore().ottieniProposteUtente(this.ottieniFruitore().getId());
					this.letturaProposte(proposte);
				} catch (ErroreInterruzioneOperazione | ErroreDatiAssenti | ErroreServerUnreachable 
						| ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				} 
				break;
				
			case CASE_READ_INFO_OPZIONI:
				try {
					this.readInfoOpzioni();
				} catch (IOException e) {
					this.accediWarner().warnUserWithCustomMessage(MSG_ERROR_LOADING);
				}
				break;
			}
		}while(scelta != InterazioneBottoni.EXIT.getIntValue());
	}
	
	/**
	 * Metodo per leggere le Gerarchie con grafica
	 * @param lista lista da cui leggere le Gerarchie
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @since TESI
	 */
	private void letturaGerarchie(List<Gerarchia> lista) throws ErroreInterruzioneOperazione, ErroreDatiAssenti {
		super.scegliFogliaWithGraphic(lista, "", false);
		throw new ErroreInterruzioneOperazione();
	}
	
	/**
	 * Metodo per creare una proposta con grafica
	 * @param lista lista da cui scegliere le Categorie per la Proposta
	 * @param proprietario Fruitore proprietario della Proposta
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	private void creazioneProposta(List<Gerarchia> lista, Fruitore proprietario) 
			throws ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreServerUnreachable,
			ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		this.accediWarner().warnUserWithCustomMessage(MSG_WARNER_START_PROPOSTA_ASK_OFFERTA);
		Gerarchia offerta = super.scegliFogliaWithGraphic(lista, MSG_ASK_GERARCHIA_OFFERTA, true);
	
		if(offerta!=null) {
		
			this.accediWarner().warnUserWithCustomMessage(MSG_WARNER_ASK_RICHIESTA);
			Gerarchia richiesta = super.scegliFogliaWithGraphic(lista, MSG_ASK_GERARCHIA_RICHIESTA, true);
			
			if(richiesta!=null) {
				if(!(offerta.equals(richiesta))) {
					this.menuProposte.creaPropostaWithGraphic(offerta, richiesta, proprietario);
				}
				else {
					this.accediWarner().warnUserWithCustomMessage(MSG_ERROR_SAME_GERARCHIA_FOR_PROPOSTA);
				}
			}
			else {
				throw new ErroreInterruzioneOperazione();
			}
		}
		else {
			throw new ErroreInterruzioneOperazione();
		}
	}
	
	/**
	 * Metodo per ritirare una proposta con grafica
	 * @param lista lista da cui attingere per scegliere la Proposta da ritirare
	 * @throws IOException
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	private void ritiraProposta(List<Proposta> lista) throws
	IOException, ErroreInterruzioneOperazione, ErroreDatiAssenti,
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		if(lista.isEmpty()) {
			this.accediWarner().warnUserWithCustomMessage(MSG_NO_OPEN_PROPOSTA_TO_WITHDRAW);
		}
		else{
			this.menuProposte.cambiaStatoPropostaDaLista(lista, Stato.RITIRATO);
		}
	}
	
	/**
	 * Metodo per leggere proposte con grafica
	 * @param lista Proposte da leggere
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @since TESI
	 */
	private void letturaProposte(List<Proposta> lista) throws 
	ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreServerUnreachable, 
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		this.menuProposte.scorriProposte(lista);
	}
	
	private void readInfoOpzioni() throws IOException {
		String descrizione = Files.readString(Path.of(UsefulPathEnum.FRUITORE_DESCRIPTION.getPath()));
		MyJTextArea area = new MyJTextArea(descrizione);
		area.setFont(area.getFont().deriveFont(46f));
		area.setFont(area.getFont().deriveFont(Font.ITALIC));
		this.accediWarner().warnUserWithMessageAndAdditionalComponent(area, MSG_INFO_OPZIONI_FRUITORE);
	}
}
