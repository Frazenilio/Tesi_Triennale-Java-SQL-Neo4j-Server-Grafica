package logica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import strutture.InsiemeChiuso;
import strutture.Proposta;
import utility.Stato;

public class GestoreProposte {
	
	private List<Proposta> arrayProposte;
	private GestoreProposteChiusura gestoreProposteChiusura;
	private ModificaStato modificaStato;

	public GestoreProposteChiusura getGestoreProposteChiusura() {
		return gestoreProposteChiusura;
	}
	private List<Proposta> getArrayProposte() {
		return arrayProposte;
	}
	private void setArrayProposte(List<Proposta> arrayProposte) {
		this.arrayProposte = arrayProposte;
	}
	private ModificaStato getModificaStato() {
		return modificaStato;
	}

	public GestoreProposte(List<Proposta> arrayProposte, ModificaStato modificaStato) {
		super();
		this.arrayProposte = arrayProposte;
		this.modificaStato = modificaStato;
		if(this.getArrayProposte()==null) this.setArrayProposte(new ArrayList<Proposta>());
		this.gestoreProposteChiusura = new GestoreProposteChiusura(this.getArrayProposte(),this.getModificaStato());
	}

	public void addProposta(Proposta prop) {
		this.arrayProposte.add(prop);
	}

	public void modificaStatoPropostaInterno(Proposta prop, Stato stato) {
		int index = -1;
		for(int i = 0; i < this.getArrayProposte().size(); i++) {
			if(prop.getId().equals(this.getArrayProposte().get(i).getId())) {
				index = i;
			}
		}
		if(index >= 0) {
			this.getArrayProposte().get(index).setStato(stato);
		}
	}
	
	/**
	 * Metodo che chiude le proposte aperte che formano un insieme chiuso
	 * @param idConfiguratoreProprietario 
	 * @throws IOException 
	 * @throws ErroreDatabaseNotWorking 
	 */
	public List<InsiemeChiuso> chiudiProposte(UUID idComprensorio) throws IOException, ErroreDatabaseNotWorking {
		return this.getGestoreProposteChiusura().chiudiProposte(idComprensorio);
	}
}
