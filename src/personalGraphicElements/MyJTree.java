package personalGraphicElements;

import java.awt.BorderLayout;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import utility.Colori;

/**
 * Classe che estende {@link JTree} per gestire il 
 * ritorno di nodi premuti
 * @param <T> tipo dei nodi nell'albero
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class MyJTree<T> extends JTree{
	
	private T selectedObject;
	private JScrollPane scrollPane;
	private JPanel panel;
	private DefaultMutableTreeNode nodeFound;
	
	public MyJTree(DefaultMutableTreeNode radice) {
		super(radice);
		selectedObject = null;
		super.setShowsRootHandles(false);
		super.putClientProperty("JTree.lineStyle", "None");
				
		this.scrollPane = new JScrollPane();
		
		this.panel = new JPanel();
	}
	
	/**
	 * Metodo per ritornare l'oggetto selezionato
	 * @return oggetto associato al nodo selezionato
	 * @since TESI
	 */
	public T passSelectedObject() {
		return selectedObject;
	}
	
	/**
	 * Metodo per ottenere l'albero in un panel già con scroll
	 * @return panel con scroll
	 * @since TESI
	 */
	public JPanel getTreeInPanelWithScrolls() {
		scrollPane = new JScrollPane(this);
		scrollPane.setHorizontalScrollBar(scrollPane.createHorizontalScrollBar());
		scrollPane.setVerticalScrollBar(scrollPane.createVerticalScrollBar());
		scrollPane.getViewport().setBackground(Colori.BLU_CHIARO.getVal());
		
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER, 0);
		panel.setBackground(Colori.BLU_CHIARO.getVal());
		
		return panel;
	}
	
	/**
	 * Metodo per aggiungere un testo rosso ad uno specifico nodo
	 * @param text testo da aggiungere
	 * @param specificNode nodo a cui aggiungere il figlio con il testo
	 * @since TESI
	 */
	public void addNewTextNodeToSpecificNode(String text, T specificNode) {
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		this.findSpecificNode(specificNode, (DefaultMutableTreeNode) this.getModel().getRoot());
		if(this.nodeFound != null) {
			JLabel label = new JLabel(text);
			label.setForeground(Colori.ROSSO.getVal());
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
			model.insertNodeInto(
					node
					, this.nodeFound, this.nodeFound.getChildCount());
		}
	}
	
	/**
	 * Metodo per cercare un nodo specifico
	 * @param specificNode nodo da cercare
	 * @param startingNode nodo da cui partire per la ricerca
	 */
	private void findSpecificNode(T specificNode, DefaultMutableTreeNode startingNode) {
		if(startingNode.getUserObject().equals(specificNode)) {
			this.nodeFound = startingNode;
		}
		for(int i = 0; i < this.getModel().getChildCount(startingNode); i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) startingNode.getChildAt(i);
			if(node.getUserObject().equals(specificNode)) {
				this.nodeFound = node;
			}
		}
		for(int i = 0; i < this.getModel().getChildCount(startingNode); i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) startingNode.getChildAt(i);
			findSpecificNode(specificNode, node);
		}
	}
	
	/**
	 * Metodo per espandere tutto l'albero 
	 * @since TESI
	 */
	public void expandTree() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.getModel().getRoot();
		Enumeration<TreeNode> e = root.depthFirstEnumeration();
	    while (e.hasMoreElements()) {
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
	        this.scrollPathToVisible(new TreePath(node.getPath()));
	    }
	    this.revalidate();
	}
	
	/**
	 * Metodo per impostare colori differenti in base alla condizione passata
	 * @param isSelectable se true, i nodi cambieranno colore in base al critero nel {@link MyCellRenderer}
	 * @since TESI
	 */
	public void setSelectableColors(boolean isSelectable) {
		MyCellRenderer renderer = (MyCellRenderer) this.getCellRenderer();
		renderer.setToBeSelectable(isSelectable);
	}
}
