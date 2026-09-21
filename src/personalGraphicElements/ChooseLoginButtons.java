package personalGraphicElements;


import utility.FrequentIcons;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe {@link ButtonsContainer} per tenere le informazioni per il MenuWithButtons di login
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class ChooseLoginButtons implements ButtonsContainer{
	
	private static final String MSG_INFO_APPLICATION = "Info Applicativo";
	private static final String MSG_BUTTON_CREA_NUOVO_FRUITORE = "Crea nuovo Fruitore";
	private static final String MSG_BUTTON_LOGIN_FRUITORE = "Login Fruitore";
	private static final String MSG_BUTTON_LOGIN_CONFIGURATORE = "Login Configuratore";
	
	private List<MyJButton> buttons;
	
	private String textArea = "Scegli come effettuare il login o crea un nuovo utente.";
	
	/**
	 * Costruttore
	 * @since TESI
	 */
	public ChooseLoginButtons() {
		
		buttons = new ArrayList<MyJButton>();
		MyJButton buttonLoginConfiguratore = new MyJButton(MSG_BUTTON_LOGIN_CONFIGURATORE, 1);		
		MyJButton buttonLoginFruitore = new MyJButton(MSG_BUTTON_LOGIN_FRUITORE, 2);
		MyJButton buttonCreationFruitore = new MyJButton(MSG_BUTTON_CREA_NUOVO_FRUITORE, 3);
		MyJButton buttonInformazioniApplicatvo = new MyJButton(MSG_INFO_APPLICATION, 4);
		buttonInformazioniApplicatvo.setIcon(FrequentIcons.INFO_ICON.getIcon());
		
		
		buttons.add(buttonLoginConfiguratore);
		buttons.add(buttonLoginFruitore);
		buttons.add(buttonCreationFruitore);
		buttons.add(buttonInformazioniApplicatvo);
	}
	
	public List<MyJButton> getButtons(){
		return this.buttons;
	}
	
	public String getText() {
		return this.textArea;
	}

	@Override
	public void setText(String newText) {
		this.textArea = newText;
	}
	
}
