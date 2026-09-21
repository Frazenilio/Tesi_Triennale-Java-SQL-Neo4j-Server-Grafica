package fromObjectToGraphicChainOfResponsibility;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import errori.ErroreDatiAssenti;
import personalGraphicElements.MyJTextArea;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di JPanel usato per rappresentare
 * una stringa
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class TextToGraphic implements ObjectConverterHandlerInterface{

	
	private static final int FRACTION_TEXT_FONT_HEIGHT = 10;
	ObjectConverterHandlerInterface nextHandler = new PropostaToGraphic();
	
	public TextToGraphic() {
	}

	@Override
	public <T> JPanel objectToGraphic(T object) throws ErroreDatiAssenti {
		if(object instanceof String) {
			String obj = (String) object;
			MyJTextArea area = new MyJTextArea(obj);
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(area, BorderLayout.CENTER, 0);
			panel.addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				@Override
				public void componentResized(ComponentEvent e) {
					panel.getComponent(0).setFont(new Font("Arial", Font.BOLD, e.getComponent().getHeight() / FRACTION_TEXT_FONT_HEIGHT));
				}
				@Override
				public void componentMoved(ComponentEvent e) {
				}
				@Override
				public void componentHidden(ComponentEvent e) {
				}
			});
			return panel;
		}
		return nextHandler.objectToGraphic(object);
	}

}
