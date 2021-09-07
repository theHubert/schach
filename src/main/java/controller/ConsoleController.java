package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import model.BeatenFigures;
import model.Model;
import schach.FileHandler;
import view.ConsoleView;

/**
 * 
 * The class Controller updates the chess-field depending on the user input.
 *
 */

public class ConsoleController {
	
	private static final String PLAYER = "player";
	private Move move;
	private Check check = new Check();
	private AI ai = new AI();
	int i = 0;
	int ai_depth = 1;
	FileHandler fileHandler = new FileHandler();
	/*
	 * already played moves
	 */
	public List<String> moves = new ArrayList<String>();
	
	/**
	 * points at the index at which the next move should be added
	 */
	public int top = 0;
	
	public void setMove(Move move) {
		this.move = move;
	}
	
	/**
	 * 
	 * Sets the view by reading out the user input and reacts to it.
	 * 
	 * @param v The View object that displays the updated chess-field.
	 */
	
	public void setView(ConsoleView v) {
		
		// String returnMoveFigure;
		String opponent_player;
		Scanner scanner = new Scanner(System.in);
		
		
		
		while (true) {
			v.printOutWhichOpponent();
			opponent_player = scanner.nextLine();
			
			if (opponent_player.equals(v.getModel().getCurrent().getString("player"))) {
				
				loadGame(v, scanner, PLAYER);
				
			}
			
			if(opponent_player.equals("computer")) {
				
				loadGame(v, scanner, "ai");
			}
			
			if (opponent_player != v.getModel().getCurrent().getString(PLAYER) && opponent_player != "computer" && !checkDifferentInput(opponent_player, v/*, moves*/, PLAYER)) {
		
				System.out.println(v.getModel().getCurrent().getString("invalidOpponent"));
				
			}
			
		}
		
	}
	
	
	/**
	 * Checks if a game should be load or a new game should be started and calls corresponding methods for starting a game.
	 * @param v Currently used View, printing out chess board. 
	 * @param moves List that stores already played moves.
	 * @param originalScanner Opened scanner for selecting opponent.
	 * @param opponent Selected opponent.
	 */
	public void loadGame(ConsoleView v, Scanner originalScanner, String opponent) {
		
		String loadGame;
		Scanner scanner = new Scanner(System.in);
				
		while (true) {
			System.out.println(v.getModel().getCurrent().getString("load"));
			loadGame = scanner.nextLine();
					
			if (loadGame.equals(v.getModel().getCurrent().getString("yes"))) {
				
				selectLoad(v, opponent);
				
				originalScanner.close();
				
			}
			if (loadGame.equals("N") && opponent == PLAYER) {
				this.i = 0;
				setViewPlayer(v, scanner);
				originalScanner.close();
			}
			else if (loadGame.equals("N")) {
				this.i = 0; 
				setViewComputer(v);
				originalScanner.close();
			}
			if(!checkDifferentInput(loadGame, v, PLAYER)) {
				System.out.println(v.getModel().getCurrent().getString("invalidInput"));
			}
		}
		
	}
	
	/**
	 * Checks what game (index) the user wants to load and calls methods to load the corresponding game.
	 * Additionally, the user can select showStored, which shows all stored games with corresponding indices.
	 * @param v currently used View
	 * @param opponent of game that should be load
	 */
	private void selectLoad(ConsoleView v, String opponent) {
		
		String selectedGame;
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			System.out.println(v.getModel().getCurrent().getString("select"));
			System.out.println(v.getModel().getCurrent().getString("show"));

			selectedGame = scanner.nextLine();
			
			if(selectedGame.equals(v.getModel().getCurrent().getString("showStored"))) {
				System.out.println(fileHandler.showStoredGames(v));
			}
			
			else {
				try {
					setNewModel(v);
					
					// simulate stored moves on new model 
					int index = Integer.parseInt(selectedGame);
					simulateMoves(v, index);
					
					if(opponent==PLAYER) {
						setViewPlayer(v, scanner);
					}
					else {
						setViewComputer(v);
					}
				}
				catch(Exception e) {
					System.out.println("invalidInput");
				}
			}
			
			
		}
	}
	
	/**
	 * Simulates moves from file on model.
	 * @param v currently used view
	 * @param index of game that should be loaded
	 */
	private void simulateMoves(ConsoleView v, int index) {
		
		List<String> loadedMoves = fileHandler.getMovementsFromFile(v, index);
		
		if(loadedMoves!=null && loadedMoves.size()>0) {
			
			for(int i=0; i<loadedMoves.size(); i++) {
				move.moveFigure(loadedMoves.get(i), v, i, moves);
				this.i++;
				moves.add(top, loadedMoves.get(i));
		    	top++;
		    	
			}
			
		}
	}
	
	/**
	 * sets a new model with the previously used language
	 * @param v
	 */
	private void setNewModel(ConsoleView v) {
		Model updatedModel = new Model();
		updatedModel.setCurrent(v.getModel().getCurrent());
		v.setModel(updatedModel);
		moves = new ArrayList<String>();
		this.i = 0;
		move.setBeatenFigures(new BeatenFigures());
	}
	
	/**
	 * checks if game vs player is selected
	 * @param opponent_player user input after request for opponent player
	 * @param v The View object that displays the updated chess-field
	 * @return true, if vs player is selected in corresponding language, otherwise false
	 */
	/*public boolean playerSelected(String opponent_player, View v) {
		return opponent_player.equals(PLAYER) && v.getModel().getCurrent().getLocale().toString().equals("en_US") ||
				opponent_player.equals("mensch") && v.getModel().getCurrent().getLocale().toString().equals("ge_GE");
	}*/
	
	/**
	 * SetViewPlayer updates the chessboard for a game against a player.
	 * 
	 * @param v View that contains model with chess field.
	 * @param moves List that stores already played moves.
	 */
	
	public void setViewPlayer(ConsoleView v, /*List<String> moves,*/ Scanner original) {
		String returnMoveFigure;
		Scanner scanner = new Scanner(System.in);
		String input;
		
		while(i<Integer.MAX_VALUE){
			v.printOutChessField();
			v.printOutEnterMove();
			
			input = scanner.nextLine();
			
			if(!checkDifferentInput(input, v/*, moves*/, PLAYER)) {
			
				// Update chess-field (swap characters at read positions)
				returnMoveFigure = move.moveFigure(input, v, i, moves);
			    if(returnMoveFigure==null) {
			    	System.out.println("!" + input);
			    	
			    	i++;
			    	moves.add(top, input);
			    	top++;
			    	moves.subList(top, moves.size()).clear(); // user input ==> delete all previous upcoming moves
			    	
			    	checkFinalSituations(v, i, moves);    	
			    }
			    else if(returnMoveFigure=="!Move not allowed") {
			    	System.out.println(v.getModel().getCurrent().getString("moveNotAllowed"));
			    }
			    else {
			    	System.out.println(v.getModel().getCurrent().getString("invalidMove"));
			    }
			}
		}
		scanner.close();
		original.close();
	}
	
	/**
	 * Toggles an input char.
	 * @param input Char, that should be toggled.
	 * @return Toggled char.
	 */
	public char toggleChar(char input) {
		char output;
		if(Character.isUpperCase(input)) {
			output = Character.toLowerCase(input);
		}
		else {
			output = Character.toUpperCase(input);
		}
		return output;
	}
	
	/**
	 * checkDifferentInput looks for different inputs other than the move syntax
	 * e.g. language and beaten figures
	 * 
	 * @param input String of the input
	 * @param v View that contains model with the chessfield
	 * @return true, if user input is 'german', 'english' or 'beaten', otherwise false
	 */
	public boolean checkDifferentInput(String input, ConsoleView v, /*List<String> moves,*/ String opponentPlayer) {
		
		// switch language
		if(input.equals("german")) {
			v.getModel().setCurrent(ResourceBundle.getBundle("schach.Bundle", new Locale("ge", "GE")));
			return true;
		}
		
		else if(input.equals("english")) {
			v.getModel().setCurrent(ResourceBundle.getBundle("schach.Bundle", new Locale("en", "US")));
			return true;
		}
		// show beaten figures
		else if(input.equals(v.getModel().getCurrent().getString("beaten"))) {
			move.getBeatenFigures().printOutBeaten();
			return true;
		}
		// undo already played moves
		else if(input.equals(v.getModel().getCurrent().getString("undoButton"))) {
			return setUndoConsole(v, opponentPlayer);
		}
		// redo 
		else if(input.equals(v.getModel().getCurrent().getString("redo"))) {	
			return setRedoConsole(v, opponentPlayer);	
		}
		
		// close game and store current state
		else if(input.equals(v.getModel().getCurrent().getString("store"))) {
			fileHandler.writeMovementsToFile(v, moves, opponentPlayer);
			return true;
		}
		
		return false;
	}
	
	/**
	 * calls method for undoing (and redoing) moves with corresponding index for selected opponent
	 * @param v currently used View
	 * @param opponentPlayer 
	 * @return
	 */
	private boolean setUndoConsole(ConsoleView v, String opponentPlayer) {
		if(opponentPlayer.equals(PLAYER)) {
			return undoMovesConsole(top-1, v);
		}
		else {
			return undoMovesConsole(top-2, v);
		}
		
	}
	
	/**
	 * calls method for redoing (and undoing) moves with corresponding index for selected opponent
	 * @param v currently used View
	 * @param opponentPlayer
	 * @return
	 */
	private boolean setRedoConsole(ConsoleView v, String opponentPlayer) {
		if(opponentPlayer.equals(v.getModel().getCurrent().getString(PLAYER)) && top < moves.size()) {
			undoMovesConsole(top+1, v);
			return true;
		}
		else if(top < moves.size()) {
			undoMovesConsole(top+2, v);
			return true;
		}
		return false;
	}
	
	/**
	 * resets model up to an index in movement history => used for undo and redo
	 * @param index List of moves to apply up to, but not including, this position
	 * @param v
	 * @param moves
	 */
	private boolean undoMovesConsole(int index, ConsoleView v) {
		top = index;
		
		Model updatedModel = new Model();
		updatedModel.setCurrent(v.getModel().getCurrent());
		v.setModel(updatedModel);
		this.i = 0;
		
		move.setBeatenFigures(new BeatenFigures());
		
		ArrayList<String> movesDone = new ArrayList<String>();
		
		// further moves should be undone
		for(int i=0; i<top; i++) {
			move.moveFigure(moves.get(i), v, i, movesDone);
			this.i++;
			movesDone.add(moves.get(i));
		}
		if(top>=0) {
			return true;
		}
		else {
			top = 0;
			return false;
		}
		
	}
	
	/**
	 * checkFinalSituations looks for ending situations during the game (draw,
	 * check and checkmate)
	 * 
	 * @param v View that contains model with chess field.
	 * @param i number of the current move in the game
	 * @param moves List that stores already played moves.
	 */
	private void checkFinalSituations(ConsoleView v, int i, List<String> moves) {
		
		// check for tie
		if(Draw.checkDraw(v, move)) {
	    	System.out.println(v.getModel().getCurrent().getString("draw"));
	    	System.exit(0);
	    }
		
    	// check for check
    	else if(check.checkCheck(v, move.getStoreOriginalCharacter())) {
    		
    		// get opponent char 
    		char opponent = toggleChar(move.getStoreOriginalCharacter());
    		
    		// check for mate
    		 if(new MoveGenerator().generateMoves(v, i, moves, opponent).size()==0) {
    		 	System.out.println(v.getModel().getCurrent().getString("mate"));
	    	 	System.exit(0);

    		}
	    	System.out.println(v.getModel().getCurrent().getString("schach"));
	    }
	}
	
	/**
	 * SetViewComputer updates the chessboard for a game against the computer 
	 * 
	 * @param v View that contains model with chess field.
	 * @param moves List that stores already played moves.
	 */
	
	public void setViewComputer(ConsoleView v/*, List<String> moves*/) {
		
		String returnMoveFigure;
		Scanner scanner = new Scanner(System.in);
		String input;
		
		
		if( moves.size()%2 != 0) {    // if ai has the turn after loading the previous game
			updateAiMove(v);
			
		}
		
		while(i<Integer.MAX_VALUE){
			v.printOutChessField();
			
				if(i%2 == 1) {
					
					// ai move
					updateAiMove(v);
					
					
				}else if (i%2 == 0){
					
					v.printOutEnterMove();
					input = scanner.nextLine();
					

					if(input.equals("ai")) {
						setDepth(v, scanner);
					}
					else if(!checkDifferentInput(input, v, /*moves,*/ "computer")) {
					
						// Update chess-field (swap characters at read positions)
						
						returnMoveFigure = move.moveFigure(input, v, i, moves);
						if(returnMoveFigure==null) {
					    	System.out.println("!" + input);
					    	i++;
					    	moves.add(top, input);
					    	top++;
					    	moves.subList(top, moves.size()).clear(); // user input ==> delete all previous upcoming moves
					    }
						else if(returnMoveFigure=="!Move not allowed") {
					    	System.out.println(v.getModel().getCurrent().getString("moveNotAllowed"));
					    	i=0;
					    }
					    else {
					    	System.out.println(v.getModel().getCurrent().getString("invalidMove"));
					    	i=0;
					    }
					}
				}
				
					//i++;
				checkFinalSituations(v, i, moves);
			    	    	
				}scanner.close();
		
	}
	
	/**
	 * executes ai move 
	 * @param v currently used view
	 */
	private void updateAiMove(ConsoleView v) {
		String ai_move = ai.calcBestMove(v, i, moves, 'k', ai_depth);
		move.moveFigure(ai_move, v, i, moves);
		i++;
		System.out.println(ai_move);
		moves.add(top, ai_move);
    	top++;
    	moves.subList(top, moves.size()).clear(); // user input ==> delete all previous upcoming moves
	}
	
	/**
	 * SetDepth asks the player for the desired ai depth and sets the local
	 * variable ai_depth to the input value
	 * 
	 * @param v View that contains model with chess field.
	 * @param scanner Scanner for the input stream.
	 */
	public void setDepth(ConsoleView v, Scanner scanner) {
		
		System.out.println(v.getModel().getCurrent().getString("aiDepth"));
		
		try {
			int depth = Integer.parseInt(scanner.nextLine());
			
			if(depth < 5 && depth > 0) {
				this.ai_depth = depth;
			}
			else {
				System.out.println(v.getModel().getCurrent().getString("aiException"));
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(v.getModel().getCurrent().getString("aiException"));
			}
		
	}
}
