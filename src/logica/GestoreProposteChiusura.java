package logica;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import errori.ErroreDatabaseNotWorking;
import strutture.InsiemeChiuso;
import strutture.Proposta;
import utility.Stato;

public class GestoreProposteChiusura {

	private List<Proposta> arrayProposte; 
	private ModificaStato modificaStato;
	
	public GestoreProposteChiusura(List<Proposta> arrayProposte, ModificaStato modificaStato) {
		this.arrayProposte = arrayProposte;
		this.modificaStato = modificaStato;
	}
	
	public List<Proposta> getArrayProposte() {
		return arrayProposte;
	}
	public ModificaStato getModificaStato() {
		return modificaStato;
	}

	public List<InsiemeChiuso> chiudiProposte(UUID idComprensorio) throws IOException, ErroreDatabaseNotWorking {
		List<InsiemeChiuso> allInsiemichiusi = new ArrayList<InsiemeChiuso>();
	
		for(Proposta p:this.getArrayProposte()) {
			if(p.getStato()==Stato.APERTO) { 
				List<Proposta> passi = new ArrayList<Proposta>();
				passi.add(p);
				loopProposta(p,p,passi, new ArrayList<Proposta>(),this.getArrayProposte());
				//chiusura delle Proposte
				for(int i=0; i<passi.size(); i++) {
					this.getModificaStato().modificaStatoProposta(passi.get(i), Stato.CHIUSO);
				}
				if(!passi.isEmpty()) {
					InsiemeChiuso cycle = new InsiemeChiuso(passi);
					cycle.setId(UUID.randomUUID());
					cycle.setComprensorioId(idComprensorio);
					allInsiemichiusi.add(cycle);
				}
			}
		}
		return allInsiemichiusi;
	}
	
	private String loopProposta(Proposta t, Proposta obiettivo, List<Proposta> passiConseguiti, List<Proposta> usati, List<Proposta> listaProposte) {

		boolean trovato = false;
		List<Proposta> possibiliLegami = new ArrayList<Proposta>();

		if(t.getRichiestaId().equals(obiettivo.getRichiestaId())) {  //caso iniziale, per vedere se ci sono dei link istantanei
			for(Proposta temp:listaProposte) {
				if(isPropostaInversa(t,temp) && temp.getStato()==Stato.APERTO
						&& temp.getComprensorioId()
						.equals(obiettivo.getComprensorioId())) { // controlla  se e' in stato Aperto e se e' l'inversa

					passiConseguiti.add(temp);
					//					System.out.println("Instant Link");
					return "Instant link";
				}
			}
		}

		//Controllo vicini in AMPIEZZA
		for(Proposta temp:listaProposte) {

			//CONTROLLO SE E' UN POSSIBILE VICINO
			if(t.getOffertaId().equals(temp.getRichiestaId()) && t.getDurataOfferta()==temp.getDurataRichiesta() //offerta1==richiesta2 e durate uguali
					&& !isPropostaInversa(t, temp) && !usati.contains(temp) //non deve essere l'inversa e non deve essere in usati
					&& temp.getStato()==Stato.APERTO //controllo se la Proposta e' in stato aperto
					&& temp.getComprensorioId().equals(obiettivo.getComprensorioId())){  //Stesso comprensorio
				possibiliLegami.add(temp);
				usati.add(temp);

				if(temp.getOffertaId().equals(obiettivo.getRichiestaId()) 
						&& temp.getDurataOfferta()==obiettivo.getDurataRichiesta()) { //check se la durata dell'ultima offerta coincide con la richiesta del primo
					passiConseguiti.add(temp);
					trovato = true;
					break;
				}
			}
		}
		if(!trovato) {
			for(Proposta x: possibiliLegami) {
				passiConseguiti.add(x);
				String risultato = loopProposta(x, obiettivo, passiConseguiti, usati, listaProposte);
				if(!risultato.equals("NotFound")) {
					return risultato;
				}
			}
			passiConseguiti.remove(passiConseguiti.size()-1);
		}
		if(trovato) {
			return "Percorso ciclico trovato";
		}
		return "NotFound";
	}
	
	private boolean isPropostaInversa(Proposta p1, Proposta p2) {
		if(p1.getRichiestaId().equals(p2.getOffertaId()) && p1.getOffertaId().equals(p2.getRichiestaId()) 
				&& p1.getDurataRichiesta() == p2.getDurataOfferta() && p1.getDurataOfferta() == p2.getDurataRichiesta()) {
			return true;
		}
		else return false;
	}
}
