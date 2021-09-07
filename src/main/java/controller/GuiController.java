package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Model;
import schach.FileHandler;
import view.ColorSelector;
import view.GuiAlerts;
import view.GuiView;
import view.GuiViewBoard;

/**
 * 
 * The class GUI-Controller updates the chess-field depending on the user input.
 *
 */

public class GuiController {
	
	private static final String NONE = "none";

	FileHandler fileHandler = new FileHandler();
	
	private static final String credits = 
		    "Made by:\n" +
		    "Isabel Stephan (681047)\n" +
		    "Marvin Raasch (705101)\n" +
		    "Michelle Diez (715382)\n" +
		    "Steffanie Otto (628136)\n";
	
	/**
	 * 
	 * Sets the GUI-view by reading out the user input and updating the chess-field.
	 * 
	 * @param v The View object that displays the updated chess-field.
	 */

	public void setGuiView(GuiView v) {
		v.getBtnInstructions().setOnAction(event -> {
			
			Stage stage = new Stage();
			Label label = new Label(v.getModel().getCurrent().getString("instructions"));
		    label.setWrapText(true);
		    
		    
		    ScrollPane scrollPane = new ScrollPane();
		    scrollPane.setFitToWidth(true);
		    
		    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		    scrollPane.setContent(label);
		    
		    scrollPane.setId("basic");
		    Scene scene = new Scene(scrollPane, 180, 180);
		    scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		    
		    stage.setTitle(v.getModel().getCurrent().getString("instructionButton"));
		    stage.setScene(scene);
		    stage.show();
			
		});

		v.getBtnVsPlayer().setOnAction(event -> {
			// selectedMode stores selected user input (1. opponent, 2. own color, 3. should previous game be loaded)
			
			List<String> selectedMode = new ArrayList<String>();
			selectedMode.add("player");
			selectedMode.add(NONE);
			selectedMode.add("new");
			GuiViewBoard guiViewBoard = new GuiViewBoard(selectedMode);
			Model m = new Model();
			m.setCurrent(v.getModel().getCurrent());
			guiViewBoard.setModel(m);
			guiViewBoard.buildBoard();
			
		});
		
		v.getBtnLoadPlayer().setOnAction(event -> {
			// selectedMode stores selected user input (1. opponent, 2. own color, 3. should previous game be loaded)
			List<String> selectedMode = new ArrayList<String>();
			selectedMode.add("player");
			selectedMode.add(NONE);
			selectedMode.add("load");
			try {
				fileHandler.getMovementsFromFile(v, Integer.parseInt(v.getIndex().getText()));
				selectedMode.add(v.getIndex().getText());
				GuiViewBoard guiViewBoard = new GuiViewBoard(selectedMode);
				Model m = new Model();
				m.setCurrent(v.getModel().getCurrent());
				guiViewBoard.setModel(m);
				guiViewBoard.buildBoard();
			}
			catch(Exception e) {
				Alert alertIndex = new Alert(AlertType.INFORMATION);
				alertIndex.setTitle(v.getModel().getCurrent().getString("invalidInput"));
				alertIndex.setHeaderText(null);
				alertIndex.setContentText(v.getModel().getCurrent().getString("invalidInputForIndex"));
				alertIndex.showAndWait();
			}
			
		});
		
		v.getBtnVsComputer().setOnAction(event -> {
			List<String> selectedMode = new ArrayList<String>();
			selectedMode.add("computer");
			selectedMode.add(NONE);
			selectedMode.add("new");
			new ColorSelector().buildColorSelector(v.getModel(), selectedMode);
		});
		
		v.getBtnLoadComputer().setOnAction(event -> {
			List<String> selectedMode = new ArrayList<String>();
			selectedMode.add("computer");
			selectedMode.add(NONE);
			selectedMode.add("load");
			try {
				fileHandler.getMovementsFromFile(v, Integer.parseInt(v.getIndex().getText()));
				selectedMode.add(v.getIndex().getText());
				new ColorSelector().buildColorSelector(v.getModel(), selectedMode);
			}
			catch(Exception e) {
				new GuiAlerts(v.getModel()).showAlertIndex();
			}
			
		});
		
		v.getBtnShowStored().setOnAction(event -> {
			Stage stage = new Stage();
			Label label = new Label(fileHandler.showStoredGames(v));
		    label.setWrapText(true);
		    
		    
		    ScrollPane scrollPane = new ScrollPane();
		    scrollPane.setFitToWidth(true);
		    
		    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		    scrollPane.setContent(label);
		    scrollPane.setId("basic");
		    
		    Scene scene = new Scene(scrollPane, 180, 180);
		    scene.getStylesheets().addAll(this.getClass().getResource("/schach/style.css").toExternalForm());

		    stage.setTitle(v.getModel().getCurrent().getString("btnShowStored"));
		    stage.setScene(scene);
		    stage.show();
		});
		
		v.getBtnCredits().setOnAction(event -> {
			Stage stage = new Stage();
			Label label = new Label(credits);
		    label.setWrapText(true);
		    
		    StackPane layout = new StackPane();
		    layout.setStyle("-fx-background-color: white; -fx-padding: 10;");
		    layout.getChildren().setAll(label);

		    stage.setTitle("Instructions");
		    stage.setScene(new Scene(layout));
		    stage.show();
		});
		
		Button[] buttons = {v.getBtnInstructions(), v.getBtnVsPlayer(), v.getBtnVsComputer(), v.getBtnLoadPlayer(), v.getBtnLoadComputer(),
				v.getBtnShowStored(), v.getBtnCredits(), v.getLanguage()};
		updateLanguage(v, buttons, v.getEnterIndex(), v.getTitle());
		
		v.getLanguage().setOnAction(event -> {
			if(v.getLanguage().getText().equals("german")) {
				v.getModel().setCurrent(ResourceBundle.getBundle("schach.Bundle", new Locale("ge", "GE")));
			}
			else {
				v.getModel().setCurrent(ResourceBundle.getBundle("schach.Bundle", new Locale("en", "US")));
			}
			
			updateLanguage(v, buttons, v.getEnterIndex(), v.getTitle());
		});
	}
	
	private void updateLanguage(GuiView v, Button[] buttons, Label enterIndex, Label title) {
		buttons[0].setText(v.getModel().getCurrent().getString("instructionButton"));
		buttons[1].setText(v.getModel().getCurrent().getString("vsPlayerButton"));
		buttons[2].setText(v.getModel().getCurrent().getString("vsComputerButton"));
		buttons[3].setText(v.getModel().getCurrent().getString("loadPlayer"));
		buttons[4].setText(v.getModel().getCurrent().getString("loadComputer"));
		buttons[5].setText(v.getModel().getCurrent().getString("btnShowStored"));
		buttons[6].setText(v.getModel().getCurrent().getString("creditsButton"));
		buttons[7].setText(v.getModel().getCurrent().getString("languageButton"));
		enterIndex.setText(v.getModel().getCurrent().getString("enterIndex"));
		title.setText(v.getModel().getCurrent().getString("title"));
	}
	

}