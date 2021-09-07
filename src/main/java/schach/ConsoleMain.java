package schach;

import java.util.Locale;
import java.util.ResourceBundle;

import controller.ConsoleController;
import controller.Move;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.ConsoleView;

/**
 * 
 * The class ConsoleMain creates and connects the needed objects for the console-based frontend.
 *
 */

public class ConsoleMain extends Application{
	
	/**
	 * Loads and initializes the specified Application class on the JavaFX Application Thread.
	 * 
	 * @param args Contains the supplied command-line arguments.
	 */

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
    public void start(Stage primaryStage) {
		
        Model m = new Model();
        m.setCurrent(ResourceBundle.getBundle("schach/Bundle", new Locale("en", "US")));
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		ConsoleController c = new ConsoleController();
		Move move = new Move();
		c.setMove(move);
		c.setView(v); 
    }

}
