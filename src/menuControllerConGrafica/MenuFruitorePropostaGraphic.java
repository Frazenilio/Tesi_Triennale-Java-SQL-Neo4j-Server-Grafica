package menuControllerConGrafica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;
import graphicUI.GraphicDisplayInterface;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MenuWithButtons;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.MyScorrimentoRisultati;
import indirectionAccessToGraphicPanels.MyScorrimentoTree;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.ModelService;
import personalGraphicElements.YesOrNoButtons;
import strutture.AdapterListaProposte;
import strutture.AdapterPropostaGraphicKit;
import strutture.Gerarchia;
import strutture.Proposta;
import utente.Fruitore;
import utility.InterazioneOperazioni;
import utility.Stato;

/**
 * Classe per la gestione di Proposte del menu Fruitore con grafica
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuFruitorePropostaGraphic {

	private static final String MSG_PLACEHOLDER_ORE_RICHIESTA = "Durata Richiesta (in ore)";
	private static final String MSG_ASK_CONFIRM_PROPOSTA = "Vuoi confermare la creazione della Proposta?\nRiepilogo: "
			+ "offri %s per %d ore, richiedi %s per %d ore";
	private static final String MSG_CONFIRM_WITHDRAW_PROPOSTA = "Confermi il Ritiro di questa Proposta? "
			+ "Offri %s per %d ore e richiedi %s per %d ore\n";
	private static final String MSG_ASK_PROPOSTA_TO_WITHDRAW = "Seleziona la Proposta che vuoi ritirare. Le proposte sono nel formato"
			+ " \"Offerta: CategoriaOfferta (OreDiOfferta) - Richiesta: CatgoriaRichiesta (OreDiRichiesta)\"";
	private static final String MSG_ASK_REINSERT_OFFERTA = "Vuoi re-inserire la durata della richiesta? Se rifiuti, tornerai"
			+ " alla schermata del menu principale del Fruitore";
	private static final String MSG_ASK_CONFIRM_DURATA_OFFERTA = "La durata dell'offerta calcolata e' %d. Confermi?";
	private static final String MSG_ASK_DURATA_RICHIESTA = "Ora inserisci la durata della richiesta %s."
			+ " Ricorda che puoi inserire solo numeri interi ed e' in ORE. Verra' calcolata la durata dell'offerta in base alle categorie scelte"
			+ " e alla durata della richiesta che stai inserendo";

	private MyInputDati inputDati;
	
	private ViewGUI gui;
	
	private ModelService gestore;
	
	private WarnerToUser warner;
	
	private MenuWithButtons menu;
	
	private MyScorrimentoRisultati<AdapterPropostaGraphicKit> scorrimento;
	
	private MyScorrimentoTree<AdapterListaProposte, Proposta> scorrimentoTree;

	/**
	 * Costruttore
	 * @param inputDati
	 * @param warner
	 * @param gui
	 * @param gestore
	 * @since TESI
	 */
	public MenuFruitorePropostaGraphic(MyInputDati inputDati, WarnerToUser warner, ViewGUI gui, ModelService gestore) {
		super();
		this.inputDati = inputDati;
		this.gui = gui;
		this.gestore = gestore;
		this.warner = warner;
		
		this.menu = new MenuWithButtons(new YesOrNoButtons());
		this.scorrimento = new MyScorrimentoRisultati<AdapterPropostaGraphicKit>("Ecco le tue proposte");
		this.scorrimentoTree = new MyScorrimentoTree<AdapterListaProposte, Proposta>("Seleziona la Proposta da ritirare");
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
	
	private MyScorrimentoRisultati<AdapterPropostaGraphicKit> accediScorrimento() {
		return this.scorrimento;
	}
	
	private MyScorrimentoTree<AdapterListaProposte, Proposta> accediScorrimentoTree(){
		return this.scorrimentoTree;
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per creare una proposta con grafica
	 * @param offerta Categoria offerta
	 * @param richiesta Categoria richiesta
	 * @param proprietario fruitore proprietario della Proposta
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public void creaPropostaWithGraphic(Gerarchia offerta, Gerarchia richiesta, Fruitore proprietario) throws ErroreInterruzioneOperazione, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		boolean finito = false;
		this.accediInputDati().setPlaceholder(MSG_PLACEHOLDER_ORE_RICHIESTA);
		do {
			this.refreshGUI(this.accediInputDati());
			
			int durataRichiesta = this.accediInputDati().
					setRequestMessageAndAskInputAsInteger(String.format(MSG_ASK_DURATA_RICHIESTA, richiesta.getCategoria()));
			
			int durataOfferta = this.accediGestore().calcolaDurataOfferta(richiesta, offerta, durataRichiesta);
			
			this.accediMenu().replaceButtonContainer(new YesOrNoButtons(String.format(MSG_ASK_CONFIRM_DURATA_OFFERTA, durataOfferta)));
			this.refreshGUI(this.accediMenu());

			this.accediMenu().setMessage(String.format(MSG_ASK_CONFIRM_PROPOSTA,
					offerta.getCategoria(), durataOfferta, richiesta.getCategoria(), durataRichiesta));
			
			int scelta = this.accediMenu().ottieniButtonPressed();
			
			if(scelta == InterazioneOperazioni.CONFIRM_OPERATION.getVal()) {
				try {
					Proposta prop = new Proposta(proprietario.getId(),
							richiesta, offerta, durataRichiesta, durataOfferta, Stato.APERTO
							, proprietario.getComprensorioId());
					prop.setId(UUID.randomUUID());
					this.accediGestore().addProposta(prop,
							proprietario.getComprensorioId());
					finito = true;
					this.refreshGUI(this.accediWarner());
					this.accediWarner().warnUserSuccessOperation();
				} catch (IOException e) {
					this.refreshGUI(this.accediWarner());
					this.accediWarner().warnUserException(e);
				}
			}
			else {
				
				this.accediMenu().replaceButtonContainer(new YesOrNoButtons(MSG_ASK_REINSERT_OFFERTA));
				this.refreshGUI(this.accediMenu());
				scelta = this.accediMenu().ottieniButtonPressed();
				if(scelta == InterazioneOperazioni.CANCEL_OPERATION.getVal()) {
					finito = true;
				}
			}
		}while(!finito);
		
	}

	
	/**
	 * Metodo per scegliere una Proposta e cambiarne lo stato
	 * @param lista Lista da cui scegliere la proposta
	 * @param nuovoStato nuovo stato con cui cambiare la proposta
	 * @throws IOException
	 * @throws ErroreInterruzioneOperazione 
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public void cambiaStatoPropostaDaLista(List<Proposta> lista, Stato nuovoStato)
			throws IOException, ErroreInterruzioneOperazione, ErroreDatiAssenti,
			ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		
		this.accediScorrimentoTree().setMessageText(MSG_ASK_PROPOSTA_TO_WITHDRAW);
		this.accediScorrimentoTree().setToBeSelcted(true);
		this.refreshGUI(this.accediScorrimentoTree());
		List<Proposta> listaConvertita = this.accediGestore().retrieveGerarchiasForProposta(lista);
		List<AdapterListaProposte> adapter = new ArrayList<AdapterListaProposte>();
		adapter.add(new AdapterListaProposte(listaConvertita));
		this.accediScorrimentoTree().setObjectsToScroll(adapter);
		Proposta propScelta = this.accediScorrimentoTree().scorriListaDiAlberi();
		
		if(propScelta != null) {
			String message = String.format(MSG_CONFIRM_WITHDRAW_PROPOSTA, 
					propScelta.getOfferta().getCategoria(),
					propScelta.getDurataOfferta(),
					propScelta.getRichiesta().getCategoria(),
					propScelta.getDurataRichiesta());
			this.accediMenu().replaceButtonContainer(new YesOrNoButtons(message));
			this.refreshGUI(this.accediMenu());
			
			int scelta = this.accediMenu().ottieniButtonPressed();
			if(scelta == InterazioneOperazioni.CONFIRM_OPERATION.getVal()) {
				this.accediGestore().modificaStatoProposta(propScelta, nuovoStato);
				this.refreshGUI(this.accediWarner());
				this.accediWarner().warnUserSuccessOperation();
			}
			else {
				throw new ErroreInterruzioneOperazione();
			}
		}
	}

	
	/**
	 * Metodo per scorrere Proposte 
	 * @param lista lista da cui attingere per visionare una proposta
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @since TESI
	 */
	public void scorriProposte(List<Proposta> lista) throws
	ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreServerUnreachable,
	ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		if(lista.isEmpty()) {
			throw new ErroreDatiAssenti();
		}
		else {
			List<AdapterPropostaGraphicKit> listaAdattata = new ArrayList<AdapterPropostaGraphicKit>();
			lista = this.accediGestore().retrieveGerarchiasForProposta(lista);
			for(Proposta p : lista) {
				listaAdattata.add(new AdapterPropostaGraphicKit(
						p, this.accediGestore().retrieveFruitoreById(p.getProprietarioId())));
			}
			this.accediScorrimento().setObjectsToScroll(listaAdattata);
			this.refreshGUI(this.accediScorrimento());
			this.accediScorrimento().scorriLista();
		}
	}
}
