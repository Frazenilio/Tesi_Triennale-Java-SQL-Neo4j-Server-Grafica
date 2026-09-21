package menuCreationChainOfResponsability;

import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.ModelService;
import menuControllerConGrafica.MenuFruitoreGrafico;
import menuControllerConGrafica.MenuGrafico;
import utente.Fruitore;
import utente.Utente;

/**
 * Classe del Chain of Responsibility per la creazione del menu Fruitore.
 * Attualemnte e' il primo della catena
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuFruitoreGraficoHandler implements MenuGraficoCreationHandler{

	MenuGraficoCreationHandler nextMenu = new MenuConfiguratoreGraficoHandler();


	@Override
	public MenuGrafico returnNewMenu(Utente u, ModelService gestore, ViewGUI view, MyInputDati inputDati,
			WarnerToUser warner) {
		if(u instanceof Fruitore) {
			return new MenuFruitoreGrafico(u, gestore, view, inputDati, warner);
		}
		return nextMenu.returnNewMenu(u, gestore, view, inputDati, warner);
	}
}
