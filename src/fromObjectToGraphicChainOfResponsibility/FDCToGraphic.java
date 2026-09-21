package fromObjectToGraphicChainOfResponsibility;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import errori.ErroreDatiAssenti;
import personalGraphicElements.MyJTextArea;
import strutture.AdapterFattoreDiConversioneGraphicKit;
import utility.Colori;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di JPanel usato per rappresentare
 * Fattori di Conversione
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class FDCToGraphic implements ObjectConverterHandlerInterface{

	private static final int FRACTION_FDC_FONT_HEIGHT = 10;
	ObjectConverterHandlerInterface nextHandler = new InsiemeChiusoToGraphic();
	
	private static final int RIGHT_INPUT_INSETS = 200;
	private static final int BOTTOM_INPUT_INSETS = 100;
	private static final int LEFT_INPUT_INSETS = 200;
	private static final int TOP_INPUT_INSETS = 100;
	
	@Override
	public <T> JPanel objectToGraphic(T object) throws ErroreDatiAssenti {
		if(object instanceof AdapterFattoreDiConversioneGraphicKit) {
			AdapterFattoreDiConversioneGraphicKit obj = (AdapterFattoreDiConversioneGraphicKit) object;
			StringBuilder builder = new StringBuilder();
			
			builder.append(String.format("Questa Gerarchia ha un fattore di conversione di %.2f " , obj.getFdc()));
			builder.append(" con la Gerarchia " + obj.getCategoriaBersaglio());
			builder.append(String.format(" (1 ora di questa categoria equivalgono a %.2f ore di %s)", 
					obj.getFdc(), obj.getCategoriaBersaglio()));
			
			MyJTextArea area = new MyJTextArea(builder.toString());
			area.setBackground(Colori.BIANCO.getVal());
			
			JScrollPane scroller = new JScrollPane(area);
			scroller.setLayout(new ScrollPaneLayout());
			
			JPanel container = new JPanel();
			
			container.setLayout(new BorderLayout());
			container.add(scroller, BorderLayout.CENTER, 0);
			container.setBorder(BorderFactory.createMatteBorder(TOP_INPUT_INSETS, LEFT_INPUT_INSETS,
					BOTTOM_INPUT_INSETS, RIGHT_INPUT_INSETS, Colori.BLU_SCURO.getVal()));
			container.addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				@Override
				public void componentResized(ComponentEvent e) {
					area.setFont(new Font("Arial", Font.BOLD,
							e.getComponent().getHeight()/FRACTION_FDC_FONT_HEIGHT));
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
