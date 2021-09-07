package schach;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import controller.GuiControllerBoard;
import controller.Move;
import controller.Pawn;
import model.Model;
import view.ConsoleView;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class PawnTest contains the tests about all actions related to the pawn figure.
 *
 */
public class PawnTest {
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
			{'p',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'p',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ','p'},
			{' ','R',' ',' ',' ',' ',' ',' '},
			{'P','P',' ',' ',' ',' ','P',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	char[][] chessFieldComparator = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'p',' ',' ',' ',' ',' ',' ',' '},
			{'p',' ',' ',' ',' ',' ',' ',' '},
			{'P',' ',' ',' ',' ',' ',' ',' '},
			{' ','R',' ',' ',' ',' ','p',' '},
			{' ','P',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	GuiControllerBoard.movement = moves;
	List<String> movesPassant = new ArrayList<>();
	//movesPassant.add("g2");
	movesPassant.add("g2-g4");
	
	
	String turn1 = "a7-a6";
	String turn2 = "a2-a4";
	String turn3 = "a4-a5";
	String turn4 = "b2-b3";
	String turn5 = "b2-f5";
	String turn6 = "g2-g4";
	String turn7 = "h4-g3";
	
	
	// Test movement
	move.moveFigure(turn1, v, 1, moves);
	
	move.moveFigure(turn2, v, 2, moves);
	
	// Test if other figure is between original position and destination
	move.moveFigure(turn3, v, 2, moves);
	
	// Test if own figure is on destination
	move.moveFigure(turn4, v, 2, moves);
	
	// Test invalid movement
	move.moveFigure(turn5, v, 2, moves);
	
	// Test en passant
	move.moveFigure(turn6, v, 8, moves);
	GuiControllerBoard.movement = movesPassant;
	move.moveFigure(turn7, v, 9, movesPassant);
	
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
			{'p',' ',' ',' ','p',' ',' ',' '},
			{' ','P',' ',' ',' ','P',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	char[][] chessFieldComparator = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ','P',' ',' ',' '},
			{' ','p',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	List<String> moves = new ArrayList<>();
	
	String turn1 = "f3-e4";
	String turn2 = "a4-b3";

	// Testing
	move.moveFigure(turn1, v, 2, moves);
	
	move.moveFigure(turn2, v, 3, moves);
	
	assertArrayEquals(chessFieldComparator, m.getChessField());
	}
	
	/**
	 * 
	 * Test for the conversion of a pawn at the opposing end of the field.
	 *
	 */
	@Test
	public void testConvertPawn() {
		// Setup
		Pawn p = new Pawn();
		char prospectivePawnConversionUpper = 'Q';
		char prospectivePawnConversionLower = 'q';
		int[] decodedMoveUpperWork = {0,0,0,0};
		int[] decodedMoveUpperFail = {0,0,0,1};
		int[] decodedMoveLowerWork = {0,0,0,7};
		int[] decodedMoveLowerFail = {0,0,0,0};
		
		boolean[] trues = new boolean[4];
		trues[0] = true;
		trues[1] = false;
		trues[2] = true;
		trues[3] = false;
		
		boolean[] checkResult = new boolean[4];
		
		// Upper
		checkResult[0] = p.convertPawn(prospectivePawnConversionUpper, decodedMoveUpperWork);
		checkResult[1] = p.convertPawn(prospectivePawnConversionUpper, decodedMoveUpperFail);
		
		// Lower
		checkResult[2] = p.convertPawn(prospectivePawnConversionLower, decodedMoveLowerWork);
		checkResult[3] = p.convertPawn(prospectivePawnConversionLower, decodedMoveLowerFail);
		
		assertArrayEquals(trues,checkResult);
		
	}
	
	/**
	 * 
	 * Test for the position of a opponent when moving a pawn.
	 *
	 */
	@Test
	public void testOpponentFigureAtDestination() {
	Model m = new Model();
	char[][] chessField = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ','p','P',' ',' ',' ','P'},
			{'p','P',' ',' ',' ','p',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Pawn p = new Pawn();
	int[] decodedMoveUpperWork = {3,3,2,2};
	int[] decodedMoveUpperFail = {7,3,6,2};
	int[] decodedMoveLowerWork = {0,4,1,5};
	int[] decodedMoveLowerFail = {5,4,6,5};
	
	boolean[] trues = new boolean[4];
	trues[0] = true;
	trues[1] = false;
	trues[2] = true;
	trues[3] = false;
	
	boolean[] checkResult = new boolean[4];
	checkResult[0] = p.opponentFigureBeside(v, 'P', decodedMoveUpperWork);
	checkResult[1] = p.opponentFigureBeside(v, 'P', decodedMoveUpperFail);
	checkResult[2] = p.opponentFigureBeside(v, 'p', decodedMoveLowerWork);
	checkResult[3] = p.opponentFigureBeside(v, 'p', decodedMoveLowerFail);
	
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
			{' ',' ',' ',' ',' ','p',' ','p'},
			{' ',' ',' ','k',' ',' ','K',' '},
			{' ',' ','P',' ','P',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Pawn p = new Pawn();
	char[] opponentKingAndOwnFigureUpper = {'k','P'};
	char[] opponentKingAndOwnFigureLower = {'K','p'};
	
	boolean[] trues = new boolean[4];
	Arrays.fill(trues, true);
	boolean[] checkResult = new boolean[4];
	
	// For Upper
	checkResult[0] = p.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 5, 2);
	checkResult[1] = p.checkPossibleCheck(v, opponentKingAndOwnFigureUpper, 5, 4);
	
	// For Lower
	checkResult[2] = p.checkPossibleCheck(v, opponentKingAndOwnFigureLower, 3, 5);
	checkResult[3] = p.checkPossibleCheck(v, opponentKingAndOwnFigureLower, 3, 7);
	
	assertArrayEquals(trues,checkResult);
	
	}
}
