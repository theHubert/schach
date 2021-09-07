package schach;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import controller.Move;
import controller.Queen;
import model.Model;
import view.ConsoleView;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class QueenTest contains the tests about all actions related to the queen figure.
 * Some of the possible movement is ignored because the queen is made out of a rook and a bishop and the movement is covered in there test classes.
 *
 */
public class QueenTest {
	/**
	 * 
	 * Contains all tests for movement except the case of beating a figure
	 *
	 */
	@Test
	public void testCheckMove() {
	//Setup
	Model m = new Model();
	char[][] chessField = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ','r','Q','R',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	char[][] chessFieldComparator = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ','Q',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ','r',' ','R',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	
	String turn1 = "e4-a3";
	String turn2 = "e4-f4";
	String turn3 = "e4-c4";
	String turn4 = "e4-e6";
	
	// Test invalid movement
	move.moveFigure(turn1, v, 2, moves);
	
	// Test if own figure is on destination
	Queen q = new Queen();
	int [] decodedMove = {4,4,5,4};
	assertTrue(q.ownFigureAtDestination(v, 'Q', decodedMove));
	move.moveFigure(turn2, v, 2, moves);
	
	// Test if other figure is between original position and destination
	move.moveFigure(turn3, v, 2, moves);
	
	// Test movement
	move.moveFigure(turn4, v, 2, moves);
	
	assertArrayEquals(chessFieldComparator, m.getChessField());
	}
	/**
	 * 
	 * Test for beating a figure
	 *
	 */
	@Test
	public void testCheckBeaten() {
	//Setup
	Model m = new Model();
	char[][] chessField = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ','q','Q',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	String turn1 = "e4-d4";

	move.moveFigure(turn1, v, 2, moves);
	assertEquals('Q', m.getChar(3,4));
	
	}
}
