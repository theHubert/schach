package controller;

import view.ViewContainer;

/**
 * The class MoveToCheck connects the classes Move and Check
 *
 */
public class MoveToCheck {
	
	private Check check = new Check();
	
	/**
	 * moveResultsInCheck checks if a move leads the own king into a check
	 * 
	 * @param v View that contains model with chess field
	 * @param figure respective Figure
	 * @param decodedMove origin and destination values of the move
	 * @param storeOriginalCharacterAndProspectivePawnConversion char of the original figure and of the prospective pawn
	 * @return true, if it leads to a check, otherwise false
	 */
	
	public boolean moveResultsInCheck(ViewContainer v, Figure figure, int[] decodedMove, char[] storeOriginalCharacterAndProspectivePawnConversion) {
		// create copy of chess field 
		ViewContainer copyOfView = v.getCopyOfView();
		// if figure of opponent gets beaten, delete beaten figure
		if(figure.checkBeaten(decodedMove, copyOfView)) {
			copyOfView.getModel().setChar(decodedMove[2], decodedMove[3], ' ');
		}
		
		//setting char from destination to origin
		copyOfView.getModel().setChar(decodedMove[0], decodedMove[1], copyOfView.getModel().getChar(decodedMove[2], decodedMove[3]));
		
		
		//setting char from origin to destination
		copyOfView.getModel().setChar(decodedMove[2], decodedMove[3], storeOriginalCharacterAndProspectivePawnConversion[0]);
		
		// check for pawn conversion (new figure needs to be of right color)
		if(figure instanceof Pawn && ((Pawn) figure).convertPawn(storeOriginalCharacterAndProspectivePawnConversion[1], decodedMove)) {
			copyOfView.getModel().setChar(decodedMove[2], decodedMove[3], storeOriginalCharacterAndProspectivePawnConversion[1]);
		}
		
		char opponent;
		if(Character.isUpperCase(storeOriginalCharacterAndProspectivePawnConversion[0])) {
			opponent = Character.toLowerCase(storeOriginalCharacterAndProspectivePawnConversion[0]);
		}
		else {
			opponent = Character.toUpperCase(storeOriginalCharacterAndProspectivePawnConversion[0]);
		}
		
		return check.checkCheck(copyOfView, opponent);
	}

}
