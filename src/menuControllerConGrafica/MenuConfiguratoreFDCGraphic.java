package menuControllerConGrafica;

import java.util.ArrayList;
import java.util.List;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreDatiAssenti;
import errori.ErroreFDCOutOfBounds;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import graphicUI.GraphicDisplayInterface;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.MyScorrimentoRisultati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.ModelService;
import personalGraphicElements.YesOrNoButtons;
import strutture.AdapterFattoreDiConversioneGraphicKit;
import strutture.FattoreDiConversione;
import strutture.Gerarchia;
import utility.InterazioneOperazioni;
import utility.Tupla;

/**
 * Classe per la gestione dei Fattori di Conversione dal menu Configuratore
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuConfiguratoreFDCGraphic {


	private static final String MSG_PLACEHOLDER_FDC = "Fattore di Conversione";
	private static final String MSG_SCORRIMENTO_FDC = "Scorrimento dei Fattori di Conversione";
	private static final String MSG_SCORRIMENTO_FDC_GERARCHIA = "Scorrimento Fattori di Conversione di %s";
	private static final String MSG_CATEGORIA_STILL_TO_SET_FDC = "La categoria scelta deve ancora essere impostata con Fattore"
			+ " di Conversione, non e' selezionabile";
	private static final String MSG_ASK_CATEGORIA_FOR_FDC = "[%s] Scegli la Categoria con cui impostare il primo Fattore"
			+ " di Conversione. Gli altri verranno calcolati automaticamente";
	private static final String MSG_FOGLIA_ATTUALE = "Foglia attuale: %s";
	private static final String MSG_IMPOSTAZIONE_FDC = "Impostazione Fattori di Conversione per le foglie della Gerarchia %s";
	private static final String MSG_ASK_FDC = "Inserisci il corrispondente fattore di conversione con la gerarchia %s. Min: %.2f; Max: %.2f. "
			+ "Il valore accetta decimali da inserire col punto. Se premi esci, riselezionerai la categoria";
	private static final String MSG_ERROR_OUT_OF_BOUNDS_FDC = "Errore, non ha inserito valori validi. riprova\n";
	
	private MyInputDati inputDati;
	
	private ViewGUI gui;
	
	private ModelService gestore;
	
	private MenuWithButtons menu;
	
	private MyScorrimentoRisultati<AdapterFattoreDiConversioneGraphicKit> scorrimento;
	
	private WarnerToUser warner;
	
	private MetodoSceltaFogliaInterface metodoSceltaFoglia;
	
	/**
	 * Costruttore
	 * @param inputDati
	 * @param warner
	 * @param gui
	 * @param gestore
	 * @since TESI
	 */
	public MenuConfiguratoreFDCGraphic(MyInputDati inputDati, WarnerToUser warner,
			ViewGUI gui, ModelService gestore, MetodoSceltaFogliaInterface metodoFoglia) {
		super();
		this.inputDati = inputDati;
		this.gui = gui;
		this.gestore = gestore;
		this.warner = warner;
		this.metodoSceltaFoglia = metodoFoglia;
		
		this.scorrimento = new MyScorrimentoRisultati<AdapterFattoreDiConversioneGraphicKit>(MSG_SCORRIMENTO_FDC);
		this.menu = new MenuWithButtons(new YesOrNoButtons());
	}
	
	private ViewGUI accediGUI() {
		return this.gui;
	}
	
	private ModelService accediGestore() {
		return this.gestore;
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
	
	private MyScorrimentoRisultati<AdapterFattoreDiConversioneGraphicKit> accediScorrimento(){
		return this.scorrimento;
	}
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per leggere i Fattori di Conversione di una data Categoria
	 * @param ger Categoria di cui leggere i Fattori di Conversione
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public void letturaFDCDiUnaFogliaWithGraphic(String categoria, List<FattoreDiConversione> fdcs) 
			throws ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreDatabaseNotWorking,
			ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply {
		if(fdcs.isEmpty()) {
			throw new ErroreDatiAssenti();
		}
		List<AdapterFattoreDiConversioneGraphicKit> adapterList = new ArrayList<AdapterFattoreDiConversioneGraphicKit>();
		for(FattoreDiConversione fdc : fdcs) {
			adapterList.add(new AdapterFattoreDiConversioneGraphicKit(fdc.getFdc().getSecond(),
					this.accediGestore().retrieveGerarchiaById(fdc.getFdc().getFirst()).getCategoria()));
		}
		this.accediScorrimento().setMessageText(String.format(MSG_SCORRIMENTO_FDC_GERARCHIA, categoria));
		this.refreshGUI(this.accediScorrimento());
		this.accediScorrimento().setObjectsToScroll(adapterList);
		this.accediScorrimento().scorriLista();
	}
	
	/**
	 * Metodo per impostare i FDC delle foglie di una certa Gerarchia radice con grafica
	 * @param radiceDaImpostare radice da cui prendere le foglie (utilizzata per considerare le foglie se presenti della gerarchia corrente)
	 * @param gerCorrente Categoria di cui si sta impostando un FDC (inizialmente i due parameri coincidono)
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @Precondizione la radice deve avere delle foglie
	 * @Postcondizione le foglie delle radice hanno FDC impostati
	 * @since TESI
	 */
	public void impostazioneFattoriDiConversioneDiUnAlberoGerarchia(Gerarchia radiceDaImpostare)
			throws ErroreDatiAssenti, ErroreInterruzioneOperazione, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		this.accediInputDati().setPlaceholder(MSG_PLACEHOLDER_FDC);
		this.refreshGUI(this.accediWarner());
		this.accediWarner().warnUserWithCustomMessage(String.format(MSG_IMPOSTAZIONE_FDC, radiceDaImpostare.getCategoria()));
		
		if(radiceDaImpostare.getFigli().isEmpty()) {
			throw new ErroreDatiAssenti();
		}
		
		for(Gerarchia figlio : radiceDaImpostare.getFigli()) {
			if(figlio.isToSetFDC()) {
				
				this.refreshGUI(this.accediWarner());
				this.accediWarner().warnUserWithCustomMessage(String.format(MSG_FOGLIA_ATTUALE, figlio.getCategoria()));
				
				this.impostaFattoriDiConversione(figlio);
			}
			else if(!figlio.getFigli().isEmpty()) {
				this.impostazioneFattoriDiConversioneDiUnAlberoGerarchia(figlio);
			}
		}
	}
	
	/**
	 * Metodo per impostare i fattori di conversione di una foglia con grafica
	 * @param nuovaGerarchia la Gerarchia foglia a cui impostare i FDC
	 * @param radice l'ultima radice aggiunta per considerarne le foglie (genericamente e' la radice della nuovaGerarchia)
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @Precondizione nuovaGerarchia deve essere una foglia e non nulla
	 * @Postcondizione i FDC di nuovaGerarchia devono essere impostati
	 * @since TESI
	 */
	public void impostaFattoriDiConversione(Gerarchia nuovaGerarchia)
			throws ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreServerUnreachable,
			ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		
		Gerarchia gerScelta;				
		boolean corretto = false;
		double fdc = 0;
		boolean ok = false;
			do {
				this.accediInputDati().hideWarning();
				ok = false;
				do {
					gerScelta = this.metodoSceltaFoglia.scegliFoglia(this.accediGestore().getArrayGerarchie(),
							String.format(MSG_ASK_CATEGORIA_FOR_FDC, nuovaGerarchia.getCategoria()), true);
					if(gerScelta == null) {
						throw new ErroreInterruzioneOperazione();
					}
					else if(gerScelta.isToSetFDC()) {
						this.refreshGUI(this.accediWarner());
						this.accediWarner().warnUserWithCustomMessage(MSG_CATEGORIA_STILL_TO_SET_FDC);
					}
					else {
						Tupla<Double, Double> minMax = this.accediGestore().calcolaMinMax(gerScelta, nuovaGerarchia);
						this.refreshGUI(this.accediInputDati());
						try{
							fdc = this.AskFDCInBoundsUnitlCorrect(minMax, gerScelta);
							ok = true;
						}catch(ErroreInterruzioneOperazione e) {
							ok = false;
						} 
					}
				}while(!ok);
					
				this.accediMenu().replaceButtonContainer(new YesOrNoButtons());
				this.refreshGUI(this.accediMenu());
				int scelta = this.accediMenu().ottieniButtonPressed();
				corretto = true;
					
				if(scelta == InterazioneOperazioni.CANCEL_OPERATION.getVal()) {
					corretto = false;
				}
						
				if(corretto) {
					try {
						this.accediGestore().calcolaFattoriConversione(
								gerScelta, nuovaGerarchia,
								fdc);
					}catch(ErroreFDCOutOfBounds e) {
						corretto = false;
						this.refreshGUI(this.accediWarner());
						this.accediWarner().warnUserException(e);
					}
				}
			}while(!corretto);
			
		nuovaGerarchia.setToSetFDC(false);
	}	
	
	
	/////////////////////////////////////////////////////////////////////////
	//////////////////////// AUXILIARY METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo ausiliario per richiedere un FDC entro i limiti
	 * @param minMax Tupla di limite inferiore e superiore da considerare
	 * @param gerScelta la catgeoria a cui attribuire il FDC
	 * @return il Fatttore di Converisone da input entro i limiti
	 * @throws ErroreInterruzioneOperazione
	 * @since TESI
	 */
	private double AskFDCInBoundsUnitlCorrect(Tupla<Double, Double> minMax, Gerarchia gerScelta) throws ErroreInterruzioneOperazione {
		boolean finito = false;
		double fdc = 0;
		do {
			this.refreshGUI(this.accediInputDati());
			fdc =
				this.accediInputDati().setRequestMessageAndAskInputAsDouble(
				String.format(MSG_ASK_FDC, gerScelta.getCategoria(), minMax.getFirst(), minMax.getSecond()));
			
			finito = true;
			
			if(fdc < minMax.getFirst() || fdc > minMax.getSecond()) {
				this.accediInputDati().warnUserWithInputDati(MSG_ERROR_OUT_OF_BOUNDS_FDC);
				finito = false;
			}
		}while(!finito);
		return fdc;
	}
	
}
