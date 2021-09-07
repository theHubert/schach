package controller;

import java.util.List;

import view.ViewContainer;

/**
 *
 * The class King contains the information about the allowed moves of a king figure
 *
 */

public class King implements Figure {

	@Override
	public boolean checkMove(char storeOriginalCharacter, int[] decodedMove, ViewContainer v, List<String> moves) {
		
		// check if there is figure own at destination
		if(ownFigureAtDestination(v, storeOriginalCharacter, decodedMove)) {
			return false;
		}
		
		// check if movement is legal (one field in each every direction)
		if(Math.abs(decodedMove[0] - decodedMove[2]) <= 1 && Math.abs(decodedMove[1] - decodedMove[3]) <= 1) {
			return true;
		}
		// checks if a rochade is intended
		return legal_rochade(moves, storeOriginalCharacter, decodedMove, v);
		
	}
	
	/**
	 * method legal_rochade checks if a rochade is intended
	 * 
	 * @param List<String> moves List of previously moved pieces
	 * @param char storeOriginalCharacter currently moving figure
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @param v View that contains model that stores chess field
	 * @return true, if a rochade is intended
	 */
	
	public boolean legal_rochade(List<String> moves, char storeOriginalCharacter, int[] decodedMove, ViewContainer v) {
		// checks if king moves two spaces and if rochade conditions are met
		List<String> movesList;
		if(GuiControllerBoard.getMoves().size()!=0) {
			movesList = GuiControllerBoard.getMoves();
		}
		else {
			movesList = moves;
		}
		
		if (Character.isUpperCase(storeOriginalCharacter) && 
				!movesList.stream().anyMatch(s -> s.contains("e1")) ||
				!Character.isUpperCase(storeOriginalCharacter)
				&& !movesList.stream().anyMatch(s -> s.contains("e8"))){
			//System.out.println("in legal rochade");
			return decodedMove[2] - decodedMove[0] == 2 && Math.abs(decodedMove[1] - decodedMove[3]) == 0 
					&& rochade_right(moves, storeOriginalCharacter, decodedMove, v)
					|| decodedMove[2] - decodedMove[0] == -2 && Math.abs(decodedMove[1] - decodedMove[3]) == 0 
					&& rochade_left(moves, storeOriginalCharacter, decodedMove, v);
		}
		
		return false;
	}
	
	/**
	 * method rochade_left checks if a rochade to the right is legal
	 * 
	 * @param List<String> moves List of previously moved pieces
	 * @param char storeOriginalCharacter currently moving figure
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @param v View that contains model that stores chess field
	 * @return false, if a rochade to the right is legal
	 */
	
	public boolean rochade_right(List<String> moves, char storeOriginalCharacter, int[] decodedMove, ViewContainer v) {
		//GuiControllerBoard.getMovesVar().remove(GuiControllerBoard.getMovesVar().size() - 1);
		// rochade from white
		
		// checks if rook and king haven't moved
		if (storeOriginalCharacter == 'K') {
			// checks if path from king to rook is empty
			if (checkPathRight(v, decodedMove)) {
				return false;
			}
			// moves rook
			v.getModel().setChar(7, 7, ' ');
			v.getModel().setChar(5, 7, 'R');
			return true;
		}
		// rochade from black
		// checks if rook and king haven't moved
		if (storeOriginalCharacter == 'k'
				&& !GuiControllerBoard.getMoves().stream().anyMatch(s -> s.contains("e8"))
				&& !GuiControllerBoard.getMoves().stream().anyMatch(s -> s.contains("h8"))) {
			// checks if path from king to rook is empty
			if (checkPathRight(v, decodedMove)) {
				return false;
			}
			// moves rook
			v.getModel().setChar(7, 0, ' ');
			v.getModel().setChar(5, 0, 'r');
			return true;
		}		
		return false;
	}

	/**
	 * method rochade_left checks if a rochade to the left is legal
	 * 
	 * @param List<String> moves List of previously moved pieces
	 * @param char storeOriginalCharacter currently moving figure
	 * @param decodedMove[0] X value of original position of figure
	 * @param decodedMove[1] Y value of original position of figure
	 * @param decodedMove[2] X value of destination of figure
	 * @param decodedMove[3] Y value of destination of figure
	 * @param v View that contains model that stores chess field
	 * @return false, if a rochade to the left is legal
	 */

	public boolean rochade_left(List<String> moves, char storeOriginalCharacter, int[] decodedMove, ViewContainer v) {
		
		// rochade from white
		// checks if rook and king haven't moved
		if (storeOriginalCharacter == 'K') {
			// checks if path from king to rook is empty
			if (checkPathLeft(v, decodedMove)) {
				return false;
			}
			// moves rook
			v.getModel().setChar(0, 7, ' ');
			v.getModel().setChar(3, 7, 'R');
			return true;
		}
		// rochade from black
		// checks if rook and king haven't moved
		if (storeOriginalCharacter == 'k'
				&& !GuiControllerBoard.getMoves().stream().anyMatch(s -> s.contains("e8"))
				&& !GuiControllerBoard.getMoves().stream().anyMatch(s -> s.contains("a8"))) {
			// checks if path from king to rook is empty
			if (checkPathLeft(v, decodedMove)) {
				return false;
			}
			// moves rook
			v.getModel().setChar(0, 0, ' ');
			v.getModel().setChar(3, 0, 'r');
			return true;
		}
		return false;
	}
	
	private boolean checkPathRight(ViewContainer v, int[] decodedMove) {
		boolean blocked = false;
		for(int i = 1; i <= 2; i++) {
			if (v.getModel().getChar(decodedMove[0] + i, decodedMove[1]) != ' ') {
				blocked = true;
			}
		}
		return blocked;
	}

	private boolean checkPathLeft(ViewContainer v, int[] decodedMove) {
		boolean blocked = false;
		for(int i = 1; i <= 3; i++) {
			if (v.getModel().getChar(decodedMove[0] - i, decodedMove[1]) != ' ') {
				blocked = true;
			}
		}
		return blocked;
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
		return Character.isUpperCase(storeOriginalCharacter) == Character.isUpperCase(charAtDestination) 
				&& charAtDestination != ' ';

	}

	@Override
	public boolean checkPossibleCheck(ViewContainer v, char[] opponentKingAndOwnFigure, int y, int x) {
		boolean Check1 = x-1 >= 0 && v.getModel().getChar(x-1, y)==opponentKingAndOwnFigure[0];
		boolean Check2 = x-1 >=0 && y-1 >= 0 && v.getModel().getChar(x-1, y-1)==opponentKingAndOwnFigure[0];
		boolean Check3 = x-1 >=0 && y+1 <= 7 && v.getModel().getChar(x-1, y+1)==opponentKingAndOwnFigure[0];
		boolean Check4 = y-1 >=0 && v.getModel().getChar(x, y-1)==opponentKingAndOwnFigure[0];
		
		return Check1 || Check2 || Check3 || Check4 || OtherPossibleChecks(v, opponentKingAndOwnFigure[0], y, x);
	}
	
	/**
	 * OtherPossibleChecks contains the queries of other possible Check 
	 * constellations for the CheckPossibleCheck method
	 * 
	 * @param v View that contains model of the chessfield
	 * @param opponentKing King of other color
	 * @param y int for the y component of char location
	 * @param x int for the y component of char location
	 * @return true, if any of the possible checks is fulfilled
	 */
	
	public boolean OtherPossibleChecks(ViewContainer v, char opponentKing, int y, int x) {
		
		boolean check1 = x+1 <= 7 && v.getModel().getChar(x+1, y)==opponentKing;
		boolean check2 = y+1 <= 7 && v.getModel().getChar(x, y+1)==opponentKing;
		boolean check3 = x+1 <= 7 && y+1 <= 7 && v.getModel().getChar(x+1, y+1)==opponentKing;
		boolean check4 = x+1 <= 7 && y-1 >= 0 && v.getModel().getChar(x+1, y-1)==opponentKing;
		
		return check1 || check2 || check3 || check4;
	}
	
}
