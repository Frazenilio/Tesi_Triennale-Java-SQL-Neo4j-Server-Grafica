package graphicUI;

import javax.swing.JPanel;

/**
 * Interface per classi che POSSIEDONO grafica indirettamente
 * @author Francesco Lozio 737664
 * @since TESI
 */
public interface GraphicDisplayInterface {

	/**
	 * Metodo per ottenere il JPanel da disegnare associato alla classe
	 * @return JPanel da disegnare
	 * @since TESI
	 */
	JPanel obtainElementsToDraw();

}
