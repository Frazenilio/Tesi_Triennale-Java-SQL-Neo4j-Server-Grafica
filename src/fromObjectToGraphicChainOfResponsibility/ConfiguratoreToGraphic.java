package fromObjectToGraphicChainOfResponsibility;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import errori.ErroreDatiAssenti;
import personalGraphicElements.MyJTextArea;
import utente.Configuratore;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di JPanel usato per rappresentare
 * un Configuratore
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class ConfiguratoreToGraphic implements ObjectConverterHandlerInterface{

	private static final int FRACTION_CONFIGURATORE_FONT_HEIGHT = 10;
	ObjectConverterHandlerInterface nextHandler = new ComprensorioToGraphic();
	
	@Override
	public <T> JPanel objectToGraphic(T object) throws ErroreDatiAssenti {
		if(object instanceof Configuratore) {
			Configuratore objConverted = (Configuratore) object;
			MyJTextArea configuratoreArea = new MyJTextArea("Configuratore: " + objConverted.getUsername());
			JPanel container = new JPanel();
			container.setLayout(new BorderLayout());
			container.add(configuratoreArea, BorderLayout.CENTER, 0);
			container.addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				@Override
				public void componentResized(ComponentEvent e) {
					container.getComponent(0).setFont(new Font("Arial", Font.BOLD,
							e.getComponent().getHeight()/FRACTION_CONFIGURATORE_FONT_HEIGHT));
				}
				@Override
				public void componentMoved(ComponentEvent e) {
				}
				@Override
				public void componentHidden(ComponentEvent e) {
				}
			});
			return container;
		}
		return nextHandler.objectToGraphic(object);
	}

}
