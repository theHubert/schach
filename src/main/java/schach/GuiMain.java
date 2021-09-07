package schach;

import controller.GuiController;
import javafx.application.Application;
//import javafx.geometry.HPos;
//import javafx.geometry.VPos;
import javafx.scene.Scene;
//import javafx.scene.control.Control;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.RowConstraints;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Model;
import view.GuiView;

/**
 * 
 * The class GuiMain reads out the supplied command-line arguments and subsequently 
 * either invokes the ConsoleMain class or creates and connects the needed objects for the GUI version.
 *
 */

public class GuiMain extends Application {
	
	/**
	 * If read out input equals --no-gui, the console based frontend will be invoked.
	 * Otherwise, the gui version will be invoked.
	 * 
	 * @param args
	 */
	
    public static void main(String[] args) {
    	
    	// select between console-based frontend and gui
    	if(args.length != 0 && args[0].equals("--no-gui")){
			ConsoleMain.main(args);
		}
    	else {
    		launch(args);
    	}
    	
    	
    }
    
    // gui
    @Override
    public void start(Stage primaryStage) {
        
        Model m = new Model();
		GuiView v = new GuiView();
		v.setModel(m);
		GuiController c = new GuiController();
		c.setGuiView(v);
		v.getBorderPane().setId("pane");
		Scene scene = new Scene(v.getBorderPane(), 550, 400);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		//primaryStage.setTitle(m.getCurrent().getString("mainMenu"));
		primaryStage.setScene(scene);
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(550);
		primaryStage.show();
    }
}
