package utility;

import java.awt.Color;

/**
 * Enum per contenere alcuni {@link Color} di default
 * @author Francesco Lozio 737664
 * @since TESI
 */
public enum Colori {
	BLU_CHIARO(new Color(125, 175, 190)),
	BLU_SCURO(new Color(150, 160, 200)),
	BLU_TOTAL(new Color(150, 150, 250)),
	VIOLETTO(new Color(150, 150, 175)),
	ROSA_SCURO(new Color(175, 160, 170)),
	ROSA(new Color(200, 150, 175)),
	ROSSO(Color.RED),
	NERO(Color.BLACK),
	ARANCIO(Color.ORANGE),
	VERDE(new Color(175, 200, 175)),
	GIALLO(new Color(255, 153, 0)),
	VERDE_SCURO(new Color(100, 255, 100)),
	ROSSOMISTO(new Color(200, 150, 180)),
	BIANCO(new Color(255, 255, 255));
	
	private Color matchingColor;
	
	Colori(Color c){
		this.matchingColor = c;
	}
	
	/**
	 * Metodo per ottenere il valore corrispondente
	 * @return il {@link Color} associato
	 */
	public Color getVal() {
		return matchingColor;
	}
}
