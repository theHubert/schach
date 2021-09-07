package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * 
 * creates a new window for selecting the figure for the pawn promotion
 *
 */
public class FigureSelector {
	
	private ComboBox<String> cb;
	private GridPane gridPane;
	
	Map<String, Character> getChar = new HashMap<String, Character>();
	
	/**
	 * constructor that sets up the new stage for selecting the figure for the pawn promotion
	 * @param square StackPane, needed for calling function squareClicked()
	 * @param colAndRowAndI position of clicked square and counter for already played moves
	 * @param guiViewBoard currently used GuiViewBoard
	 * @param opponentAndColor List, storing chosen opponent and color 
	 */
	public FigureSelector(StackPane square, int[] colAndRowAndI, GuiViewBoard guiViewBoard, List<String> opponentAndColor) {
		
		getChar.put(guiViewBoard.getModel().getCurrent().getString("queen"), 'Q');
		getChar.put(guiViewBoard.getModel().getCurrent().getString("rook"), 'R');
		getChar.put(guiViewBoard.getModel().getCurrent().getString("bishop"), 'B');
		getChar.put(guiViewBoard.getModel().getCurrent().getString("knight"), 'N');
		
		Stage stage = new Stage();
		stage.setMinHeight(300);
		stage.setMinWidth(350);
		
		gridPane = new GridPane();
		gridPane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
		gridPane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
		
		buildGridPane(guiViewBoard, square, colAndRowAndI, opponentAndColor);
		
		gridPane.setId("basic");
		
		Scene scene = new Scene(gridPane);
		scene.getStylesheets().addAll(this.getClass().getResource("/schach/style.css").toExternalForm());
        stage.setScene(scene);
        //stage.setTitle("Promotion");
        stage.show();
	}
	
	
	/**
	 * builds the GridPane (will be set to stage)
	 * @param guiViewBoard currently used GuiViewBoard
	 * @param square StackPane, needed for calling function squareClicked()
	 * @param colAndRowAndI position of clicked square and counter for already played moves
	 * @param opponentAndColor List, storing chosen opponent and color 
	 */
	public void buildGridPane(GuiViewBoard guiViewBoard, StackPane square, int[] colAndRowAndI, List<String> opponentAndColor) {
		
		gridPane.add(new Label(guiViewBoard.getModel().getCurrent().getString("promotionChooseFigure")), 0, 0);
		cb = new ComboBox<String>();
		ObservableList<String> possibleFigures = FXCollections.observableArrayList(guiViewBoard.getModel().getCurrent().getString("selectFigure"), 
				guiViewBoard.getModel().getCurrent().getString("queen"), 
				guiViewBoard.getModel().getCurrent().getString("rook"),
				guiViewBoard.getModel().getCurrent().getString("bishop"), 
				guiViewBoard.getModel().getCurrent().getString("knight"));
		cb.setItems(possibleFigures);
		cb.getSelectionModel().select(0);
		
		cb.setId("button");
		
		cb.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
			try {
				char selectedFigure = getChar.get(newValue);
				guiViewBoard.guiControllerBoard.squareClicked(square, colAndRowAndI, guiViewBoard, selectedFigure);
			}
			catch(Exception e){
				Alert alertMate = new Alert(AlertType.INFORMATION);
				alertMate.setTitle(guiViewBoard.getModel().getCurrent().getString("noFigureSelected"));
				alertMate.setHeaderText(null);
				alertMate.setContentText(guiViewBoard.getModel().getCurrent().getString("errorNoFigureSelected"));
				alertMate.showAndWait();
			}
	    }
	    ); 
		gridPane.add(cb, 1, 0);
	}

	
	

}
