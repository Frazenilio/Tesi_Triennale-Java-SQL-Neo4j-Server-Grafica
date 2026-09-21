package indirectionAccessToGraphicPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JComponent;
import javax.swing.JPanel;

import graphicUI.GraphicDisplayInterface;
import graphicUI.WarnerToUserGraphic;
import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTextPane;
import utility.Colori;
import utility.FrequentIcons;
import utility.InterazioneBottoni;

/**
 * Metodo per la gestione di un ammonitore per l'utente
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class WarnerToUser implements GraphicDisplayInterface{

	private static final String MSG_OPERATION_SUCCESS = "Operazione Completata con Successo!";
	private static final String MSG_BUTTON_CONFIRM = "Prosegui";
	private static final String MSG_INSUFFICENT_DATA = "Dati Insufficienti o errati. Riprova.";
	private static final String MSG_ERROR_INVALID_CREDENTIAL = "Credenziali Errate. Riprova";
	private static final String MSG_OPERATION_CANCELLED = "Operazione Cancellata";
	
	private MyJTextPane message;
	
	private WarnerToUserGraphic grafica;
	
	private MyJButton continueButton;
	
	private boolean isIconToSet;
	
	private CompletableFuture<Boolean> prosegui;
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END VARIABLES /////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Costruttore ma non imposta il messaggio personalizzato.
	 * Rivolgersi a setTextArea per il messaggio personalizzato
	 * @since TESI
	 */
	public WarnerToUser() {
		this.message = new MyJTextPane();
		
		this.continueButton = new MyJButton(MSG_BUTTON_CONFIRM, InterazioneBottoni.CONFIRM_DATA.getIntValue());
		
		this.continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prosegui.complete(true);
			}
		});
		
		this.grafica = new WarnerToUserGraphic(message, continueButton);
		this.isIconToSet = false;
	}

	/**
	 * Metodo per impostare un nuovo messaggio
	 * @param newText nuova String da impostare
	 * @since TESI
	 */
	public void setTextArea(String newText) {
		this.message.setText(newText);
		this.grafica.setMessageText(newText);
	}

	@Override
	public JPanel obtainElementsToDraw() {
		return this.grafica;
	}

	/**
	 * Metodo per avvisare l'utente con un messaggio personalizzato
	 * @param warning String personalizzata
	 * @since TESI
	 */
	public void warnUserWithCustomMessage(String warning) {
		this.prosegui = new CompletableFuture<Boolean>();
		this.grafica.removeAdditionalComponent();
		this.setTextArea(warning);
		if(this.isIconToSet) {
			this.message.addStyle(FrequentIcons.GREEN_V.getIcon());
		}
		this.grafica.dispatchEvent(new ComponentEvent(grafica, ComponentEvent.COMPONENT_RESIZED));
		try {
			prosegui.get();
		} catch (InterruptedException | ExecutionException e) {
		}
	}
	
	/**
	 * Metodo per avvisare l'utente di un'operazione cancellata
	 * @since TESI
	 */
	public void warnUserOperationCancelled() {
		this.warnUserWithCustomMessage(MSG_OPERATION_CANCELLED);
	}
	
	/**
	 * Metodo per avvisare l'utente di credenziali non valide
	 * @since TESI
	 */
	public void warnUserInvalidCredentials() {
		this.warnUserWithCustomMessage(MSG_ERROR_INVALID_CREDENTIAL);
	}
	
	/**
	 * Metodo per avvisare l'utente di dati insufficienti per l'operazione
	 * @since TESI
	 */
	public void warnUserInsufficentData() {
		this.warnUserWithCustomMessage(MSG_INSUFFICENT_DATA);
	}
	
	/**
	 * Metodo per avvisare l'utente di un'exception lanciata
	 * @param e exception lanciata da cui prendere il messaggio
	 * @since TESI
	 */
	public void warnUserException(Exception e) {
		this.message.setForeground(Colori.ROSSO.getVal());
		this.warnUserWithCustomMessage(e.getMessage());
		this.message.setForeground(Colori.NERO.getVal());
	}
	
	public void warnUserSuccessOperation() {
		this.message.setForeground(Colori.VERDE_SCURO.getVal());
		this.isIconToSet = true;
		this.warnUserWithCustomMessage(MSG_OPERATION_SUCCESS);
		this.isIconToSet = false;
		this.message.setForeground(Colori.NERO.getVal());
	}
	
	public <T> void warnUserWithMessageAndAdditionalComponent(JComponent comp, String warning) {
		this.prosegui = new CompletableFuture<Boolean>();
		this.grafica.removeAdditionalComponent();
		this.setTextArea(warning);
		this.grafica.addComponentWithMessage(comp);
		this.grafica.dispatchEvent(new ComponentEvent(grafica, ComponentEvent.COMPONENT_RESIZED));
		try {
			prosegui.get();
		} catch (InterruptedException | ExecutionException e) {
		}
	}
}
