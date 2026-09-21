package fromObjectToTreeChainOfResponsibility;

import javax.swing.tree.DefaultMutableTreeNode;

import errori.ErroreDatiAssenti;
import personalGraphicElements.MyCellRenderer;
import personalGraphicElements.MyJTree;
import strutture.Gerarchia;
import utility.Colori;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di MyJTree usato per rappresentare
 * una Gerarchia
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class GerarchiaToTree implements TreeConverterHandlerInterface{
	
	TreeConverterHandlerInterface nextHandler = new PropostaToTree();
	
	@Override
	public <T> MyJTree treeToGraphic(T object) throws ErroreDatiAssenti {
		if(object instanceof Gerarchia) {
			Gerarchia obj = (Gerarchia) object;
			
			DefaultMutableTreeNode categoria = this.ottieniNodo(obj);
			MyJTree tree = new MyJTree(categoria);
			
			MyCellRenderer myRenderer = new MyCellRenderer();

			tree.setCellRenderer(myRenderer);
			
			tree.setFont(tree.getFont().deriveFont(45f));
			tree.setBackground(Colori.BLU_SCURO.getVal());
			return tree;
		}
		return nextHandler.treeToGraphic(object);
	}

	private DefaultMutableTreeNode ottieniNodo(Gerarchia ger) {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(ger);
		
		if(ger.isFoglia()) {
			return nodo;
		}
		else {
			for(Gerarchia figlio : ger.getFigli()) {
				DefaultMutableTreeNode nodoFiglio = this.ottieniNodo(figlio);
				nodo.add(nodoFiglio);
			}
		}
		
		return nodo;
	}
}
