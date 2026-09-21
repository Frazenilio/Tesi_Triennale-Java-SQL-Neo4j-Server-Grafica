package indirectionAccessToGraphicPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;

import errori.ErroreInterruzioneOperazione;
import graphicUI.GraphicDisplayInterface;
import graphicUI.MenuWithButtonsGraphic;
import personalGraphicElements.*;
import utility.Colori;

/**
 * Classe per la gestione ed interazione di un menu
 * che fa uso di bottoni
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class MenuWithButtons implements GraphicDisplayInterface{
	
	private List<MyJButton> buttons;
	private ExitButton exitButton;
	private MyJTextPane message;
	
	private MenuWithButtonsGraphic grafica;
	
	private CompletableFuture<Integer> buttonValue;
	
	/**
	 * Costruttore
	 * @param buttonsContainer contenitore con le info necessarie: lista di MyJButton e
	 * un messaggio da mostrare
	 * @since TESI
	 */
	public MenuWithButtons(ButtonsContainer buttonsContainer) {
		
		this.exitButton = new ExitButton();
		this.buttons = buttonsContainer.getButtons();
		
		this.message = new MyJTextPane(buttonsContainer.getText());
		this.message.setEditable(false);
		this.message.setBackground(Colori.VIOLETTO.getVal());
				
		for(MyJButton b : buttons) {
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonValue.complete(b.getValueWhenPressed());
				}
			});
		}
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonValue.completeExceptionally(new ErroreInterruzioneOperazione());
			}
		});
		
		this.grafica = new MenuWithButtonsGraphic(exitButton, message, buttons);
	}
	
	public List<MyJButton> getButtons() {
		return this.buttons;
	}
	
	public ExitButton getExitButton() {
		return this.exitButton;
	}
	
	public MyJTextPane getMessage() {
		return this.message;
	}
	
	public void setMessage(String newMessage) {
		this.message.setText(newMessage);
	}
	
	/**
	 * Metodo per cambiare le informazioni
	 * @param newContainer nuovo ButtonsContainer da cui attingere per le nuove informazioni
	 */
	public void replaceButtonContainer(ButtonsContainer newContainer) {
		this.buttons.clear();
		this.buttons = newContainer.getButtons();
		this.message.setText(newContainer.getText());;
		
		for(MyJButton b : buttons) {
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonValue.complete(b.getValueWhenPressed());
				}
			});
		}
		
		this.grafica.changeButtonsAndText(buttons, newContainer.getText());;
		
	}
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	/**
	 * Metodo per ottenere il valore del bottone premuto
	 * @return button pressed
	 * @throws ErroreInterruzioneOperazione 
	 * @since TESI
	 */
	public int ottieniButtonPressed() throws ErroreInterruzioneOperazione {
		this.buttonValue = new CompletableFuture<Integer>();
		try {
			return this.buttonValue.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new ErroreInterruzioneOperazione();
		}
	}

	@Override
	public JPanel obtainElementsToDraw() {
		return this.grafica;
	}

}
