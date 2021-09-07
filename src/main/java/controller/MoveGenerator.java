package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import view.ViewContainer;

/**
 * Class MoveGenerator is used to get all possible moves on chess field by one players figures.
 * These moves are used to check for checkmate (if no more moves are possible, it is checkmate).
 */

public class MoveGenerator {
	
	public Map<Number, Character> encodeMoveX = new HashMap<>();
	public Map<Number, Character> encodeMoveY = new HashMap<>();
	
	Move move = new Move();
	
	/**
	 * MoveGenerator sets the values for the encodeMoveX and encodeMoveY hashmaps
	 */
	public MoveGenerator() {
		encodeMoveX.put(0, 'a');
		encodeMoveX.put(1, 'b');
		encodeMoveX.put(2, 'c');
		encodeMoveX.put(3, 'd');
		encodeMoveX.put(4, 'e');
		encodeMoveX.put(5, 'f');
		encodeMoveX.put(6, 'g');
		encodeMoveX.put(7, 'h');

		encodeMoveY.put(0, '8');
		encodeMoveY.put(1, '7');
		encodeMoveY.put(2, '6');
		encodeMoveY.put(3, '5');
		encodeMoveY.put(4, '4');
		encodeMoveY.put(5, '3');
		encodeMoveY.put(6, '2');
		encodeMoveY.put(7, '1');
	}
	
	/**
	 * Generates all possible moves of one's players figures.
	 * @param v View that contains model with chess field.
	 * @param i Number of already played moves.
	 * @param moves List that stores already played moves.
	 * @param ownFigure Char of one's players figures, equivalent to color of figures.
	 * @return List of moves as String type (equivalent to user input).
	 */
	public List<String> generateMoves(ViewContainer v, int i, List<String> moves, char ownFigure) {
		
		List<String> possibleMoves = new ArrayList<String>();
		
		// iterate over copied field
		for(int originY=0; originY<v.getModel().getChessField().length; originY++) {
			for(int originX=0; originX<v.getModel().getChessField()[originY].length; originX++) {
				
				// überprüfe jede mögliche Move Kombi 
				// regeneriere hierfür input string
				if(Character.isUpperCase(v.getModel().getChessField()[originY][originX])==Character.isUpperCase(ownFigure)) {
					for(int destinationY=0; destinationY<v.getModel().getChessField().length; destinationY++) {
						for(int destinationX=0; destinationX<v.getModel().getChessField()[destinationY].length; destinationX++) {
							String generatedInput = "";
							generatedInput += encodeMoveX.get((Number) originX);
							generatedInput += encodeMoveY.get((Number) originY);
							generatedInput += '-';
							generatedInput += encodeMoveX.get((Number) destinationX);
							generatedInput += encodeMoveY.get((Number) destinationY);
							
							// überprüfe checkMove für input und nehme in possible Moves auf, wenn true
							Move m = new Move();
							String isAllowed = m.moveFigure(generatedInput, v.getCopyOfView(), i, moves);
							
							if(isAllowed == null) {
								possibleMoves.add(generatedInput);
							}
						}
					}
				}
			}
		}
		return possibleMoves;
	}
	
	/**
	 * Generates a list of clickable squares for a figure.
	 * @param v View that contains model with chess field.
	 * @param posXYandI horizontal, vertical coordinates and counter for moves.
	 * @param moves List that stores already played moves.
	 * @param ownFigure Char of one's players figures, equivalent to color of figures.
	 * @return List of clickable squares as String array list.
	 */
	public List<Number> possibleDestinationsForClickedFigure(ViewContainer v, int[] posXYandI, List<String> moves, char ownFigure){
		 
		List<Number> possibleDestinations = new ArrayList<Number>();
		
		// get first two letters of input
		String inputFirstPart = "";
		inputFirstPart += encodeMoveX.get((Number) posXYandI[0]);
		inputFirstPart += encodeMoveY.get((Number) posXYandI[1]);
		
		// filter list of all possible moves of a player (generateMoves) for input position
		List<String> allPossibleMoves = generateMoves(v, posXYandI[2], moves, ownFigure);
		for(String move : allPossibleMoves) {
			if(move.substring(0, 2).equals(inputFirstPart)) {
				// decode destination to integer values 
				possibleDestinations.add((Number) this.move.getDecodeMoveX().get(move.charAt(3)));
				possibleDestinations.add((Number) this.move.getDecodeMoveY().get(move.charAt(4)));
			}
		}
		return possibleDestinations;		
	}
}
