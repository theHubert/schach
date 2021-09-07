package schach;

import org.junit.jupiter.api.Test;

import controller.Move;
import controller.Rook;
import model.Model;
import view.ConsoleView;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class RookTest contains the tests about all actions related to the rook figure.
 *
 */
public class RookTest {
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
			{' ',' ',' ',' ','r',' ',' ',' '},
			{' ',' ',' ','r',' ','Q',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ','R',' ',' ',' ',' ',' '},
	};
	char[][] chessFieldComparator = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ','r',' ',' ',' '},
			{' ',' ',' ','r','R','Q',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	
	String turn1 = "c1-e1";
	String turn2 = "e1-e4";
	String turn3 = "e4-c3";
	String turn4 = "e4-e6";
	String turn5 = "e4-c4";
	String turn6 = "e4-f4";
	
	// Test horizontal movement
	move.moveFigure(turn1, v, 2, moves);
	
	// Test vertical movement
	move.moveFigure(turn2, v, 4, moves);
	
	// Test invalid movement
	move.moveFigure(turn3, v, 6, moves);
	
	// Test if other figure is between original position and destination
	move.moveFigure(turn4, v, 6, moves);
	move.moveFigure(turn5, v, 6, moves);
	
	// Test if own figure is on destination
	move.moveFigure(turn6, v, 6, moves);
	
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
			{' ',' ',' ','r','R',' ',' ',' '},
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
	assertEquals('R', m.getChar(3,4));
	
	}
	/**
	 * 
	 * Tests all possible directions for check
	 *
	 */
	@Test
	public void testCheckPossibleCheck() {
	//Setup
	Model m = new Model();
	char[][] chessField = {
			{' ',' ',' ','R',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'R',' ',' ','k',' ',' ',' ','R'},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ','R',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Rook r = new Rook();
	char[] opponentKingAndOwnFigure = {'k','R'};
	
	boolean[] trues = new boolean[4];
	Arrays.fill(trues, true);
	boolean[] checkResult = new boolean[4];
	checkResult[0] = r.checkPossibleCheck(v, opponentKingAndOwnFigure, 4, 0);
	checkResult[1] = r.checkPossibleCheck(v, opponentKingAndOwnFigure, 0, 3);
	checkResult[2] = r.checkPossibleCheck(v, opponentKingAndOwnFigure, 4, 7);
	checkResult[3] = r.checkPossibleCheck(v, opponentKingAndOwnFigure, 7, 3);
	
	assertArrayEquals(trues,checkResult);
	}
	
}
