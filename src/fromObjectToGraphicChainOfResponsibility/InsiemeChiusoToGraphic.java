package fromObjectToGraphicChainOfResponsibility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import errori.ErroreDatiAssenti;
import personalGraphicElements.MyJTextArea;
import strutture.AdapterInsiemiChiusiGraphicKit;
import utente.Fruitore;
import utility.FakeProposta;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di JPanel usato per rappresentare
 * un ciclo chiuso
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class InsiemeChiusoToGraphic implements ObjectConverterHandlerInterface{

	private static final int FRACTION_CICLO_FONT_HEIGHT = 6;

	private static final int MAX_NUMBER_PROPOSTA_IN_ROW = 3;
	
	int column = 0;
	
	List<MyJTextArea> aree = new ArrayList<MyJTextArea>();
	
	ObjectConverterHandlerInterface nextHandler;
	
	@Override
	public <T> JPanel objectToGraphic(T object) throws ErroreDatiAssenti {
		if(object instanceof AdapterInsiemiChiusiGraphicKit) {
			
			AdapterInsiemiChiusiGraphicKit obj = (AdapterInsiemiChiusiGraphicKit) object;
			
			JPanel container = new JPanel();
	
			
			container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
			
			for(int i = 0; i < obj.getFakeProposte().size(); i++) {
				MyJTextArea proposta = this.PropostaAsTextArea(
						obj.getFakeProposte().get(i),
						obj.getFruitoriCoinvolti().get(i));
				aree.add(proposta);
				JScrollPane scroller = new JScrollPane(proposta);
				scroller.setLayout(new ScrollPaneLayout());
				container.add(scroller);
			}
			
			container.addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				@Override
				public void componentResized(ComponentEvent e) {
					int fractionWidthResize = column + 1;
					int fractionHeightResize = obj.getFakeProposte().size() < MAX_NUMBER_PROPOSTA_IN_ROW 
							? obj.getFakeProposte().size() : MAX_NUMBER_PROPOSTA_IN_ROW;
					
					for(MyJTextArea proposta : aree) {
						int width = e.getComponent().getWidth() / fractionWidthResize;
						int height = e.getComponent().getHeight() / fractionHeightResize;
						
						proposta.setPreferredSize(new Dimension(width,
								height));
						proposta.setFont(new Font("Arial", Font.BOLD, height / FRACTION_CICLO_FONT_HEIGHT));
					}
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
		throw new ErroreDatiAssenti();
	}

	private MyJTextArea PropostaAsTextArea(FakeProposta p, Fruitore f) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Offerta: " + p.getOfferta() + "\n(Radice: " + 
		p.getRadiceOfferta());
		builder.append(")\nOre offerte: " + p.getDurataOfferta() + "\n");
		
		builder.append("Richiesta: " + p.getRichiesta() + "\n(Radice: " + 
		p.getRadiceRichiesta());
		builder.append(")\nOre richieste: " + p.getDurataRichiesta() + "\n");
		
		builder.append("Mail Fruitore: " + f.getEmail());
		
		MyJTextArea propostaArea = new MyJTextArea(builder.toString());
				
		int red = ThreadLocalRandom.current().nextInt(50, 254 + 1);
		int green = ThreadLocalRandom.current().nextInt(50, 254 + 1);
		int blue = ThreadLocalRandom.current().nextInt(50, 254 + 1);
		
		Color randomColor = new Color(red, green, blue);
		
		propostaArea.setBackground(randomColor);
		
		
		return propostaArea;
	}
}
