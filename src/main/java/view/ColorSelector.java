package view;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;

/**
 * ColorSelector builds a new window for selecting the color in a game against
 * the computer
 */
public class ColorSelector {
	
	private Button btnWhite;
	private String color;
	Stage stage;
	private StackPane color_root;
	private Button btnBlack;
	
	/**
	 * ColorSelector() creates new instances of all relevant components for
	 * the new window
	 */
	public ColorSelector() {
		btnWhite = new Button();
		color = "";
		stage = new Stage();
		color_root = new StackPane();
		btnBlack = new Button();
	}
	/**
	 * buildColorSelector() sets parameters for the new instances and shows the 
	 * color selection window
	 */
	public void buildColorSelector(Model model, List<String> selectedMode) {
		stage.setMinHeight(300);
		stage.setMinWidth(350);
		
		color_root.setAlignment(Pos.CENTER);
	    btnWhite.setTranslateY(-50);
	    btnWhite.setText(model.getCurrent().getString("white"));
	    btnBlack.setTranslateY(10);
	    btnBlack.setText(model.getCurrent().getString("black"));
	    
	    // add style of buttons
	    btnWhite.setId("button");
	    btnWhite.setCursor(Cursor.HAND);
	    btnBlack.setId("button");
	    btnBlack.setCursor(Cursor.HAND);
	    
	    Text title = new Text(model.getCurrent().getString("selectColor"));
	    title.setTranslateY(-100);
	    color_root.getChildren().add(title);
	    color_root.getChildren().add(btnWhite);
	    color_root.getChildren().add(btnBlack);
	    
	    color_root.setId("basic");
	    
	    Scene scene = new Scene(color_root);
	    scene.getStylesheets().addAll(this.getClass().getResource("/schach/style.css").toExternalForm());
        stage.setScene(scene);
        //stage.setTitle(model.getCurrent().getString("chooseColor"));
        stage.show();
        
        
		btnWhite.setOnAction(event -> {
				color = "white";
				/*List<String> opponentAndColor = new ArrayList<String>();
				opponentAndColor.add("computer");
				opponentAndColor.add(color);*/
				selectedMode.set(1, color);
				GuiViewBoard guiViewBoard = new GuiViewBoard(selectedMode);
				Model m = new Model();
				m.setCurrent(model.getCurrent());
				guiViewBoard.setModel(m);
				guiViewBoard.buildBoard();
				stage.close();
	    }
	    );
		 
	        
	        btnBlack.setOnAction(event -> {
	        	color = "black";
	        	/*List<String> opponentAndColor = new ArrayList<String>();
				opponentAndColor.add("computer");
				opponentAndColor.add(color);*/
	        	selectedMode.set(1, color);
				GuiViewBoard guiViewBoard = new GuiViewBoard(selectedMode);
				guiViewBoard.setModel(new Model());
				guiViewBoard.buildBoard();
				guiViewBoard.flipForBlack();
				stage.close();
	        });
	}
}
