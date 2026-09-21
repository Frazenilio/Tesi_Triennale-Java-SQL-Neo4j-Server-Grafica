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
import graphicUI.GraphicDisplayInterface;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MyScorrimentoRisultati;
import logica.ModelService;
import strutture.AdapterInsiemiChiusiGraphicKit;
import strutture.InsiemeChiuso;
import strutture.Proposta;
import utente.Configuratore;
import utente.Fruitore;
import utility.FakeProposta;

/**
 * Classe per la gestione dei Cicli Chiusi dal menu Configuratore
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuConfiguratoreCicliChiusiGrafico {

	private static final String MSG_DISPLAY_CICLI_CHIUSI = "Ecco i tuoi cicli da chiudere";

	private ModelService gestore;
	
	private MyScorrimentoRisultati<AdapterInsiemiChiusiGraphicKit> scorrimento;
	
	private ViewGUI gui;

	/**
	 * Costruttore 
	 * @param gestore
	 * @param gui
	 * @since TESI
	 */
	public MenuConfiguratoreCicliChiusiGrafico(ModelService gestore, ViewGUI gui) {
		super();
		this.gestore = gestore;
		this.gui = gui;
		
		this.scorrimento = new MyScorrimentoRisultati<AdapterInsiemiChiusiGraphicKit>(MSG_DISPLAY_CICLI_CHIUSI);
	}
	
	private ModelService accediGestore() {
		return this.gestore;
	}
	
	private ViewGUI accediGUI() {
		return this.gui;
	}
	
	private MyScorrimentoRisultati<AdapterInsiemiChiusiGraphicKit> accediScorrimento(){
		return this.scorrimento;
	}
	
	private void refreshGUI(GraphicDisplayInterface newToDisplay) {
		this.accediGUI().changeToDisplay(newToDisplay);
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per leggere i cicli chiusi associati ad un Configuratore
	 * @param c configuratore associato ai cicli chiusi da leggere
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti 
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public void letturaOwnCicliDaChiudere(Configuratore c) 
			throws ErroreInterruzioneOperazione, ErroreDatiAssenti, ErroreServerUnreachable,
			ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {

		List<InsiemeChiuso> listaPreConversione = this.accediGestore().ottieniCicliChiusi(c.getId());
		if(listaPreConversione.isEmpty()) {
			throw new ErroreDatiAssenti();
		}
		for(int i = 0; i < listaPreConversione.size(); i++) {
			if(listaPreConversione.get(i).isAreProposteToRetrieve()) {
				for(UUID id : listaPreConversione.get(i).getCycleIds()) {
					Proposta propostabyId = this.accediGestore().retrievePropostabyId(id);
					propostabyId.setOfferta(this.accediGestore().retrieveGerarchiaById(propostabyId.getOffertaId()));
					propostabyId.setRichiesta(this.accediGestore().retrieveGerarchiaById(propostabyId.getRichiestaId()));
					listaPreConversione.get(i).getCycle().add(propostabyId);
				}
			}
		}
		
		List<AdapterInsiemiChiusiGraphicKit> listaFinale = new ArrayList<AdapterInsiemiChiusiGraphicKit>();
		for(int i = 0; i < listaPreConversione.size(); i++) {
			List<Fruitore> listaFruitori = new ArrayList<Fruitore>();
			List<FakeProposta> listaFakeProposte = new ArrayList<FakeProposta>();
			for(Proposta p : listaPreConversione.get(i).getCycle()) {
				listaFruitori.add(this.accediGestore().retrieveFruitoreById(p.getProprietarioId()));
				
				UUID idRadiceRichiesta = this.accediGestore().retrieveRadiceOfGerarchia(p.getRichiesta());
				UUID idRadiceOfferta = this.accediGestore().retrieveRadiceOfGerarchia(p.getOfferta());
				FakeProposta f = new FakeProposta(p.getId(),
						this.accediGestore().retrieveGerarchiaById(idRadiceRichiesta).getCategoria(),
						p.getRichiesta().getCategoria() ,
						this.accediGestore().retrieveGerarchiaById(idRadiceOfferta).getCategoria(),
						p.getOfferta().getCategoria(),
						p.getDurataRichiesta(),
						p.getDurataOfferta(),
						p.getStato());
				listaFakeProposte.add(f);
			}
			listaFinale.add(new AdapterInsiemiChiusiGraphicKit(listaFruitori
					, listaFakeProposte));
		}

		this.accediScorrimento().setObjectsToScroll(listaFinale);
		this.refreshGUI(this.accediScorrimento());
		this.accediScorrimento().scorriLista();
	}
}
