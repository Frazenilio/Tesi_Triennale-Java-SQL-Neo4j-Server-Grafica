package personalGraphicElements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import strutture.Gerarchia;
import utility.Colori;
import utility.FrequentIcons;
import utility.TreeDefaultTextEnum;

/**
 * Classe che estende {@link DefaultTreeCellRenderer} per personalizzare i colori
 * @author Francesco Lozio 737664
 * @since TESI
 */
@SuppressWarnings("serial")
public class MyCellRenderer extends DefaultTreeCellRenderer {

	private ImageIcon leafIcon = new ImageIcon("resource/images/leafIcon.png");
	private ImageIcon nodeBranchIcon = new ImageIcon("resource/images/nodeBranchIcon.png");
	private ImageIcon nodeBranchOpenIcon = new ImageIcon("resource/images/ramoAperto.png");
	private boolean toBeSelectable;
	
	public MyCellRenderer() {
		super.setLeafIcon(leafIcon);
		super.setClosedIcon(nodeBranchIcon);
		super.setOpenIcon(nodeBranchOpenIcon);
		this.toBeSelectable = false;
	}
	
	 /**
	 * @return the toBeSelectable
	 */
	public boolean isToBeSelectable() {
		return toBeSelectable;
	}

	/**
	 * @param toBeSelectable the toBeSelectable to set
	 */
	public void setToBeSelectable(boolean toBeSelectable) {
		this.toBeSelectable = toBeSelectable;
	}

	@Override
	 public Color getBackgroundNonSelectionColor() {
	     return (null);
	 }

	 @Override
	 public Color getBackgroundSelectionColor() {
	     return Colori.BLU_TOTAL.getVal();
	 }

	 @Override
	 public Color getBackground() {
	     return (null);
	 }
	 
	 @Override
	 public Component getTreeCellRendererComponent(JTree tree, Object value,
	                                                  boolean selected, boolean expanded,
	                                                  boolean leaf, int row, boolean hasFocus) {
		 JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

		 if (value.toString().equals(TreeDefaultTextEnum.YOU_ARE_HERE.getVal())) {
			 setForeground(Color.RED);
			 setIcon(FrequentIcons.RED_ARROW.getIcon());
		 } 
		 
		 if(this.toBeSelectable) {
			 if(leaf) {
				 DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
				 if(node.getUserObject() instanceof Gerarchia) {
					 Gerarchia obj = (Gerarchia) node.getUserObject();
					 //NON DISPONIBILE PER FDC
					 if(obj.isToSetFDC()) {
						 setForeground(Colori.GIALLO.getVal());
					 }
					 else {
						 setForeground(Colori.VERDE_SCURO.getVal());
					 }
					 //COSTRUZIONE IN CORSO MA HA DEI FIGLI DA CREARE
					 if(obj.isToSetFigli()) {
						 setIcon(nodeBranchIcon);
					 }
				 }
				 //DEFAULT
				 else{
					 setForeground(Colori.VERDE_SCURO.getVal());
				 }
			 }
			 //DA SELEZIONARE MA NON E' FOGLIA
			 else {
				 setForeground(Colori.GIALLO.getVal());
			 }
		 }
		 
		 TreePath pathForRow = tree.getPathForRow(row);
		 if(pathForRow != null) {
			 int level = pathForRow.getPathCount() - 1;
			 setBorder(BorderFactory.createEmptyBorder(0, level * 20, 0, 0));
			 
			 label.setPreferredSize(new Dimension((int) (value.toString().length() * 32f), (int) label.getPreferredSize().getHeight()));
		 }

		 return this;
	    }
}
