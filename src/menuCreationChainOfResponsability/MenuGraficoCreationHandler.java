package menuCreationChainOfResponsability;

import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.ModelService;
import menuControllerConGrafica.MenuGrafico;
import utente.Utente;

/**
 * Interface per la Chain of Responsibility di creazione di menu
 * @author Francesco Lozio 737664
 * @since TESI
 */
public interface MenuGraficoCreationHandler {

	/**
	 * Metodo per ottenere il menu associato alla classe della Chain of Responsibility
	 * @param u
	 * @param gestore
	 * @param view
	 * @param inputDati
	 * @param warner
	 * @return menu associato
	 * @since TESI
	 */
	MenuGrafico returnNewMenu(Utente u, ModelService gestore, ViewGUI view, MyInputDati inputDati,
			WarnerToUser warner);
}
