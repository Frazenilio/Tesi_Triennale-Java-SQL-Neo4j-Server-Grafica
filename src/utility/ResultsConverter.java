package utility;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe con metodi statici per la conversione di liste nel formato ideale
 * @author Francesco Lozio 737664
 * @since TESI
 */
public class ResultsConverter {

	/**
	 * Metodo per convertire liste di liste in una sola lista
	 * @param <T> Tipo di oggetti nelle liste
	 * @param lista lista da cambiare
	 * @return lista unica con tutti gli elementi
	 * @since TESI
	 */
	public static <T> List<T> converterToListSingle(List<List<T>> lista){
		List<T> risultato = new ArrayList<T>();
		for(List<T> l : lista) {
			for(T element : l) {
				risultato.add(element);
			}
		}
		return risultato;
	}
	
	/**
	 * Metodo per convertire liste di liste in una sola lista di tuple
	 * @param <T> Tipo di oggetti nelle liste
	 * @param lista lista da cambiare
	 * @return lista unica con tutte le tuple con tutti gli elmenti
	 * @Precondizione richiede che le liste nella lista di parametro abbiano solo 2 elementi. L'utilizzo 
	 * senza considerazione di questo elemento comporta una perdita degli elementi nella lista
	 * @since TESI
	 */
	public static <T> List<Tupla<T, T>> converterToListTupla(List<List<T>> lista){
		List<Tupla<T, T>> risultato = new ArrayList<Tupla<T,T>>();
		for(List<T> l : lista) {
			if(l.size() == 1) {
				System.err.println("Lista troppo piccola!");
			}
			else{
				Tupla<T, T> conv = new Tupla<T, T>(l.get(0), l.get(1));
				risultato.add(conv);
			}
		}
		return risultato;
	}
	
	/**
	 * Metodo per convertire una lista di {@link Tupla} in una lista di liste
	 * @param <T> tipo dell'oggetto salvato, e' necessario che la tupla contenga elementi dello stesso tipo
	 * @param lista lista da convertire
	 * @return lista convertita
	 * @since TESI
	 */
	public static <T> List<List<T>> convertFromTuplasToLists(List<Tupla<T, T>> lista){
		List<List<T>> risultato = new ArrayList<List<T>>();
		for(Tupla<T, T> tupla : lista) {
			List<T> l = new ArrayList<T>();
			l.add(tupla.getFirst());
			l.add(tupla.getSecond());
			risultato.add(l);
		}
		return risultato;
	}
	
	/**
	 * Metodo per trasformare un elemento in una lista col singolo elemento
	 * @param <T> Tipo dell'elemento
	 * @param elemento elemento da convertire
	 * @return la lista con solo l'elemento
	 * @since TESI
	 */
	public static <T> List<T> converterToListFromSingle(T elemento){
		List<T> lista = new ArrayList<T>();
		lista.add(elemento);
		return lista;
	}
	
	/**
	 * Metodo per convertire un array bidimensionale in una lista di liste
	 * @param <T> Tipo dell'array
	 * @param obj array da convertire
	 * @return lista convertita
	 * @since TESI
	 */
	public static <T> List<List<T>> convertDoubleArrayInListList(T[][] obj){
		List<List<T>> list = new ArrayList<>();
        for (T[] row : obj) {
            List<T> innerList = new ArrayList<>();
            for (T element : row) {
                innerList.add(element);
            }
            list.add(innerList);
        }
        return list;
	}
	
	/**
	 * Metodo per convertire una lista di liste in un array bidimensionale
	 * @param <T> tipo degli oggetti contenuti in lista
	 * @param lista lista da convertire
	 * @param classe tipo del risultato
	 * @return array convertito
	 * @since TESI
	 */
	public static <T> T[][] convertListTo2DArray(List<List<T>> lista, Class<T> classe) {

		int numRows = lista.size();

		T[][] array = (T[][]) Array.newInstance(classe, numRows, 0);

		for (int i = 0; i < numRows; i++) {
			List<T> sublist = lista.get(i);
			array[i] = sublist.toArray((T[]) Array.newInstance(classe, sublist.size()));
		}
		
		return array;
    }
}
