package controller;

import java.util.List;

import view.ViewContainer;

/**
 * 
 * The class Knight contains the information about the allowed moves of a knight figure 
 *
 */

public class Knight implements Figure {
	
	Rook r = new Rook();

	@Override
	public boolean checkMove(char storeOriginalCharacter, int[] decodedMove, ViewContainer v, List<String> moves) {
		
		// check if there is own figure at destination
		if(ownFigureAtDestination(v, storeOriginalCharacter, decodedMove)) {
			return false;
		}
		
		// check if combination of distance difference between x and y direction is allowed 
		if(Math.abs(decodedMove[0]-decodedMove[2])==1 && Math.abs(decodedMove[1]-decodedMove[3])==2) {
			return true;
		}
		else if(Math.abs(decodedMove[0]-decodedMove[2])==2 && Math.abs(decodedMove[1]-decodedMove[3])==1) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean checkBeaten(int[] decodedMove, ViewContainer v) {
		return r.checkBeaten(decodedMove, v);
	}

	/**
	 * method ownFigureAtDestination checks if there is a figure of the same
	 * color at the destination of the move.
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
		//links, links oben
		if(checkPossibleCheckLeftTop(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		else if(checkPossibleCheckTopLeft(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		else if(checkPossibleCheckRightTop(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		else if(checkPossibleCheckTopRight(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		else if(checkPossibleCheckBottomLeft(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		else if(checkPossibleCheckLeftBottom(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		else if(checkPossibleCheckRightBottom(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		else {
			return checkPossibleCheckBottomRight(v, opponentKingAndOwnFigure[0], y, x);
		}
		
	}
	
	/**
	 * Checks weather a move of knight in left top direction results in check.
	 * @param v View that contains model with chess field.
	 * @param opponentKing Char of opponent king.
	 * @param y Y position of knight.
	 * @param x X position of knight.
	 * @return True, if move of knight in left top direction results in check, otherwise false.
	 */
	public boolean checkPossibleCheckLeftTop(ViewContainer v, char opponentKing, int y, int x) {
		return x-2 >= 0 && y-1 >= 0 && v.getModel().getChar(x-2, y-1) == opponentKing;
	}
	
	/**
	 * Checks weather a move of knight in top left direction results in check.
	 * @param v View that contains model with chess field.
	 * @param opponentKing Char of opponent king.
	 * @param y Y position of knight.
	 * @param x X position of knight.
	 * @return True, if move of knight in top left direction results in check, otherwise false.
	 */
	public boolean checkPossibleCheckTopLeft(ViewContainer v, char opponentKing, int y, int x) {
		return x-1 >= 0 && y-2 >= 0 && v.getModel().getChar(x-1, y-2) == opponentKing;
	}
	
	/**
	 * Checks weather a move of knight in right top direction results in check.
	 * @param v View that contains model with chess field.
	 * @param opponentKing Char of opponent king.
	 * @param y Y position of knight.
	 * @param x X position of knight.
	 * @return True, if move of knight in right top direction results in check, otherwise false.
	 */
	public boolean checkPossibleCheckRightTop(ViewContainer v, char opponentKing, int y, int x) {
		return x+2 <= 7 && y-1 >= 0 && v.getModel().getChar(x+2, y-1) == opponentKing;
	}
	
	/**
	 * Checks weather a move of knight in top right direction results in check.
	 * @param v View that contains model with chess field.
	 * @param opponentKing Char of opponent king.
	 * @param y Y position of knight.
	 * @param x X position of knight.
	 * @return True, if move of knight in top right direction results in check, otherwise false.
	 */
	public boolean checkPossibleCheckTopRight(ViewContainer v, char opponentKing, int y, int x) {
		return x+1 <= 7 && y-2 >= 0 && v.getModel().getChar(x+1, y-2) == opponentKing;
	}
	
	/**
	 * Checks weather a move of knight in bottom left direction results in check.
	 * @param v View that contains model with chess field.
	 * @param opponentKing Char of opponent king.
	 * @param y Y position of knight.
	 * @param x X position of knight.
	 * @return True, if move of knight in bottom left direction results in check, otherwise false.
	 */
	public boolean checkPossibleCheckBottomLeft(ViewContainer v, char opponentKing, int y, int x) {
		return x-1 >= 0 && y+2 <= 7 && v.getModel().getChar(x-1, y+2) == opponentKing;
	}
	
	/**
	 * Checks weather a move of knight in left bottom direction results in check.
	 * @param v View that contains model with chess field.
	 * @param opponentKing Char of opponent king.
	 * @param y Y position of knight.
	 * @param x X position of knight.
	 * @return True, if move of knight in left bottom direction results in check, otherwise false.
	 */
	public boolean checkPossibleCheckLeftBottom(ViewContainer v, char opponentKing, int y, int x) {
		return x-2 >= 0 && y+1 <= 7 && v.getModel().getChar(x-2, y+1) == opponentKing;
	}
	
	/**
	 * Checks weather a move of knight in right bottom direction results in check.
	 * @param v View that contains model with chess field.
	 * @param opponentKing Char of opponent king.
	 * @param y Y position of knight.
	 * @param x X position of knight.
	 * @return True, if move of knight in right bottom direction results in check, otherwise false.
	 */
	public boolean checkPossibleCheckRightBottom(ViewContainer v, char opponentKing, int y, int x) {
		return x+2 <= 7 && y+1 <= 7 && v.getModel().getChar(x+2, y+1) == opponentKing;
	}
	
	/**
	 * Checks weather a move of knight in bottom right direction results in check.
	 * @param v View that contains model with chess field.
	 * @param opponentKing Char of opponent king.
	 * @param y Y position of knight.
	 * @param x X position of knight.
	 * @return True, if move of knight in bottom right direction results in check, otherwise false.
	 */
	public boolean checkPossibleCheckBottomRight(ViewContainer v, char opponentKing, int y, int x) {
		return x+1 <= 7 && y+2 <= 7 && v.getModel().getChar(x+1, y+2) == opponentKing;
	}

}
