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
import strutture.AdapterComprensorioGraphicKit;
import utility.Colori;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di JPanel usato per rappresentare
 * un Comprensorio
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class ComprensorioToGraphic implements ObjectConverterHandlerInterface{

	private static final int FRACTION_COMPRENSORIO_FONT_HEIGHT = 10;
	
	private static final int RIGHT_INPUT_INSETS = 200;
	private static final int BOTTOM_INPUT_INSETS = 100;
	private static final int LEFT_INPUT_INSETS = 200;
	private static final int TOP_INPUT_INSETS = 100;
	
	ObjectConverterHandlerInterface nextHandler = new FDCToGraphic();
	
	@Override
	public <T> JPanel objectToGraphic(T object) throws ErroreDatiAssenti {
		if(object instanceof AdapterComprensorioGraphicKit) {
			AdapterComprensorioGraphicKit comp = (AdapterComprensorioGraphicKit) object;
			StringBuilder builder = new StringBuilder();
			
			builder.append("Lista Comuni:\n" + comp.getComuni());
			builder.append("\nQuesto comprensorio e' stato creato da " + comp.getProprietario().getUsername());
			
			MyJTextArea area = new MyJTextArea(builder.toString());
			
			area.setBackground(Colori.BIANCO.getVal());
			
			JScrollPane scroller = new JScrollPane(area);
			scroller.setLayout(new ScrollPaneLayout());
			
			JPanel container = new JPanel();
			container.setLayout(new BorderLayout());
			container.add(scroller, BorderLayout.CENTER);
			container.setBorder(BorderFactory.createMatteBorder(TOP_INPUT_INSETS, LEFT_INPUT_INSETS,
					BOTTOM_INPUT_INSETS, RIGHT_INPUT_INSETS, Colori.BLU_SCURO.getVal()));
			container.addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				@Override
				public void componentResized(ComponentEvent e) {
					area.setFont(new Font("Arial", Font.BOLD,
							e.getComponent().getHeight()/FRACTION_COMPRENSORIO_FONT_HEIGHT));
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
