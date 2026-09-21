package loginControllerConGrafica;

import java.io.IOException;
import java.util.List;

import errori.ErroreDatabaseNotWorking;
import errori.ErroreInterruzioneOperazione;
import errori.ErroreRispostaNonConforme;
import errori.ErroreServerReply;
import errori.ErroreServerUnreachable;

/**
 * Interface per injection di metodi di login
 * @author Francesco Lozio 737664
 * @since TESI
 */
public interface MetodiDaLoginGrafici {

	
	/**
	 * Metodo per ottenere le credenziali base
	 * @param isDuplicatedBanned booleano per chiedere se l'username duplicato e' ammissibile
	 * @param message messaggio con cui chiedere le credenziali. Se "", si usa quello di default
	 * @return Credenziali base
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @since TESI
	 */
	List<String> ritornaCredenzialiBaseWithGraphic(String message)
			throws ErroreInterruzioneOperazione, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking;
	
	/**
	 * Metodo per registrare un nuovo configuratore
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreServerReply 
	 * @throws ErroreRispostaNonConforme 
	 * @throws ErroreServerUnreachable 
	 * @throws ErroreDatabaseNotWorking 
	 * @throws IOException 
	 * @since TESI
	 */
	void registrazioneConfiguratoreWithGraphic() throws ErroreInterruzioneOperazione, ErroreServerUnreachable, ErroreRispostaNonConforme, ErroreServerReply, ErroreDatabaseNotWorking, IOException;
	
	void hideWarningsAndResetToPlaceholderForCredentials();
	
	void warnInvalidCredentials();
}
