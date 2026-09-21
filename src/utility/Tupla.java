package utility;

import java.io.Serializable;

/**
 * Classe per gestire una tupla di oggetti di classe F,S
 * @param <F>
 * @param <S>
 * @since 1
 */
public class Tupla<F, S> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private F first; //first member of pair
	private S second; //second member of pair

	public Tupla(F first, S second) {
		this.first = first;
		this.second = second;
	}
	
	public void setFirst(F first) {
		this.first = first;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "(" + first + "," + second + ")";
	}
}

