package controller;

import java.util.ArrayList;

import javafx.application.Platform;
import view.GuiViewBoard;

@SuppressWarnings("PMD.DataClass")
//This class mainly stores the variables needed for calculating an ai move and is therefore a data class. 


/**
 * 
 * The class AiThread enables to create a new thread for calculating an ai move. 
 *
 */
public class AiThread extends Thread{
	
	private final GuiViewBoard guiViewBoard;
	private final GuiControllerBoard guiControllerBoard;
	private final int i;
	private final char ownFigure;
	private final int depth;
	private volatile String result;
	public volatile boolean resultReady; // stores state if calculation of ai_move is already finished
	
	/**
	 * Initializes a new AiThread by setting variables, needed for calculating an ai move.
	 * @param gb Currently used GuiControllerBoard
	 * @param v Currently used GuiControllerBoard
	 * @param i Count of already done moves (starts with 0, gets updated after done movement when GuiViewBoard is getting updated)
	 * @param ownfigure Char of the color the ai plays.
	 * @param depth of the minimax algorithm.
	 */
	public AiThread(GuiViewBoard v, int i, char ownfigure, int depth) {
		this.guiControllerBoard = v.guiControllerBoard;
		//this.guiViewBoard = (GuiViewBoard) v.getCopyOfView(); // just don't copy; somehow the copy destroys everything!!!!!
		// further investigation is needed ;)
		this.guiViewBoard = v;
		this.i = i;
		this.ownFigure = ownfigure;
		this.depth = depth;
		this.result = null;
		this.resultReady = false;
	}
	
	/**
	 * Performs calculation of move by calling calcBestMove method of class AI.
	 */
	@Override
	public void run() {
	      AI ai = new AI();
		  result = ai.calcBestMove(guiViewBoard, i, new ArrayList<String>(), ownFigure, depth);
		  
		 /**
		  * Calls method for doing movement and updating ui.
		  */
          Platform.runLater(new Runnable() {
              @Override
              public void run() {
            	  guiControllerBoard.onCalculatedMove(result, guiViewBoard, i);
              }
          });
		  
		  this.resultReady = true;
	 }

	
	public int getI() {
		return i;
	}

	public char getOwnFigure() {
		return ownFigure;
	}

	public int getDepth() {
		return depth;
	}

}
