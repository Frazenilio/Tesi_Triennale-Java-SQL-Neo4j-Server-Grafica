package fromObjectToGraphicChainOfResponsibility;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import errori.ErroreDatiAssenti;
import personalGraphicElements.MyJTextArea;
import strutture.AdapterPropostaGraphicKit;
import utility.Colori;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di JPanel usato per rappresentare
 * una Proposta
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class PropostaToGraphic implements ObjectConverterHandlerInterface{

	private static final int FRACTION_STATE_FONT_HEIGHT = 7;
	private static final int FRACTION_PROPOSTA_FONT_HEIGHT = 10;
	ObjectConverterHandlerInterface nextHandler = new FruitoreToGraphic();
	
	@Override
	public <T> JPanel objectToGraphic(T object) throws ErroreDatiAssenti {
		if(object instanceof AdapterPropostaGraphicKit) {
			AdapterPropostaGraphicKit prop = (AdapterPropostaGraphicKit) object;
			StringBuilder builderOfferta = new StringBuilder();
			builderOfferta.append(prop.getProprietario() + " OFFRE\n");
			builderOfferta.append(prop.getProposta().getOfferta().getCategoria());
			builderOfferta.append("\nPer " + prop.getProposta().getDurataOfferta() + " ore.");
			MyJTextArea offertaArea = new MyJTextArea(builderOfferta.toString());
			offertaArea.setBackground(Colori.BLU_SCURO.getVal());
			
			StringBuilder builderRichiesta = new StringBuilder();
			builderRichiesta.append(prop.getProprietario() + " RICHIEDE\n");
			builderRichiesta.append(prop.getProposta().getRichiesta().getCategoria());
			builderRichiesta.append("\nPer " + prop.getProposta().getDurataRichiesta() + " ore.");
			MyJTextArea richiestaArea = new MyJTextArea(builderRichiesta.toString());
			richiestaArea.setBackground(Colori.BLU_CHIARO.getVal());
			
			MyJTextArea statoArea = new MyJTextArea("Stato Proposta: " + prop.getProposta().getStato());
			statoArea.setBackground(Colori.VIOLETTO.getVal());
			
			JPanel containerProposte = new JPanel();
			containerProposte.setLayout(new BoxLayout(containerProposte, BoxLayout.X_AXIS));
			containerProposte.add(new JScrollPane(offertaArea), 0);
			containerProposte.add(new JScrollPane(richiestaArea), 1);
			containerProposte.addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				@Override
				public void componentResized(ComponentEvent e) {
					offertaArea.setFont(new Font("Arial", Font.BOLD,
							e.getComponent().getHeight()/FRACTION_PROPOSTA_FONT_HEIGHT));
					
					richiestaArea.setFont(new Font("Arial", Font.BOLD,
							e.getComponent().getHeight()/FRACTION_PROPOSTA_FONT_HEIGHT));
				}
				@Override
				public void componentMoved(ComponentEvent e) {
				}
				@Override
				public void componentHidden(ComponentEvent e) {
				}
			});
			
			JPanel container = new JPanel();
			container.setLayout(new BorderLayout());
			container.add(containerProposte, BorderLayout.CENTER, 0);
			container.add(statoArea, BorderLayout.PAGE_END, 1);
			container.addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				@Override
				public void componentResized(ComponentEvent e) {
					container.getComponent(1).setFont(new Font("Arial", Font.BOLD, container.getComponent(0).getHeight()/FRACTION_STATE_FONT_HEIGHT));
				}
				@Override
				public void componentMoved(ComponentEvent e) {
				}
				@Override
				public void componentHidden(ComponentEvent e) {
				}
			});
			
			return container;
		}
		return nextHandler.objectToGraphic(object);
	}

}
