package model;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * The class GuiFigureGenerator enables to convert a figure encoded as a char to an image.
 */
public class GuiFigureGenerator {
	
	Map<Character, Image> charToImageFigure = new HashMap<Character, Image>();
	
	/**
	 * Generates each figure with char letter and correlating image.
	 */
	public GuiFigureGenerator() {
		charToImageFigure.put('r', ImageHandler.getInstance().getImage("blackRook"));
		charToImageFigure.put('R', ImageHandler.getInstance().getImage("whiteRook"));
		charToImageFigure.put('b', ImageHandler.getInstance().getImage("blackBishop"));
		charToImageFigure.put('B', ImageHandler.getInstance().getImage("whiteBishop"));
		charToImageFigure.put('k', ImageHandler.getInstance().getImage("blackKing"));
		charToImageFigure.put('K', ImageHandler.getInstance().getImage("whiteKing"));
		charToImageFigure.put('n', ImageHandler.getInstance().getImage("blackKnight"));
		charToImageFigure.put('N', ImageHandler.getInstance().getImage("whiteKnight"));
		charToImageFigure.put('p', ImageHandler.getInstance().getImage("blackPawn"));
		charToImageFigure.put('P', ImageHandler.getInstance().getImage("whitePawn"));
		charToImageFigure.put('q', ImageHandler.getInstance().getImage("blackQueen"));
		charToImageFigure.put('Q', ImageHandler.getInstance().getImage("whiteQueen"));
	}

	public Map<Character, Image> getCharToImageFigure() {
		return charToImageFigure;
	}

	public void setCharToImageFigure(Map<Character, Image> charToImageFigure) {
		this.charToImageFigure = charToImageFigure;
	}
	
}
