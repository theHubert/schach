package controller;

import java.util.List;

import view.ViewContainer;

/**
 * 
 * The class Rook contains the information about the allowed moves of a rook figure 
 *
 */

public class Rook implements Figure {

	@Override
	public boolean checkMove(char storeOriginalCharacter, int[] decodedMove, ViewContainer v, List<String> moves) {
		
		// check if move is straight on chessfield
		if(decodedMove[0] == decodedMove[2] || decodedMove[1] == decodedMove[3]) {
			
			// check if other figure is between original position and destination 
			if((Math.abs(decodedMove[0]-decodedMove[2])>1 || Math.abs(decodedMove[1]-decodedMove[3])>1) && !checkIfFree(decodedMove, v)) {
				return false;
			}
			// check if own figure is on destination
			return !ownFigureAtDestination(v, storeOriginalCharacter, decodedMove);
			
		}
		return false;

	}
	
	/**
	 * method checkIfFree checks if there are any other figures blocking the 
	 * way of the rook
	 * 
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @param v View that contains model that stores chess field
	 * @return false, if the way is blocked, otherwise true
	 */
	
	public boolean checkIfFree(int[] decodedMove, ViewContainer v) {
		// if rook moves straight
		if(decodedMove[0] == decodedMove[2]) {
			
			for(int i=1; i<(Math.abs(decodedMove[3]-decodedMove[1]));i++) {
				if(v.getModel().getChar(decodedMove[0], Math.min(decodedMove[1], decodedMove[3])+i) != ' ') {
					return false;
				}
			}
			return true;
		}
		
		// if rook moves sideways
		else {
			
			for(int i=1; i<(Math.abs(decodedMove[2]-decodedMove[0]));i++) {
				if(v.getModel().getChar(Math.min(decodedMove[0], decodedMove[2])+i, decodedMove[1]) != ' ') {
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public boolean checkBeaten(int[] decodedMove, ViewContainer v) {
		return v.getModel().getChar(decodedMove[2], decodedMove[3]) != ' ';
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
		char charAtDestination = v.getModel().getChar(decodedMove[2], decodedMove[3]);
		
		return charAtDestination != ' ' && Character.isUpperCase(storeOriginalCharacter) == Character.isUpperCase(charAtDestination);
	}

	@Override
	public boolean checkPossibleCheck(ViewContainer v, char[] opponentKingAndOwnFigure, int y, int x) {
		
		// bottom to top
		if(checkPossibleCheckBottomTop(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		
		// top to bottom
		else if(checkPossibleCheckTopBottom(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		
		// left to right
		else if(checkPossibleCheckLeftRight(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		
		// right to left
		else {
			return checkPossibleCheckRightLeft(v, opponentKingAndOwnFigure[0], y, x);
		}
		
	}
	
	/**
	 * checkPossibleCheckBottomTop contains the query for a possible check
	 * in the front direction
	 * 
	 * @param v View that contains model that stores chess field
	 * @param opponentKing char of the opponent king
	 * @param y value of chessboard
	 * @param x value of chessboard
	 * @return true, if there is a possible check, otherwise false
	 */
	public boolean checkPossibleCheckBottomTop(ViewContainer v, char opponentKing, int y, int x) {
		int i = 1;
		// bottom to top
		while((y-i)>=0) {
			
			if(v.getModel().getChessField()[y-i][x]==opponentKing) {
				return true;
			}
			else if(v.getModel().getChessField()[y-i][x]!=' ') {
				return false;
			}
			i++;
		}
		return false;
	}
	
	/**
	 * checkPossibleCheckTopBottom contains the query for a possible check
	 * in the back direction
	 * 
	 * @param v View that contains model that stores chess field
	 * @param opponentKing char of the opponent king
	 * @param y value of chessboard
	 * @param x value of chessboard
	 * @return true, if there is a possible check, otherwise false
	 */
	public boolean checkPossibleCheckTopBottom(ViewContainer v, char opponentKing, int y, int x) {
		int i=1;
		// top to bottom
		while((y+i)<=7) {
			
			if(v.getModel().getChessField()[y+i][x]==opponentKing) {
				return true;
			}
			else if(v.getModel().getChessField()[y+i][x]!=' ') {
				return false;
			}
			i++;
		}
		return false;
	}
	
	/**
	 * checkPossibleCheckLeftRight contains the query for a possible check
	 * in the right direction
	 * 
	 * @param v View that contains model that stores chess field
	 * @param opponentKing char of the opponent king
	 * @param y value of chessboard
	 * @param x value of chessboard
	 * @return true, if there is a possible check, otherwise false
	 */
	public boolean checkPossibleCheckLeftRight(ViewContainer v, char opponentKing, int y, int x) {
		
		int i=1;
		while((x+i)<=7) {
			if(v.getModel().getChessField()[y][x+i]==opponentKing) {
				return true;
			}
			else if(v.getModel().getChessField()[y][x+i]!=' ') {
				return false;
			}
			i++;
		}
		return false;
	}
	
	/**
	 * checkPossibleCheckRightLeft contains the query for a possible check
	 * in the left direction
	 * 
	 * @param v View that contains model that stores chess field
	 * @param opponentKing char of the opponent king
	 * @param y value of chessboard
	 * @param x value of chessboard
	 * @return true, if there is a possible check, otherwise false
	 */
	public boolean checkPossibleCheckRightLeft(ViewContainer v, char opponentKing, int y, int x) {
		
		int i=1;
		while((x-i)>=0) {
			if(v.getModel().getChessField()[y][x-i]==opponentKing) {
				return true;
			}
			else if(v.getModel().getChessField()[y][x-i]!=' ') {
				return false;
			}
			i++;
		}
		return false;
	}


}
