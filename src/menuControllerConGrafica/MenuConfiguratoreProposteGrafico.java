package menuControllerConGrafica;

import java.util.ArrayList;
import java.util.List;

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
import strutture.AdapterPropostaGraphicKit;
import strutture.Gerarchia;
import strutture.Proposta;

/**
 * Classe per gestire le Proposte dal menu configuratore
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuConfiguratoreProposteGrafico {

	private static final String MSG_DISPLAY_PROPOSTE = "Lista Proposte della Gerarchia scelta come OFFERTA o RICHIESTA";

	private MyScorrimentoRisultati<AdapterPropostaGraphicKit> scorrimento;
	
	private ModelService gestore;
	
	private ViewGUI gui;

	/**
	 * Costruttore
	 * @param gestore
	 * @param gui
	 * @since TESI
	 */
	public MenuConfiguratoreProposteGrafico(ModelService gestore, ViewGUI gui) {
		super();
		this.gestore = gestore;
		this.gui = gui;
		
		this.scorrimento = new MyScorrimentoRisultati<AdapterPropostaGraphicKit>(MSG_DISPLAY_PROPOSTE);
	}
	
	private ModelService accediGestore() {
		return this.gestore;
	}
	
	private ViewGUI accediGUI() {
		return this.gui;
	}
	
	private MyScorrimentoRisultati<AdapterPropostaGraphicKit> accediScorrimento(){
		return this.scorrimento;
	}
	
	private void refreshGUI(GraphicDisplayInterface newToDisplay) {
		this.accediGUI().changeToDisplay(newToDisplay);
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo per leggere le Proposte legate ad una foglia
	 * @param ger Categoria foglia di cui leggere le proposte
	 * @Precondizioni ger deve essere una foglia
	 * @throws ErroreDatiAssenti
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	public void letturaProposteDiUnaFoglia(Gerarchia ger) 
			throws ErroreDatiAssenti, ErroreInterruzioneOperazione,
			ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking {
		List<Proposta> lista = new ArrayList<Proposta>();
		
		lista = this.accediGestore().ottieniProposteDiFoglia(ger);
		
		if(lista.isEmpty() || lista == null) {
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
