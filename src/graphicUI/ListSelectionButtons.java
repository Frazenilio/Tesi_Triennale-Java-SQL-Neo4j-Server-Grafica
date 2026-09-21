package graphicUI;

import java.util.ArrayList;
import java.util.List;

import fromObjectToButtonTextChainOfResponsibility.ObjectToTextInterface;
import fromObjectToButtonTextChainOfResponsibility.TextToButtonText;
import personalGraphicElements.ButtonsContainer;
import personalGraphicElements.MyJButton;

/**
 * Classe per la gestione di bottoni per 
 * scelta da una lista
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class ListSelectionButtons implements ButtonsContainer{

	private String text = "Seleziona Scelta Lista";
	private List<MyJButton> buttons;
	
	private ObjectToTextInterface converterHandler = new TextToButtonText();
	
	/**
	 * Costruttore ListSelectionButtons
	 * @param <T> Tipo degli oggetti passati
	 * @param list lista di oggetti da scorrere
	 * @since TESI
	 */
	public <T> ListSelectionButtons(List<T> list) {
		buttons = new ArrayList<MyJButton>();
		for(int i = 0; i < list.size(); i++) {
			MyJButton button = new MyJButton(this.setButtonText(list.get(i)) ,i + 1);
			buttons.add(button);
		}
	}
	
	/**
	 * Costruttore ListSelectionButtons con testo personalizzato
	 * @param <T> Tipo degli oggetti passati
	 * @param list lista di oggetti da scorrere
	 * @param text testo personalizzato
	 * @since TESI
	 */
	public <T> ListSelectionButtons(List<T> list, String text) {
		this.text = text;
		
		buttons = new ArrayList<MyJButton>();
		for(int i = 0; i < list.size(); i++) {
			MyJButton button = new MyJButton(this.setButtonText(list.get(i)) ,i + 1);
			buttons.add(button);
		}
	}
	
	@Override
	public List<MyJButton> getButtons() {
		return this.buttons;
	}

	@Override
	public String getText() {
		return text;
	}
	
	/**
	 * Metodo ausiliario per assegnare un testo ad un bottone
	 * @param <T> Tipo dell'oggetto da convertire
	 * @param objectToConvert oggetto da convertire
	 * @return stringa associata all'oggetto
	 * @since TESI 
	 */
	private <T> String setButtonText(T objectToConvert) {
		return this.converterHandler.objectToTextButton(objectToConvert);
	}

	@Override
	public void setText(String newText) {
		this.text = newText;
	}
}
