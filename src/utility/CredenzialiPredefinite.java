package utility;

/**
 * Enum per conservare le credenziali predefinite per il primo 
 * accesso dei Configuratori
 * @author Francesco Lozio 737664
 * @since Parte B
 */
public enum CredenzialiPredefinite {
	USERNAME_PREDEFINITO("a"), PASSWORD_PREDEFINITA("a");
	
	private String val;

    CredenzialiPredefinite(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
