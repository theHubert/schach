package controller;

import java.util.List;

import view.ViewContainer;

/**
*
* The class Pawn contains the information about the allowed moves of a pawn figure
*
*/
public class Pawn implements Figure{

	@Override
	public boolean checkMove(char storeOriginalCharacter, int[] decodedMove, ViewContainer v, List<String> moves) {
		
		// check if move is one forward step in right direction and there is no figure at destination
		if(!figureAtDestination(v, storeOriginalCharacter, decodedMove) 
				&& (checkStepForward(storeOriginalCharacter, decodedMove) 
				|| checkFirstStep(storeOriginalCharacter, decodedMove, v))) {
			return true;
		}
		
		// check if move is one diagonal step in right direction and there is an opponent 
		// figure at destination
		else if(checkDiagonalStep(storeOriginalCharacter, decodedMove) && 
				opponentFigureAtDestination(v, storeOriginalCharacter, decodedMove)) {
			return true;
		}
		// checks en passant
		else if(checkDiagonalStep(storeOriginalCharacter, decodedMove) 
				&& opponentFigureBeside(v, storeOriginalCharacter, decodedMove)
				&& checkPreviousPawn(decodedMove, moves)) {
			v.getModel().setChar(decodedMove[2], decodedMove[1], ' ');
			return true;
		}
		
		return false;
	}

	@Override
	public boolean checkBeaten(int[] decodedMove, ViewContainer v) {
		// if step was diagonal, opponent figure is beaten
		return Math.abs(decodedMove[0]-decodedMove[2]) == 1;
	}
	
	/**
	 * Checks, if the pawn for en passant moved 2 spaces in the last turn
	 * @param List<String> moves List of previously moved pieces.
	 * @param decodedMove Contains information about original position and destination.
	 * @return True, if pawn moved two in the last turn.
	 */
	private boolean checkPreviousPawn(int[] decodedMove, List<String> moves) {
		// define the last movement
		String pawnMove;
		if(GuiControllerBoard.getMoves().size()!=0) {
			pawnMove = GuiControllerBoard.getMoves().get(GuiControllerBoard.getMoves().size()-1);
		}
		else {
			pawnMove = moves.get(moves.size()-1);
		}
		
		
		// convert x position in into integer
		pawnMove = pawnMove.replace("a", "0");
		pawnMove = pawnMove.replace("b", "1");
		pawnMove = pawnMove.replace("c", "2");
		pawnMove = pawnMove.replace("d", "3");
		pawnMove = pawnMove.replace("e", "4");
		pawnMove = pawnMove.replace("f", "5");
		pawnMove = pawnMove.replace("g", "6");
		pawnMove = pawnMove.replace("h", "7");
		
		//System.out.println("");
		
		// check if the pawn beside moved two right before
//		return pawnOrg.charAt(1) == '7' && pawnDes.charAt(1) == '5' && 
//				Character.getNumericValue(pawnDes.charAt(0)) == decodedMove[2]
//				|| pawnOrg.charAt(1) == '2' && pawnDes.charAt(1) == '4';
		return pawnMove.charAt(1) == '7' && pawnMove.charAt(4) == '5' &&
				Character.getNumericValue(pawnMove.charAt(3)) == decodedMove[2]
				|| pawnMove.charAt(1) == '2' && pawnMove.charAt(4) == '4'
				&& Character.getNumericValue(pawnMove.charAt(3)) == decodedMove[2];
		
	}

	/**
	 * Checks, if there is an opponent figure next to the pawn
	 * @param v View, that contains model with chess field.
	 * @param storeOriginalCharacter Character/figure that should be moved.
	 * @param decodedMove Contains information about original position and destination.
	 * @return True, if there is an opponent figure at destination, otherwise false.
	 */
	public boolean opponentFigureBeside(ViewContainer v, char storeOriginalCharacter, int[] decodedMove) {
		if (v.getModel().getChar(decodedMove[2], decodedMove[3]) == ' ' 
				&& Character.isUpperCase(storeOriginalCharacter) != Character.isUpperCase(v.getModel().getChar(decodedMove[2], decodedMove[1]))) {
			if (Character.isUpperCase(storeOriginalCharacter)
					&& decodedMove[1] == 3 && v.getModel().getChar(decodedMove[2], decodedMove[1]) == 'p') {
				return true;
			}
			if (!Character.isUpperCase(storeOriginalCharacter)
					&& decodedMove[1] == 4 && v.getModel().getChar(decodedMove[2], decodedMove[1]) == 'P') {
				return true;
			}
		}
				
		return false;
	} 
	
	/**
	 * Checks, if there is an opponent figure at destination.
	 * @param v View, that contains model with chess field.
	 * @param storeOriginalCharacter Character/figure that should be moved.
	 * @param decodedMove Contains information about original position and destination.
	 * @return True, if there is an opponent figure at destination, otherwise false.
	 */
	public boolean opponentFigureAtDestination(ViewContainer v, char storeOriginalCharacter, int[] decodedMove) {
		char charAtDestination = v.getModel().getChar(decodedMove[2], decodedMove[3]);
		return charAtDestination != ' ' && Character.isUpperCase(storeOriginalCharacter) !=
				Character.isUpperCase(charAtDestination);
	} 
	
	/**
	 * Checks if the input is a diagonal step in right direction.
	 * @param storeOriginalCharacter Character/figure that should be moved.
	 * @param decodedMove Contains information about original position and destination.
	 * @return True, if move is diagonal move in right direction and the move is 
	 * therefore allowed, otherwise false.
	 */
	public boolean checkDiagonalStep(char storeOriginalCharacter, int[] decodedMove) {
		// move of white pawns
		if(Character.isUpperCase(storeOriginalCharacter) && (decodedMove[1]-decodedMove[3])==1 && 
				Math.abs(decodedMove[0]-decodedMove[2])==1) {
			return true;
		}
		// move of black pawns
		else{
			return !Character.isUpperCase(storeOriginalCharacter) && (decodedMove[3]-decodedMove[1])==1 && 
					Math.abs(decodedMove[0]-decodedMove[2])==1;
		}
	}
	
	/**
	 * Checks, if there is a figure at destination.
	 * @param v View, that contains model with chess field.
	 * @param storeOriginalCharacter Char/figure that should be moved.
	 * @param decodedMove Contains information about original position and destination.
	 * @return True, if there is a figure at destination, otherwise false.
	 */
	public boolean figureAtDestination(ViewContainer v, char storeOriginalCharacter, int[] decodedMove) {
		return v.getModel().getChar(decodedMove[2], decodedMove[3])!=' ';
	}
	
	/**
	 * Checks, if the input is a step forward in right direction.
	 * @param storeOriginalCharacter Char/figure that should be moved.
	 * @param decodedMove Contains information about original position and destination.
	 * @return True, if input is a step forward in right direction and the move is
	 * therefore allowed, otherwise false.
	 */
	public boolean checkStepForward(char storeOriginalCharacter, int[] decodedMove) {
		// direction of white pawns
		if(Character.isUpperCase(storeOriginalCharacter) && (decodedMove[1]-decodedMove[3])==1 && 
				decodedMove[0]-decodedMove[2]==0) {
			return true;
		}
		// direction of black pawns
		else{
			return !Character.isUpperCase(storeOriginalCharacter) && (decodedMove[3]-decodedMove[1])==1 && 
					decodedMove[0]-decodedMove[2]==0;
		}
	}
	
	/**
	 * Checks, if a pawn is located on its starting position and can therefore move 2 steps forward.
	 * @param storeOriginalCharacter Char/figure that should be moved.
	 * @param decodedMove Contains information about original position and destination.
	 * @param v View, that contains model with chess field.
	 * @return True, if pawn is located on its starting position and if input equals a 
	 * 2 step forward move, otherwise false.
	 */
	public boolean checkFirstStep(char storeOriginalCharacter, int[] decodedMove, ViewContainer v) {
		
		int whitePawnFirstRow = v.getModel().getChessField().length -2;	
		return (decodedMove[1] == whitePawnFirstRow && Character.isUpperCase(storeOriginalCharacter)&& 
				v.getModel().getChar(decodedMove[0], 5) == ' '|| 
				(decodedMove[1] == 1 && v.getModel().getChar(decodedMove[0], 2) == ' ') && 
				!Character.isUpperCase(storeOriginalCharacter) )&&
				Math.abs(decodedMove[1]-decodedMove[3]) == 2 && decodedMove[0]-decodedMove[2] == 0;
	}
	
	/**
	 * Checks if pawn should be converted.
	 * @param prospectivePawnConversion Char/Figure, in that pawn should be converted.
	 * @param decodedMove Contains information about original position and destination.
	 * @return True, if pawn should be converted, otherwise false.
	 */
	public boolean convertPawn(char prospectivePawnConversion, int[] decodedMove) {
		// white pawns
		if(Character.isUpperCase(prospectivePawnConversion) && decodedMove[3]==0) {
			return true;
		}
		else{
			return !Character.isUpperCase(prospectivePawnConversion) && 
					 decodedMove[3]==7;
		}
	}

	@Override
	public boolean checkPossibleCheck(ViewContainer v, char[] opponentKingAndOwnFigure, int y, int x) {
		
		boolean Check1 = Character.isUpperCase(opponentKingAndOwnFigure[1]) && x-1 >= 0 && y-1 >= 0 && v.getModel().getChar(x-1, y-1) == opponentKingAndOwnFigure[0]; 
		boolean Check2 = Character.isUpperCase(opponentKingAndOwnFigure[1]) && x+1 <= 7 && y-1 >= 0 && v.getModel().getChar(x+1, y-1) == opponentKingAndOwnFigure[0];
		
			return Check1 || Check2 || checkCheckBlackPawn(v, opponentKingAndOwnFigure[0], y, x);

		}

	/**
	 * checkCheckBlackPawn contains the queries of possible check constellations
	 * for the black pawn and is linked to the CheckPossibleCheck method, which
	 * contains the queries for the white pawn
	 * 
	 * @param v View, that contains model with chess field
	 * @param opponentKing King of the other color 
	 * @param y
	 * @param x
	 * @return true for a possible Check for the black pawn, otherwise false
	 */
	
	public boolean checkCheckBlackPawn(ViewContainer v, char opponentKing, int y, int x) {
		char storeOriginalCharacter = v.getModel().getChar(x, y);
		
		boolean Check1 = Character.isLowerCase(storeOriginalCharacter) && x-1 >= 0 && y+1 <= 7 && v.getModel().getChar(x-1, y+1) == opponentKing;
		boolean Check2 = Character.isLowerCase(storeOriginalCharacter) && x+1 <= 7 && y+1 <= 7 && v.getModel().getChar(x+1, y+1) == opponentKing;
		
		return Check1 || Check2;
	}

	

}
