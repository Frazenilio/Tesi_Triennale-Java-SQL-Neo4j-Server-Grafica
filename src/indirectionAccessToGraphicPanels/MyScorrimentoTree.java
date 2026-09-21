package indirectionAccessToGraphicPanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.tree.DefaultMutableTreeNode;

import errori.ErroreDatiAssenti;
import errori.ErroreInterruzioneOperazione;
import fromObjectToTreeChainOfResponsibility.GerarchiaToTree;
import personalGraphicElements.MyJButton;
import personalGraphicElements.MyJTree;

/**
 * Classe per lo scorrimento di un albero
 * @author Francesco Lozio
 * @since TESI
 */
public class MyScorrimentoTree<T, E> extends MyScorrimentoRisultati<T>{

	private MyJTree<T> treeToGraphic;
	private GerarchiaToTree handler;
	private boolean toBeSelcted;

	
	/**
	 * Costruttore
	 * @param text testo del messaggio che appare durante lo scorrimento
	 * @since TESI
	 */
	public MyScorrimentoTree(String text) {
		super(text);
		handler = new GerarchiaToTree();
		this.toBeSelcted = false;
		for(ActionListener a : super.scrollUpButton.getActionListeners()) {
			super.scrollUpButton.removeActionListener(a);
		}
		super.scrollUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					modificaIndex(scrollUpButton);
				} catch (ErroreDatiAssenti e1) {
				}
				changeButtonsColorBasedOnIndex();
			}
		});
		
		for(ActionListener a : super.scrollDownButton.getActionListeners()) {
			super.scrollDownButton.removeActionListener(a);
		}
		super.scrollDownButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					modificaIndex(scrollDownButton);
				} catch (ErroreDatiAssenti e1) {
				}
				changeButtonsColorBasedOnIndex();
			}
		});
	}
	
	/**
	 * @return the toBeSelcted
	 */
	public boolean isToBeSelcted() {
		return toBeSelcted;
	}

	/**
	 * @param toBeSelcted the toBeSelcted to set
	 */
	public void setToBeSelcted(boolean toBeSelcted) {
		this.toBeSelcted = toBeSelcted;
	}

	/**
	 * Metodo per graficare un oggetto come un albero
	 * @param object oggetto da graficare
	 * @throws ErroreDatiAssenti
	 * @since TESI
	 */
	private void graficaTreeToDisplay(T object) throws ErroreDatiAssenti {
		MyJTree<T> tree = handler.treeToGraphic(object);
		tree.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
		            if (e.getClickCount() == 2) {
		                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		                       tree.getLastSelectedPathComponent();
		                if(node != null && node.isLeaf()) {
		                	risultato.complete((T) node.getUserObject());
						 }
		            }
		        }
		});
		treeToGraphic = tree;
		treeToGraphic.setSelectableColors(toBeSelcted);
		if(this.toBeSelcted) {
			treeToGraphic.expandTree();
		}
		
		super.setGraphicObject(treeToGraphic.getTreeInPanelWithScrolls());
		super.getGrafica().setObject(super.getGraphicObject(), super.index + 1, super.getObjectsToScroll().size());
	}
	
	/**
	 * Metodo per scorrere una lista di alberi ed ottenerne l'elemento selezionato
	 * @return elemento selezionato dall'utente
	 * @throws ErroreInterruzioneOperazione
	 * @throws ErroreDatiAssenti
	 * @since TESI
	 */
	public E scorriListaDiAlberi() throws ErroreInterruzioneOperazione, ErroreDatiAssenti {
		super.index = 0;
		super.risultato = new CompletableFuture<T>();
		super.getGrafica().greenButtonsLimitsFree();
		super.getGrafica().redButtonLimitReached(false);
		if(super.getObjectsToScroll().size() - 1 == 0) {
			super.getGrafica().redButtonLimitReached(true);
		}
		this.graficaTreeToDisplay((T)this.obtainCurrentObject());
		try {
			return (E) super.risultato.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new ErroreInterruzioneOperazione();
		}
	}
	
	/**
	 * Metodo synchronized per modificare l'index corrente entro i limiti consentiti (tra 0 e dimensione della lista - 1)
	 * @param button MyJButton che ha innescato l'aumento
	 * @throws ErroreDatiAssenti 
	 * @since TESI
	 */
	private synchronized void modificaIndex(MyJButton button) throws ErroreDatiAssenti {
		int value = button.getValueWhenPressed();
		if((value < 0 && super.index > 0) || (value > 0 && super.index < super.getObjectsToScroll().size() - 1)){
			index += value;
		}
		this.graficaTreeToDisplay((T) this.obtainCurrentObject());
	}
}
