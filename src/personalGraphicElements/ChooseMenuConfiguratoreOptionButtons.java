package personalGraphicElements;

import java.util.ArrayList;
import java.util.List;

import utility.FrequentIcons;

/**
 * Classe {@link ButtonsContainer} per le informazioni per il menu del configuratore
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class ChooseMenuConfiguratoreOptionButtons implements ButtonsContainer{

	private static final String MSG_INFO_OPZIONI = "Info Opzioni";
	private static final String MSG_READ_FATTORI_CONVERSIONE_FOGLIA = "Visualizza i fattori di conversione di una foglia";
	private static final String MSG_READ_GERARCHIE = "Visualizza le Gerarchie esistenti";
	private static final String MSG_READ_COMPRENSORIO = "Visualizza i Comprensori esistenti";
	private static final String MSG_NEW_GERARCHIA = "Crea una nuova gerarchia";
	private static final String MSG_NEW_COMPRENSORIO = "Crea un Nuovo Comprensorio";
	private static final String MSG_READ_PROPOSTE_DI_FOGLIA = "Visualizza Proposte legate ad una Foglia";
	private static final String MSG_READ_CICLI_DA_CHIUDERE = "Visualizza Cicli da Chiudere";


	private List<MyJButton> buttons;
	
	private String textArea = "Scegli l'operazione desiderata.";

	
	/**
	 * Costruttore
	 * @since TESI
	 */
	public ChooseMenuConfiguratoreOptionButtons() {
		
		buttons = new ArrayList<MyJButton>();
		
		MyJButton buttonNewComprensorio = new MyJButton(MSG_NEW_COMPRENSORIO, 1);
		MyJButton buttonNewGerarchia = new MyJButton(MSG_NEW_GERARCHIA, 2);
		MyJButton buttonReadComprensori= new MyJButton(MSG_READ_COMPRENSORIO, 3);
		MyJButton buttonReadGerarchie = new MyJButton(MSG_READ_GERARCHIE, 4);
		MyJButton buttonReadFDC = new MyJButton(MSG_READ_FATTORI_CONVERSIONE_FOGLIA, 5);
		MyJButton buttonReadProposteDiFoglia= new MyJButton(MSG_READ_PROPOSTE_DI_FOGLIA, 6);
		MyJButton buttonReadCicliDaChiudere = new MyJButton(MSG_READ_CICLI_DA_CHIUDERE, 7);
		MyJButton buttonInfoConfiguratore = new MyJButton(MSG_INFO_OPZIONI, 8);
		buttonInfoConfiguratore.setIcon(FrequentIcons.INFO_ICON.getIcon());
		
		buttons.add(buttonNewComprensorio);
		buttons.add(buttonNewGerarchia);
		buttons.add(buttonReadComprensori);
		buttons.add(buttonReadGerarchie);
		buttons.add(buttonReadFDC);
		buttons.add(buttonReadProposteDiFoglia);
		buttons.add(buttonReadCicliDaChiudere);
		buttons.add(buttonInfoConfiguratore);
	}
	
	@Override
	public List<MyJButton> getButtons() {
		return this.buttons;
	}

	@Override
	public String getText() {
		return this.textArea;
	}

	@Override
	public void setText(String newText) {
		this.textArea = newText;
	}

}
