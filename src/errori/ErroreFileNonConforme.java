package errori;

import java.io.IOException;

/**
 * Errore in caso ci siano problemi con un file (restituisce anche quale file nel messaggio)
 * @author Francesco Lozio 737664
 * @since Parte B
 */
public class ErroreFileNonConforme extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG_ERROR_FILE_NON_CONFORME = "Problema col file ";
	String fileName;
	
	public ErroreFileNonConforme(String fileName) {
		this.fileName = fileName;
	}
	
	public String getMessage() {
		return MSG_ERROR_FILE_NON_CONFORME + this.fileName;
	}
}
