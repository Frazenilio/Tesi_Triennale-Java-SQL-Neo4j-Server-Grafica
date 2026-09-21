package fromObjectToTreeChainOfResponsibility;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import errori.ErroreDatiAssenti;
import personalGraphicElements.MyCellRenderer;
import personalGraphicElements.MyJTree;
import strutture.AdapterListaProposte;
import strutture.Proposta;
import utility.Colori;

/**
 * Classe per pattern Chain of Responsibilty 
 * per la creazione di MyJTree usato per rappresentare
 * una lista di Proposte
 * @author Francesco Lozio, 737664
 * @since TESI
 */
public class PropostaToTree implements TreeConverterHandlerInterface{

	TreeConverterHandlerInterface nextHandler;
	
	@Override
	public <T> MyJTree treeToGraphic(T object) throws ErroreDatiAssenti {
		if(object instanceof AdapterListaProposte) {
			List<Proposta> lista = ((AdapterListaProposte) object).ottieniLista();
			if(!lista.isEmpty()) {
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Proposte");
				for(Proposta p : lista) {
					root.add(new DefaultMutableTreeNode(p));
				}
				MyJTree<Proposta> tree = new MyJTree<Proposta>(root);
				
				tree.setCellRenderer(new MyCellRenderer());
				
				tree.setFont(tree.getFont().deriveFont(45f));
				tree.setBackground(Colori.BLU_SCURO.getVal());
				
				tree.setRootVisible(false);
				
				return tree;
			}
		}
		throw new ErroreDatiAssenti();
	}

}
