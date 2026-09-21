package personalGraphicElements;

import java.util.ArrayList;
import java.util.List;

import utility.FrequentIcons;

/**
 * Classe {@link ButtonsContainer} per le informazioni per il menu del Fruitore
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class ChooseMenuFruitoreOptionButtons implements ButtonsContainer{

	private static final String MSG_INFO_OPZIONI = "Info Opzioni";
	private static final String MSG_READ_PROPOSTE = "Visiona Proposte";
	private static final String MSG_RITIRA_PROPOSTA = "Ritira Proposta";
	private static final String MSG_NEW_PROPOSTA = "Crea Proposta";
	private static final String MSG_READ_GERARCHIA = "Scorri Gerarchia";

	private List<MyJButton> buttons;
	
	private String textArea = "Scegli l'operazione desiderata.";
	
	/**
	 * Costruttore
	 * @since TESI
	 */
	public ChooseMenuFruitoreOptionButtons() {
		
		buttons = new ArrayList<MyJButton>();
		
		MyJButton buttonScorriGerarchia = new MyJButton(MSG_READ_GERARCHIA, 1);
		MyJButton buttonCreaProposta = new MyJButton(MSG_NEW_PROPOSTA, 2);
		MyJButton buttonRitiraProposta = new MyJButton(MSG_RITIRA_PROPOSTA, 3);
		MyJButton buttonReadProposte = new MyJButton(MSG_READ_PROPOSTE, 4);
		MyJButton buttonInfoFruitore = new MyJButton(MSG_INFO_OPZIONI, 5);
		buttonInfoFruitore.setIcon(FrequentIcons.INFO_ICON.getIcon());
		
		buttons.add(buttonScorriGerarchia);
		buttons.add(buttonCreaProposta);
		buttons.add(buttonRitiraProposta);
		buttons.add(buttonReadProposte);
		buttons.add(buttonInfoFruitore);
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
