package logica;

import java.io.IOException;

import errori.ErroreDatabaseNotWorking;
import strutture.Proposta;
import utility.Stato;

public interface ModificaStato {

	public void modificaStatoProposta(Proposta prop, Stato nuovoStato) throws IOException, ErroreDatabaseNotWorking;
}
