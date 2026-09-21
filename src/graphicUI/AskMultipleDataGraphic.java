package graphicUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import personalGraphicElements.AreaForInput;
import personalGraphicElements.ExitButton;
import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTextPane;
import utility.Colori;

/**
 * Classe per la rappresentazione di spazi di input multipli
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class AskMultipleDataGraphic extends JPanel{

	private static final int FRACTION_ACTIONS_HEIGHT = 4;
	private static final int FRACTION_ACTIONS_FONT_HEIGHT = 4;
	
	private static final int FRACTION_SCROLLER_FONT_HEIGHT = 5;
	private static final int FRACTION_SCROLLER_HEIGHT = 5;
	private static final int MOLTIPLICATION_SCROLLER_HEIGHT = 2;
	
	private static final int FRACTION_MESSAGE_HEIGHT = 5;
	private static final int FRACTION_MESSAGE_FONT_HEIGHT = 4;
	
	
	private static final int RIGHT_ACTIONS_INSETS = 15;
	private static final int BOTTOM_ACTIONS_INSETS = 15;
	private static final int LEFT_ACTIONS_INSETS = 15;
	private static final int TOP_ACTIONS_INSETS = 15;
	
	private static final int RIGHT_INPUT_INSETS = 300;
	private static final int BOTTOM_INPUT_INSETS = 15;
	private static final int LEFT_INPUT_INSETS = 300;
	private static final int TOP_INPUT_INSETS = 15;
	
	private static final int INDEX_ACTIONS_IN_PANEL = 2;
	private static final int INDEX_INPUTS_IN_PANEL = 1;
	private static final int INDEX_MESSAGE_IN_PANEL = 0;
	
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END CONSTANT VARIABLES ////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private List<AreaForInput> areeDiInput;
	private ExitButton exitButton;
	
	private MyJTextPane message;
	private JScrollPane messageScroller;
	
	private MyJButton confirmButton;
	
	private JScrollPane scroller;
	private JPanel inputContainer;
	
	private MyJButton addAreaButton;
	private MyJButton removeAreaButton;
	private JPanel inputManagerPanel;
	
	private JPanel panelForAdding;
	
	private JPanel actionsContainer;
	
	private int numberCompsInRow;
	
	private List<JPanel> panelsForInputs;
		
	
	public AskMultipleDataGraphic(List<AreaForInput> areeDiInput, ExitButton exitButton,
			MyJTextPane message, MyJButton confirm, MyJButton addAreaButton, MyJButton removeAreaButton, int raggruppamento) {
		super();
		this.numberCompsInRow = raggruppamento;
		this.areeDiInput = new ArrayList<AreaForInput>(areeDiInput);
				
		this.exitButton = exitButton;
		
		this.message = message;
		this.message.setBackground(Colori.BLU_CHIARO.getVal());
		this.message.setBorder(new LineBorder(Colori.NERO.getVal()));
		
		this.messageScroller = new JScrollPane();
		this.messageScroller.setLayout(new ScrollPaneLayout());
		this.messageScroller.setViewportView(this.message);
		
		this.confirmButton = confirm;
		this.panelsForInputs = new ArrayList<JPanel>();
		this.panelForAdding = new JPanel();
		this.panelForAdding.setBackground(Colori.BLU_CHIARO.getVal());
		
		if(addAreaButton != null) {
			this.addAreaButton = addAreaButton;
		}
		
		if(removeAreaButton != null) {
			this.removeAreaButton = removeAreaButton;
		}
		
		this.scroller = new JScrollPane();
		
		this.inputContainer = new JPanel();
		this.inputContainer.setBackground(Colori.BLU_CHIARO.getVal());
		this.actionsContainer = new JPanel();
		this.actionsContainer.setBackground(Colori.BLU_CHIARO.getVal());
		
		initialize();
	}

	/**
	 * Metodo per l'inizializzazione della classe, usata nel costruttore
	 * @since TESI
	 */
	private void initialize() {
		
		this.scroller.setLayout(new ScrollPaneLayout());
		this.scroller.setViewportView(this.placeInputsInContainer(this.areeDiInput));
		this.scroller.setBorder(BorderFactory.createMatteBorder(TOP_INPUT_INSETS, LEFT_INPUT_INSETS,
				BOTTOM_INPUT_INSETS, RIGHT_INPUT_INSETS, Colori.BLU_TOTAL.getVal()));
		
		this.panelForAdding.setLayout(new BoxLayout(this.panelForAdding, BoxLayout.Y_AXIS));
		this.panelForAdding.setBackground(Colori.BLU_TOTAL.getVal());
		this.panelForAdding.add(this.scroller);
		
		if(this.addAreaButton != null && this.removeAreaButton != null) {
			this.inputManagerPanel = new JPanel();
			this.inputManagerPanel.setBackground(Colori.BLU_TOTAL.getVal());
			this.inputManagerPanel.setLayout(new BoxLayout(inputManagerPanel, BoxLayout.X_AXIS));
			this.inputManagerPanel.add(this.addAreaButton);
			this.inputManagerPanel.add(this.removeAreaButton);
			this.panelForAdding.add(inputManagerPanel);
		}
		
		List<MyJButton> buttons = new ArrayList<MyJButton>();
		buttons.add(this.exitButton);
		buttons.add(this.confirmButton);
		this.placeActionButtonsInContainer(buttons);
		
		this.placeEverything();
		this.setBackground(Colori.BLU_CHIARO.getVal());
		
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentResized(ComponentEvent e) {
				updateSelfCoordinates(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	/**
	 * Metodo ausiliario per inserire tutti i componenti nel panel finale
	 * @since TESI
	 */
	private void placeEverything() {
		this.setLayout(new BorderLayout());
		this.add(this.messageScroller, BorderLayout.PAGE_START, INDEX_MESSAGE_IN_PANEL);
		this.add(this.panelForAdding, BorderLayout.CENTER, INDEX_INPUTS_IN_PANEL);
		this.add(this.actionsContainer, BorderLayout.PAGE_END, INDEX_ACTIONS_IN_PANEL);
		this.setBackground(Colori.BLU_CHIARO.getVal());
	}
	
	/**
	 * Metodo per aggiornare le dimensioni di tutti i componenti presenti
	 * @param newW nuova width
	 * @param newH nuova height
	 * @since TESI
	 */
	private void updateSelfCoordinates(int newW, int newH) {
		
		int messageHeight = newH / FRACTION_MESSAGE_HEIGHT;
		int messageHeightFont = messageHeight / FRACTION_MESSAGE_FONT_HEIGHT;
		
		this.message.setFont(new Font("Arial", Font.BOLD, messageHeightFont));
		this.message.setPreferredSize(new Dimension(newW, messageHeight));
		this.messageScroller.setPreferredSize(new Dimension(newW, messageHeight));
		
		
		int scrollerHeight = MOLTIPLICATION_SCROLLER_HEIGHT * newH / FRACTION_SCROLLER_HEIGHT;
		int scrollerWidth = newW / 3;
		
		this.updateInputsCoordinates(scrollerWidth, scrollerHeight);
		this.scroller.setPreferredSize(new Dimension(scrollerWidth, scrollerHeight / 2));
		
		int actionsHeight = newH / FRACTION_ACTIONS_HEIGHT;
		
		this.updateActionsCoordinates(newW, actionsHeight);
	}

	
	/////////////////////////////////////////////////////////////////////////
	//////////////////////// AUXILIARY METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Metodo ausiliario per aggiornamento delle dimensioni della componente
	 * degli input
	 * @param newW nuova width
	 * @param newH nuova height
	 * @since TESI
	 */
	private void updateInputsCoordinates(int newW, int newH) {
		int scrollHeightFont = newH / FRACTION_SCROLLER_FONT_HEIGHT;
		Font font = new Font("Arial", Font.BOLD, scrollHeightFont);
		
		if(this.addAreaButton != null) {
			this.panelForAdding.setPreferredSize(new Dimension((int) (1.5 * this.numberCompsInRow * newW),
					scrollHeightFont * 2 * this.areeDiInput.size()));
			
			for(JPanel p : this.panelsForInputs) {
				p.setPreferredSize(new Dimension(this.numberCompsInRow * newW, 2 * scrollHeightFont));
			}
			this.addAreaButton.setPreferredSize(new Dimension(newW, scrollHeightFont));
			this.addAreaButton.setFont(font);
			
			this.removeAreaButton.setPreferredSize(new Dimension(newW, scrollHeightFont));
			this.removeAreaButton.setFont(font);
			
			this.inputManagerPanel.setPreferredSize(new Dimension(newW * 2, scrollHeightFont));
		}
		
		this.inputContainer.setPreferredSize(new Dimension((int) (1.5 * this.numberCompsInRow * newW),
				scrollHeightFont * 2 * this.areeDiInput.size()));
		
		for(JPanel panel : this.panelsForInputs) {
			panel.setPreferredSize(new Dimension(newW * this.numberCompsInRow, scrollHeightFont * 3));
		}
		
		for(AreaForInput a : this.areeDiInput) {
			a.setPanelSize(newW,scrollHeightFont);
			a.setFont(font);
			a.setPreferredSize(newW, scrollHeightFont);
		}
	}
	
	/**
	 * Metodo ausiliario per aggiornamento delle dimensioni della componente
	 * dei bottoni delle azioni (es.: exit button)
	 * @param newW nuova width
	 * @param newH nuova height
	 * @since TESI
	 */
	private void updateActionsCoordinates(int newW, int newH) {
		int actionHeightFont = newH / FRACTION_ACTIONS_FONT_HEIGHT;
		
		Font font = new Font("Arial", Font.BOLD, actionHeightFont);
		
		this.exitButton.setFont(font);
		this.confirmButton.setFont(font);
		
	}
	
	/**
	 * Metodo ausiliario per l'inserimento dei componenti della componente
	 * degli input
	 * @param aree aree di input da inserire
	 * @since TESI
	 */
	public JPanel placeInputsInContainer(List<AreaForInput> aree) {
		this.inputContainer.removeAll();
		this.inputContainer.setLayout(new BoxLayout(this.inputContainer, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < this.areeDiInput.size(); i = i + this.numberCompsInRow) {
			JPanel complexPanel = new JPanel();
			complexPanel.setLayout(new BoxLayout(complexPanel, BoxLayout.X_AXIS));
			for(int j = 0; j < this.numberCompsInRow; j++) {
				JPanel toAdd = this.areeDiInput.get(i + j).obtainPanelAreaForInput();
				toAdd.setBackground(Colori.BIANCO.getVal());
				toAdd.setBorder(new LineBorder(Colori.NERO.getVal()));
				this.areeDiInput.get(i + j).setBackground(Colori.BIANCO.getVal());
				this.areeDiInput.get(i + j).setWarningBackground(Colori.BIANCO.getVal());
				this.areeDiInput.get(i + j).setBorder(new LineBorder(Colori.NERO.getVal()));
				complexPanel.add(toAdd);
			}
			
			this.panelsForInputs.add(complexPanel);
			this.inputContainer.add(complexPanel);
		}
		
		return this.inputContainer;
	}
	
	/**
	 * Metodo ausiliario per l'inserimento dei componenti della componente
	 * dei bottoni delle azioni (es.: exit button)
	 * @param buttons bottoni da inserire
	 * @since TESI
	 */
	public void placeActionButtonsInContainer(List<MyJButton> buttons) {
		this.actionsContainer.setLayout(new GridBagLayout());
		this.actionsContainer.removeAll();
		for(int i = 0; i < buttons.size(); i++) {
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.gridx = i;
			c.gridy = 0;
			c.anchor = GridBagConstraints.CENTER;
			c.weightx = 1;
			c.weighty = 1;
			c.insets = new Insets(TOP_ACTIONS_INSETS, LEFT_ACTIONS_INSETS,
					BOTTOM_ACTIONS_INSETS, RIGHT_ACTIONS_INSETS);
			
			this.actionsContainer.add(buttons.get(i), c);
		}
		this.actionsContainer.setBorder(new LineBorder(Colori.NERO.getVal()));
	}
	
	/**
	 * Metodo ausiliario per l'inserimento di una (o piu') nuove area di input nella componente
	 * degli input
	 * @param a aree di input NUOVE da inserire
	 * @Precondizioni newAreas.size() == this.numberCompsInRow, componenti in eccesso verranno scartati
	 * @since TESI
	 */
	public void addAreaDiInpuInScroller(List<AreaForInput> newAreas) {
		JPanel complexPanel = new JPanel();
		complexPanel.setLayout(new BoxLayout(complexPanel, BoxLayout.X_AXIS));
		for(int i = 0; i < this.numberCompsInRow; i++) {
			JPanel toAdd = newAreas.get(i).obtainPanelAreaForInput();
			toAdd.setBackground(Colori.BIANCO.getVal());
			toAdd.setBorder(new LineBorder(Colori.NERO.getVal()));
			newAreas.get(i).setBackground(Colori.BIANCO.getVal());
			newAreas.get(i).setWarningBackground(Colori.BIANCO.getVal());
			newAreas.get(i).setBorder(new LineBorder(Colori.NERO.getVal()));
			complexPanel.add(toAdd);
			this.areeDiInput.add(newAreas.get(i));
		}
		this.panelsForInputs.add(complexPanel);
		this.inputContainer.add(complexPanel);
		
		this.inputContainer.revalidate();
		this.panelForAdding.revalidate();
		this.scroller.setViewportView(this.inputContainer);
		this.scroller.revalidate();
		
		this.revalidate();
		this.dispatchEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
		this.repaint();
	}
	
	/**
	 * Metodo per rimuovere l'ultima area di input aggiunta
	 * @Precondizione L'array delle aree di input non deve essere vuoto (o nullo)
	 * @since TESI
	 */
	public void removeAreaDiInputInScroller(int indexToRemove) {
		int indexPanelToRemove = (int) (indexToRemove / this.numberCompsInRow);
		this.inputContainer.remove(indexPanelToRemove);
		for(int i = 0; i < this.numberCompsInRow; i++) {
			this.areeDiInput.remove(indexToRemove);
		}
		this.dispatchEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
		this.inputContainer.revalidate();
	}
	
	/**
	 * Metodo per scrollare ad una specifica area di input
	 * @param index indice dell'area di input a cui scrollare
	 * @since TESI
	 */
	public void scrollToSpecificInput(int index) {
		Rectangle rect = this.inputContainer.getComponent(index / this.numberCompsInRow).getBounds();
		this.scroller.getViewport().setViewPosition(rect.getLocation());
	}
	
	/**
	 * Metodo per cambiare il colore del bottone di conferma
	 * @param availability true per verde, false per rosso
	 * @since TESI
	 */
	public void changeConfirmAvailability(boolean availability) {
		if(availability) {
			this.confirmButton.setBackground(Colori.VERDE.getVal());
		}
		else {
			this.confirmButton.setBackground(Colori.ROSSOMISTO.getVal());
		}
	}
	
	/**
	 * Metodo per modificare il testo del bottone di conferma
	 * @param confirm nuovo testo
	 * @since TESI
	 */
	public void changeConfirmMessage(String confirm) {
		this.confirmButton.setText(confirm);
	}
}
