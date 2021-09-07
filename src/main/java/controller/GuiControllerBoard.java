package controller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.BeatenFigures;
import model.Model;
import schach.FileHandler;
import view.FigureSelector;
import view.GuiAlerts;
import view.GuiViewBoard;


/**
 * 
 * The class GuiControllerChessField contains EventHandlers for the squares 
 * and the logic for moving a figure.
 *
 */

public class GuiControllerBoard {
	
	private Move move;
	// stores position (column and row) of clicked square/figure and counter of already played moves of the previous click
	int[] storeLastClickCRI;
	private StackPane storeLastClickSquare;
	boolean figureFirstClick = false;
	private AiThread t;
	int counter;
	public static List<String> movement = new ArrayList<>();
	
	List<Number> possibleDestinations;
	
	// undoIndex stores the index of the last move, that should not be undone 
	// in the case that nothing should be undone undoIndex is -1
	public int undoIndex;
	
	/**
	 * Getter for previous moves
	 */
	public static List<String> getMoves()
	{
	    return movement;
	}
	
	public void setCounter(int i) {
		counter = i;
	}
	
	public int getCounter() {
		return counter+1;
	}
	
	/**
	 * Constructor initializes variables for storing the previous click
	 */
	public GuiControllerBoard() {
		this.storeLastClickCRI = new int[] {-1, -1, -1};
		this.storeLastClickSquare = null;
		figureFirstClick = false;
		move = new Move();
		possibleDestinations = new ArrayList<>();
		undoIndex = -1;

	}
	
	/**
	 * creates a copy of movement (list storing executed moves)
	 * @return copy of movement
	 */
	public List<String> copyMoves(){
		return new ArrayList<String>(movement);
	}
	
	public StackPane getStoreLastClickSquare() {
		return storeLastClickSquare;
	}

	public void setStoreLastClickSquare(StackPane storeLastClickSquare) {
		this.storeLastClickSquare = storeLastClickSquare;
	}

	public int[] getStoreLastClickCRI() {
		return Arrays.copyOf(storeLastClickCRI, 3);
	}

	public void setStoreLastClickCRI(int[] storeLastClickCRI) {
		this.storeLastClickCRI = Arrays.copyOf(storeLastClickCRI, 3);
	}
	
	/**
	 * Adds EventHandlers for each square and image of the board (stored as StackPane)
	 * @param square StackPane representing clicked square
	 * @param iv ImageView that contains image
	 * @param colAndRowAndI stores position and counter (for executed moves) of destination
	 * @param guiViewBoard used for 1. storing model, that is getting updated and for 2. updating the view
	 */
	public void setAction(StackPane square, ImageView iv, int[] colAndRowAndI, GuiViewBoard guiViewBoard) {

		if (guiViewBoard.getselectedMode().get(1) == getBlack() && colAndRowAndI[2] == 0) {
			move.moveFigure("d2-d4", guiViewBoard, colAndRowAndI[2], new ArrayList<>());
			if(movement.isEmpty()) {movement.add("d2-d4");}
			colAndRowAndI[2] = 1;
		}
		
		square.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

            	handleClick(square, colAndRowAndI, guiViewBoard);
            }
        });
		
		iv.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

            	handleClick(square, colAndRowAndI, guiViewBoard);
            }
        });
		
	}
	
	private void handleClick(StackPane square, int[] colAndRowAndI, GuiViewBoard guiViewBoard) {
		
		// in the case of a pawn conversion open a new window, where figure can be chosen
		if(checkPawnPromotion(guiViewBoard, colAndRowAndI)) {

			GuiViewBoard copyOfGuiViewBoard = (GuiViewBoard) guiViewBoard.getCopyOfView();
			
			int sizeBeatenFigures = move.getBeatenFigures().getStoreBeatenFigures().size();
			if(move.moveFigure(new GuiInputGenerator().generateInput(colAndRowAndI, getStoreLastClickCRI()), copyOfGuiViewBoard, colAndRowAndI[2], new ArrayList<>())==null) {
								
				// if a figure got beaten, remove doubled entry
				if(move.getBeatenFigures().getStoreBeatenFigures().size()!=sizeBeatenFigures) {
					move.getBeatenFigures().getStoreBeatenFigures().remove( move.getBeatenFigures().getStoreBeatenFigures().size() - 1 );
				}
				new FigureSelector(square, colAndRowAndI, guiViewBoard, guiViewBoard.getselectedMode());
			}
			else {
				squareClicked(square, colAndRowAndI, guiViewBoard, ' ');
			}
			
		}
		else {
			squareClicked(square, colAndRowAndI, guiViewBoard, ' ');
		}
	}
	
	/**
	 * Checks move and executes move, if it is allowed
	 * @param square StackPane representing clicked square
	 * @param colAndRowAndI stores position and counter (for executed moves) of destination
	 * @param guiViewBoard used for 1. storing model, that is getting updated and for 2. updating the view
	 */
	public void squareClicked(StackPane square, int[] colAndRowAndI, GuiViewBoard guiViewBoard, char pawnPromotion){
		// remove borders and highlights of all squares
		//removeBordersAndHighlight(guiViewBoard);
		
		// new move started, origin field clicked 
		if((getStoreLastClickCRI()[0]==-1 && getStoreLastClickCRI()[1]==-1 ||  ownFigure(getStoreLastClickCRI(), colAndRowAndI, guiViewBoard)) && !figureFirstClick) {
			
			// remove borders and highlights of all squares
			removeBordersAndHighlight(guiViewBoard);

			squareClickedReaction(square, colAndRowAndI, guiViewBoard, pawnPromotion);

		}
		// destination field clicked
		else {
   
			// remove border of squares
			getStoreLastClickSquare().setBorder(null);
			
			// decolor possible destination squares
			for(int x=0; x<possibleDestinations.size()-1; x=x+2) {
				guiViewBoard.storeSquares[possibleDestinations.get(x+1).intValue()][possibleDestinations.get(x).intValue()].getStyleClass().clear();
			}
			moveGuiFigures(square, pawnPromotion, colAndRowAndI, guiViewBoard);
		}
	}
	
	/**
	 * squareClickedReaction contains the Reaction of the first Click on the
	 * origin field
	 * 
	 * @param square StackPane representing clicked square
	 * @param colAndRowAndI stores position and counter
	 * @param guiViewBoard used for 1. storing model, that is getting updated 
	 * and for 2. updating the view
	 * @param pawnPromotion char for the pawn conversion
	 */
	public void squareClickedReaction(StackPane square, int[] colAndRowAndI, GuiViewBoard guiViewBoard, char pawnPromotion) {
		
		// remove border of all squares
		removeBordersAndHighlight(guiViewBoard);
		
		possibleDestinations = new MoveGenerator().possibleDestinationsForClickedFigure(guiViewBoard, colAndRowAndI, 
				new ArrayList<>(), guiViewBoard.getModel().getChar(colAndRowAndI[0], colAndRowAndI[1]));
		
		// if highlight button is activated highlight possible destinations
		if(guiViewBoard.getButtonsActivated().isHighlightActivated()) {
			
			// color possible destination squares
			for(int x=0; x<possibleDestinations.size()-1; x=x+2) {
				guiViewBoard.storeSquares[possibleDestinations.get(x+1).intValue()][possibleDestinations.get(x).intValue()].setStyle(
						"-fx-background-color: #F2EDAD;" + "-fx-border-color: black;");
			}
		}

		// set border for clicked square
		square.setBorder(new Border(new BorderStroke(Color.BLUE, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

		// set position and square as previously clicked
		setStoreLastClickCRI(colAndRowAndI);
		setCounter(colAndRowAndI[2]);
		setStoreLastClickSquare(square);
		
		
		// if no more possible move options for clicked figure, allow reselection of figure 
		if(possibleDestinations.size()==0) {
			setStoreLastClickSquare(square);
			setStoreLastClickCRI(new int[] {-1,-1, colAndRowAndI[2]});
		}
				
		// if reselect button is not activated and clicked square is not empty and not opponent figure, store click
		if(!guiViewBoard.getButtonsActivated().isReselectFigure() && possibleDestinations.size()!=0 &&
			guiViewBoard.getModel().getChar(colAndRowAndI[0], colAndRowAndI[1])!=' ' && 
			move.correctFigure(guiViewBoard.getModel().getChar(colAndRowAndI[0], colAndRowAndI[1]), colAndRowAndI[2])) {
			figureFirstClick = true;
		}

		possibleDestinations.clear();
	}
	
	/**
	 * moveGuiFigures moves the Figures for the GUI and updates the whole 
	 * BorderPane
	 * 
	 * @param square StackPane representing clicked square
	 * @param pawnPromotion char for the pawn conversion
	 * @param colAndRowAndI stores position and counter
	 * @param guiViewBoard used for 1. storing model, that is getting updated and for 2. updating the view
	 */
	public void moveGuiFigures(StackPane square, char pawnPromotion, int[] colAndRowAndI, GuiViewBoard guiViewBoard) {

		boolean endOfGame = false;
		char local_pawnPromotion = pawnPromotion;
		
		
		String input = new GuiInputGenerator().generateInput(colAndRowAndI, getStoreLastClickCRI());
		
		if(local_pawnPromotion != ' ') {
			if(Character.isLowerCase(guiViewBoard.getModel().getChar(getStoreLastClickCRI()[0], getStoreLastClickCRI()[1]))) {
				local_pawnPromotion = Character.toLowerCase(local_pawnPromotion);
			}
			input = input.substring(0,5) + local_pawnPromotion;
		}
		
		if(move.moveFigure(input, guiViewBoard, colAndRowAndI[2], new ArrayList<>())==null) {
			
			guiViewBoard.updateBeaten(move.getBeatenFigures().getStoreBeatenFigures());
			finalSituation(guiViewBoard, colAndRowAndI[2]);
			colAndRowAndI[2] = colAndRowAndI[2] + 1;
		
			// set position and square as previously clicked
			setStoreLastClickSquare(square);
			setStoreLastClickCRI(new int[] {-1,-1, colAndRowAndI[2]});
			
			// reset variable that stored the first selection of a figure
			figureFirstClick = false;
			
			// update movement list
			if(this.undoIndex!=-1) {
				List<String> copyOfMovement = new ArrayList<>();
				copyOfMovement = copyMoves();
				movement.clear();
				for(int i=0; i<undoIndex; i++) {
					movement.add(copyOfMovement.get(i));
				}
			}
			movement.add(input);

			
			// update history
			guiViewBoard.updateHistory(movement);
			this.undoIndex = -1;
		}
		else {
			guiViewBoard.putFiguresOnBoard(colAndRowAndI[2], endOfGame, guiViewBoard.getselectedMode());
		}
		
		if (guiViewBoard.getselectedMode().get(0) == "computer" ) {
			
			// calculate and execute ai move
			aiMove(guiViewBoard, colAndRowAndI[2]);
			
		}
	}
	
	/**
	 * Opens a new thread for calculating a new ai move.
	 * @param guiViewBoard Currently used GuiViewBoard.
	 * @param i Count of already done moves (starts with 0, gets updated after done movement when GuiViewBoard is getting updated).
	 */
	private void aiMove(GuiViewBoard guiViewBoard, int i) {
		
		char ai_figure = ' ';
		int ai_depth = guiViewBoard.getSpinnerValue();
		
		if(guiViewBoard.getselectedMode().get(1) == getBlack()) {
			ai_figure = 'K';
		}
		else if(guiViewBoard.getselectedMode().get(1) == "white") {
			ai_figure = 'k';
		}

		this.t = new AiThread(guiViewBoard, i,  ai_figure, ai_depth);
		this.t.start();
		
	}
	
	/**
	 * Executes calculated ai move.
	 * @param ai_input String form of move, that can be passed to class Move (where move gets executed).
	 * @param guiViewBoard Currently used GuiViewBoard. 
	 * @param i Count of already done moves (starts with 0, gets updated after done movement when GuiViewBoard is getting updated).
	 */
	public void onCalculatedMove(String ai_input, GuiViewBoard guiViewBoard, int i) {
		
		if(move.moveFigure(ai_input, guiViewBoard, i, new ArrayList<>()) == null){
			guiViewBoard.updateBeaten(move.getBeatenFigures().getStoreBeatenFigures());
			movement.add(ai_input);
			guiViewBoard.updateHistory(movement);
			finalSituation(guiViewBoard, i);
			//guiViewBoard.putFiguresOnBoard(i+1, false, guiViewBoard.getselectedMode());
		}
	}
	
	/**
	 * Removes the borders and highlights of all squares on the chess board. 
	 * @param guiViewBoard Currently used GuiViewBoard. 
	 */
	private void removeBordersAndHighlight(GuiViewBoard guiViewBoard) {
		for(int y=0; y<8; y++) {
			for(int x=0; x<8; x++) {
				guiViewBoard.storeSquares[y][x].setBorder(null);
				if((x+y)%2==0) {
					guiViewBoard.storeSquares[y][x].setStyle(
							"-fx-background-color: white;");
				}
				else {
					guiViewBoard.storeSquares[y][x].setStyle(
							"-fx-background-color: black;");
				}
			}
		}
	}
	
	/**
	 * Checks if the currently selected figure and the previously clicked figure are of the same color.
	 * @param storeClickCRI Stores position and counter of previous click.
	 * @param colAndRowAndI Position and counter of current click. 
	 * @param guiViewBoard Currently used GuiViewBoard. 
	 * @return True if currently selected figure and previously selected figure are of the same color, otherwise false.
	 */
	private boolean ownFigure(int[] storeClickCRI, int[] colAndRowAndI, GuiViewBoard guiViewBoard) {
		if(storeClickCRI[0]!=-1) {
			return Character.isUpperCase(guiViewBoard.getModel().getChar(storeClickCRI[0], storeClickCRI[1])) && 
				Character.isUpperCase(guiViewBoard.getModel().getChar(colAndRowAndI[0], colAndRowAndI[1])) ||
				!Character.isUpperCase(guiViewBoard.getModel().getChar(storeClickCRI[0], storeClickCRI[1])) && 
				!Character.isUpperCase(guiViewBoard.getModel().getChar(colAndRowAndI[0], colAndRowAndI[1]))
				&& guiViewBoard.getModel().getChar(colAndRowAndI[0], colAndRowAndI[1]) != ' ';
		}	
		return false;
	}
	
	/**
	 * Checks for a possible pawn promotion.
	 * @param guiViewBoard
	 * @param colAndRowAndI
	 * @return True if a pawn is selected and should be moved to a field of either the first or the last row, otherwise false.
	 */
	private boolean checkPawnPromotion(GuiViewBoard guiViewBoard, int[] colAndRowAndI) {
		
		
		return getStoreLastClickCRI()[0] != -1 &&
				(colAndRowAndI[1]==0 || colAndRowAndI[1]==7) && 
				(guiViewBoard.getModel().getChar(getStoreLastClickCRI()[0], getStoreLastClickCRI()[1])=='p' || 
						guiViewBoard.getModel().getChar(getStoreLastClickCRI()[0], getStoreLastClickCRI()[1])=='P');
	}
	
	/**
	 * Updates UI and checks if move ends up in a check, draw or checkmate => shows alert 
	 * @param guiViewBoard Currently used GuiViewBoard.
	 * @param i Count of already done moves (starts with 0, gets updated after done movement when GuiViewBoard is getting updated).
	 */
	private void finalSituation(GuiViewBoard guiViewBoard, int i) {
		boolean endOfGame = false;
		
		// check for draw
		if(Draw.checkDraw(guiViewBoard, move)) {
			new GuiAlerts(guiViewBoard.getModel()).showAlertDraw();
		}
		else if(new Check().checkCheck(guiViewBoard, move.getStoreOriginalCharacter())) {
			
			char output = new ConsoleController().toggleChar(move.getStoreOriginalCharacter());
    		// check for mate
    		if(new MoveGenerator().generateMoves(guiViewBoard, i+1, new ArrayList<>(), output).size()==0) {
    			new GuiAlerts(guiViewBoard.getModel()).showAlertMate();
    		 	endOfGame = true;
    		}
    		else {
    			
    			if(guiViewBoard.getButtonsActivated().isCheckNotificationActivated()) {
    				new GuiAlerts(guiViewBoard.getModel()).showAlertCheck();
    			}
    		}
    		guiViewBoard.putFiguresOnBoard(i+1, endOfGame, guiViewBoard.getselectedMode());
	    }
		else {
			guiViewBoard.putFiguresOnBoard(i+1, endOfGame, guiViewBoard.getselectedMode());
		}
	}
	
	/**
	 * Does moves on a new model until (not including) a given index.
	 * @param index First position that should not be used anymore.
	 * @param guiViewBoard Currently used GuiViewBoard. 
	 * @param selectedMode List of Strings that stores selected user input (1. opponent, 2. own color, 3. should previous game be loaded).
	 */
	public void undoMovesGui(int index, GuiViewBoard guiViewBoard, List<String> selectedMode) {
		Model updatedModel = new Model();
		updatedModel.setCurrent(guiViewBoard.getModel().getCurrent());
		guiViewBoard.setModel(updatedModel);
		
		move.setBeatenFigures(new BeatenFigures());
		
		// first move should be undone
		if(index==0) {
			guiViewBoard.putFiguresOnBoard(0, false, selectedMode);
			guiViewBoard.updateBeaten(move.getBeatenFigures().getStoreBeatenFigures());
		}
		// further moves should be undone
		else {
			for(int i=0; i<index; i++) {
				move.moveFigure(movement.get(i), guiViewBoard, i, new ArrayList<>());
				guiViewBoard.putFiguresOnBoard(i+1, false, selectedMode);
				//finalSituation(guiViewBoard, i);
			}
			guiViewBoard.updateBeaten(move.getBeatenFigures().getStoreBeatenFigures());
		}
		
		// set undoIndex
		this.undoIndex = index;
		
		// greyout moves
		guiViewBoard.getBoardComponents().greyOut(undoIndex, movement);
		
	}
	
	
	
	
	/**
	 * Simulates in file stored moves and updates ui. 
	 * @param v Currently used GuiViewBoard.
	 * @param selectedMode List of Strings that stores selected user input (1. opponent, 2. own color, 3. should previous game be loaded).
	 */
	public void loadIntoGui(GuiViewBoard v, List<String> selectedMode) {
		// simulate stored moves on new model 
		List<String> loadedMoves = new FileHandler().getMovementsFromFile(v, Integer.parseInt(selectedMode.get(3)));
		movement = new ArrayList<>();
		move.setBeatenFigures(new BeatenFigures());
		
		if(loadedMoves!=null && loadedMoves.size()>0) {
			
			loadMoves(v, loadedMoves, movement, selectedMode);
			
			updateBeatenAndHistory(v, loadedMoves);
		}
		else {
			v.putFiguresOnBoard(0, false, selectedMode);
		}
		
		// if computer has the turn, do ai move
		if(selectedMode.get(0).equals("computer") && selectedMode.get(1).equals("white") && loadedMoves.size()%2 != 0 ||
				selectedMode.get(0).equals("computer") && selectedMode.get(1).equals(getBlack()) && loadedMoves.size()%2 == 0) {
			
			aiMove(v, loadedMoves.size());
		
		}
		
	}
	
	public AiThread getT() {
		return t;
	}

	public void setT(AiThread t) {
		this.t = t;
	}

	private void loadMoves(GuiViewBoard v, List<String> loadedMoves, List<String> movement, List<String> selectedMode) {
		for(int i=0; i<loadedMoves.size(); i++) {
			move.moveFigure(loadedMoves.get(i), v, i, movement);
			v.putFiguresOnBoard(i+1, false, selectedMode);
		}
	}
	
	private void updateBeatenAndHistory(GuiViewBoard v, List<String> loadedMoves) {
		v.updateBeaten(move.getBeatenFigures().getStoreBeatenFigures());
		v.updateHistory(loadedMoves);
	}
	
	
	
	
	/**
	 * Getter for GuiControllerBoard
	 */
	public GuiControllerBoard getGuiControllerBoard() {
		return this;
	}
	
	private String getBlack() {
		return "black";
	}
}
