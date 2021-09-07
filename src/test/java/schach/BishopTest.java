package schach;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import controller.Bishop;
import controller.Move;
import model.Model;
import view.ConsoleView;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class BishopTest contains the tests about all actions related to the bishop figure. 
 *
 */

public class BishopTest {
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
			{' ',' ',' ',' ','B',' ','r',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ','Q',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	char[][] chessFieldComparator = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ','r',' '},
			{' ',' ',' ',' ',' ','B',' ',' '},
			{' ',' ',' ',' ',' ',' ','Q',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	String turn1 = "e4-f5";
	String turn2 = "f5-e4";
	String turn3 = "e4-d5";
	String turn4 = "d5-f3";
	String turn5 = "f3-a1";
	String turn6 = "f3-h5";
	String turn7 = "f3-g2";
	
	
	// Test movement
	move.moveFigure(turn1, v, 2, moves);
	move.moveFigure(turn2, v, 4, moves);
	move.moveFigure(turn3, v, 6, moves);	
	move.moveFigure(turn4, v, 8, moves);
	
	// Test invalid movement
	move.moveFigure(turn5, v, 10, moves);
	
	// Test if other figure is between original position and destination
	move.moveFigure(turn6, v, 10, moves);
	
	// Test if own figure is on destination
	move.moveFigure(turn7, v, 10, moves);
	
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
			{' ',' ',' ','b',' ',' ',' ',' '},
			{' ',' ',' ',' ','B',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	String turn1 = "e4-d5";

	move.moveFigure(turn1, v, 2, moves);
	
	assertEquals('B', m.getChar(3,3));
	
	}
	
	/**
	 * 
	 * Tests all possible directions for blocked movement
	 *
	 */
	@Test
	public void testCheckMoveBlocked() {
		//Setup
		Model m = new Model();
		char[][] chessField = {
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ','r',' ','r',' ',' '},
				{' ',' ',' ',' ','B',' ',' ',' '},
				{' ',' ',' ','r',' ','r',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
		};
		char[][] chessFieldComparator = chessField.clone();
		m.setChessField(chessField);
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		Move move = new Move();
		List<String> moves = new ArrayList<>();
		
		String turn1 = "e4-c2";
		String turn2 = "e4-c6";
		String turn3 = "e4-g2";
		String turn4 = "e4-g6";
		
		move.moveFigure(turn1, v, 10, moves);
		move.moveFigure(turn2, v, 10, moves);		
		move.moveFigure(turn3, v, 10, moves);
		move.moveFigure(turn4, v, 10, moves);
	
		assertArrayEquals(chessFieldComparator, m.getChessField());
		
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
			{' ','B',' ',' ',' ','B',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ','k',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ','B',' ',' ',' ','B',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Bishop b = new Bishop();
	char[] opponentKingAndOwnFigure = {'k','B'};
	boolean[] trues = new boolean[4];
	Arrays.fill(trues, true);
	boolean[] checkResult = new boolean[4];
	checkResult[0] = b.checkPossibleCheck(v, opponentKingAndOwnFigure, 6, 1);
	checkResult[1] = b.checkPossibleCheck(v, opponentKingAndOwnFigure, 2, 1);
	checkResult[2] = b.checkPossibleCheck(v, opponentKingAndOwnFigure, 2, 5);
	checkResult[3] = b.checkPossibleCheck(v, opponentKingAndOwnFigure, 6, 5);

	assertArrayEquals(trues,checkResult);
	
	}
}
