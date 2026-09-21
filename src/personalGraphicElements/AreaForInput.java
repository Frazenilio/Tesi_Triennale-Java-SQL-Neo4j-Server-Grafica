package personalGraphicElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utility.Colori;

/**
 * Classe per specificare un {@link JTextField} adibita all'input dati
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class AreaForInput extends JTextField{
	
	private static final int INDEX_INPUT_IN_PANEL = 0;
	private static final int INDEX_WARNING_IN_PANEL = 1;
	
	private static final String MSG_INITIAL = "Inserisci testo qui";
	
	private JPanel panel;
	private MyJTextArea warn;
	private String placeholderMessage;
	
	private boolean isInputSetted;
	
	/**
	 * Costruttore
	 * @since TESI
	 */
	public AreaForInput() {
		super(MSG_INITIAL);
		this.placeholderMessage = MSG_INITIAL;
		initialize();
	}
	
	/**
	 * Costruttore con placeholder personalizzabile
	 * @param placeholder testo del nuovo placeholder
	 * @since TESI
	 */
	public AreaForInput(String placeholder) {
		super(placeholder);
		this.placeholderMessage = placeholder;
		initialize();
	}


	/**
	 * Metodo ausiliario per l'inizializzazione di questa classe
	 * @since TESI
	 */
	private void initialize() {
		this.setForeground(Color.GRAY);
		this.setEditable(true);
		this.setFocusable(true);
		this.setVisible(true);
		super.setDisabledTextColor(Color.GRAY);
		
		this.isInputSetted = false;
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				grabFocus();
			}
		});
		
		super.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if(isInputSetted() && getText().isBlank()) {
					resetToPlaceholder();
				}
				removeBorderForSelected();
			}
			@Override
			public void focusGained(FocusEvent e) {
				placeBorderForSelected();
			}
		});
		
		super.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent key) {
				if(getText().equalsIgnoreCase(getPlaceholderMessage())
						&& !Character.isWhitespace(key.getKeyCode())) {
					key.consume();
				}
			}
			
			public void keyPressed(KeyEvent key) {
			  	if(getText().equalsIgnoreCase(getPlaceholderMessage())
					&& Character.isLetterOrDigit(key.getKeyChar())
					&& !Character.isWhitespace(key.getKeyCode())) {
			  			setForeground(Colori.NERO.getVal());
						setText("");
						isInputSetted = true;
				}
			  	else if(getText().equalsIgnoreCase(getPlaceholderMessage())
			  			&& Character.isISOControl(key.getKeyCode())){
			  		key.consume();
			  	}
			  	
			}
		});
		
		this.warn = new MyJTextArea("");
		this.warn.setForeground(Colori.ROSSO.getVal());
		
		this.panel = new JPanel();
		this.panel.setLayout(new GridBagLayout());
		
		this.placeInputAreaInPanel();
		this.placeWarningInPanel();
		this.panel = this.obtainPanelAreaForInput();
	}
	
	/**
	 * Metodo per resettare questa area di input col proprio placeholder
	 * @since TESI
	 */
	public void resetToPlaceholder() {
		setText(placeholderMessage);
		setForeground(Color.GRAY);
		this.isInputSetted = false;
	}
	
	public String getPlaceholderMessage() {
		return this.placeholderMessage;
	}
	
	/**
	 * Metodo per impostare il background del warning
	 * @param c colore del background del warning
	 * @since TESI
	 */
	public void setWarningBackground(Color c) {
		this.warn.setBackground(c);
	}
	
	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if(this.warn != null) {
			this.warn.setFont(new Font(font.getName(), font.getStyle(), font.getSize() / 2));
		}
	}
	
	public void setPanelSize(int newW, int newH) {
		this.panel.setPreferredSize(new Dimension(newW, newH));
	}
	
	public void setPreferredSize(int newW, int newH) {
		super.setPreferredSize(new Dimension(newW, newH));
		this.warn.setPreferredSize(new Dimension(newW, newH / 2));
	}
	
	public void setPlaceholder(String newPlaceholder) {
		this.placeholderMessage = newPlaceholder;
	}
	
	/**
	 * @return the isInputSetted
	 */
	public boolean isInputSetted() {
		return isInputSetted;
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	///////////////////// END STANDARD METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	
	

	/**
	 * Metodo per ottenere il panel di questa area senza testo di warning
	 * @return panel della classe
	 * @since TESI
	 */
	public JPanel obtainPanelAreaForInput() {
		return panel;
	}
	
	/**
	 * Metodo per aggiungere un testo al warning
	 * @param warnText testo del warning da mostrare
	 * @since TESI
	 */
	public void addMiniWarning(String warnText) {
		this.warn.setText(warnText);	
		panel.repaint();
		panel.revalidate();
	}

	/**
	 * Metodo per nascondere il warning (rimpiazzato con stringa vuota)
	 * @since TESI
	 */
	public void hideMiniWarning() {
		this.warn.setText("");		
		panel.repaint();
		panel.revalidate();
	}
	

	/////////////////////////////////////////////////////////////////////////
	//////////////////////// AUXILIARY METHODS //////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	
	/**
	 * Metodo per inserire il warning nel panel
	 * @since TESI
	 */
	private void placeWarningInPanel() {
		GridBagConstraints cWarn = new GridBagConstraints();
		cWarn.fill = GridBagConstraints.BOTH;
		cWarn.anchor = GridBagConstraints.CENTER;
		cWarn.weightx = 1;
		cWarn.weighty = 1;
		cWarn.gridx = 0;
		cWarn.gridy = 1;

		panel.add(warn, cWarn, INDEX_WARNING_IN_PANEL);
	}
	
	/**
	 * Metodo per inserire l'area di input nel panel
	 * @since TESI
	 */
	private void placeInputAreaInPanel() {
		
		GridBagConstraints cInput = new GridBagConstraints();
		cInput.fill = GridBagConstraints.BOTH;
		cInput.anchor = GridBagConstraints.CENTER;
		cInput.weightx = 1;
		cInput.weighty = 1;
		cInput.gridx = 0;
		cInput.gridy = 0;
		panel.add(this, cInput, INDEX_INPUT_IN_PANEL);
	}
	
	private void placeBorderForSelected() {
		this.panel.setBorder(BorderFactory.createLineBorder(Colori.NERO.getVal(), 3));
	}
	
	private void removeBorderForSelected() {
		this.panel.setBorder(null);
	}
}
