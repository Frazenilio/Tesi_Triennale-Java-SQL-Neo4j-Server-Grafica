package personalGraphicElements;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import utility.Colori;
import utility.InterazioneOperazioni;

/**
 * Classe per specificare un {@link MyJButton} per inviare comandi di uscita
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class ExitButton extends MyJButton{

	private JPanel panel;
	
	/**
	 * Costruttore
	 * @since TESI
	 */
	public ExitButton() {
		super("Esci", InterazioneOperazioni.CANCEL_OPERATION.getVal());
		panel = new JPanel();
	}
	
	/**
	 * Metodo per ottenere un panel che contiene l'exit button con insets
	 * @return panel
	 * @since TESI
	 */
	public JPanel getExitButtonWithInsets() {
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(15, 200, 15, 200);
		panel.add(this, c);
		panel.setBackground(Colori.BLU_CHIARO.getVal());
		panel.setBorder(new LineBorder(Colori.NERO.getVal()));
		return panel;
	}
}
