package controller;

import java.util.List;

import view.ViewContainer;

/**
 * 
 * Interface figure describes the general type of a figure
 *
 */

public interface Figure {
	
	/**
	 * Checks if a move is allowed for a specific figure.
	 * @param storeOriginalCharacter stores figure at original position
	 * @param decodedMove contains information about original and final position of move
	 * @param v View that contains model that stores chess field
	 * @return True, if move is allowed, otherwise false
	 */
    boolean checkMove(char storeOriginalCharacter, int[] decodedMove, ViewContainer v, List<String> moves);
    
    /**
     * Checks if an opponent figure gets beaten
     * @param decodedMove contains information about original and final position of move
     * @param v View that contains model that stores chess field
     * @return True if an opponent figure gets beaten, otherwise false
     */
    boolean checkBeaten(int[] decodedMove, ViewContainer v);
    
    /**
     * Checks if a figure threatens the opponent king directly (=check)    
     * @param v View that stores model with chess field
     * @param opponentKingAndOwnFigure stores the chars of one's own figure and the opponent king 
     * (equivalent to colors of figures)
     * @param y Y position of figure.
     * @param x X position of figure.
     * @return True, if figure threatens the opponent king directly (=check)
     */
    boolean checkPossibleCheck(ViewContainer v, char[] opponentKingAndOwnFigure, int y, int x);
    
}
