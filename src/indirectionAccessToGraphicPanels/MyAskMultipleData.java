package indirectionAccessToGraphicPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;

import errori.ErroreInterruzioneOperazione;
import graphicUI.AskMultipleDataGraphic;
import graphicUI.GraphicDisplayInterface;
import personalGraphicElements.AreaForInput;
import personalGraphicElements.ExitButton;
import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTextPane;
import utility.Colori;
import utility.InterazioneBottoni;

/**
 * Classe per richiedere piu' dati contemporaneamente all'utente
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MyAskMultipleData implements GraphicDisplayInterface{

	private List<AreaForInput> areeDiInput;
	
	private ExitButton exitButton;
	
	private MyJTextPane message;
	
	private AskMultipleDataGraphic grafica;
	
	private MyJButton confirmDatas;
	
	private MyJButton addInputArea;
	private MyJButton removeInputArea;
	
	private List<String> addedInputPlaceholder;
	
	private int standardNumberInputs;
	private int numberInputsPerData;
	
	private int relevantForValidity;
	
	private int indexFocused;
	
	private static int addedAreaCounter = 1;
	
	private CompletableFuture<List<List<String>>> inputs;
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END VARIABLES /////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Costruttore
	 * @param message messaggio mostrato
	 * @param listaPlaceholder lista di placeholder da utilizzare per la creazione delle prime aree di input
	 * @param addableAreas boolean per indicare se le aree di input sono aggiungibili o no
	 * @param numero minimo di aree di input da inserire e mantenere
	 * @param raggruppamento intero per indicare quanti input indicano un singolo con conseguenti ripercussioni sulla rappresentazione.
	 * Se inserisci un valore minore o uguale a 0, verra' usato il valore di default 1
	 * @Precondizione la lista di placeholder non dovrebbe essere di dimensione inferiore al numero minimo di componenti (non c'e'
	 * alcun controllo su questo ma non comporta a particolari effetti indesiderati)
	 * @since TESI
	 */
	public MyAskMultipleData(String message, List<String> listaPlaceholder, boolean addableAreas, int raggruppamento, int minimumNumber) {
		super();
		this.message = new MyJTextPane(message);
		this.standardNumberInputs = minimumNumber;
		this.numberInputsPerData = raggruppamento <= 0 ? 1 : raggruppamento;
		if(raggruppamento == 1) {
			relevantForValidity = 0;
		}
		
		if(addableAreas) {
			this.addInputArea = new MyJButton(InterazioneBottoni.ADD_SECTION.getValue(), InterazioneBottoni.ADD_SECTION.getIntValue());
			this.addInputArea.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addInpuAreaInScroller();
				}
			});
			
			this.removeInputArea = new MyJButton(InterazioneBottoni.REMOVE_SECTION.getValue(),
					InterazioneBottoni.REMOVE_SECTION.getIntValue());
			this.removeInputArea.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(areeDiInput.size() > standardNumberInputs) {
						removeInputAreaInScroller();
					}
				}
			});
			
		}
		else {
			this.addInputArea = null;
			this.removeInputArea = null;
		}
		initialize(listaPlaceholder);
	}


	/**
	 * Metodo per inizializzare la classe
	 * @param listaPlaceholder lista di placeholder per creare le aree di input iniziali
	 * @since TESI
	 */
	private void initialize(List<String> listaPlaceholder) {
		this.exitButton = new ExitButton();
		this.confirmDatas = new MyJButton(InterazioneBottoni.CONFIRM_DATA.getValue(), InterazioneBottoni.CONFIRM_DATA.getIntValue());
		this.areeDiInput = new ArrayList<AreaForInput>();
		this.addedInputPlaceholder = new ArrayList<String>();
		
		initializeInputsBasedOnPlaceholders(listaPlaceholder);
		for(AreaForInput a : this.areeDiInput) {
			this.addListenersForInput(a);
		}
		
		this.confirmDatas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				raccogliInput();
			}
		});
		this.confirmDatas.setBackground(Colori.BLU_SCURO.getVal());
		
		this.exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputs.completeExceptionally(new ErroreInterruzioneOperazione());
			}
		});
		
		this.grafica = new AskMultipleDataGraphic(this.areeDiInput, this.exitButton, this.message,
				this.confirmDatas, this.addInputArea, this.removeInputArea, this.numberInputsPerData);
	}


	/**
	 * Metodo per creare aree di input iniziali
	 * @param listaPlaceholder lista da cui attingere per la creazione di aree 
	 * di input iniziali. Ogni elemento corrisponde ad un'are di input
	 * @since TESI
	 */
	private void initializeInputsBasedOnPlaceholders(List<String> listaPlaceholder) {
		for(String p : listaPlaceholder) {
			AreaForInput a = new AreaForInput(p);
			this.areeDiInput.add(a);
		}
	}

	@Override
	public JPanel obtainElementsToDraw() {
		return this.grafica;
	}
	
	public void setMessage(String message) {
		this.message.setText(message);
	}
	
	public void setListaPlaceholders(List<String> lista) {
		this.initializeInputsBasedOnPlaceholders(lista);
	}
	
	public void setPlaceholderMessageForAdded(List<String> newPlaceholder) {
		this.addedInputPlaceholder = newPlaceholder;
	}
	
	/**
	 * Metodo per nascondere i warning di tutte le aree di input
	 * @since TESI
	 */
	public void hideAllWarnings() {
		for(AreaForInput a : this.areeDiInput) {
			a.hideMiniWarning();
		}
	}
	
	/**
	 * Metodo per verificare che una data area di input (posseduta da questa classe) abbia il testo uguale al placeholder
	 * @param index indice dell'area da controllare
	 * @return true se il testo e' uguale al placeholder, false altrimenti
	 * @since TESI
	 */
	public boolean isTextEqualToPlaceholder(int index) {
		return this.areeDiInput.get(index).getText()
				.equalsIgnoreCase(
				this.areeDiInput.get(index).getPlaceholderMessage())
				? true : false;
	}
	
	/**
	 * Metodo per resettare al placeholder e nascondere warning
	 * di tutte le aree di input
	 * @since TESI
	 */
	public void resetToPlaceholderAndHideWarning() {
		for(AreaForInput a : this.areeDiInput) {
			a.resetToPlaceholder();
			a.hideMiniWarning();
		}
	}
	
	/**
	 * Metodo per resettare al placeholder e aggiungere un warning warning
	 * ad un'area di input
	 * @param text testo del warning
	 * @param index indice dell'area da considerare
	 * @since TESI
	 */
	public void setToPlaceholderAndAddWarning(String text, int index) {
		this.areeDiInput.get(index).resetToPlaceholder();
		this.areeDiInput.get(index).addMiniWarning(text);
	}
	
	public void resetAllToPlaceholder() {
		for(AreaForInput a : this.areeDiInput) {
			a.resetToPlaceholder();
		}
	}
	
	public void setRelevantControlForValidity(int n) {
		this.relevantForValidity = n;
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	


	/**
	 * Metodo per settare un messaggio e chiedere credenziali
	 * @param message messaggio da impostare
	 * @return lista di stringhe dalle multiple aree di input
	 * @throws ErroreInterruzioneOperazione
	 * @since TESI
	 */
	public List<List<String>> setMessageAndAskCredentials(String message) throws ErroreInterruzioneOperazione{
		this.setMessage(message);
		return askCredentials();
	}


	/**
	 * Metodo per chiedere multipli input all'utente
	 * @return lista di stringhe dalle multiple aree di input
	 * @throws ErroreInterruzioneOperazione
	 * @since TESI
	 */
	public List<List<String>> askCredentials() throws ErroreInterruzioneOperazione {
		this.grafica.changeConfirmAvailability(false);
		this.inputs = new CompletableFuture<List<List<String>>>();
		try {
			return this.inputs.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new ErroreInterruzioneOperazione();
		}	
	}

	/**
	 * Metodo per aggiungere un warning a tutte le aree di input
	 * @param warning testo del warning da inserire
	 * @since TESI
	 */
	public void addWarningUnderAllInputArea(String warning) {
		for(AreaForInput a : this.areeDiInput) {
			a.addMiniWarning(warning);
		}
	}
	
	/**
	 * Metodo per aggiungere un warning ad una sola area di input
	 * @param warning testo warning da inserire
	 * @param index indice dell'area di input da considerare
	 * @since TESI
	 */
	public void addWarningUnderSingleInputAreaBasedOnIndex(String warning, int index) {
		this.areeDiInput.get(index).addMiniWarning(warning);
	}
	
	/**
	 * Metodo per inserire una nuova area di input con la molteplicita' dichiarata nel costruttore
	 * @since TESI
	 */
	public void addInpuAreaInScroller() {
		List<AreaForInput> areasToPass = new ArrayList<AreaForInput>();
		for(int i = 0; i < this.numberInputsPerData; i++) {
			AreaForInput a = new AreaForInput();
			if((this.addedInputPlaceholder != null || !this.addedInputPlaceholder.isEmpty())
					&& !this.addedInputPlaceholder.get(i).equalsIgnoreCase("")) {
				a = new AreaForInput(addedInputPlaceholder.get(i) + addedAreaCounter);
			}
			this.areeDiInput.add(a);
			areasToPass.add(a);
		}
		for(AreaForInput a : areasToPass) {
			addListenersForInput(a);
		}
		addedAreaCounter++;
		this.grafica.addAreaDiInpuInScroller(areasToPass);
		this.scrollToSpecificInput(this.areeDiInput.size() - 1 - this.numberInputsPerData);
		this.grafica.changeConfirmAvailability(false);
	}

	
	/**
	 * Metodo per rimuovere l'ultima area di input aggiunta
	 * @Precondizione L'array delle aree di input non deve essere vuoto (o null)
	 * @since TESI
	 */
	public void removeInputAreaInScroller() {
		if(this.indexFocused > this.areeDiInput.size() - 1) {
			this.indexFocused = this.areeDiInput.size() - 1;
		}
		for(int i = 0; i < this.numberInputsPerData; i++) {
			this.areeDiInput.remove(this.indexFocused);
		}
		this.grafica.removeAreaDiInputInScroller(this.indexFocused);
		if(areAllAreaInputsSet(relevantForValidity)) {
			grafica.changeConfirmAvailability(true);
		}
		else {
			grafica.changeConfirmAvailability(false);
		}
	}
	
	/**
	 * Metodo per avvisare la grafica di scrollare la visuale ad uno specifico input
	 * @param index indice dell'area a cui scrollare
	 * @since TESI
	 */
	public void scrollToSpecificInput(int index) {
		this.grafica.scrollToSpecificInput(index);
	}
	
	/**
	 * Metodo ausiliario per controllare che tutte le aree di input abbiano un valore valido impostato
	 * @param numeroPerData
	 * @return
	 */
	private boolean areAllAreaInputsSet(int numeroPerData) {
		for(int i = numeroPerData; i < this.areeDiInput.size(); i = i + this.numberInputsPerData) {
			if(!this.areeDiInput.get(i).isInputSetted() || this.areeDiInput.get(i).getText().isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Metodo per cambiare il messaggio del bottone di conferma
	 * @param confirm nuovo testo di bottone di conferma
	 * @since TESI
	 */
	public void changeConfirmMessage(String confirm) {
		this.confirmDatas.setText(confirm);
		this.grafica.changeConfirmMessage(confirm);
	}
	
	public void resetCounterForAddedAreas() {
		addedAreaCounter = 1;
	}
	
	/**
	 * Classe per ottenere tutti gli input di tutte le aree di input e completare il {@link CompletableFuture} inputs
	 * @since TESI
	 */
	private void raccogliInput() {
		List<List<String>> inputResults = new ArrayList<List<String>>();
		for(int i = 0; i < this.areeDiInput.size(); i = i + this.numberInputsPerData) {
			List<String> data = new ArrayList<String>();
			for(int j = 0; j < this.numberInputsPerData; j++) {
				data.add(this.areeDiInput.get(i + j).getText());
			}
			inputResults.add(data);
		}
		this.inputs.complete(inputResults);
	}
	
	/**
	 * Metodo ausiliario per inserire Listener alle aree di input
	 * @param a area di input a cui aggiungere i Listener
	 * @since TESI
	 */
	private void addListenersForInput(AreaForInput a) {
		a.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			@Override
			public void focusGained(FocusEvent e) {
				indexFocused = areeDiInput.indexOf(a);
			}
		});
		a.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(areAllAreaInputsSet(relevantForValidity)) {
					grafica.changeConfirmAvailability(true);
				}
				else {
					grafica.changeConfirmAvailability(false);
				}
			}
		});
	}
}
