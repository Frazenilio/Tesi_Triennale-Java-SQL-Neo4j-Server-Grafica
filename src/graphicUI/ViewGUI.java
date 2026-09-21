package graphicUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import utility.Colori;

/**
 * Classe VIEW per rappresentare la GUI all'utente
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class ViewGUI extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GraphicDisplayInterface toDisplay;

	
	/**
	 * Costruttore
	 * @since TESI
	 */
	public ViewGUI() {

		this.setFocusable(true);
		this.requestFocus();
		
		this.setLayout(new BorderLayout());
		
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentResized(ComponentEvent e) {
				if(toDisplay != null) {
					toDisplay.obtainElementsToDraw().dispatchEvent(e);
				}
				repaint();
			}
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//SET BG
		this.setBackground(Colori.BLU_CHIARO.getVal());
		
		this.removeAll();
		
		if(toDisplay == null) {
		}
		else {
			add(toDisplay.obtainElementsToDraw(), BorderLayout.CENTER);
		}
	}

	/**
	 * Metodo per cambiare l'oggetto da rappresentare
	 * @param newDisplay nuova GraphicDisplayInterface da cui attingere all'oggetto da rappresentare
	 * @since TESI
	 */
	public void changeToDisplay(GraphicDisplayInterface newDisplay) {
		this.toDisplay = newDisplay;
		this.toDisplay.obtainElementsToDraw().dispatchEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
		this.add(toDisplay.obtainElementsToDraw(), BorderLayout.CENTER);
		this.toDisplay.obtainElementsToDraw().repaint();
		this.toDisplay.obtainElementsToDraw().revalidate();
		
		this.repaint();
	}
	
	/**
	 * Metodo per ottenere l'oggetto al momento associato
	 * @return GraphicDisplayInterface in uso
	 * @since TESI
	 */
	public GraphicDisplayInterface getToDisplay() {
		return this.toDisplay;
	}

	/**
	 * Metodo per togliere la GraphicDisplayInterface corrente
	 * @since TESI
	 */
	public void cleanDisplay() {
		this.removeAll();
		this.changeToDisplay(null);
		this.repaint();
		this.revalidate();
	}
}
