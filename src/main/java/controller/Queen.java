package controller;

import java.util.List;

import view.ViewContainer;

/**
 * The class Queen contains the information about the allowed moves of a queen
 * figure by combining the logic of the classes rook and bishop
 *
 */
public class Queen implements Figure {
	
	Rook r = new Rook();
	Bishop b = new Bishop();

	@Override
	public boolean checkMove(char storeOriginalCharacter, int[] decodedMove, ViewContainer v, List<String> moves) {
		
		return r.checkMove(storeOriginalCharacter, decodedMove, v, moves) || b.checkMove(storeOriginalCharacter, decodedMove, v, moves);
	}

	@Override
	public boolean checkBeaten(int[] decodedMove, ViewContainer v) {
		
		return r.checkBeaten(decodedMove, v);
	}

	
	/**
	 * method ownFigureAtDestination checks if there is a figure of the same
	 * color at the destination of the move. Since the queen receives the logics
	 * from rook and bishop, the same method from one of them is called
	 * 
	 * @param v View that contains model that stores chess field
	 * @param storeOriginalCharacter char of the figure to be moved
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @return true, if there is a figure of the same color, otherwise false
	 */

	public boolean ownFigureAtDestination(ViewContainer v, char storeOriginalCharacter, int[] decodedMove) {
		
		return r.ownFigureAtDestination(v, storeOriginalCharacter, decodedMove);
		
	}
	
	@Override
	public boolean checkPossibleCheck(ViewContainer v, char[] opponentKingAndOwnFigure, int y, int x) {
		
		return r.checkPossibleCheck(v, opponentKingAndOwnFigure, y, x)||b.checkPossibleCheck(v, opponentKingAndOwnFigure, y, x);

	}

	
}
