package view;

import java.util.List;

import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

@SuppressWarnings("PMD.DataClass")
//this class should only contain components of the BorderPane and therefore is a data class

/**
 * 
 * stores components of the used BorderPane (root) in GuiViewBoard
 *
 */
public class BoardComponents {
	
	private GridPane root_board;
	private VBox beaten;
	private VBox history;
	private Spinner<Integer> spinner;
	
	/**
	 * initializes the variables
	 */
	public BoardComponents() {
		this.root_board = new GridPane();
		this.beaten = new VBox();
		this.history = new VBox();
		this.spinner = new Spinner<Integer>();
	}
	
	/**
	 * Greys out all history entries up to undoIndex.
	 * @param undoIndex Intex, up to where history elements will be colored black. 
	 * The following elements will be colored grey.
	 * @param movement List of history elements.
	 */
	public void greyOut(int undoIndex, List<String> movement) {
		
		for(int i=1; i<=undoIndex; i++) {
			((Text)history.getChildren().get(i)).setFill(Color.BLACK);
		}
		for(int i=undoIndex+1; i<=movement.size(); i++) {
			((Text)history.getChildren().get(i)).setFill(Color.GREY);

		}
	}
	
	public Spinner<Integer> getSpinner() {
		return spinner;
	}
	
	public void setSpinner(Spinner<Integer> spinner) {
		this.spinner = spinner;
	}
	
	public GridPane getRoot_board() {
		return root_board;
	}

	public void setRoot_board(GridPane root_board) {
		this.root_board = root_board;
	}

	public VBox getBeaten() {
		return beaten;
	}

	public void setBeaten(VBox beaten) {
		this.beaten = beaten;
	}

	public VBox getHistory() {
		return history;
	}

	public void setHistory(VBox history) {
		this.history = history;
	}

	

}