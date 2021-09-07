package controller;

import java.util.HashMap;
import java.util.Map;

import view.ViewContainer;

/**
 * Class Check contains method for checking for a check.
 *
 */

public class Check {
	
	/**
	 * checkCheck accesses a figure class and checks the respective check
	 * conditions of a figure
	 * 
	 * @param copyOfView View that contains model with chess field.
	 * @param ownFigure stores char of one's players figures, analogue to color of figures.
	 * @return True, if opponents king is in check, otherwise false.
	 */
	
	
	public boolean checkCheck(ViewContainer copyOfView, char ownFigure) {
		
		char opponentKing;
		
		Map<Character, Figure> figureOutOfChar = new HashMap<Character, Figure>();
		
		figureOutOfChar.put('p', new Pawn());
		figureOutOfChar.put('P', new Pawn());
		figureOutOfChar.put('k', new King());
		figureOutOfChar.put('K', new King());
		figureOutOfChar.put('q', new Queen());
		figureOutOfChar.put('Q', new Queen());
		figureOutOfChar.put('b', new Bishop());
		figureOutOfChar.put('B', new Bishop());
		figureOutOfChar.put('n', new Knight());
		figureOutOfChar.put('N', new Knight());
		figureOutOfChar.put('r', new Rook());
		figureOutOfChar.put('R', new Rook());
		
		// iterate over chess field and check for each figure if opponent king can be beaten
		for(int i=0; i<copyOfView.getModel().getChessField().length; i++) {
			for(int j=0; j<copyOfView.getModel().getChessField()[i].length; j++) {
				
				// set opponent king
				if(Character.isUpperCase(ownFigure)) {
					opponentKing = 'k';
				}
				else {
					opponentKing = 'K';
				}
			
				// check only for own figures (not empty fields)
				if(Character.isUpperCase(ownFigure) == Character.isUpperCase(copyOfView.getModel().getChessField()[i][j]) && 
						copyOfView.getModel().getChessField()[i][j] != ' ') {
					Figure currentFigure = figureOutOfChar.get(copyOfView.getModel().getChessField()[i][j]);
					
					char[] opponentKingAndOwnFigure = {opponentKing, ownFigure};
					if(currentFigure.checkPossibleCheck(copyOfView, opponentKingAndOwnFigure, i, j)) {
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
	
}
