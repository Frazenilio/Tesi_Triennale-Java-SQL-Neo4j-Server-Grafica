package indirectionAccessToGraphicPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import fromObjectToGraphicChainOfResponsibility.ObjectListGraphic;
import graphicUI.GraphicDisplayInterface;
import graphicUI.ScorrimentoRisultatiGraphic;
import personalGraphicElements.ExitButton;
import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTextPane;
import utility.Colori;
import utility.InterazioneBottoni;

/**
 * Classe per la gestione ed interazione con un
 * menu di scorrimento di risultati
 * @param <T> Tipo degli oggetti che si stanno scorrendo
 */
public class MyScorrimentoRisultati<T> implements GraphicDisplayInterface{
	
	private ExitButton exitButton;
	private JPanel graphicObject;
	private MyJTextPane message;
	protected MyJButton scrollDownButton;
	protected MyJButton scrollUpButton;
	
	private List<T> objectsToScroll;
	
	protected int index;
	
	private ScorrimentoRisultatiGraphic grafica;
	
	protected CompletableFuture<T> risultato;
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END VARIABLES /////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	/**
	 * Costruttore SENZA LISTA DI OGGETTI. Rivolgersi al metodo apposito per poter scorrere la lista (setObjectsToScroll)
	 * @param text testo del messaggio personalizzato posto in alto
	 * @since TESI
	 */
	public MyScorrimentoRisultati(String text){
		this.exitButton = new ExitButton();
		this.message = new MyJTextPane(text);
		this.message.setBackground(Colori.VIOLETTO.getVal());
		
		
		this.scrollUpButton = new MyJButton(InterazioneBottoni.INDIETRO.getValue(), InterazioneBottoni.INDIETRO.getIntValue());
		this.scrollDownButton = new MyJButton(InterazioneBottoni.AVANTI.getValue(), InterazioneBottoni.AVANTI.getIntValue());
		
		this.scrollUpButton.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
		this.scrollDownButton.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
				
		this.message.setEditable(false);
		
		this.index = 0;
		
		this.objectsToScroll = new ArrayList<T>();
		
		this.exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				risultato.completeExceptionally(new ErroreInterruzioneOperazione());
			}
		});
		
		this.scrollUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					modificaIndex(scrollUpButton);
				} catch (ErroreDatiAssenti e1) {
				}
				changeButtonsColorBasedOnIndex();
			}
		});
		this.scrollDownButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					modificaIndex(scrollDownButton);
				} catch (ErroreDatiAssenti e1) {
				}
				changeButtonsColorBasedOnIndex();
			}
		});
		
		this.grafica = new ScorrimentoRisultatiGraphic(message, exitButton, scrollUpButton, scrollDownButton);
	}

	protected List<T> getObjectsToScroll(){
		return this.objectsToScroll;
	}
	/**
	 * @return the graphicObject
	 */
	protected JPanel getGraphicObject() {
		return graphicObject;
	}

	/**
	 * @param graphicObject the graphicObject to set
	 */
	protected void setGraphicObject(JPanel graphicObject) {
		this.graphicObject = graphicObject;
	}

	public void setMessageText(String newText) {
		this.message.setText(newText);
	}
	
	public void setObjectsToScroll(List<T> objs) {
		this.objectsToScroll = objs;
	}
	
	public ScorrimentoRisultatiGraphic getGrafica() {
		return this.grafica;
	}

	/**
	 * Metodo per ottenere l'oggetto da rappresentare corrente
	 * @return oggetto da rappresentare corrente
	 * @since TESI
	 */
	protected T obtainCurrentObject() {
		if(this.index < 0) {
			index = 0;
		}
		if(this.index > this.getObjectsToScroll().size()-1 && this.getObjectsToScroll().size() > 0) {
			index = this.getObjectsToScroll().size()-1;
		}
		return this.getObjectsToScroll().get(index);
	}
	

	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	

	/**
	 * Metodo per trasformare un oggetto in un componente grafico. Viene anche aggiunto alla graficazione di questa classe
	 * @param object oggetto da grafica
	 * @throws ErroreDatiAssenti 
	 * @since TESI
	 */
	private void graficaObjectToDisplay(T object) throws ErroreDatiAssenti {
		this.setGraphicObject(ObjectListGraphic.objConverterToGraphic(object));
		this.grafica.setObject(this.getGraphicObject(), this.index + 1, this.getObjectsToScroll().size());
	}

	@Override
	public JPanel obtainElementsToDraw() {
		return this.grafica;
	}
	
	/**
	 * Metodo per scorrere una lista di oggetti uno ad uno
	 * @throws ErroreInterruzioneOperazione exception lanciata per interrompere lo scorrimento
	 * @throws ErroreDatiAssenti 
	 * @since TESI
	 */
	public void scorriLista() throws ErroreInterruzioneOperazione, ErroreDatiAssenti {
		this.risultato = new CompletableFuture<T>();
		this.index = 0;
		this.getGrafica().greenButtonsLimitsFree();
		this.getGrafica().redButtonLimitReached(false);
		if(this.getObjectsToScroll().size() - 1 == 0) {
			this.getGrafica().redButtonLimitReached(true);
		}
		this.graficaObjectToDisplay(this.obtainCurrentObject());
		try {
			this.risultato.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new ErroreInterruzioneOperazione();
		}
	}
	
	/**
	 * Metodo synchronized per modificare l'index corrente entro i limiti consentiti (tra 0 e dimensione della lista - 1)
	 * @param button MyJButton che ha innescato l'aumento
	 * @throws ErroreDatiAssenti 
	 * @since TESI
	 */
	private synchronized void modificaIndex(MyJButton button) throws ErroreDatiAssenti {
		int value = button.getValueWhenPressed();
		if((value < 0 && index > 0) || (value > 0 && index < this.objectsToScroll.size() - 1)){
			index += value;
			this.graficaObjectToDisplay(this.obtainCurrentObject());
		}	
	}
	
	/**
	 * Metodo per cambiare il colore dei colori in base all'indice passato
	 * @param index indice
	 * @since TESI
	 */
	protected void changeButtonsColorBasedOnIndex() {
		if(this.getObjectsToScroll().size() - 1 == 0) {
			this.getGrafica().redButtonLimitReached(true);
			this.getGrafica().redButtonLimitReached(false);
		}
		else if(index == 0) {
			this.getGrafica().redButtonLimitReached(false);
			this.getGrafica().greenButtonScrollLimit(false);
		}
		else if(index == this.getObjectsToScroll().size() - 1) {
			this.getGrafica().redButtonLimitReached(true);
			this.getGrafica().greenButtonScrollLimit(true);
		}
		else if(index > 0 && index < this.getObjectsToScroll().size() - 1) {
			this.getGrafica().greenButtonsLimitsFree();
		}
		
	}
}
