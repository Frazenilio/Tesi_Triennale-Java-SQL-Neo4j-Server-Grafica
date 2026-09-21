package personalGraphicElements;

import java.util.ArrayList;
import java.util.List;

import utility.InterazioneOperazioni;

/**
 * Classe {@link ButtonsContainer} per un menu che chiede di confermare o annullare un'operazione
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class YesOrNoButtons implements ButtonsContainer{

	private static final String MSG_NOT_BUTTON_TEXT = "No";

	private static final String MSG_YES_BUTTON_TEXT = "Si";

	private String textArea;
	
	private List<MyJButton> buttons;
	
	/**
	 * Costruttore
	 * @since TESI
	 */
	public YesOrNoButtons() {
		this.buttons = new ArrayList<MyJButton>();
		this.textArea = "Confermi l'operazione?";
		
		MyJButton yesButton = new MyJButton(MSG_YES_BUTTON_TEXT, InterazioneOperazioni.CONFIRM_OPERATION.getVal());
		MyJButton noButton = new MyJButton(MSG_NOT_BUTTON_TEXT, InterazioneOperazioni.CANCEL_OPERATION.getVal());
		
		buttons.add(yesButton);
		buttons.add(noButton);
	}
	
	public YesOrNoButtons(String personalMessage) {
		this.buttons = new ArrayList<MyJButton>();
		this.textArea = personalMessage;
		
		MyJButton yesButton = new MyJButton(MSG_YES_BUTTON_TEXT, InterazioneOperazioni.CONFIRM_OPERATION.getVal());
		MyJButton noButton = new MyJButton(MSG_NOT_BUTTON_TEXT, InterazioneOperazioni.CANCEL_OPERATION.getVal());
		
		buttons.add(yesButton);
		buttons.add(noButton);
	}
	
	@Override
	public List<MyJButton> getButtons() {
		return this.buttons;
	}

	@Override
	public String getText() {
		return textArea;
	}

	@Override
	public void setText(String newText) {
		this.textArea = newText;
	}

}
