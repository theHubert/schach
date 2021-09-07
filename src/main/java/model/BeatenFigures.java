package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
//This class only stores the beaten figures and the corresponding unicode and is therefore a data class.


/**
 * 
 * Class BeatenFigures stores all beaten figures and enables to print them out.
 *
 */

public class BeatenFigures {
	
	List<Character> storeBeatenFigures;
	Map<Character, String> figureAsUnicode = new HashMap<>();
	
	/**
	 * Initializes the list that stores the beaten figures.
	 */
	public BeatenFigures() {
		storeBeatenFigures = new ArrayList<Character> ();
		
		figureAsUnicode.put('K', "\u2654");
		figureAsUnicode.put('Q', "\u2655");
		figureAsUnicode.put('R', "\u2656");
		figureAsUnicode.put('B', "\u2657");
		figureAsUnicode.put('N', "\u2658");
		figureAsUnicode.put('P', "\u2659");
		figureAsUnicode.put('k', "\u265A");
		figureAsUnicode.put('q', "\u265B");
		figureAsUnicode.put('r', "\u265C");
		figureAsUnicode.put('b', "\u265D");
		figureAsUnicode.put('n', "\u265E");
		figureAsUnicode.put('p', "\u265F");
	}
	
	
	public Map<Character, String> getFigureAsUnicode() {
		return figureAsUnicode;
	}


	public void setFigureAsUnicode(Map<Character, String> figureAsUnicode) {
		this.figureAsUnicode = figureAsUnicode;
	}


	/**
	 * Prints out beaten figures.
	 */
	public void printOutBeaten() {
		
		for(Character i : storeBeatenFigures) {
			System.out.println(i);
		}
	}

	public List<Character> getStoreBeatenFigures() {
		return storeBeatenFigures;
	}

	public void setStoreBeatenFigures(List<Character> storeBeatenFigures) {
		this.storeBeatenFigures = storeBeatenFigures;
	}
	
	

}
