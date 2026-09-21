package utility;

/**
 * Enum per testi di uso frequente negli alberi
 * @author Francesco Lozio 737664
 * @since TESI
 */
public enum TreeDefaultTextEnum {
	
	YOU_ARE_HERE("Sei qui");
	
	private String text;
	
	TreeDefaultTextEnum(String text) {
		this.text = text;
	}
	
	public String getVal() {
		return this.text;
	}
}
