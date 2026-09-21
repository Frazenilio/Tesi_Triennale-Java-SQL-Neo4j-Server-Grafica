package graphicUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import personalGraphicElements.ExitButton;
import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTextPane;
import utility.Colori;

/**
 * Classe per la gestione grafica della classe MyScorrimentoRisultati
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class ScorrimentoRisultatiGraphic extends JPanel{

	private static final String MSG_AVANZAMENTO = "Pagina\n%d / %d";

	private static final int FRACTION_AVANZAMENTO_WIDTH = 10;

	private static final int FRACTION_OBJECT_FONT_HEIGHT = 20;
	
	private static final int FRACTION_SCROLL_CONTAINER_FONT_HEIGHT = 5;
	private static final int FRACTION_SCROLL_CONTAINER_WIDTH = 15;
	
	private static final int NUMBER_SCROLL_BUTTON = 3;
	private static final int FRACTION_SCROLL_BUTTON_FONT_HEIGHT = 3;
	
	private static final int FRACTION_EXIT_FONT_HEIGHT = 3;
	private static final int FRACTION_EXIT_HEIGHT = 7;
	
	private static final int FRACTION_CENTER_HEIGHT = 5;
	private static final int MOLTIPLICATION_CENTER_HEIGHT = 3;
	
	private static final int FRACTION_MESSAGE_FONT_HEIGHT = 4;
	private static final int FRACTION_MESSAGE_HEIGHT = 5;
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END CONSTANT VARIABLES ////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	
	private MyJTextPane message;
	private JScrollPane messageScroller;
	
	private ExitButton exitButton;
	private MyJTextPane avanzamentoScorrimento;
	private JPanel exitAndScorrimentoPanel;
	
	private MyJButton scrollLeft;
	private MyJButton scrollRight;
	
	private JPanel objectGraphic;
	
	private JPanel scrollPanel;
	private JPanel centerContainer;
	
	
	
	
	/**
	 * Costruttore
	 * @param message messaggio da mostrare in alto
	 * @param exitButton MyJButton d'uscita da mostrare in basso
	 * @param scrollUp MyJButton per scorrere all'indietro, mostrato in posizione centrale a sinistra
	 * @param scrollDown MyJButton per scorrere in avanti, mostrato in posizione centrale a destra
	 * @since TESI
	 */
	public ScorrimentoRisultatiGraphic(MyJTextPane message, ExitButton exitButton, MyJButton scrollUp,
			MyJButton scrollDown) {
		super();
		this.message = message;
		this.message.setBackground(Colori.BLU_CHIARO.getVal());
		this.message.setBorder(new LineBorder(Colori.NERO.getVal()));
		
		this.exitButton = exitButton;
		this.scrollLeft = scrollUp;
		this.scrollRight = scrollDown;
		this.objectGraphic = new JPanel();
		this.objectGraphic.setBackground(Colori.BLU_SCURO.getVal());

		this.scrollPanel = placeScrollButtonsInPanel(scrollDown, scrollUp);
		
		
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
		
		this.centerContainer = new JPanel();
		this.centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.X_AXIS));
		this.centerContainer.add(this.objectGraphic, 0);
		this.centerContainer.add(this.scrollPanel, 1);
		this.centerContainer.setBackground(Colori.BLU_SCURO.getVal());
		this.centerContainer.setBorder(new LineBorder(Colori.NERO.getVal()));
		
		this.centerContainer.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentResized(ComponentEvent e) {
				updateCenterContainer(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
		
		this.messageScroller = new JScrollPane(this.message);
		this.messageScroller.setLayout(new ScrollPaneLayout());
		
		this.avanzamentoScorrimento = new MyJTextPane("Pagina");
		
		this.exitAndScorrimentoPanel = new JPanel();
		this.exitAndScorrimentoPanel.setLayout(new BoxLayout(this.exitAndScorrimentoPanel, BoxLayout.X_AXIS));
		this.exitAndScorrimentoPanel.add(this.exitButton.getExitButtonWithInsets());
		this.exitAndScorrimentoPanel.add(this.avanzamentoScorrimento);
		this.exitAndScorrimentoPanel.setBackground(Colori.BLU_TOTAL.getVal());
		
		super.setLayout(new BorderLayout());
		super.add(this.messageScroller, BorderLayout.PAGE_START, 0);
		super.add(this.centerContainer, BorderLayout.CENTER, 1);
		super.add(this.exitAndScorrimentoPanel, BorderLayout.PAGE_END, 2);
	
		
	}
	
	/**
	 * Metodo per piazzare gli scroll button nel panel apposito
	 * @param scrollRight bottone per avanzare nello scorrimento
	 * @param scrollLeft bottone per retrocedere nello scorrimento
	 * @return il panel con i bottoni di scroll inseriti
	 * @since TESI
	 */
	private JPanel placeScrollButtonsInPanel(MyJButton scrollRight, MyJButton scrollLeft) {
		JPanel scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		
		scrollPanel.add(scrollLeft, 0);
		scrollPanel.add(scrollRight, 1);
		
		scrollPanel.setBackground(Colori.BLU_SCURO.getVal());
		
		return scrollPanel;
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
		
	    int centerHeight = MOLTIPLICATION_CENTER_HEIGHT * newH / FRACTION_CENTER_HEIGHT;
	    
	    this.updateCenterContainer(newW, centerHeight);
	    
		
		int exitHeight = newH / FRACTION_EXIT_HEIGHT;
		
		this.updateExitAndScorrimentoContainer(newW, exitHeight);
		
		this.repaint();
	}
	
	/**
	 * Metodo per impostare la rappresentazione grafica di un oggetto
	 * @param obj oggetto rappresentato
	 * @since TESI
	 */
	public void setObject(JPanel obj, int index, int listSize) {
		this.objectGraphic = obj;
		this.centerContainer.remove(0);
		this.centerContainer.add(this.objectGraphic, 0);
		this.avanzamentoScorrimento.setText(String.format(MSG_AVANZAMENTO, index, listSize));
		this.centerContainer.revalidate();
		super.revalidate();
		this.updateSelfCoordinates(getWidth(), getHeight());
	}
	
	private void updateScrollContainer(int newW, int newH) {
		int scrollButtonFont = newH / FRACTION_SCROLL_BUTTON_FONT_HEIGHT;
		
		this.scrollPanel.setPreferredSize(new Dimension(newW, newH));
		this.scrollPanel.setFont(new Font("Arial", Font.BOLD, scrollButtonFont));
		
		this.scrollLeft.setPreferredSize(new Dimension(newW, newH /NUMBER_SCROLL_BUTTON));
		this.scrollLeft.setFont(new Font("Arial", Font.BOLD, scrollButtonFont));
		
		this.scrollRight.setPreferredSize(new Dimension(newW, newH / NUMBER_SCROLL_BUTTON));
		this.scrollRight.setFont(new Font("Arial", Font.BOLD, scrollButtonFont));
		
		this.scrollPanel.repaint();
	}
	
	/**
	 * metodo per aggiornare le dimensioni
	 * @param newW nuova width
	 * @param newH nuova height
	 * @since TESI
	 */
	private void updateCenterContainer(int newW, int newH) {
		int scrollButtonWidth = newW / FRACTION_SCROLL_CONTAINER_WIDTH;
		
		this.centerContainer.setPreferredSize(new Dimension(newW, newH));
		this.centerContainer.setFont(new Font("Arial", Font.BOLD, newH /FRACTION_SCROLL_CONTAINER_FONT_HEIGHT));
		
		this.updateScrollContainer(scrollButtonWidth, newH);
		
		this.updateObjectContainer(newW - scrollButtonWidth, newH);
	}
	
	/**
	 * Metodo per aggiornare le dimensioni dell'oggetto mostrato
	 * @param newW nuova width
	 * @param newH nuova height
	 * @since TESI
	 */
	private void updateObjectContainer(int newW, int newH) {
		int objHeightFont = newH / FRACTION_OBJECT_FONT_HEIGHT;
		
		this.objectGraphic.setFont(new Font("Arial", Font.BOLD, objHeightFont));
		this.objectGraphic.setPreferredSize(new Dimension(newW, newH));
	}
	
	/**
	 * Metodo per cambiare il colore di una delle frecce in rosso
	 * @param isBottomReached true per cambiare quella in giu', false per quella in su
	 * @since TESI
	 */
	public void redButtonLimitReached(boolean isBottomReached) {
		if(isBottomReached) {
			this.scrollRight.setBackground(Colori.ROSSOMISTO.getVal());
		}
		else {
			this.scrollLeft.setBackground(Colori.ROSSOMISTO.getVal());
		}
	}
	
	private void updateExitAndScorrimentoContainer(int newW, int newH) {
		int scorrimentoWidth = newW / FRACTION_AVANZAMENTO_WIDTH;
		
		this.exitAndScorrimentoPanel.setPreferredSize(new Dimension(newW, newH));
		
		this.exitButton.setPreferredSize(new Dimension(newW - scorrimentoWidth, newH));
		this.exitButton.setFont(new Font("Arial", Font.BOLD, newH / FRACTION_EXIT_FONT_HEIGHT));
		
		this.avanzamentoScorrimento.setPreferredSize(new Dimension(scorrimentoWidth, newH));
		this.avanzamentoScorrimento.setFont(new Font("Arial", Font.BOLD, newH / FRACTION_EXIT_FONT_HEIGHT));
	}
	
	/**
	 * Metodo per impostare a verde entrambe le frecce di scorrimento
	 * @since TESI
	 */
	public void greenButtonsLimitsFree() {
		this.scrollLeft.setBackground(Colori.VERDE.getVal());
		this.scrollRight.setBackground(Colori.VERDE.getVal());
	}
	
	/**
	 * Metodo per impostare a verde un solo specifico bottone
	 * @param isBottomReached true per il bottone di scorrimento in giu', false per quello in su
	 * @since TESI
	 */
	public void greenButtonScrollLimit(boolean isBottomReached) {
		if(isBottomReached) {
			this.scrollLeft.setBackground(Colori.VERDE.getVal());
		}
		else {
			this.scrollRight.setBackground(Colori.VERDE.getVal());
		}
	}
}
