package model;


import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * The class model stores the current state of the chess-field and offers 
 * methods to access single figures.
 *
 */

public class Model {
	
	// general model, stores chess field as 2d array
	
	private char[][] chessField = {
			{'r','n','b','q','k','b','n','r'},
			{'p','p','p','p','p','p','p','p'},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'P','P','P','P','P','P','P','P'},
			{'R','N','B','Q','K','B','N','R'},
	};
	
	//private BeatenFigures beatenFigures;
	
	// current stores selected language for internationalization
	ResourceBundle current;
	
	/**
	 * Constructor that initializes a new model with english as current language.
	 */
	public Model() {
		setCurrent(ResourceBundle.getBundle("schach/Bundle", new Locale("en", "US")));
		//beatenFigures = new BeatenFigures();
	}
	
	public ResourceBundle getCurrent() {
		return current;
	}

	public void setCurrent(ResourceBundle current) {
		this.current = current;
	}
	
	/**
	 * Allows to get a single figure.
	 * 
	 * @param x Position on x axis (a-h).
	 * @param y Position on y axis (1-8).
	 * @return Returns Figure at Position (x,y).
	 */

	public char getChar(int x, int y) {
		return chessField[y][x];
	}
	
	/**
	 * Allows to set a single figure.
	 * 
	 * @param x Position on x axis (a-h).
	 * @param y Position on y axis (1-8).
	 * @param replace New figure, that should be put on position (x,y).
	 */
	
	public void setChar(int x, int y, char replace) {
		chessField[y][x] = replace;
	}

	public char[][] getChessField() {
		return cloneChessField(chessField);
	}

	public void setChessField(char[][] chessField) {
		if(chessField == null) {
			this.chessField = new char[8][8];
		}
		else {
			this.chessField = cloneChessField(chessField);
		}
	}
	
	/**
	 * Clones a two-dimensional char array.
	 * 
	 * @param chessField Input 2d char array, that should be cloned.
	 * @return Copy of the input array.
	 */
	
	public char[][] cloneChessField(char[][] chessField){
		int length = chessField.length;
	    char[][] copyChessField = new char[length][chessField[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(chessField[i], 0, copyChessField[i], 0, chessField[i].length);
	    }
	    return copyChessField;
	}
	
	/**
	 * Copies the model.
	 * @return Copy of model
	 */
	public Model getCopyOfModel() {
		Model m = new Model();
		m.chessField = cloneChessField(this.chessField);
		m.current = this.current;
		return m;
	}
	
	/*public BeatenFigures getBeatenFigures() {
		return this.beatenFigures;
	}
	
	public void setBeatenFigures(BeatenFigures beatenFigures) {
		this.beatenFigures = beatenFigures;
	}*/
	

}
