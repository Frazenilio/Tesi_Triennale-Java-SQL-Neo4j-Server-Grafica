package utility;

/**
 * Enum per i Path di file usati nel programma
 * @author Francesco Lozio
 * @since TESI
 */
public enum UsefulPathEnum {

	APP_DESCRIPTION("resource/texts/descriptionShort.txt"),
	CONFIGURATORE_DESCRIPTION("resource/texts/descriptionConfiguratore.txt"),
	FRUITORE_DESCRIPTION("resource/texts/descriptionFruitore");
	
	private String path;

	UsefulPathEnum(String path) {
		this.path = path;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
}
