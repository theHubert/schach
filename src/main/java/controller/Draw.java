package controller;

import java.util.ArrayList;
import java.util.List;

import view.ViewContainer;

/**
*
* The class Draw checks if the game is a draw
*
*/

public class Draw {
	
	/**
	 * Checks if the game is at a draw.
	 * @param guiViewBoard View that contains model that stores chess field
	 * @param Move move Changes to the positions of single figures on the chess field.
	 * @return True, if game is a draw, otherwise false
	 */
	public static boolean checkDraw(ViewContainer v, Move move) {
		
		List<String> remaining = new ArrayList<>();
		
		// iterates over board
		for(int i=0; i<v.getModel().getChessField().length; i++) {
			for(int j=0; j<v.getModel().getChessField()[i].length; j++) {
				// adds remaining figures to list
				remaining.add(String.valueOf(v.getModel().getChar(i, j)));
				// removes empty spaces
				remaining.remove(" ");
			}
		}
		
		// if kings and one of bishop or knight are remaining its a draw
		return checkRemains(remaining);
	}
	
	/**
	 * Checks remaining pieces for a draw
	 * @param List<String> remaining pieces still on field
	 * @return True, if specific pieces remain
	 */
	public static boolean checkRemains(List<String> remaining) {
		return remaining.size() == 3 && remaining.contains("K") && remaining.contains("k")
				&& (remaining.contains("B") || remaining.contains("b")
				|| remaining.contains("N") || remaining.contains("n"))
				|| remaining.size() == 2 && remaining.contains("K") && remaining.contains("k");
	}
}
