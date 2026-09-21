package responsePackage;

import java.io.Serializable;

import utility.EsitoRequest;

/**
 * Interfaccia per implementare risposte del server al client
 * @author Francesco Lozio 737664
 * @since TESI CLIENT-SERVER
 */
public interface Response extends Serializable{

	EsitoRequest getEsito();
}
