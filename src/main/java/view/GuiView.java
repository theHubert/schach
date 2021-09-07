package view;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Model;


@SuppressWarnings("PMD.DataClass")
//this class should only contain components for the selection window and therefore is a data class

/**
 * 
 * The class GuiView creates the gui of the main menu.
 *
 */
public class GuiView extends HBox implements ViewContainer{
	
	private static final String BUTTON = "button";
	private BorderPane borderPane = new BorderPane();
	private GridPane grid = new GridPane();
	private Model model;
	private Button[] buttonsMainMenu = {new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button(), new Button()};
	private Label enterIndex = new Label();
	private TextField index = new TextField();
	private Label title = new Label();

	

	/**
	 * Builds GUI of main menu
	 */
	
	public GuiView(){
		
		
	    // set Cursors and Ids for layout for components
		buttonsMainMenu[0].setId(BUTTON);
		buttonsMainMenu[0].setCursor(Cursor.HAND);
	    
		buttonsMainMenu[1].setId(BUTTON);
		buttonsMainMenu[1].setCursor(Cursor.HAND);
        
		buttonsMainMenu[2].setId(BUTTON);
		buttonsMainMenu[2].setCursor(Cursor.HAND);
		
		buttonsMainMenu[3].setId(BUTTON);
		buttonsMainMenu[3].setCursor(Cursor.HAND);
		
		buttonsMainMenu[4].setId(BUTTON);
		buttonsMainMenu[4].setCursor(Cursor.HAND);
		
		buttonsMainMenu[5].setId(BUTTON);
		buttonsMainMenu[5].setCursor(Cursor.HAND);
		
		buttonsMainMenu[6].setId(BUTTON);
		buttonsMainMenu[6].setCursor(Cursor.HAND);
		
		buttonsMainMenu[7].setId(BUTTON);
		buttonsMainMenu[7].setCursor(Cursor.HAND);
		
		enterIndex.setId("enterIndex");
		index.setPrefWidth(USE_PREF_SIZE);
		
		
		// add components onto grid
		grid.add(buttonsMainMenu[1], 0, 0);
		grid.add(buttonsMainMenu[2], 1, 0);
		grid.add(buttonsMainMenu[3], 0, 1);
		grid.add(buttonsMainMenu[4], 1, 1);
		grid.add(enterIndex, 0, 2);
		grid.add(index, 1, 2);
		grid.add(buttonsMainMenu[0], 0, 3);
		grid.add(buttonsMainMenu[6], 1, 3);
		grid.add(buttonsMainMenu[5], 0, 4);
		grid.add(buttonsMainMenu[7], 1, 4);
		
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		title.setId("chessTitle");
		
		borderPane.setCenter(grid);
		borderPane.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);
	   
	}
	
	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}
	
	public Label getEnterIndex() {
		return enterIndex;
	}

	public void setEnterIndex(Label enterIndex) {
		this.enterIndex = enterIndex;
	}
	
	public TextField getIndex() {
		return index;
	}

	public void setIndex(TextField index) {
		this.index = index;
	}
	
	/**
	 * Getter for button for showing the stored games.
	 * @return Button for showing the stored games.
	 */
	public Button getBtnShowStored() {
		return buttonsMainMenu[5];
	}
	
	/**
	 * Setter for button for showing the stored games. 
	 * @param btnShowStored Button that should replace showStored button.
	 */
	public void setBtnShowStored(Button btnShowStored) {
		this.buttonsMainMenu[5] = btnShowStored;
	}
	
	/**
	 * Getter for button for loading a previous game into a vs player game.
	 * @return Button for loading a previous game into a vs player game.
	 */
	public Button getBtnLoadPlayer() {
		return buttonsMainMenu[3];
	}
	
	/**
	 * Setter for button for loading a previous game into a vs player game. 
	 * @param btnShowStored Button that should replace button for loading a previous game into a vs player game.
	 */
	public void setBtnLoadPlayer(Button btnLoadPlayer) {
		this.buttonsMainMenu[3] = btnLoadPlayer;
	}
	
	/**
	 * Getter for button for loading a previous game into a vs computer game.
	 * @return Button for loading a previous game into a vs computer game.
	 */
	public Button getBtnLoadComputer() {
		return buttonsMainMenu[4];
	}
	
	/**
	 * Setter for button for loading a previous game into a vs computer game. 
	 * @param btnShowStored Button that should replace button for loading a previous game into a vs computer game.
	 */
	public void setBtnLoadComputer(Button btnLoadComputer) {
		this.buttonsMainMenu[4] = btnLoadComputer;
	}
	
	/**
	 * Getter for button for showing the game instructions.
	 * @return Button for showing the game instructions.
	 */
	public Button getBtnInstructions() {
		return buttonsMainMenu[0];
	}
	
	/**
	 * Setter for button for showing the game instructions.
	 * @param btnShowStored Button that should replace button for showing the game instructions.
	 */
	public void setBtnInstructions(Button btnInstructions) {
		this.buttonsMainMenu[0] = btnInstructions;
	}
	
	/**
	 * Getter for button for starting a vs player game.
	 * @return Button for starting a vs player game.
	 */
	public Button getBtnVsPlayer() {
		return buttonsMainMenu[1];
	}
	
	/**
	 * Setter for button for starting a vs player game.
	 * @param btnShowStored Button that should replace button for starting a vs player game.
	 */
	public void setBtnVsPlayer(Button btnVsPlayer) {
		this.buttonsMainMenu[1] = btnVsPlayer;
	}
	
	/**
	 * Getter for button for starting a vs computer game.
	 * @return Button for starting a vs computer game.
	 */
	public Button getBtnVsComputer() {
		return buttonsMainMenu[2];
	}
	
	/**
	 * Setter for button for starting a vs computer game.
	 * @param btnShowStored Button that should replace button for starting a vs computer game.
	 */
	public void setBtnVsComputer(Button btnVsComputer) {
		this.buttonsMainMenu[2] = btnVsComputer;
	}
	
	/**
	 * Getter for button for switching the language.
	 * @return Button for switching the language.
	 */
	public Button getLanguage() {
		return buttonsMainMenu[7];
	}
	
	/**
	 * Setter for button for switching the language.
	 * @param btnShowStored Button that should replace button for switching the language.
	 */
	public void setLanguage(Button language) {
		this.buttonsMainMenu[7] = language;
	}
	
	public BorderPane getBorderPane() {
		return borderPane;
	}

	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}
	
	/**
	 * Getter for button for showing credits.
	 * @return Button for showing credits.
	 */
	public Button getBtnCredits() {
		return buttonsMainMenu[6];
	}
	
	/**
	 * Setter for button for showing credits.
	 * @param btnCredits Button that should replace button for showing credits.
	 */
	public void setBtnCredits(Button btnCredits) {
		this.buttonsMainMenu[6] = btnCredits;
	}

	@Override
	public Model getModel() {
		return this.model;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
		
	}

	@Override
	public ViewContainer getCopyOfView() {
		// TODO Auto-generated method stub
		return null;
	}


}