package graphicUI;

/**
 * interface usata dalle classi che USANO la grafica (menu principali)
 * @author Francesco Lozio 737664
 * @since TESI
 */
public interface GraphicImplementer {
	
	/**
	 * metodo per ottenere la gui usata dalla classe
	 * @return ViewGUI usata
	 * @since TESI
	 */
	ViewGUI returnView();
}
