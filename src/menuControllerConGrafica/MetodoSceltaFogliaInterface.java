package menuControllerConGrafica;

import java.util.List;

import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import strutture.Gerarchia;

public interface MetodoSceltaFogliaInterface {

	Gerarchia scegliFoglia(List<Gerarchia> lista, String text, boolean leafSelecatble) throws ErroreInterruzioneOperazione, ErroreDatiAssenti;
}
