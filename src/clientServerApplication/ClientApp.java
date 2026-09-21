package clientServerApplication;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gestoreClientPackage.GestoreClient;
import graphicUI.ViewGUI;
import indirectionAccessToGraphicPanels.MyInputDati;
import indirectionAccessToGraphicPanels.WarnerToUser;
import loginControllerConGrafica.LoginGrafico;
import menuControllerConGrafica.MenuGrafico;
import menuCreationChainOfResponsability.MenuFruitoreGraficoHandler;
import menuCreationChainOfResponsability.MenuGraficoCreationHandler;
import personalGraphicElements.MyJFrame;
import utente.Utente;

public class ClientApp {

	private static MyJFrame f = new MyJFrame();
	private static ObjectInputStream input;
	private static ObjectOutputStream output;
	private static Socket socket;
	
	public ClientApp(Socket socket, ObjectOutputStream out, ObjectInputStream input) {
		this.socket = socket;
		this.output = out;
		this.input = input;
		
		int w = 640;
		int h = 480;
		
		f.setBounds(100, 100, 450, 300);
		f.setSize(w, h);
		
		f.setTitle("Client-Server");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	/**
	 * Metodo che gestisce l'operazione del client
	 * @param socket socket che collega il client al server
	 * @param out output di oggetti
	 * @param input input di oggetti
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @since TESI CLIENT-SERVER
	 */
	public static void main() throws FileNotFoundException, IOException {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientApp window = new ClientApp(socket, output, input);
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		ViewGUI view = new ViewGUI();
		GestoreClient gestore = new GestoreClient(input, output);
		MyInputDati graphicID = new MyInputDati();
		WarnerToUser warner = new WarnerToUser();
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
	}
	
	public static MyJFrame getFrame() {
		return f;
	}
}
