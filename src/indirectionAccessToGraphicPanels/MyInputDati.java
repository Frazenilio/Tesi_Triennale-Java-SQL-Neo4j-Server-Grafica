package indirectionAccessToGraphicPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;

import errori.ErroreInterruzioneOperazione;
import graphicUI.GraphicDisplayInterface;
import graphicUI.MyInputDatiGraphic;
import personalGraphicElements.MyJTextPane;
import personalGraphicElements.AreaForInput;
import personalGraphicElements.ExitButton;
import personalGraphicElements.MyJButton;
import utility.InterazioneBottoni;
import utility.InterazioneOperazioni;

/**
 * Classe per la gestione ed interazione di un
 * input dati
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MyInputDati implements GraphicDisplayInterface{

		
	private static final String MSG_ERROR_TEXT_BLANK = "Testo vuoto non ammesso, riprova.";
	private static final String MSG_ERROR_TEXT_PLACEHOLDER = "Il testo inserito non e' valido, riprova.";
	private static final String MSG_LIMIT_CHARACTER = "Limite di caratteri superato, riprova.";
	private static final int MAX_INPUT_CHARACTERS = 30;
	private static final String MSG_WARN_INVALID_FORMAT = "Il dato che hai inserito non è nel formato corretto, riprova";
	
	private AreaForInput inputArea;
	private MyJTextPane requestDataArea;
	private ExitButton exitButton;
	
	private MyInputDatiGraphic grafica;
		
	private MyJButton confirmData;
	
	private CompletableFuture<String> result; 

	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END VARIABLES /////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Costruttore
	 * @since TESI
	 */
	public MyInputDati() {
		inputArea = new AreaForInput();
		initialize();
	}
	
	public MyInputDati(String placeholder) {
		inputArea = new AreaForInput(placeholder);
		initialize();
	}
	
	/**
	 * Metodo per reimpostare l'input area ad un valore iniziale
	 * @since TESI
	 */
	public void cleanTextArea() {
		inputArea.setText("");
	}
	
	/**
	 * Metodo per impostare il messaggio posto in alto con un nuovo valore
	 * @param newMessage nuovo messaggio da mostrare
	 * @since TESI
	 */
	public void setRequestDataMessage(String newMessage) {
		this.requestDataArea.setText(newMessage);
	}

	/**
	 * Metodo ausiliario per l'inizializzazione della classe. 
	 * Dispone il messaggio in alto, input area al centro e messaggio d'uscita in basso
	 * @since TESI
	 */
	private void initialize() {
		
		requestDataArea = new MyJTextPane();
		exitButton = new ExitButton();
		confirmData = new MyJButton(InterazioneBottoni.CONFIRM_DATA.getValue(), InterazioneBottoni.CONFIRM_DATA.getIntValue());
		
		requestDataArea.setEditable(false);
		
		inputArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				if(key.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!inputArea.getText().equalsIgnoreCase("")){
						if(inputArea.getText().length() > MAX_INPUT_CHARACTERS) {
							inputArea.addMiniWarning(MSG_LIMIT_CHARACTER);
						}
						else {
							tryToComplete();
						}
					}
				}
			}
		});
		
		this.exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performQuitEvent();
			}
		});
		
		this.confirmData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tryToComplete();
			}
		});
		
		this.grafica = new MyInputDatiGraphic(requestDataArea, inputArea, exitButton, confirmData);
	}
	
	/**
	 * Metodo per nascondere il warning dell'area di input
	 * @since TESI
	 */
	public void hideWarning() {
		this.inputArea.hideMiniWarning();
	}
	
	/**
	 * Metodo per resettare al placeholder l'area di input
	 * @param placeholder
	 */
	public void setPlaceholder(String placeholder) {
		this.inputArea.setPlaceholder(placeholder);
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	/**
	 * Metodo per richiedere un input (restando in attesa) ed inserire un messaggio personalizzato
	 * @param newText testo personalizzato da inserire
	 * @return String ottenuta da input
	 * @throws ErroreInterruzioneOperazione exception lanciata quando si preme il bottone d'uscita
	 * @since TESI
	 */
	public String setRequestMessageAndAskInput(String newText) throws ErroreInterruzioneOperazione {
		this.setRequestDataMessage(newText);
		return this.passInputText();
	}
	
	/**
	 * Metodo per richiedere un input numerico double (restando in attesa) ed inserire un messaggio personalizzato
	 * @param newText testo personalizzato da inserire
	 * @return double ottenuto da input
	 * @throws ErroreInterruzioneOperazione exception lanciata quando si preme il bottone d'uscita
	 * @since TESI
	 */
	public double setRequestMessageAndAskInputAsDouble(String newText) throws ErroreInterruzioneOperazione {
		this.setRequestDataMessage(newText);
		boolean ok = false;
		double result = InterazioneOperazioni.NO_EVENT.getVal();
		do {
			String temp = this.passInputText();
			ok = true;
			
			try{
				
				result = Double.parseDouble(temp);
				
				if(result <= 0) {
					ok = false;
					inputArea.addMiniWarning(MSG_WARN_INVALID_FORMAT);
				}
				
			} catch(Exception e) {
				
				ok = false;
				inputArea.addMiniWarning(MSG_WARN_INVALID_FORMAT);
			
			}
		}while(!ok);
		
		return result;
	}
	
	/**
	 * Metodo per richiedere un input numerico int (restando in attesa) ed inserire un messaggio personalizzato
	 * @param newText testo personalizzato da inserire
	 * @return int ottenuto da input
	 * @throws ErroreInterruzioneOperazione exception lanciata quando si preme il bottone d'uscita
	 * @since TESI
	 */
	public int setRequestMessageAndAskInputAsInteger(String newText) throws ErroreInterruzioneOperazione {
		this.setRequestDataMessage(newText);
		boolean ok = false;
		int result = InterazioneOperazioni.NO_EVENT.getVal();
		do {
			String temp = this.passInputText();
			ok = true;
			
			try{
				
				result = Integer.parseInt(temp);
				
				if(result <= 0) {
					ok = false;
					inputArea.addMiniWarning(MSG_WARN_INVALID_FORMAT);
				}
				
			} catch(Exception e) {
				
				ok = false;
				inputArea.addMiniWarning(MSG_WARN_INVALID_FORMAT);
			
			}
		}while(!ok);
		
		return result;
	}

	@Override
	public JPanel obtainElementsToDraw() {
		return this.grafica;
	}
	
	/**
	 * Metodo synchronized per chiudere l'input 
	 * @since TESI
	 */
	private synchronized void performQuitEvent() {
		this.result.completeExceptionally(new ErroreInterruzioneOperazione());
		notify();
	}
	
	/**
	 * Metodo per passare una String
	 * @return String ottenuta da input
	 * @throws ErroreInterruzioneOperazione exception lanciata quando si chiude l'operazione senza una String ottenuta
	 */
	public String passInputText() throws ErroreInterruzioneOperazione {
		this.result = new CompletableFuture<String>();
		this.inputArea.setFocusable(true);
		this.inputArea.setEditable(true);
		this.inputArea.resetToPlaceholder();
		try {
			return this.result.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new ErroreInterruzioneOperazione();
		}
	}
	
	/**
	 * Metodo per aggiungere un warning all'area di input
	 * @param text testo del warning
	 * @since TESI
	 */
	public void warnUserWithInputDati(String text) {
		this.inputArea.addMiniWarning(text);
	}
	
	private void tryToComplete() {
		this.inputArea.hideMiniWarning();
		boolean correct = true;
		if(this.inputArea.getText().equals(this.inputArea.getPlaceholderMessage())) {
			correct = false;
			this.warnUserWithInputDati(MSG_ERROR_TEXT_PLACEHOLDER);
		}
		else if(this.inputArea.getText().isBlank()) {
			correct = false;
			this.warnUserWithInputDati(MSG_ERROR_TEXT_BLANK);
		}
		if(correct) {
			result.complete(inputArea.getText());
		}
	}
}
