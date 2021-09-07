package controller;

import java.util.List;

import view.ViewContainer;

/**
 * 
 * The class Bishop contains the information about the allowed moves of a bishop figure 
 *
 */
public class Bishop implements Figure {

	@Override
	public boolean checkMove(char storeOriginalCharacter, int[] decodedMove, ViewContainer v, List<String> moves) {
		
		// check if move is diagonal on chessfield
		if( Math.abs(decodedMove[1]-decodedMove[3]) == Math.abs(decodedMove[0]-decodedMove[2]) ) {
			
			// check if other figure is between original position and destination 
			// (destination not included)
			if(Math.abs(decodedMove[0]-decodedMove[2])>1 && checkMoveBlocked(decodedMove, v)) {
				return false;
			}
			// check if own figure is on destination
			
			return !ownFigureAtDestination(v, storeOriginalCharacter, decodedMove);
			
		}
		return false;
	}
	
	/**
	 * Checks if a specific move is blocked by another figure
	 * @param decodedMove Array that contains original and final position
	 * @param v View that contains model with chess field
	 * @return True, if move is blocked, otherwise false
	 */
	public boolean checkMoveBlocked(int[] decodedMove, ViewContainer v) {
		// if direction of move is from bottom left corner to top right corner
		if(decodedMove[0]<decodedMove[2] && decodedMove[1]>decodedMove[3] ) {
			return bottomLeftTopRight(decodedMove, v);
		}
		
		// if direction of move is from top left corner to bottom right corner
		else if(decodedMove[0]<decodedMove[2] ) {
			return topLeftBottomRight(decodedMove, v);
			
		}
		
		else {
			return checkMoveBlocked2(decodedMove, v);
		}
	}
	
	/**
	 * Checks if a specific move is blocked by another figure
	 * @param decodedMove Array that contains original and final position
	 * @param v View that contains model with chess field
	 * @return True, if move is not blocked, otherwise false
	 */
	
	public boolean checkMoveBlocked2(int[] decodedMove, ViewContainer v) {
		// if direction of move is from bottom right corner to top left corner
		if(decodedMove[0]>decodedMove[2] && decodedMove[1]>decodedMove[3]) {
			return bottomRightTopLeft(decodedMove, v);
		}
		
		// if direction of move is from top right corner to bottom left corner
		else if(decodedMove[0]>decodedMove[2] && decodedMove[1]<decodedMove[3]){
			return topRightBottomLeft(decodedMove, v);
		}
		return true;
	}
	
	/**
	 * Checks if other figure is blocking move from 
	 * bottom left to top right
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @param v View that contains model that stores chess field
	 * @return True, if other figure is blocking the move, otherwise false.
	 */
	public boolean bottomLeftTopRight(int[] decodedMove, ViewContainer v) {
		for(int i=1; i<(decodedMove[2]-decodedMove[0]);i++) {
			if(v.getModel().getChar(decodedMove[0]+i, decodedMove[1]-i) != ' ') {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Checks if other figure is blocking move from 
	 * top left to bottom right
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @param v View that contains model that stores chess field
	 * @return True, if other figure is blocking the move, otherwise false.
	 */
	public boolean topLeftBottomRight(int[] decodedMove, ViewContainer v) {
		
		for(int i=1; i<(decodedMove[2]-decodedMove[0]);i++) {
			if(v.getModel().getChar(decodedMove[0]+i, decodedMove[1]+i) != ' ') {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Checks if other figure is blocking move from 
	 * bottom right to top left
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @param v View that contains model that stores chess field
	 * @return True, if other figure is blocking the move, otherwise false.
	 */
	public boolean bottomRightTopLeft(int[] decodedMove, ViewContainer v) {
		
		for(int i=1; i<(decodedMove[0]-decodedMove[2]);i++) {
			if(v.getModel().getChar(decodedMove[0]-i, decodedMove[1]-i) != ' ') {
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Checks if other figure is blocking move from 
	 * top right to bottom left
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @param v View that contains model that stores chess field
	 * @return True, if other figure is blocking the move, otherwise false.
	 */
	public boolean topRightBottomLeft(int[] decodedMove, ViewContainer v) {
		
		for(int i=1; i<(decodedMove[0]-decodedMove[2]);i++) {
			if(v.getModel().getChar(decodedMove[0]-i, decodedMove[1]+i) != ' ') {
				return true;
			}
		}
		return false;
		
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
		return charAtDestination != ' ' && Character.isUpperCase(storeOriginalCharacter) == 
				Character.isUpperCase(charAtDestination);
	}

	@Override
	public boolean checkBeaten(int[] decodedMove, ViewContainer v) {
		return v.getModel().getChar(decodedMove[2], decodedMove[3]) != ' ';
	}

	@Override
	public boolean checkPossibleCheck(ViewContainer v, char[] opponentKingAndOwnFigure, int y, int x) {
		
		// bottom left to top right direction
		if(checkPossibleCheckBottomLeftTopRight(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		
		// bottom right to top left direction
		if(checkPossibleCheckBottomRightTopLeft(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		
		// top left to bottom right direction
		if(checkPossibleCheckTopLeftBottomRight(v, opponentKingAndOwnFigure[0], y, x)) {
			return true;
		}
		
		// top right to bottom left direction
		else {
			return checkPossibleCheckTopRightBottomLeft(v, opponentKingAndOwnFigure[0], y, x);
		}
		
	}
	
	/**
	 * method checkPossibleCheckBottomLeftTopRight checks for check
	 * from bottom left to top right.
	 * 
	 * @param v View that contains model that stores chess field
	 */
	public boolean checkPossibleCheckBottomLeftTopRight(ViewContainer v, char opponentKing, int y, int x) {
		int i = 1;
		
		while((x+i)<=7 && (y-i)>=0) {
			
			if(v.getModel().getChessField()[y-i][x+i]==opponentKing) {
				return true;
			}
			else if(v.getModel().getChessField()[y-i][x+i]!=' ') {
				return false;
			}
			i++;
		}
		return false;
	}
	
	/**
	 * method checkPossibleCheckBottomRightTopLeft checks for check
	 * from bottom right to top left.
	 * 
	 * @param v View that contains model that stores chess field
	 */
	public boolean checkPossibleCheckBottomRightTopLeft(ViewContainer v, char opponentKing, int y, int x) {
		int i = 1;
		while((x-i)>=0 && (y-i)>=0) {
			
			if(v.getModel().getChessField()[y-i][x-i]==opponentKing) {
				return true;
			}
			else if(v.getModel().getChessField()[y-i][x-i]!=' ') {
				return false;
			}
			i++;
		}
		return false;
		
	}
	
	/**
	 * method checkPossibleCheckTopLeftBottomRight checks for check
	 * from top left to bottom right.
	 * 
	 * @param v View that contains model that stores chess field
	 */
	public boolean checkPossibleCheckTopLeftBottomRight(ViewContainer v, char opponentKing, int y, int x) {
		int i = 1;
		while((x+i)<=7 && (y+i)<=7) {
			
			if(v.getModel().getChessField()[y+i][x+i]==opponentKing) {
				return true;
			}
			else if(v.getModel().getChessField()[y+i][x+i]!=' ') {
				return false;
			}
			i++;
		}
		return false;
	}
	
	/**
	 * method checkPossibleCheckTopRightBottomLeft checks for check
	 * from top right to bottom left.
	 * 
	 * @param v View that contains model that stores chess field
	 */
	public boolean checkPossibleCheckTopRightBottomLeft(ViewContainer v, char opponentKing, int y, int x) {
		int i = 1;
		while((x-i)>=0 && (y+i)<=7) {
			
			if(v.getModel().getChessField()[y+i][x-i]==opponentKing) {
				return true;
			}
			else if(v.getModel().getChessField()[y+i][x-i]!=' ') {
				return false;
			}
			i++;
		}
		return false;
	}
}