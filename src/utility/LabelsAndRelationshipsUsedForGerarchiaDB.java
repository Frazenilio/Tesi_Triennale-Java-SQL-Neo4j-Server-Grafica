package utility;

import strutture.Gerarchia;

/**
 * Enum per relazioni e label usati nel salvataggio sul Database della {@link Gerarchia}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public enum LabelsAndRelationshipsUsedForGerarchiaDB {
	
	FOGLIA("foglia"),
	RADICE("radice"),
	NODO("nodo"),
	HAS_FATTORE_CONVERSIONE("HAS_FATTORE_CONVERSIONE"),
	IS_PARENT("IS_PARENT");
	
	private String value;
	private LabelsAndRelationshipsUsedForGerarchiaDB(String value) {
		this.value = value;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
}
