package schach;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import controller.Knight;
import controller.Move;
import model.Model;
import view.ConsoleView;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class KnightTest contains the tests about all actions related to the knight figure.
 *
 */
public class KnightTest {
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
			{' ',' ',' ',' ','N',' ',' ',' '},
			{' ',' ','P',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	char[][] chessFieldComparator = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ','N',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ','P',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	String turn1 = "e4-d6";
	String turn2 = "d6-b5";
	String turn3 = "b5-c3";
	String turn4 = "b5-f7";
	
	// Test movement
	move.moveFigure(turn1, v, 2, moves);
	move.moveFigure(turn2, v, 2, moves);
	
	// Test if own figure is on destination
	move.moveFigure(turn3, v, 2, moves);
	
	// Test invalid movement
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
			{' ',' ',' ','n',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ','N',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	String turn1 = "e4-d6";

	move.moveFigure(turn1, v, 2, moves);
	assertEquals('N', m.getChar(3,2));
	
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
			{' ',' ','N',' ','N',' ',' ',' '},
			{' ','N',' ',' ',' ','N',' ',' '},
			{' ',' ',' ','k',' ',' ',' ',' '},
			{' ','N',' ',' ',' ','N',' ',' '},
			{' ',' ','N',' ','N',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Knight n = new Knight();
	char[] opponentKingAndOwnFigureUpper = {'k','N'};
	
	boolean[] trues = new boolean[8];
	Arrays.fill(trues, true);
	boolean[] checkResult = new boolean[8];
	checkResult[0] = n.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 5, 1);
	checkResult[1] = n.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 3, 1);
	checkResult[2] = n.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 2, 2);
	checkResult[3] = n.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 2, 4);
	checkResult[4] = n.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 3, 5);
	checkResult[5] = n.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 5, 5);
	checkResult[6] = n.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 6, 4);
	checkResult[7] = n.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 6, 2);
	
	assertArrayEquals(trues,checkResult);
	}
}
