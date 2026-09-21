package menuCreationChainOfResponsability;

import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.ModelService;
import menuControllerConGrafica.MenuConfiguratoreGrafico;
import menuControllerConGrafica.MenuGrafico;
import utente.Configuratore;
import utente.Utente;

/**
 * Classe del Chain of Responsibility per la creazione del menu configuratore.
 * Attualemnte e' l'ultimo della catena
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuConfiguratoreGraficoHandler implements MenuGraficoCreationHandler{

	MenuGraficoCreationHandler nextMenu;
	
	@Override
	public MenuGrafico returnNewMenu(Utente u, ModelService gestore, ViewGUI view, MyInputDati inputDati,
			WarnerToUser warner) {
		if(u instanceof Configuratore) {
			return new MenuConfiguratoreGrafico((Configuratore) u, gestore, view, inputDati, warner);
		}
		return //nextMenu.returnNewMenu(u, gestore, view, inputDati);
				null;
	}
}
