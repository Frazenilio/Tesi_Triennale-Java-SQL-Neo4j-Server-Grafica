package strutture;

/**
 * Classe Adapter per la rappresentazione grafica della classe {@link FattoreDiConversione}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class AdapterFattoreDiConversioneGraphicKit {

	private double fdc;
	private String categoriaBersaglio;
	public AdapterFattoreDiConversioneGraphicKit(double fdc, String categoriaBersaglio) {
		super();
		this.fdc = fdc;
		this.categoriaBersaglio = categoriaBersaglio;
	}
	/**
	 * @return the fdc
	 */
	public double getFdc() {
		return fdc;
	}
	/**
	 * @return the categoriaBersaglio
	 */
	public String getCategoriaBersaglio() {
		return categoriaBersaglio;
	}
	
	
}
