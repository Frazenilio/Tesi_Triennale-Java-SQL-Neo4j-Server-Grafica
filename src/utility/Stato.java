package utility;

import strutture.Proposta;

/**
 * Enum per gli stati delle {@link Proposta}
 * @author Francesco Lozio 737664
 * @since Parte B
 */
public enum Stato {
	APERTO, CHIUSO, RITIRATO;
	
	public static Stato fromString(String status) {
        for (Stato s : Stato.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
