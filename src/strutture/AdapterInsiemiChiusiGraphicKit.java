package strutture;

import java.io.Serializable;
import java.util.List;

import utente.Fruitore;
import utility.FakeProposta;

/**
 * Classe Adapter per la rappresentazione di {@link InsiemeChiuso}
 * @author Francesco Lozio 737664
 * @since TESI DATABASE
 */
public class AdapterInsiemiChiusiGraphicKit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fruitore> fruitoriCoinvolti;
	private List<FakeProposta> fakeProposte;
	
	
	public AdapterInsiemiChiusiGraphicKit(List<Fruitore> fruitoriCoinvolti,
			List<FakeProposta> fakes) {
		this.fruitoriCoinvolti = fruitoriCoinvolti;
		this.fakeProposte = fakes;
	}

	/**
	 * @return the fruitoriCoinvolti
	 */
	public List<Fruitore> getFruitoriCoinvolti() {
		return fruitoriCoinvolti;
	}
	/**
	 * @return the fakeProposte
	 */
	public List<FakeProposta> getFakeProposte() {
		return fakeProposte;
	}
	
}
