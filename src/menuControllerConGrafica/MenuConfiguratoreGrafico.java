package menuControllerConGrafica;

import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreParametriNonConformi;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import logica.ModelService;
import personalGraphicElements.ButtonsContainer;
import personalGraphicElements.ChooseMenuConfiguratoreOptionButtons;
import personalGraphicElements.MyJTextArea;
import strutture.InsiemeChiuso;
import strutture.Comprensorio;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import utente.Configuratore;
import utility.InterazioneBottoni;
import utility.InterazioneOperazioni;
import utility.UsefulPathEnum;

/**
 * Classe CONTROLLER per il menu del Configuratore con grafica
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuConfiguratoreGrafico extends MenuGrafico implements MetodoSceltaFogliaInterface{

	private static final String MSG_CHOOSE_FOGLIA_FOR_FDC = "Seleziona la Categoria di cui vuoi leggere i Fattori di Conversione. Le Categorie"
			+ " disponibili per la scelta sono segnate in verde.";
	private static final String MSG_CHOOSE_FOGLIA_FOR_PROPOSTA = "Seleziona una Categoria di cui vuoi leggere le Proposte disponibili. "
			+ "Le Categorie valide per la selezione sono segnate in verde. In caso non ci siano Proposte per quella Categoria, riceverai un"
			+ " messaggio di avviso";
	private static final String MSG_ERROR_LOADING = "Errore nel carimento. Riprova.";
	private static final String MSG_INFO_CONFIGURATORE_OPZIONI = "Spiegazione Opzioni Configuratore";
	private static final String MSG_ERROR_IO = "Errore nel salvataggio, riprova.";
	
	private static final int CASE_NEW_COMPRENSORIO = 1;
	private static final int CASE_NEW_GERARCHIA = 2;
	private static final int CASE_READ_COMPRENSORI = 3;
	private static final int CASE_READ_GERARCHIE = 4;
	private static final int CASE_READ_FATTORI_CONVERSIONE_FOGLIA = 5;
	private static final int CASE_READ_PROPOSTE_DI_FOGLIA = 6;
	private static final int CASE_READ_CICLI_DA_CHIUDERE = 7;
	private static final int CASE_READ_INFO = 8;
	
	private MenuConfiguratoreComprensoriGrafico menuComprensori;
	private MenuConfiguratoreGerarchiaGraphic menuGerarchie;
	private MenuConfiguratoreFDCGraphic menuFDC;
	private MenuConfiguratoreProposteGrafico menuProposte;
	private MenuConfiguratoreCicliChiusiGrafico menuCycles;
	
	private ButtonsContainer menuConfButtons;
	private MenuWithButtons startingMenu;
	
	/**
	 * Costruttore
	 * @param user
	 * @param gestore
	 * @param gui
	 * @param GUIInputDati
	 * @param warner
	 * @since TESI
	 */
	public MenuConfiguratoreGrafico(Configuratore user, ModelService gestore, ViewGUI gui,
			MyInputDati GUIInputDati, WarnerToUser warner) {
		
		super(user, gestore, gui, GUIInputDati, warner);
		
		this.menuConfButtons = new ChooseMenuConfiguratoreOptionButtons();
		
		this.menuComprensori = new MenuConfiguratoreComprensoriGrafico(gui, gestore);
		this.menuGerarchie = new MenuConfiguratoreGerarchiaGraphic(GUIInputDati, warner, gui, gestore);
		this.menuFDC = new MenuConfiguratoreFDCGraphic(GUIInputDati, warner, gui, gestore, this);
		this.menuProposte = new MenuConfiguratoreProposteGrafico(gestore, gui);
		this.menuCycles = new MenuConfiguratoreCicliChiusiGrafico(gestore, gui);
		
		this.startingMenu = new MenuWithButtons(menuConfButtons);
		
		super.setMenu(startingMenu);
	}

	private Configuratore ottieniConfiguratore() {
		return (Configuratore) super.ottieniUtente();
	}

	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public void azioniMenuGrafico() {
		int scelta = InterazioneOperazioni.NO_EVENT.getVal();
		do {
			
			List<InsiemeChiuso> cicliDaChiudere = null;
			try {
				cicliDaChiudere = this.accediGestore().ottieniCicliChiusi(this.ottieniConfiguratore().getId());
			} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
				this.accediWarner().warnUserException(e);
			} 
			if(cicliDaChiudere != null && !cicliDaChiudere.isEmpty()) {
				this.startingMenu.getMessage().setText(this.menuConfButtons.getText() + String.format(" Hai %d cicli da chiudere.", cicliDaChiudere.size()));
			}
			super.setMenu(startingMenu);
			try {
				scelta = super.accediMenu().ottieniButtonPressed();
			} catch (ErroreInterruzioneOperazione e) {
				scelta = InterazioneBottoni.EXIT.getIntValue();
			}
			switch(scelta) {
				case CASE_NEW_COMPRENSORIO:
					try {
						
						Comprensorio nuovoComprensorio = null;
						nuovoComprensorio = this.creazioneComprensorioWithGraphic();
						if(nuovoComprensorio != null) {
							this.accediGestore().addComprensorio(nuovoComprensorio);
							this.accediWarner().warnUserSuccessOperation();
						}
						else {
							throw new ErroreParametriNonConformi();
						}
						
					} catch (ErroreInterruzioneOperazione | ErroreDatiAssenti | ErroreServerUnreachable
							 | ErroreRispostaNonConforme | ErroreServerReply | ErroreParametriNonConformi | 
							 ErroreDatabaseNotWorking | IOException e) {
						this.accediWarner().warnUserException(e);
					} 
					break;
					
				case CASE_NEW_GERARCHIA:
					
					try {
						List<String> listaPerDoppioni = this.ottieniListaStringheDaGerarchie(this.accediGestore().getArrayGerarchie());
						this.creazioneGerarchia(listaPerDoppioni);
						this.accediWarner().warnUserSuccessOperation();
					} catch (ErroreInterruzioneOperazione | ErroreDatiAssenti | ErroreServerUnreachable
							 | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
						try {
							this.accediGestore().cleanGerarchiaDatabaseFromUncompletedGerarchias();
						} catch (ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e1) {
							this.accediWarner().warnUserException(e1);
						}
						this.accediWarner().warnUserException(e);
					} catch (IOException e) {
						this.accediWarner().warnUserWithCustomMessage(MSG_ERROR_IO);
					}
					break;
					
				case CASE_READ_COMPRENSORI:
					try {
						this.leggiComprensori(this.accediGestore().getArrayComprensori());
					} catch (ErroreInterruzioneOperazione | ErroreServerUnreachable
							 | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
					} catch (ErroreDatiAssenti e) {
						this.accediWarner().warnUserException(e);
					}
					break;
					
				case CASE_READ_GERARCHIE:
					try {
						this.leggiGerarchie(this.accediGestore().getArrayGerarchie());
					} catch (ErroreInterruzioneOperazione | ErroreServerUnreachable
							 | ErroreRispostaNonConforme | ErroreServerReply | ErroreDatabaseNotWorking e) {
					} catch (ErroreDatiAssenti e) {
						this.accediWarner().warnUserException(e);
					}
					break;
					
				case CASE_READ_FATTORI_CONVERSIONE_FOGLIA:
					try {
						this.letturaFDCDiFoglia(this.accediGestore().getArrayGerarchie());
					} catch (ErroreInterruzioneOperazione | ErroreDatabaseNotWorking e) {
					} catch (ErroreDatiAssenti | ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
							| ErroreParametriNonConformi e) {
						this.accediWarner().warnUserException(e);
					}
					break;
					
				case CASE_READ_PROPOSTE_DI_FOGLIA:
					try {
						this.letturaProposteDiUnaFoglia(
								this.accediGestore().getArrayGerarchie());
					} catch (ErroreInterruzioneOperazione | ErroreDatabaseNotWorking e) {
					} catch (ErroreDatiAssenti | ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply
							| ErroreParametriNonConformi e) {
						this.accediWarner().warnUserException(e);
					}
					break;
					
				case CASE_READ_CICLI_DA_CHIUDERE:
					try {
						this.letturaCicliChiusi();
					} catch (ErroreInterruzioneOperazione | ErroreDatabaseNotWorking e) {
					} catch (ErroreDatiAssenti | ErroreServerUnreachable | ErroreRispostaNonConforme | ErroreServerReply e) {
						this.accediWarner().warnUserException(e);
					}
					break;
				case CASE_READ_INFO:
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
	 * Metodo per creare un comprensorio con grafica
	 * @return Comprensorio creato
	 * @throws ErroreParametriNonConformi
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	private Comprensorio creazioneComprensorioWithGraphic()
			throws ErroreParametriNonConformi, ErroreInterruzioneOperazione, ErroreDatiAssenti,
			ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		return this.menuComprensori.creazioneComprensorioWithGraphic(this.ottieniConfiguratore());
	}

	/**
	 * Metodo per leggere comprensori con grafica
	 * @param lista lista da cui leggere Comprensori
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @since TESI
	 */
	private void leggiComprensori(List<Comprensorio> lista) throws 
	ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreServerUnreachable, ErroreRispostaNonConforme,
	ErroreServerReply, ErroreDatabaseNotWorking {
		this.menuComprensori.letturaComprensori(lista);
	}
	
	/**
	 * Metodo per leggere Gerarchie con grafica
	 * @param lista lista da cui leggere Gerarchie
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @since TESI
	 */
	private void leggiGerarchie(List<Gerarchia> lista) throws ErroreInterruzioneOperazione, ErroreDatiAssenti {
		super.scegliFogliaWithGraphic(lista, "", false);
		throw new ErroreInterruzioneOperazione();
	}
	
	/**
	 * Metodo di lettura dei Fattori di Conversione di una foglia con grafica
	 * @param lista lista da cui attingere per scegliere la foglia
	 * @throws ErroreParametriNonConformi
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	private void letturaFDCDiFoglia(List<Gerarchia> lista) 
			throws ErroreParametriNonConformi, ErroreInterruzioneOperazione,
			ErroreDatiAssenti, ErroreDatabaseNotWorking, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		Gerarchia gerScelta = super.scegliFogliaWithGraphic(lista, MSG_CHOOSE_FOGLIA_FOR_FDC, true);
		if(gerScelta == null) {
			throw new ErroreParametriNonConformi();
		}
		else {
			List<FattoreDiConversione> fdcs = this.accediGestore().retrieveAllFdcsFromFoglia(gerScelta);
			this.menuFDC.letturaFDCDiUnaFogliaWithGraphic(
					gerScelta.getCategoria(), fdcs);
		}
	}
	
	/**
	 * Metodo leggere le Proposte legate ad una foglia con grafica
	 * @param listaGerarchia lista di categorie da cui attingere per scegliere la foglia
	 * @throws ErroreParametriNonConformi
	 * @throws ErroreDatiAssenti
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	private void letturaProposteDiUnaFoglia(List<Gerarchia> listaGerarchia) throws
	ErroreParametriNonConformi, ErroreDatiAssenti, ErroreInterruzioneOperazione, 
	ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		Gerarchia gerScelta = super.scegliFogliaWithGraphic(listaGerarchia, MSG_CHOOSE_FOGLIA_FOR_PROPOSTA, true);
		if(gerScelta == null) {
			throw new ErroreParametriNonConformi();
		}
		else {
			this.menuProposte.letturaProposteDiUnaFoglia(gerScelta);
		}
	}
	
	/**
	 * Metodo per leggere cicli chiusi con grafica
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	private void letturaCicliChiusi() 
			throws ErroreInterruzioneOperazione, ErroreDatiAssenti, 
			ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		 try{
			 this.menuCycles.letturaOwnCicliDaChiudere(this.ottieniConfiguratore());
		 } catch(ErroreDatiAssenti e) {
			 this.accediWarner().warnUserException(e);
		 }
	}
	
	/**
	 * Metodo per creare una Gerarchia con grafica
	 * @param listaPerDoppioni lista di String da cui attingere per evitare nomi doppioni (evita ambiguita')
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti
	 * @throws IOException
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 */
	private void creazioneGerarchia(List<String> listaPerDoppioni) 
			throws ErroreInterruzioneOperazione, ErroreDatiAssenti, IOException,
			ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		Gerarchia radice = this.menuGerarchie.creazioneCategoriaWithGraphic(
				listaPerDoppioni, true, this.ottieniConfiguratore().getId(), null, null);
		
		List<String> figliPerDoppioni = new ArrayList<String>();
		
		figliPerDoppioni = this.ottieniListaStringheDaFigliDiGerarchia(radice);
		
		this.menuGerarchie.creazioneFigliWithGraphic(radice, figliPerDoppioni);
		
		this.accediGestore().addGerarchia(radice);
		
		this.menuFDC.impostazioneFattoriDiConversioneDiUnAlberoGerarchia(radice);
	}
	
	/**
	 * Metodo ausiliario per ottenere una lista di stringhe partendo da una lista di gerarchie (ne "estrae" i nomi) con grafica
	 * @param lista lista di gerarchie da cui partire
	 * @return lista di stringhe ricavata
	 * @since TESI
	 */
	private List<String> ottieniListaStringheDaGerarchie(List<Gerarchia> lista){
		List<String> result = new ArrayList<String>();
		for(Gerarchia g : lista) {
			result.add(g.getCategoria());
		}
		return result;
	}
	
	/**
	 * Metodo ausiliario per ottenere una lista di stringhe dai figli di una Categoria (ne "estrae" i nomi) con grafica
	 * @param ger Categoria da cui estrarre i nommi
	 * @return lista di stringhe ricavata
	 * @since TESI
	 */
	private List<String> ottieniListaStringheDaFigliDiGerarchia(Gerarchia ger) {
		List<String> lista = new ArrayList<String>();
		for(Gerarchia g : ger.getFigli()) {
			if(g.isFoglia()) {
				lista.add(g.getCategoria());
			}
			else {
				lista.addAll(this.ottieniListaStringheDaFigliDiGerarchia(ger));
			}
		}
		return lista;
	}
	
	/**
	 * Metodo per leggere le informazioni sulle opzioni
	 * @throws IOException
	 * @since TESI DATABASE
	 */
	private void readInfoOpzioni() throws IOException {
		String descrizione = Files.readString(Path.of(UsefulPathEnum.CONFIGURATORE_DESCRIPTION.getPath()));
		MyJTextArea area = new MyJTextArea(descrizione);
		area.setFont(area.getFont().deriveFont(46f));
		area.setFont(area.getFont().deriveFont(Font.ITALIC));
		this.accediWarner().warnUserWithMessageAndAdditionalComponent(area, MSG_INFO_CONFIGURATORE_OPZIONI);
	}

	@Override
	public Gerarchia scegliFoglia(List<Gerarchia> lista, String text, boolean leafSelecatble)
			throws ErroreInterruzioneOperazione, ErroreDatiAssenti {
		Gerarchia ger = super.scegliFogliaWithGraphic(lista, text, leafSelecatble);
		return ger;
	}
}
