package graphicUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTextPane;
import utility.Colori;

/**
 * Classe per la gestione grafica della classe WarnerToUser
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class WarnerToUserGraphic extends JPanel{

	private static final int MOLTIPLICATION_MESSAGE_HEIGHT = 4;
	private static final int FRACTION_CONTINUE_HEIGHT_FONT = 3;
	private static final int FRACTION_CONTINUE_HEIGHT = 5;
	private static final int FRACTION_FONT_SUB_SMALL = 4;
	private static final int FRACTION_FONT_OVER_SMALL = 5;
	private static final int FRACTION_FONT_OVER_MEDIUM = 6;
	private static final int FRACTION_FONT_OVER_LARGE = 12;
	
	private static final int LENGTH_SMALL = 15;
	private static final int LENGTH_MEDIUM = 30;
	private static final int LENGTH_LARGE = 45;
	
	private int fractionFont;
	
	private MyJTextPane message;
	private JScrollPane scroller;
	
	private JPanel panelTotal;
	private JScrollPane additionalScroller;
	
	private MyJButton continueButton;
	
	private int numberComp;

	/**
	 * Costruttore
	 * @param message messaggio da mostrare a schermo intero
	 */
	public WarnerToUserGraphic(MyJTextPane message, MyJButton continueButton) {
		super();
		this.message = message;
		this.message.setBackground(Colori.BLU_CHIARO.getVal());
		this.message.setBorder(new LineBorder(Colori.NERO.getVal()));
		
		this.numberComp = 1;
		this.fractionFont = FRACTION_FONT_SUB_SMALL;
		
		this.continueButton = continueButton;
		this.continueButton.setBackground(Colori.VIOLETTO.getVal());
				
		this.scroller = new JScrollPane(this.message);
		this.scroller.setLayout(new ScrollPaneLayout());
		
		this.additionalScroller = new JScrollPane();
		
		super.setLayout(new BorderLayout());
		super.add(this.scroller, BorderLayout.CENTER, 0);
		super.add(this.continueButton, BorderLayout.PAGE_END, 1);
		
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
	 * Metodo per aggiornare il messaggio mostrato
	 * @param text nuovo testo da impostare come messaggio
	 * @since TESI
	 */
	public void setMessageText(String text) {
		this.message.setText(text);
		
		this.fractionFont = FRACTION_FONT_SUB_SMALL;
		
		if(text.length() >= LENGTH_LARGE) {
			this.fractionFont = FRACTION_FONT_OVER_LARGE;
		}
		else if(text.length() >= LENGTH_MEDIUM && text.length() < LENGTH_LARGE) {
			this.fractionFont = FRACTION_FONT_OVER_MEDIUM;
		}
		else if(text.length() >= LENGTH_SMALL && text.length() < LENGTH_MEDIUM) {
			this.fractionFont = FRACTION_FONT_OVER_SMALL;
		}
		
		this.message.getFont().deriveFont(this.message.getPreferredSize().height / fractionFont);
	}
	
	/**
	 * Metodo per l'aggiornamento delle dimensioni
	 * @param newW nuova Width di riferimento
	 * @param newH nuova Height di riferimento
	 * @since TESI
	 */
	private void updateSelfCoordinates(int newW, int newH) {
		int continueHeight = newH / FRACTION_CONTINUE_HEIGHT;
		int continueHeightFont = continueHeight / FRACTION_CONTINUE_HEIGHT_FONT;
		
		this.continueButton.setPreferredSize(new Dimension(newW, continueHeight));
		this.continueButton.setFont(new Font("Arial", Font.BOLD, continueHeightFont));
		
		int messageHeight = MOLTIPLICATION_MESSAGE_HEIGHT * continueHeight / this.numberComp;
		int messageHeightFont = messageHeight / fractionFont;
		
		if(this.panelTotal != null) {
			this.panelTotal.setPreferredSize(new Dimension(newW, newH - continueHeight));
			this.additionalScroller.setPreferredSize(new Dimension(newW, messageHeight));
			this.additionalScroller.setMaximumSize(new Dimension(newW, messageHeight));
		}
		
		this.message.setPreferredSize(new Dimension(newW, messageHeight / this.numberComp));
		this.scroller.setPreferredSize(new Dimension(newW, messageHeight / this.numberComp));
		this.message.setFont(new Font("Arial", Font.BOLD, messageHeightFont));
		
		repaint();
	}
	
	/**
	 * Metodo per aggiungere un nuovo componente insieme a quello del messaggio
	 * @param comp nuovo componente da aggiungere
	 * @since TESI
	 */
	public void addComponentWithMessage(JComponent comp) {
		this.numberComp += 1;
		
		this.panelTotal = new JPanel();
		this.panelTotal.setLayout(new BorderLayout());
		this.panelTotal.add(this.message, BorderLayout.PAGE_START);
		
		this.additionalScroller = new JScrollPane();
		this.additionalScroller.setLayout(new ScrollPaneLayout());
		this.additionalScroller.setViewportView(comp);
		
		this.panelTotal.add(this.additionalScroller, BorderLayout.CENTER);
		
		this.add(this.panelTotal, BorderLayout.CENTER, 0);
		
		this.panelTotal.repaint();
		this.panelTotal.revalidate();
		this.revalidate();
	}
	
	/**
	 * Metodo per rimuovere il componente aggiuntivo
	 * @since TESI
	 */
	public void removeAdditionalComponent() {
		if(this.panelTotal != null) {
			this.numberComp -= 1;
			this.panelTotal.removeAll();
			this.panelTotal = null;
			this.add(this.message, BorderLayout.CENTER, 0);
			this.additionalScroller = null;
			this.revalidate();
		}
	}
}
