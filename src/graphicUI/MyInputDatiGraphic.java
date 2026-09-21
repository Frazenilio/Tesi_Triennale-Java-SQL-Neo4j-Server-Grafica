package graphicUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import personalGraphicElements.AreaForInput;
import personalGraphicElements.ExitButton;
import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTextPane;
import utility.Colori;

/**
 * Classe per gestire la grafica di MyInputDati
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class MyInputDatiGraphic extends JPanel{

	
	private static final int FRACTION_EXIT_FONT_HEIGHT = 2;
	private static final int FRACTION_EXIT_HEIGHT = 7;
	
	private static final int FRACTION_INPUT_FONT_HEIGHT = 10;
	private static final int FRACTION_INPUT_HEIGHT = 5;
	
	private static final int MOLTIPLICATION_INPUT_HEIGHT = 3;
	private static final int FRACTION_MESSAGE_FONT_HEIGHT = 4;
	private static final int FRACTION_MESSAGE_HEIGHT = 5;
	
	private static final int TOP_ACTIONS_INSETS = 15;
	private static final int LEFT_ACTIONS_INSETS = 15;
	private static final int RIGHT_ACTIONS_INSETS = 15;
	private static final int BOTTOM_ACTIONS_INSETS = 15;
	
	private static final int RIGHT_INPUT_INSETS = 200;
	private static final int BOTTOM_INPUT_INSETS = 100;
	private static final int LEFT_INPUT_INSETS = 200;
	private static final int TOP_INPUT_INSETS = 100;
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END CONSTANT VARIABLES ////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	
	private MyJTextPane message;
	private JScrollPane messageScroller;
	
	private AreaForInput inputArea;
	private JPanel areaForBorder;
	
	private ExitButton exitButton;
	private MyJButton confirmButton;
	private JPanel actionPanel;
	
	/**
	 * Costruttore 
	 * @param message messaggio da mostrare in alto
	 * @param inputArea area di input da mostrare al centro
	 * @param exitButton MyJButton d'uscita da mostrare in basso
	 * @param confirmButton MyJButton di conferma dell'operazione
	 */
	public MyInputDatiGraphic(MyJTextPane message, AreaForInput inputArea, ExitButton exitButton, MyJButton confirmButton) {
		super();
		this.message = message;
		this.message.setBackground(Colori.BLU_CHIARO.getVal());
		this.message.setBorder(new LineBorder(Colori.NERO.getVal()));
		
		this.messageScroller = new JScrollPane();
		this.messageScroller.setViewportView(this.message);
		
		this.inputArea = inputArea;
		this.inputArea.setBorder(new LineBorder(Colori.NERO.getVal()));

		this.areaForBorder = new JPanel();
		this.areaForBorder.setLayout(new BorderLayout());
		this.areaForBorder.add(this.inputArea.obtainPanelAreaForInput(), BorderLayout.CENTER, 0);
		this.areaForBorder.setBorder(BorderFactory.createMatteBorder(TOP_INPUT_INSETS, LEFT_INPUT_INSETS,
				BOTTOM_INPUT_INSETS, RIGHT_INPUT_INSETS, Colori.BLU_TOTAL.getVal()));
		
		this.exitButton = exitButton;
		this.confirmButton = confirmButton;
		this.confirmButton.setBackground(Colori.BLU_SCURO.getVal());
		
		this.actionPanel = new JPanel();
		List<MyJButton> lista = new ArrayList<MyJButton>();
		lista.add(this.exitButton);
		lista.add(this.confirmButton);
		placeActionButtonsInPanel(lista);
		
		super.setLayout(new BorderLayout());
		super.add(this.messageScroller, BorderLayout.PAGE_START, 0);
		super.add(this.areaForBorder, BorderLayout.CENTER, 1);
		super.add(this.actionPanel, BorderLayout.PAGE_END, 2);
				
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
	 * Metodo per piazzare i bottoni d'azione (es.: exit button) nel panel apposito
	 * @param buttons bottoni da inserire
	 * @since TESI
	 */
	private void placeActionButtonsInPanel(List<MyJButton> buttons) {
		this.actionPanel.setLayout(new GridBagLayout());
		this.actionPanel.removeAll();
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
			
			this.actionPanel.add(buttons.get(i), c);
		}
		this.actionPanel.setBackground(Colori.BLU_CHIARO.getVal());
		this.actionPanel.setBorder(new LineBorder(Colori.NERO.getVal()));
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
		this.messageScroller.setPreferredSize(new Dimension(newW, messageHeight));
		
		int inputAreaHeight = MOLTIPLICATION_INPUT_HEIGHT * newH / FRACTION_INPUT_HEIGHT;
		int inputAreaHeightFont = newH / FRACTION_INPUT_FONT_HEIGHT;

		this.inputArea.setFont(new Font("Arial", Font.BOLD, inputAreaHeightFont));
		this.inputArea.setPreferredSize(new Dimension(newW, inputAreaHeight));
		this.areaForBorder.setPreferredSize(new Dimension(newW, inputAreaHeight));
		
		int exitHeight = newH / FRACTION_EXIT_HEIGHT;
		int exitHeightFont = exitHeight / FRACTION_EXIT_FONT_HEIGHT;
		
		this.exitButton.setPreferredSize(new Dimension(newW, exitHeight));
		this.exitButton.setFont(new Font("Arial", Font.BOLD, exitHeightFont));
		
		this.confirmButton.setPreferredSize(new Dimension(newW, exitHeight));
		this.confirmButton.setFont(new Font("Arial", Font.BOLD, exitHeightFont));
	}
}
