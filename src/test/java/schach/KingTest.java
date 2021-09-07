package schach;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import controller.King;
import controller.Move;
import model.Model;
import view.ConsoleView;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class KingTest contains the tests about all actions related to the king figure.
 *
 */
public class KingTest {
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
			{' ',' ',' ',' ','K',' ','Q',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	char[][] chessFieldComparator = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ','K','Q',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	String turn1 = "e4-f4";
	String turn2 = "f4-g4";
	String turn3 = "f4-a8";
	
	// Test movement
	move.moveFigure(turn1, v, 2, moves);
	
	// Test if own figure is on destination
	move.moveFigure(turn2, v, 2, moves);
	
	// Test invalid movement
	move.moveFigure(turn3, v, 2, moves);
	
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
			{' ',' ',' ',' ','K',' ',' ',' '},
			{' ',' ',' ','n',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	String turn1 = "e4-d3";

	move.moveFigure(turn1, v, 2, moves);
	assertEquals('K', m.getChar(3,5));
	
	}
	
	/**
	 * 
	 * Test for all 4 possible rochade moves
	 *
	 */
	@Test
	public void testLegal_rochade() {
	//Setup
	Model m = new Model();
	char[][] chessFieldLeft = {
			{'r',' ',' ',' ','k',' ',' ','r'},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'R',' ',' ',' ','K',' ',' ','R'},
	};
	m.setChessField(chessFieldLeft);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	List<String> movesLeft = new ArrayList<>();
	List<String> movesRight = new ArrayList<>();
	King k = new King();
	int [] rochadeLeftUpper = {4,7,2,7};
	int [] rochadeLeftLower = {4,0,2,0};
	int [] rochadeRightUpper = {4,7,6,7};
	int [] rochadeRightLower = {4,0,6,0};
	
	boolean[] trues = new boolean[4];
	Arrays.fill(trues, true);
	boolean[] checkResult = new boolean[4];
	
	// Rochade left
	checkResult[0] = k.legal_rochade(movesLeft, 'K', rochadeLeftUpper, v);
	checkResult[1] = k.legal_rochade(movesLeft, 'k', rochadeLeftLower, v);
	
	// Rochade right
	char[][] chessFieldRight = {
			{'r',' ',' ',' ','k',' ',' ','r'},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'R',' ',' ',' ','K',' ',' ','R'},
	};
	m.setChessField(chessFieldRight);
	
	checkResult[2] = k.legal_rochade(movesRight, 'K', rochadeRightUpper, v);
	checkResult[3] = k.legal_rochade(movesRight, 'k', rochadeRightLower, v);
	
	assertArrayEquals(trues,checkResult);
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
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ','K','K','K',' ',' ',' '},
			{' ',' ','K','k','K',' ',' ',' '},
			{' ',' ','K','K','K',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	King k = new King();
	char[] opponentKingAndOwnFigureUpper = {'k','K'};
	
	boolean[] trues = new boolean[8];
	Arrays.fill(trues, true);
	boolean[] checkResult = new boolean[8];
	checkResult[0] = k.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 5, 2);
	checkResult[1] = k.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 4, 2);
	checkResult[2] = k.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 3, 2);
	checkResult[3] = k.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 3, 3);
	checkResult[4] = k.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 3, 4);
	checkResult[5] = k.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 4, 4);
	checkResult[6] = k.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 5, 4);
	checkResult[7] = k.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 5, 3);
	
	assertArrayEquals(trues,checkResult);

	}
}
