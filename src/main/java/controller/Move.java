package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BeatenFigures;
import view.ViewContainer;
@SuppressWarnings("PMD.DataClass")

/**
 * 
 * The class Move enables to change the position of single figures on the chess field.
 *
 */

public class Move {
	
	
	// decodeMoveX stores input characters for the move with corresponding column in chessField array
	Map<Character, Number> decodeMoveX = new HashMap<Character, Number>();
	
	// decodeMoveY stores input numbers for the move with corresponding row in chessField array
	Map<Character, Number> decodeMoveY = new HashMap<Character, Number>();
	
	Map<Character, Figure> decodeFigure = new HashMap<Character, Figure>();
	
	private Figure figure;
	
	private BeatenFigures beatenFigures = new BeatenFigures();
	
	private MoveToCheck moveToCheck = new MoveToCheck();
	
	private char storeOriginalCharacter;
	
	private int undoIndexConsole;
	
	/**
	 * initializes maps for decoding Move and figures
	 */
	public Move() {
		decodeMoveX.put('a', 0);
		decodeMoveX.put('b', 1);
		decodeMoveX.put('c', 2);
		decodeMoveX.put('d', 3);
		decodeMoveX.put('e', 4);
		decodeMoveX.put('f', 5);
		decodeMoveX.put('g', 6);
		decodeMoveX.put('h', 7);
		
		decodeMoveY.put('8', 0);
		decodeMoveY.put('7', 1);
		decodeMoveY.put('6', 2);
		decodeMoveY.put('5', 3);
		decodeMoveY.put('4', 4);
		decodeMoveY.put('3', 5);
		decodeMoveY.put('2', 6);
		decodeMoveY.put('1', 7);
		
		decodeFigure.put('p', new Pawn());
		decodeFigure.put('P', new Pawn());
		decodeFigure.put('k', new King());
		decodeFigure.put('K', new King());
		decodeFigure.put('q', new Queen());
		decodeFigure.put('Q', new Queen());
		decodeFigure.put('b', new Bishop());
		decodeFigure.put('B', new Bishop());
		decodeFigure.put('n', new Knight());
		decodeFigure.put('N', new Knight());
		decodeFigure.put('r', new Rook());
		decodeFigure.put('R', new Rook());
		
		setUndoIndexConsole(-1);
	}
	
	/**
	 * Enables to move a figure by switching the char at the original position and the char at the 
	 * destination. The permissiveness of the move for the specific figure and the syntax of the
	 * input will be checked.
	 * 
	 * @param input User input, that contains information about origin and destination.
	 * @param v View, that contains the model, which in turn contains the information 
	 * about the chess field.
	 * @param i Number of already played moves.
	 * @param moves List that stores already played moves + move to apply
	 * @return True, if move is made.
	 */
	public String moveFigure(String input, ViewContainer v, int i, List<String> moves) {
		
		
		try {
			//decoding input (xOrigin, yOrigin, xDestination, yDestination)
			int[] decodedMove = {decodeMoveX.get(input.charAt(0)).intValue(), 
					decodeMoveY.get(input.charAt(1)).intValue(),
					decodeMoveX.get(input.charAt(3)).intValue(),
					decodeMoveY.get(input.charAt(4)).intValue()};
			
			// stores char of the figure
			storeOriginalCharacter = v.getModel().getChar(decodedMove[0], decodedMove[1]);
		
			char prospectivePawnConversion = setProspectivePawnConversion(input, storeOriginalCharacter, decodeFigure);
			
			setFigure(decodeFigure.get(storeOriginalCharacter));

			if(legalMove(new int[] {decodedMove[0], decodedMove[1], decodedMove[2], decodedMove[3], i}, new char[] {storeOriginalCharacter, prospectivePawnConversion}, v, moves)) {

				
				reactToMove(v, input, moves, decodedMove);

				// check for pawn conversion (new figure needs to be of right color)
				if(figure instanceof Pawn && ((Pawn) figure).convertPawn(prospectivePawnConversion, decodedMove)) {
					v.getModel().setChar(decodedMove[2], decodedMove[3], prospectivePawnConversion);
				}
				return null;
			}
			else {
				return "!Move not allowed";
			}
		}
		catch(Exception e) {
			//e.printStackTrace();
			return "!Invalid move";
		}
	}
	

	public Map<Character, Number> getDecodeMoveX() {
		return decodeMoveX;
	}


	public void setDecodeMoveX(Map<Character, Number> decodeMoveX) {
		this.decodeMoveX = decodeMoveX;
	}


	public Map<Character, Number> getDecodeMoveY() {
		return decodeMoveY;
	}


	public void setDecodeMoveY(Map<Character, Number> decodeMoveY) {
		this.decodeMoveY = decodeMoveY;
	}


	public char getStoreOriginalCharacter() {
		return storeOriginalCharacter;
	}


	/**
	 * Checks if the player, who has the turn, moves his own figure.
	 * 
	 * @param storeOriginalCharacter Figure at original position.
	 * @param i Number of already played moves.
	 * @return If figure is allowed to be moved, return true. Otherwise return false.
	 */
	
	public boolean correctFigure(char storeOriginalCharacter, int i) {
		// if white (uppercase) has the turn
		return i%2 == 0 && Character.isUpperCase(storeOriginalCharacter) || i%2 == 1 
				&& Character.isLowerCase(storeOriginalCharacter);
	}
	
	public Map<Character, Figure> getDecodeFigure(){
		return this.decodeFigure;
	}
	
	public Figure getFigure() {
		return this.figure;
	}
	
	public void setFigure(Figure figure) {
		this.figure = figure;
	}
	
	public BeatenFigures getBeatenFigures() {
		return this.beatenFigures;
	}
	
	public void setBeatenFigures(BeatenFigures beatenFigures) {
		this.beatenFigures = beatenFigures;
	}
	
	
	
	/**
	 * checks if move is legal made and for which figure
	 */
	public boolean legalMove(int[] decodedMoveAndI, char[] storeOriginalCharacterAndProspectivePawnConversion, ViewContainer v, List<String> moves) {
		return !(decodedMoveAndI[0]==decodedMoveAndI[2] && decodedMoveAndI[1]==decodedMoveAndI[3]) 
				&& correctFigure(storeOriginalCharacterAndProspectivePawnConversion[0], decodedMoveAndI[4]) 
				&& figure.checkMove(storeOriginalCharacterAndProspectivePawnConversion[0], decodedMoveAndI, v, moves) 
				&& Character.isUpperCase(storeOriginalCharacterAndProspectivePawnConversion[0]) 
				== Character.isUpperCase(storeOriginalCharacterAndProspectivePawnConversion[1]) 
				&& !moveToCheck.moveResultsInCheck(v, figure, decodedMoveAndI, new char[]{storeOriginalCharacterAndProspectivePawnConversion[0], storeOriginalCharacterAndProspectivePawnConversion[1]});
	}
	
	/**
	 * sets pawn conversion
	 */
	public char setProspectivePawnConversion(String input,char storeOriginalCharacter, Map<Character, Figure> decodeFigure) throws Exception {
		if(input.length() <= 5 && Character.isUpperCase(storeOriginalCharacter)) {
			return 'Q';
		}
		else if(input.length() <= 5 && !Character.isUpperCase(storeOriginalCharacter)) {
			return 'q';
		}
		else {
			
			 if(!decodeFigure.containsKey(input.charAt(5))) {
				 throw new Exception();
			 }
			 return input.charAt(5);
		}
	}
	
	
	/**
	 * execute different reactions to a move
	 */
	public void reactToMove(ViewContainer v, String input, List<String> moves, int[] decodedMove) {
		
		// if figure of opponent gets beaten get beaten figure and store it in class BeatenFigures
		if(figure.checkBeaten(decodedMove, v)) {
			
			char beaten = v.getModel().getChar(decodedMove[2], decodedMove[3]);
			// sets beaten for en passant white
			if (beaten == ' ' && v.getModel().getChar(decodedMove[0], decodedMove[1]) == 'P') {
				beaten = 'p';
				getBeatenFigures().getStoreBeatenFigures().add(beaten);
			}
			// sets beaten for en passant black
			else if (beaten == ' ' && v.getModel().getChar(decodedMove[0], decodedMove[1]) == 'p') {
				beaten = 'P';
				getBeatenFigures().getStoreBeatenFigures().add(beaten);
			}
			else {
				getBeatenFigures().getStoreBeatenFigures().add(beaten);
			}
			// delete beaten figure
			v.getModel().setChar(decodedMove[2], decodedMove[3], ' ');
		}
		
		//setting char from destination to origin
		v.getModel().setChar(decodedMove[0], decodedMove[1], v.getModel().getChar(decodedMove[2], decodedMove[3]));
		
		//setting char from origin to destination
		v.getModel().setChar(decodedMove[2], decodedMove[3], storeOriginalCharacter);
		
	}
	
	/**
	 * copies the given List
	 * 
	 * @param List<String> of moves
	 * @return copied List<String> of moves
	 */
	public List<String> copyList(List<String> moves){
		return new ArrayList<String>(moves);
	}

	public int getUndoIndexConsole() {
		return undoIndexConsole;
	}

	public void setUndoIndexConsole(int undoIndexConsole) {
		this.undoIndexConsole = undoIndexConsole;
	}
}
