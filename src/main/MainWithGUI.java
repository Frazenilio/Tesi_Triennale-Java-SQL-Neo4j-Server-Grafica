package main;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import errori.ErroreDatabaseNotWorking;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import logica.Gestore;
import loginControllerConGrafica.LoginGrafico;
import menuControllerConGrafica.MenuGrafico;
import menuCreationChainOfResponsability.MenuFruitoreGraficoHandler;
import menuCreationChainOfResponsability.MenuGraficoCreationHandler;
import personalGraphicElements.MyJFrame;
import utente.Utente;

/**
 * Classe main con uso di grafica
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MainWithGUI {

	static MyJFrame f = new MyJFrame();
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWithGUI window = new MainWithGUI();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		ViewGUI view = new ViewGUI();
		MyInputDati graphicID = new MyInputDati();
		WarnerToUser warner = new WarnerToUser();
		try {
			Gestore gestore = new Gestore();
			LoginGrafico login = new LoginGrafico(gestore, view, warner);
			
			getFrame().addGraphicImplementer(login);
			Utente user = login.azioniLoginWithGraphic();
			
			if(user != null) {
				//CHAIN OF RESPONSIBILITY PER OTTENERE IL MENU ASSOCIATO
				MenuGraficoCreationHandler menuCreator = new MenuFruitoreGraficoHandler();
				MenuGrafico menu = menuCreator.returnNewMenu(user, gestore, view, graphicID, warner);
			
				//AZIONI DEL MENU PER INTERAGIRE CON L'UTENTE
				getFrame().addGraphicImplementer(menu);
				menu.azioniMenuGrafico();
			}
			
			view.changeToDisplay(warner);
			warner.warnUserWithCustomMessage("Arrivederci");
			
			System.exit(0);
		} catch (IOException | ErroreDatabaseNotWorking e) {
			System.err.print("Errore DB");
			e.printStackTrace();
		}
		
	}
	
	public MainWithGUI() throws FileNotFoundException, IOException {
		int w = 640;
		int h = 480;
		
		f.setBounds(100, 100, 450, 300);
		f.setSize(w, h);
		f.setTitle("GUI");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public static MyJFrame getFrame() {
		return f;
	}
}
