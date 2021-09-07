package controller;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import view.ViewContainer;

@SuppressWarnings("PMD.ExcessiveParameterList")
/*the methods for the minimax algorithm have excessive parameter lists because
 *of the additional variables for depth, alpha and beta, which cannot be shortened*/

/**
 * The class AI contains methods for the AI, which is used for a chessgame against the computer
 *
 */

public class AI {
	
	MoveGenerator moveGenerator = new MoveGenerator();
	Move move = new Move();
	Scanner scanner = new Scanner(System.in);
	int first_depth = 0;
	String savedMove = "";
	char originalFigure = ' ';
	
	/**
	 * calcBestMove initiates the calculation of the next ai move
	 * 
	 * @param v View that contains model with chess board.
	 * @param i Number of the current move in the game (starting with 0 => getting updated after movement when updating GuiViewBoard).
	 * @param moves List that stores already played moves.
	 * @param char of the color the ai plays.
	 * @param depth of the minimax algorithm.
	 * @return savedMove The currently best move regarding position evaluation.
	 */
	
	public String calcBestMove(ViewContainer v, int i, List<String> moves, char ownfigure, int depth){
		
		this.originalFigure = ownfigure;
		this.first_depth = depth;
		
		Max(v, i, moves, ownfigure, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		if(savedMove == "") {
			savedMove = "a1-a1";
		}
		
		return savedMove;
	}
	
	/**
	 * Max represents the calculation for the maximizing player in the minimax
	 * algorithm
	 * 
	 * @param v View that contains model with chess board.
	 * @param i Number of the current move in the game.
	 * @param moves List that stores already played moves.
	 * @param char of the color the ai plays.
	 * @param depth of the minimax algorithm.
	 * @param alpha value for alpha beta pruning.
	 * @param beta value for alpha beta pruning.
	 * @return maxValue the value of the best possible move for the maximizing
	 * player.
	 */
	public int Max(ViewContainer v, int i, List<String> moves, char ownFigure, int depth, int alpha, int beta) {
		
		int temp_value = Integer.MIN_VALUE;
		char[][] originalBoard = v.getModel().getChessField();
		List<String> possibleMoves = new ArrayList<String>();
		possibleMoves.addAll(moveGenerator.generateMoves(v, i, moves, ownFigure));
		Collections.shuffle(possibleMoves);
		
		if(depth == 0 || possibleMoves.isEmpty()) {
			int finalValue = evaluateBoard(v, originalFigure);
			return finalValue;
		}
		
		int maxValue = alpha;
		
		for(String s : possibleMoves) {
			
			move.moveFigure(s, v, i, moves);
			
			temp_value = Min(v, i+1, moves, changePlayer(ownFigure), depth-1, maxValue, beta);
		
			v.getModel().setChessField(originalBoard);
			
			if(temp_value > maxValue) {
				maxValue = temp_value;
				
				if(depth == this.first_depth) {
					this.savedMove = s;
				}
				if(maxValue >= beta) {
					break;
				}
			}
		}
		
		return maxValue;
	}
	
	/**
	 * Min represents the calculation for the minimizing player in the minimax
	 * algorithm
	 * 
	 * @param v View that contains model with chess board.
	 * @param i Number of the current move in the game.
	 * @param moves List that stores already played moves.
	 * @param char of the color the ai plays.
	 * @param depth of the minimax algorithm.
	 * @param alpha value for alpha beta pruning.
	 * @param beta value for alpha beta pruning.
	 * @return minValue the value of the best possible move for the minimizing
	 * player.
	 */
	public int Min(ViewContainer v, int i, List<String> moves, char ownFigure, int depth, int alpha, int beta) {
		
		int temp_value = Integer.MAX_VALUE;
		char[][] originalBoard = v.getModel().getChessField();
		List<String> possibleMoves = new ArrayList<String>();
		possibleMoves.addAll(moveGenerator.generateMoves(v, i, moves, ownFigure));
		Collections.shuffle(possibleMoves);
		
		if(depth == 0 || possibleMoves.isEmpty()) {
			int finalValue = evaluateBoard(v, originalFigure);
			return finalValue;
		}
		
		int minValue = beta;
		
		for(String s : possibleMoves) {
			
			move.moveFigure(s, v, i, moves);
			
			
			temp_value = Max(v, i+1, moves, changePlayer(ownFigure), depth-1, alpha, minValue);
			
			v.getModel().setChessField(originalBoard);
			
			if(temp_value < minValue) {
				minValue = temp_value;
				
				if(minValue <= alpha) {
					break;
				}
			}
		}
			
		return minValue;
	}
	
	/**
	 * evaluateBoard evaluates the position of the current chess board by
	 * calculating the figures with specific values
	 * 
	 * @param v View that contains model with chess board.
	 * @return boardValue calculated value of the current chess board.
	 */
	
	public int evaluateBoard(ViewContainer v, char ownfigure) {
		
		//creates values for each piece
		
		Map<Character, Integer> pieceValues = new HashMap<Character, Integer>();
		int boardValue = 0;
		
		pieceValues.put('p', 10);
		pieceValues.put('n', 30);
		pieceValues.put('b', 30);
		pieceValues.put('r', 50);
		pieceValues.put('q', 90);
		pieceValues.put('k', 900);
		
		//iterate over chessboard
			
			for(int originY=0; originY<v.getModel().getChessField().length; originY++) {
				for(int originX=0; originX<v.getModel().getChessField()[0].length; originX++) {
					
					if(Character.isLowerCase(v.getModel().getChessField()[originY][originX]) == Character.isLowerCase(ownfigure) && v.getModel().getChessField()[originY][originX] != ' ') {
						boardValue += pieceValues.get(Character.toLowerCase(v.getModel().getChar(originX, originY)));
					}
					else if(Character.isUpperCase(v.getModel().getChessField()[originY][originX]) == Character.isLowerCase(ownfigure) && v.getModel().getChessField()[originY][originX] != ' ') {
						
						boardValue -= pieceValues.get(Character.toLowerCase(v.getModel().getChar(originX, originY)));
					}
				}
			}
		
		return boardValue;
	}
	
	/**
	 * change Player changes the character of the current player for the
	 * minimax algorithm
	 * 
	 * @param ownFigure, character of the own figure
	 * @return character of the opponent figure
	 */
	public char changePlayer(char ownFigure) {
		char opponentFigure;
		if(Character.isUpperCase(ownFigure)) {
			opponentFigure = Character.toLowerCase(ownFigure);
		}
		else {
			opponentFigure = Character.toUpperCase(ownFigure);
		}
		return opponentFigure;
	}
	
}
