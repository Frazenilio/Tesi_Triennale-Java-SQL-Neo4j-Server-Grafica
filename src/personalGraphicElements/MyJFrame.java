package personalGraphicElements;

import java.awt.Component;

import javax.swing.JFrame;

import graphicUI.GraphicImplementer;

/**
 * Classe per un {@link JFrame} personalizzato
 */
public class MyJFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo per aggiungere un {@link GraphicImplementer} al frame
	 * @param graphicer {@link GraphicImplementer} da aggiungere
	 * @return Component aggiunto
	 * @since TESI
	 */
	public Component addGraphicImplementer(GraphicImplementer graphicer) {
		return this.getContentPane().add(graphicer.returnView());
	}
}
