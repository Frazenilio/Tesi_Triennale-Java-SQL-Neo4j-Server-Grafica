package graphicUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import personalGraphicElements.ExitButton;
import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTextPane;
import utility.Colori;

/**
 * Classe per gestire la grafica della classe MenuWithButtons
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class MenuWithButtonsGraphic extends JPanel{

	
	private static final int RIGHT_INSETS_BUTTON_MULTIPLE_COLUMN = 100;
	private static final int BOTTOM_INSETS_BUTTON_MULTIPLE_COLUMN = 10;
	private static final int LEFT_INSETS_BUTTON_MULTIPLE_COLUMN = 100;
	private static final int TOP_INSETS_BUTTON_MULTIPLE_COLUMN = 10;
	
	private static final int RIGHT_INSETS_BUTTON_SINGLE_COLUMN = 200;
	private static final int BOTTOM_INSETS_BUTTON_SINGLE_COLUMN = 15;
	private static final int LEFT_INSETS_BUTTON_SINGLE_COLUMN = 200;
	private static final int TOP_INSETS_BUTTON_SINGLE_COLUMN = 15;
	
	private static final int FRACTION_EXIT_FONT_HEIGHT = 2;
	private static final int FRACTION_EXIT_HEIGHT = 5;
	
	private static final int FRACTION_CONTAINER_HEIGHT = 5;
	private static final int MOLTIPLICATION_CONTAINER_HEIGHT = 3;
	
	private static final int FRACTION_MESSAGE_FONT_HEIGHT = 4;
	private static final int FRACTION_MESSAGE_HEIGHT = 5;
	
	
	private static final int FRACTION_FONT_SUB_SMALL = 3;
	private static final int FRACTION_FONT_OVER_SMALL = 4;
	private static final int FRACTION_FONT_OVER_MEDIUM = 4;
	private static final int FRACTION_FONT_OVER_LARGE = 5;
	
	private static final int LENGTH_SMALL = 20;
	private static final int LENGTH_MEDIUM = 25;
	private static final int LENGTH_LARGE = 30;
	
	private static final int MAX_NUMBER_BUTTONS_IN_COLUMN = 4;
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END CONSTANT VARIABLES ////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private ExitButton exitButton;
	private MyJTextPane message;
	private JScrollPane scrollerMessage;
	private List<MyJButton> buttons;
	private JPanel buttonsContainer;
	private int numColumns;
	
	/**
	 * Costruttore
	 * @param exitButton bottone d'uscita da mostrare in basso
	 * @param message messaggio da mostrare in alto
	 * @param buttons bottone da mostrare al centro
	 * @since TESI
	 */
	public MenuWithButtonsGraphic(ExitButton exitButton, MyJTextPane message, List<MyJButton> buttons) {
		super();
		this.exitButton = exitButton;
		
		this.message = message;
		this.message.setBackground(Colori.BLU_CHIARO.getVal());
		this.message.setBorder(new LineBorder(Colori.NERO.getVal()));
		
		this.buttons = buttons;
		
		this.buttonsContainer = new JPanel();
		
		this.setButtonsPanel(this.buttons);
		
		this.scrollerMessage = new JScrollPane();
		this.scrollerMessage.setViewportView(this.message);
		this.scrollerMessage.setLayout(new ScrollPaneLayout());
		
		super.setLayout(new BorderLayout());
		super.add(this.scrollerMessage, BorderLayout.PAGE_START,0 );
		super.add(this.buttonsContainer, BorderLayout.CENTER, 1);
		super.add(this.exitButton.getExitButtonWithInsets(), BorderLayout.PAGE_END, 2);
		
		this.addComponentListener(new ComponentListener() {
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
	 * Metodo per l'aggiornamento delle dimensioni
	 * @param newW nuova Width di riferimento
	 * @param newH nuova Height di riferimento
	 * @since TESI
	 */
	private void updateSelfCoordinates(int newW, int newH) {
		int messageHeight = newH / FRACTION_MESSAGE_HEIGHT;
		int messageHeightFont = messageHeight / FRACTION_MESSAGE_FONT_HEIGHT;
		
		this.message.setFont(new Font("Arial", Font.BOLD, messageHeightFont));
		this.message.setPreferredSize(new Dimension(newW, messageHeight));
		this.scrollerMessage.setPreferredSize(new Dimension(newW, messageHeight));
		
		
		int containerHeight = MOLTIPLICATION_CONTAINER_HEIGHT * newH / FRACTION_CONTAINER_HEIGHT;
		this.updateButtonsContainer(newW, containerHeight);
		
		
		int exitHeight = newH / (FRACTION_EXIT_HEIGHT + 2);
		int exitHeightFont = exitHeight / FRACTION_EXIT_FONT_HEIGHT;
		
		this.exitButton.setPreferredSize(new Dimension(newW, exitHeight));
		this.exitButton.setFont(new Font("Arial", Font.BOLD, exitHeightFont));
		
		repaint();
	}
	
	/**
	 * Extract Method per fare update delle dimensioni del contenitore dei MyJButton
	 * @param newW nuova Width del container di riferimento
	 * @param newH nuova Height del container di riferimento
	 * @since TESI
	 */
	private void updateButtonsContainer(int newW, int newH) {
		this.buttonsContainer.setPreferredSize(new Dimension(newW, newH));
		int fractionWidthResize = this.numColumns;
		int fractionHeightResize = this.buttons.size() < MAX_NUMBER_BUTTONS_IN_COLUMN ? this.buttons.size() : MAX_NUMBER_BUTTONS_IN_COLUMN;
		
		for(MyJButton button : this.buttons) {
			int width = newW / fractionWidthResize;
			int height = newH / fractionHeightResize;
			
			
			button.setPreferredSize(new Dimension(width,
					height));
			
			Font font
			= this.obtainFontBasedOnLenght(button, height);
			button.setFont(font);
			button.repaint();
		}
	}
	
	/**
	 * Metodo per disporre i MyJButton secondo il layout scelto (GridBadLayout)
	 * @param buttons lista di MyJButton da inserire
	 * @since TESI
	 */
	private void setButtonsPanel(List<MyJButton> buttons) {
		GridBagLayout grid = new GridBagLayout();
		
		this.buttonsContainer.removeAll();
		
		this.buttonsContainer.setLayout(grid);
		
		if(buttons.size() <= MAX_NUMBER_BUTTONS_IN_COLUMN) {
			this.numColumns = 1;
			for(int i = 0; i < this.buttons.size(); i++) {
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.gridx = 0;
				c.gridy = i;
				c.anchor = GridBagConstraints.CENTER;
				c.weightx = 1;
				c.weighty = 1;
				c.insets = new Insets(TOP_INSETS_BUTTON_SINGLE_COLUMN, LEFT_INSETS_BUTTON_SINGLE_COLUMN,
						BOTTOM_INSETS_BUTTON_SINGLE_COLUMN, RIGHT_INSETS_BUTTON_SINGLE_COLUMN);
				
				this.buttonsContainer.add(this.buttons.get(i), c);
				
			}
		}
		else {
			int row = 0;
			int column = 0;
			for(int i = 0; i < this.buttons.size(); i++) {
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.gridx = column;
				c.gridy = row;
				c.anchor = GridBagConstraints.CENTER;
				c.weightx = 1;
				c.weighty = 1;
				c.insets = new Insets(TOP_INSETS_BUTTON_MULTIPLE_COLUMN,
						LEFT_INSETS_BUTTON_MULTIPLE_COLUMN / (this.buttons.size() / MAX_NUMBER_BUTTONS_IN_COLUMN),
						BOTTOM_INSETS_BUTTON_MULTIPLE_COLUMN,
						RIGHT_INSETS_BUTTON_MULTIPLE_COLUMN / (this.buttons.size() / MAX_NUMBER_BUTTONS_IN_COLUMN));
				
				row++;
				if(row == MAX_NUMBER_BUTTONS_IN_COLUMN && i < this.buttons.size() - 1) {
					row = 0;
					column++;
				}
				if(i == this.buttons.size() - 1) {
					this.numColumns = column + 1;
				}
				this.buttonsContainer.add(this.buttons.get(i), c);
			}
		}
		
		this.buttonsContainer.setBackground(Colori.BLU_SCURO.getVal());
	}
	
	/**
	 * Metodo per rimpiazzare i bottoni e il testo da mostrare 
	 * @param newButtons nuovi bottoni con cui rimpiazzare i vecchi
	 * @param newText nuovo testo da mostrare
	 * @since TESI
	 */
	public void changeButtonsAndText(List<MyJButton> newButtons, String newText) {
		this.buttons = newButtons;
		this.message.setText(newText);
		this.setButtonsPanel(this.buttons);
		
		super.remove(1);
		super.add(buttonsContainer, BorderLayout.CENTER, 1);
		
		super.remove(0);
		super.add(this.message, BorderLayout.PAGE_START, 0);
		
		dispatchEvent(new ComponentEvent(this.buttonsContainer, ComponentEvent.COMPONENT_RESIZED));
		super.revalidate();
	}
	
	/**
	 * Metodo per ottenere il font in base alla lunghezza del testo
	 * @param button bottone da adattare
	 * @param heightButton altezza del bottone da cui calcolare la size del font
	 * @return font adatto al bottone
	 * @since TESI
	 */
	private Font obtainFontBasedOnLenght(MyJButton button, int heightButton) {
		int fractionFont;
		String text = button.getText();
		
		fractionFont = FRACTION_FONT_SUB_SMALL;
		
		if(text.length() >= LENGTH_LARGE) {
			fractionFont = FRACTION_FONT_OVER_LARGE;
		}
		else if(text.length() >= LENGTH_MEDIUM && text.length() < LENGTH_LARGE) {
			fractionFont = FRACTION_FONT_OVER_MEDIUM;
		}
		else if(text.length() >= LENGTH_SMALL && text.length() < LENGTH_MEDIUM) {
			fractionFont = FRACTION_FONT_OVER_SMALL;
		}
		
		return new Font("Arial", Font.BOLD, heightButton / fractionFont);
	}
}
