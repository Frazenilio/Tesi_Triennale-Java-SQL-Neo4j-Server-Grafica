package utility;

import javax.swing.ImageIcon;

/**
 * Enum contenente immagini usate di frequente
 * @author Francesco Lozio 737664
 * @since TESI
 */
public enum FrequentIcons {

	RED_ARROW(new ImageIcon("resource/images/frecciaYouAreHere.png")),
	INFO_ICON(new ImageIcon("resource/images/infoIcon.png")),
	GREEN_V(new ImageIcon("resource/images/greenVIcon.png"));
	
	private ImageIcon icon;
	
	FrequentIcons(ImageIcon icon) {
		this.icon = icon;
	}

	/**
	 * @return the icon
	 */
	public ImageIcon getIcon() {
		return icon;
	}

}
