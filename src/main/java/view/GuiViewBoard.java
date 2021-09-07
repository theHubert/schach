package view;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import controller.AiThread;
import controller.GuiControllerBoard;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.BeatenFigures;
import model.GuiFigureGenerator;
import model.Model;
import schach.FileHandler;

/**
 * 
 * The class GuiViewBoard creates the stage with the chess board and the figures.
 *
 */
public class GuiViewBoard implements ViewContainer{
	
	private Model model;
	public GuiControllerBoard guiControllerBoard;
	private List<ImageView> ivs;
	public StackPane[][] storeSquares;
	private List<String> selectedMode;
	private ButtonsActivated buttonsActivated;
	private BoardComponents boardComponents;
	private Text[] textElements;
	private Button[] buttons;
	private FileHandler fileHandler;
	
	/**
	 * initializes the attributes of a GuiViewBaord
	 */
	public GuiViewBoard(List<String> selectedMode) {
		this.storeSquares = new StackPane[8][8];
		this.guiControllerBoard = new GuiControllerBoard();
		this.ivs = new ArrayList<ImageView>();
		this.selectedMode = selectedMode;
		this.buttonsActivated = new ButtonsActivated();
		this.boardComponents = new BoardComponents();
		this.fileHandler = new FileHandler();
		this.buttons = new Button[9];
	}

	/**
	 * builds stage with chess board
	 */
	public void buildBoard() {
		Stage stage = new Stage();
		stage.setMinHeight(500);
		stage.setMinWidth(700);
		boardComponents.getRoot_board().setMinSize(350, 350);
		

        // Tile size is proportional to window size
        final int size = 8 ;
        for (int i = 0; i < size; i++) {
        	boardComponents.getRoot_board().getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
        	boardComponents.getRoot_board().getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        
        this.textElements = new Text[2];
		this.textElements[0] = new Text(getModel().getCurrent().getString("moveHistoryText"));
		this.textElements[1] = new Text(getModel().getCurrent().getString("beatenPiecesText"));

        ScrollPane beaten_sp = new ScrollPane();
        beaten_sp.setFitToWidth(true);
        ScrollPane history_sp = new ScrollPane();
        history_sp.setFitToWidth(true);
        HBox hBoxBottom = createHBoxForButtonsBottom(stage);
        HBox hBoxTop = createHBoxForButtonsTop();
        setBeaten();
        setHistory();
        beaten_sp.setContent(boardComponents.getBeaten());
        history_sp.setContent(boardComponents.getHistory());
        
        // load previous game
        if(selectedMode.get(2)=="load") {
        	guiControllerBoard.loadIntoGui(this, selectedMode);
        }
        else {
        	putFiguresOnBoard(0, false, selectedMode);
        }
        
                
        // sets alignment for Borderpane & builds Window
        BorderPane.setAlignment(boardComponents.getRoot_board(),Pos.CENTER);
        BorderPane.setAlignment(hBoxBottom,Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(beaten_sp,Pos.CENTER_LEFT);
        BorderPane.setAlignment(history_sp,Pos.CENTER_RIGHT);
        BorderPane.setAlignment(hBoxTop,Pos.TOP_CENTER);
        BorderPane root = new BorderPane(boardComponents.getRoot_board(), hBoxTop, history_sp, hBoxBottom, beaten_sp);
       
        Scene scene = new Scene(root);
        setStyleBorderPane(root);
        history_sp.setPrefWidth(155);
        beaten_sp.setPrefWidth(155);
        scene.getStylesheets().addAll(this.getClass().getResource("/schach/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
	}
	
	private void setStyleBorderPane(BorderPane root) {
		root.setPrefSize(850, 650);     
        root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        root.setId("pane");
	}
	
	/**
	 * Creates the Button to flip the Board.
	 * @param btnRight Button for flipping the board
	 * @param list that stores the ImageViews, that contain the images of the figures
	 */
	private void flipButton(Button btnRight) {
		btnRight.setOnAction(event -> {
			buttonsActivated.setFlipButtonClicked(!getButtonsActivated().isFlipButtonClicked());
			if(getButtonsActivated().isFlipButtonClicked()) {
				btnRight.setStyle(customBackground());
				// if black has the turn updateFlip
				if(guiControllerBoard.getStoreLastClickCRI()[2] % 2 == 1){
					updateFlip(getIvs());
				}
			}
			else {
				btnRight.setStyle(null);
			}
		});
	}
	
	/**
	 * background for activated buttons can be accessed
	 * @return String with css style for background color of activated buttons
	 */
	public String customBackground() {
		return "-fx-background-color: #B22222; ";
	}
	
	/**
	 * Updates Flip Button
	 * @param list that stores the ImageViews, that contain the images of the figures
	 */
	public void updateFlip(List<ImageView> ivs) {
		if(getButtonsActivated().isFlipButtonClicked()) {
			
			if (guiControllerBoard.getCounter() % 2 == 0) {
				//even
				boardComponents.getRoot_board().setRotate(0);
				flipBoard(ivs);
			}
			else if (guiControllerBoard.getCounter() % 2 == 1) {
				//odd
				boardComponents.getRoot_board().setRotate(180);
				flipBoard(ivs);
			}
		}
	}
	
	/**
	 * Creates the Button to flip the board 180°
	 * @param list that stores the ImageViews, that contain the images of the figures
	 */
	private void flipBoard(List<ImageView> ivs) {
    	for (int i = 0; i < ivs.size(); i++) {
    		getIvs().get(i).setRotate(boardComponents.getRoot_board().getRotate());
    	}
	}
	
	/**
	 * Creates the Button to flip the board 180°
	 * @param list that stores the ImageViews, that contain the images of the figures
	 */
	public void flipForBlack() {
    		boardComponents.getRoot_board().setRotate(180);
			flipBoard(ivs);
	}
	
	/**
	 * creates a custom font
	 * @return the created font
	 */
	public Font customFont() {
		return Font.font("Arial", FontWeight.BOLD, 14);
	}
	
	/**
	 * Creates the Button to go back to the main menu
	 * @param close Button for getting back to main menu
	 * @param stage the current stage
	 */
	private void closeButton(Button close, Stage stage) {
		close.setOnAction(event -> {
			stage.close();

			if(GuiControllerBoard.movement != null) {
				GuiControllerBoard.movement.clear();
			}
		});
	}

	/**
	 * creates Field for History
	 */
	public void setHistory() {
		boardComponents.getHistory().setSpacing(8);
		boardComponents.getHistory().autosize();
		boardComponents.getHistory().centerShapeProperty();
		boardComponents.getHistory().setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
		boardComponents.getHistory().setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
	    
	    this.textElements[0].setFont(customFont());
	    boardComponents.getHistory().getChildren().add(this.textElements[0]);
	}
		
	/**
	 * Updates History
	 * @param list that stores the ImageViews, that contain the images of the figures
	 */
	public void updateHistory(List<String> movement) {
		GuiControllerBoard.movement = movement;
		boardComponents.getHistory().getChildren().clear();
		boardComponents.getHistory().getChildren().add(this.textElements[0]);
	    this.textElements[0].setFont(customFont());
	   
	    int index = 1;
	    for (int j=0; j<movement.size(); j++) {
	    	Text input_text = new Text(movement.get(j));
	    	int c = index-1;
	    	
	    	// action listeners for text elements of history: 
	    	// in the case of a vs. player game for every element
	    	// in the case of a vs. computer game only for the player moves 
	    	if(selectedMode.get(0).equals(getPlayer()) || 
	    			selectedMode.get(1).equals("black") && j%2==1 ||
	    			selectedMode.get(1).equals("white") && j%2==0) {
	    		input_text.setCursor(Cursor.HAND);
	    		input_text.setOnMouseClicked(e -> {
	    			

	    			if(((Text)boardComponents.getHistory().getChildren().get(c+1)).getFill() == Color.BLACK) {
	    				if(guiControllerBoard.getT()!=null && !guiControllerBoard.getT().resultReady) {
							guiControllerBoard.getT().stop();
						}
	    				guiControllerBoard.undoMovesGui(c, this, selectedMode);
	    				((Text)boardComponents.getHistory().getChildren().get(c+1)).setFill(Color.GREY);
	    			}
	    			else {
	    				if(guiControllerBoard.getT()!=null && !guiControllerBoard.getT().resultReady) {
							guiControllerBoard.getT().stop();
							
						}
	    				guiControllerBoard.undoMovesGui(c+1, this, selectedMode);
	    				if(selectedMode.get(0).equals("computer")) {
	    					AiThread t = new AiThread(this, guiControllerBoard.getT().getI(),
									guiControllerBoard.getT().getOwnFigure(), guiControllerBoard.getT().getDepth());
							t.start();
	    				}
	    				((Text)boardComponents.getHistory().getChildren().get(c+1)).setFill(Color.BLACK);
	    			}

			    	for(int i = 0; i <= c; i++) {
			    		((Text)boardComponents.getHistory().getChildren().get(i)).setFill(Color.BLACK);
			    	}
			    	for (int i = c+2; i <= movement.size(); i++) {
			    		((Text)boardComponents.getHistory().getChildren().get(i)).setFill(Color.GREY);
			    	}
		        });
	    	}
		    
		    boardComponents.getHistory().getChildren().add(index, input_text);
	    	index++;
	    }
	    
	}
	
	
	
	/**
	 * creates Field for Beaten Figures
	 */
	public void setBeaten() {
		boardComponents.getBeaten().setSpacing(8);
		boardComponents.getBeaten().autosize();
		boardComponents.getBeaten().setCenterShape(true);
		boardComponents.getBeaten().setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
		boardComponents.getBeaten().setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

		this.textElements[1].setFont(customFont());
	    boardComponents.getBeaten().getChildren().add(this.textElements[1]); 
	}
	
	/**
	 * Updates Beaten Figures
	 * @param list that stores the ImageViews, that contain the images of the figures
	 */
	public void updateBeaten(List<Character> storeBeatenFigures) {
		boardComponents.getBeaten().getChildren().clear();
		this.textElements[1].setFont(customFont());
	    boardComponents.getBeaten().getChildren().add(this.textElements[1]);
		
		int index = 1;
		for(char figure : storeBeatenFigures) {
			//boardComponents.getBeaten().getChildren().add(index, new Text(Character.toString(figure)));
			Text unicodeFigure = new Text(new BeatenFigures().getFigureAsUnicode().get(figure));
			unicodeFigure.setStyle("-fx-font-size: 25px;");
			boardComponents.getBeaten().getChildren().add(index, unicodeFigure);
			index++;
	    }
	}

	/**
	 * horizontal box that clusters the buttons for undo, 
	 * close, redo, store and language
	 * @param stage the current stage
	 * @return HBox containing the buttons
	 */
	private HBox createHBoxForButtonsBottom(Stage stage) {
		HBox boxForButtons = new HBox();
		boxForButtons.setPadding(new Insets(10, 5, 5, 5));
		
		Button close = new Button(getModel().getCurrent().getString("closeButton"));
		close.setId(getButtonsActivated().getButton());
		close.setCursor(Cursor.HAND);
		Button undo = new Button(getModel().getCurrent().getString("undoButton"));
		undo.setId(getButtonsActivated().getButton());
		undo.setCursor(Cursor.HAND);
		Button redo = new Button(getModel().getCurrent().getString("redo"));
		redo.setId(getButtonsActivated().getButton());
		redo.setCursor(Cursor.HAND);
		Button store = new Button(getModel().getCurrent().getString("store"));
		store.setId(getButtonsActivated().getButton());
		store.setCursor(Cursor.HAND);
		Button language = new Button(getModel().getCurrent().getString("languageButton"));
		language.setId(getButtonsActivated().getButton());
		language.setCursor(Cursor.HAND);
		Label label = new Label("");
		
		this.buttons[2] = close;
		this.buttons[5] = undo;
		this.buttons[6] = redo;
		this.buttons[7] = store;
		this.buttons[8] = language;
		
		closeButton(close, stage);
		setUndoButton(undo);
		setRedoButton(redo);
		setStoreButton(store);
		
		close.setLayoutX(300);
	    close.setLayoutY(220);
		
		boxForButtons.getChildren().add(close);
		boxForButtons.getChildren().add(undo);
		boxForButtons.getChildren().add(redo);
		boxForButtons.getChildren().add(store);
		boxForButtons.getChildren().add(language);
		
		setLanguageButton(buttons, label);
		boxForButtons.setSpacing(20);
		boxForButtons.setAlignment(Pos.CENTER);
		return boxForButtons;
	}
	
	/**
	 * horizontal box that clusters the buttons for flip, 
	 * check notification, highlighting, reselecting and the spinner for
	 * ai depth
	 * @return HBox containing the buttons and spinner
	 */
	private HBox createHBoxForButtonsTop() {
		HBox boxForButtons = new HBox();
		boxForButtons.setPadding(new Insets(10, 5, 5, 5));
		
		Button flip = new Button(getModel().getCurrent().getString("flipButton"));
		flip.setId(getButtonsActivated().getButton());
		flip.setCursor(Cursor.HAND);
		Button checkNotification = new Button(getModel().getCurrent().getString("checkNotificationButton"));
		checkNotification.setId(getButtonsActivated().getButton());
		checkNotification.setCursor(Cursor.HAND);
		Button highlight = new Button(getModel().getCurrent().getString("highlightButton"));
		highlight.setId(getButtonsActivated().getButton());
		highlight.setCursor(Cursor.HAND);
		Button reselect = new Button(getModel().getCurrent().getString("reselectionButton"));
		reselect.setId(getButtonsActivated().getButton());
		reselect.setCursor(Cursor.HAND);
		Label label = new Label("");
		
		this.buttons[0] = flip;
		this.buttons[1] = checkNotification;
		this.buttons[3] = highlight;
		this.buttons[4] = reselect;
		
		flipButton(flip);
		setCheckNotification(checkNotification);
		setHighlightMoves(highlight);
		setReselect(reselect);
		
		
		boxForButtons.getChildren().add(checkNotification);
		boxForButtons.getChildren().add(highlight);
		boxForButtons.getChildren().add(reselect);

		if(this.selectedMode.get(0) == getPlayer()) {
			boxForButtons.getChildren().add(flip);
		}
		
		if(this.selectedMode.get(0) == "computer") {
			label.setText(getModel().getCurrent().getString("aiLabel"));
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1);
			boardComponents.getSpinner().setValueFactory(valueFactory);
			
			boxForButtons.getChildren().add(label);
			boxForButtons.getChildren().add(boardComponents.getSpinner());
		}
		
		setLanguageButton(buttons, label);
		boxForButtons.setSpacing(20);
		boxForButtons.setAlignment(Pos.CENTER);
		return boxForButtons;
	}
	
	/**
	 * returns the AI depth of the spinner
	 * @return AI depth 
	 */
	public int getSpinnerValue() {
		
		int depth = 1;
		
		try {
			depth = boardComponents.getSpinner().getValue();
			}
		catch(NullPointerException e) {
			depth=1;
		}
		
		return depth;
	}
	
	/**
	 * Sets the action for the language button. If clicked, it switches the language between english and german.
	 * @param buttons
	 */
	private void setLanguageButton(Button[] buttons, Label label) {
		buttons[8].setOnAction(event -> {
			if(buttons[8].getText().equals("german")) {
				getModel().setCurrent(ResourceBundle.getBundle("schach.Bundle", new Locale("ge", "GE")));
			}
			else {
				getModel().setCurrent(ResourceBundle.getBundle("schach.Bundle", new Locale("en", "US")));
			}
			updateLanguage(buttons, label);
		});
	}
	
	/**
	 * updates the language of all gui text elements
	 * @param buttons Array containing all buttons
	 */
	private void updateLanguage(Button[] buttons, Label label) {
		buttons[0].setText(getModel().getCurrent().getString("flipButton"));
		buttons[1].setText(getModel().getCurrent().getString("checkNotificationButton"));
		buttons[2].setText(getModel().getCurrent().getString("closeButton"));
		buttons[3].setText(getModel().getCurrent().getString("highlightButton"));
		buttons[4].setText(getModel().getCurrent().getString("reselectionButton"));
		buttons[5].setText(getModel().getCurrent().getString("undoButton"));
		buttons[6].setText(getModel().getCurrent().getString("redo"));
		buttons[7].setText(getModel().getCurrent().getString("store"));
		buttons[8].setText(getModel().getCurrent().getString("languageButton"));
		this.textElements[0].setText(getModel().getCurrent().getString("moveHistoryText"));
		this.textElements[1].setText(getModel().getCurrent().getString("beatenPiecesText"));
		label.setText(getModel().getCurrent().getString("aiLabel"));
	}
	
	private void setStoreButton(Button storeButton) {
		
		storeButton.setOnAction(event -> {
			fileHandler.writeMovementsToFile(this, GuiControllerBoard.getMoves(), selectedMode.get(0));
			
		});
		
		
	}
	
	/**
	 * sets the action for the undo-button: last move will be undone
	 * @param undo Button
	 */
	private void setUndoButton(Button undo) {
	
		undo.setOnAction(event -> {
			
			if(guiControllerBoard.undoIndex==-1 && selectedMode.get(0).equals(getPlayer())) {

				guiControllerBoard.undoMovesGui(GuiControllerBoard.movement.size()-1, this, selectedMode);
		    		
			}
			else if(selectedMode.get(0).equals(getPlayer())){
				guiControllerBoard.undoMovesGui(guiControllerBoard.undoIndex-1, this, selectedMode);
				
			}
			else if(guiControllerBoard.undoIndex==-1) {
				if(guiControllerBoard.getT()!=null && !guiControllerBoard.getT().resultReady) {
					guiControllerBoard.getT().stop();
					guiControllerBoard.undoMovesGui(GuiControllerBoard.movement.size()-1, this, selectedMode);
				}
				else {
					guiControllerBoard.undoMovesGui(GuiControllerBoard.movement.size()-2, this, selectedMode);
				}
				
			}
			else {
				if(guiControllerBoard.getT()!=null && !guiControllerBoard.getT().resultReady) {
					guiControllerBoard.undoMovesGui(GuiControllerBoard.movement.size()-1, this, selectedMode);
				}
				else {
					guiControllerBoard.undoMovesGui(GuiControllerBoard.movement.size()-2, this, selectedMode);
				}
			}
			
			if(boardComponents.getHistory().hasProperties()) {
				// set color of undone moves (still possible to be reloaded) to grey
				for(int i = guiControllerBoard.undoIndex+1; i <= GuiControllerBoard.movement.size(); i++) {
					((Text)boardComponents.getHistory().getChildren().
			    			get(i)).setFill(Color.GREY);
				}
			}
			
		});
	}
	
	private void setRedoButton(Button redo) {
		redo.setOnAction(event -> {
			
			if(guiControllerBoard.undoIndex != -1 && GuiControllerBoard.movement.size() != guiControllerBoard.undoIndex) {
				if(selectedMode.get(0).equals(getPlayer())) {
					guiControllerBoard.undoMovesGui(guiControllerBoard.undoIndex+1, this, selectedMode);
				}
				else {
					if(guiControllerBoard.getT()!=null && !guiControllerBoard.getT().resultReady) {
						guiControllerBoard.getT().stop();
						guiControllerBoard.undoMovesGui(guiControllerBoard.undoIndex+1, this, selectedMode);
						AiThread t = new AiThread(this, guiControllerBoard.getT().getI(),
								guiControllerBoard.getT().getOwnFigure(), guiControllerBoard.getT().getDepth());
						t.start();
					}
					else {
						guiControllerBoard.undoMovesGui(guiControllerBoard.undoIndex+2, this, selectedMode);
					}
				}
				
				// set color of reloaded moves in history back to black
				for(int i = 0; i <= guiControllerBoard.undoIndex; i++) {
					((Text)boardComponents.getHistory().getChildren().
			    			get(i)).setFill(Color.BLACK);
				}
			}

		});
	}
	
	/**
	 * sets the action for the button, used for choosing if possible destination fields should be highlighted
	 * @param highlight Button for choosing if possible destination fields should be highlighted
	 */
	private void setHighlightMoves(Button highlight) {
		highlight.setOnAction(event -> {
			getButtonsActivated().setHighlightActivated(!getButtonsActivated().isHighlightActivated());
			if(getButtonsActivated().isHighlightActivated()) {
				highlight.setStyle(customBackground());
			}
			else {
				highlight.setStyle(null);
			}
		});
	}
	
	/**
	 * sets the action of button if a reselection of a figure should be allowed
	 * @param reselect Button if a reselection of a figure should be allowed
	 */
	private void setReselect(Button reselect) {
		reselect.setOnAction(event -> {
			getButtonsActivated().setReselectFigure(!getButtonsActivated().isReselectFigure());
			if(getButtonsActivated().isReselectFigure()) {
				reselect.setStyle("-fx-background-color: #B22222; ");
			}
			else {
				reselect.setStyle(null);
			}
		});
	}

	/**
	 * set the action of button if a notification in the case of a check should be displayed
	 * @param checkNotification Button if a notification in the case of a check should be displayed
	 */
	private void setCheckNotification(Button checkNotification) {
		checkNotification.setOnAction(event -> {
			getButtonsActivated().setCheckNotificationActivated(!getButtonsActivated().isCheckNotificationActivated());
			if(getButtonsActivated().isCheckNotificationActivated()) {
				checkNotification.setStyle(customBackground());
			}
			else {
				checkNotification.setStyle(null);
			}
		});
	}
	
	/**
	 * puts figures (as ImageViews) on chess board
	 * @param i counter for moves (for checking if player is trying to move its own figures)
	 * @param list that stores the ImageViews, that contain the images of the figures
	 * @param endOfGame is true in case of a check mate -> in case of a mate, no more moves are possible (no listeners are set)
	 * @param selectedMode stores the selected opponent (player or computer) and selected color (if opponent is computer)
	 */
	public void putFiguresOnBoard(int i, boolean endOfGame, List<String> selectedMode) {

			// Builds squares for board
	    	final int size = 8 ;
	    	getIvs().clear();
	    	for (int row = 0; row < size; row++) {
	    			for (int col = 0; col < size; col ++) {
	    			StackPane square = new StackPane();
	    			String color ;
	                if ((row + col) % 2 == 0) {
	                    color = "white";
	                } else {
	                    color = "black";
	                }
	                String cssDefault = "-fx-background-color: "+color+";";
	                square.setStyle(cssDefault);
	                square.setCursor(Cursor.HAND);
	               
	                boardComponents.getRoot_board().add(square, col, row);
	                ImageView iv = new ImageView();
	                iv.setImage(placeFigures(row, col));
	                iv.setFitHeight(50);
	                iv.setFitWidth(50);
	                iv.fitWidthProperty().bind(square.widthProperty());
	                iv.setRotate(0);
	                iv.setCursor(Cursor.HAND);
	                boardComponents.getRoot_board().add(iv, col, row);
	                boardComponents.getRoot_board().setRotate(0);
	                
	                getIvs().add(iv);
	                this.storeSquares[row][col] = square;
	                
	                if(!endOfGame) {
	                guiControllerBoard.setAction(square, iv, new int[] {col, row, i}, this);
	                }
	            }
	        }
        if(getButtonsActivated().isFlipButtonClicked()) {
        	
        	updateFlip(getIvs());
        	//flipBoard(getIvs());
        }
        if(selectedMode.get(0).equals("computer") && selectedMode.get(1).equals("black")) {
        	flipForBlack();
        }
	}
	
	/**
	 * Get the figure at a given position of chess field.
	 * @param row 
	 * @param col
	 * @return Image of figure.
	 */
	private Image placeFigures(int row, int col) {
		char figureAsChar = this.getModel().getChar(col, row);
		if(figureAsChar != ' ') {
			Image figureAsImage = new GuiFigureGenerator().getCharToImageFigure().get(figureAsChar);
			return figureAsImage;
		}
		return null;
	}
	
	public List<String> getselectedMode() {
		return selectedMode;
	}
	

	public StackPane[][] getStoreSquares() {
		StackPane[][] copyOfStoreSquares = new StackPane[storeSquares.length][];
		for(int i = 0; i < storeSquares.length; i++)
		{
			copyOfStoreSquares[i] = new StackPane[storeSquares[i].length];
		  for (int j = 0; j < storeSquares[i].length; j++)
		  {
		    copyOfStoreSquares[i][j] = storeSquares[i][j];
		  }
		}
		return copyOfStoreSquares;
	}

	public void setStoreSquares(StackPane[][] storeSquares) {
		StackPane[][] copyOfStoreSquares = new StackPane[storeSquares.length][];
		for(int i = 0; i < storeSquares.length; i++)
		{
			copyOfStoreSquares[i] = new StackPane[storeSquares[i].length];
		  for (int j = 0; j < storeSquares[i].length; j++)
		  {
		    copyOfStoreSquares[i][j] = storeSquares[i][j];
		  }
		}
		this.storeSquares = copyOfStoreSquares;
	}

	/**
	 * set variable selectedMode
	 * @param selectedMode
	 */
	public void setselectedMode(List<String> selectedMode) {
		this.selectedMode = selectedMode;
	}
	
	@Override
	public Model getModel() {
		return model;
	}
	@Override
	public void setModel(Model model) {
		this.model = model;
	}
	
	public BoardComponents getBoardComponents() {
		return boardComponents;
	}
	
	public ButtonsActivated getButtonsActivated() {
		return buttonsActivated;
	}
	
	public List<ImageView> getIvs() {
		return ivs;
	}
	public void setIvs(List<ImageView> ivs) {
		this.ivs = ivs;
	}
	
	/**
	 * returns current guiViewBoard (itself)
	 * @return guiViewBoard
	 */
	public GuiViewBoard getGuiViewBoard() {
		return this;
	}
	
	/**
	 * returns the string player for checking the vs. player mode 
	 * @return String "player"
	 */
	public String getPlayer() {
		return "player";
	}
	

	@Override
	public ViewContainer getCopyOfView() {
		GuiViewBoard v = new GuiViewBoard(getselectedMode());
		v.setModel(this.model.getCopyOfModel());
		v.setStoreSquares(getStoreSquares());
		v.getButtonsActivated().setCheckNotificationActivated(getButtonsActivated().isCheckNotificationActivated());
		v.getButtonsActivated().setFlipButtonClicked(getButtonsActivated().isFlipButtonClicked());
		v.getButtonsActivated().setHighlightActivated(getButtonsActivated().isHighlightActivated());
		v.getButtonsActivated().setReselectFigure(getButtonsActivated().isReselectFigure());
		BoardComponents bc = new BoardComponents();
		bc = this.boardComponents;
		v.boardComponents = bc;
		Text[] copyOfTextElements = new Text[2];
		System.arraycopy(this.textElements, 0, copyOfTextElements, 0, 2);
		v.textElements = copyOfTextElements;
		ButtonsActivated ba = new ButtonsActivated();
		ba = this.buttonsActivated;
		v.buttonsActivated = ba;
		return v;
	}
}
