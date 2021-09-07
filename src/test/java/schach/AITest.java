package schach;

import org.junit.jupiter.api.Test;

import controller.AI;
import model.Model;
import view.ConsoleView;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class AITest contains all tests related to the behavior of the AI.
 *
 */
public class AITest {
	/**
	 * 
	 * Test for the best move in a specific scenario.
	 *
	 */
	@Test
	public void testCalcBestMove() {
		Model m = new Model();
		char[][] chessField = {
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'p',' ',' ',' ',' ',' ',' ','p'},
				{' ',' ',' ',' ',' ',' ','P',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'P',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
		};
		m.setChessField(chessField);
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		List<String> moves = new ArrayList<>();
		AI ai = new AI();
		
		assertEquals("h7-g6", ai.calcBestMove(v, 1, moves, 'p', 2));
	}
	/**
	 * 
	 * Test for the balance of a game state.
	 *
	 */
	@Test
	public void testEvaluateBoard() {
		Model m = new Model();
		char[][] chessField = {
				{'r','n','b','q','k','b','n','r'},
				{'p','p','p','p','p','p','p','p'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'P','P','P','P','P','P','P','P'},
				{'R','N','B','Q','K','B','N','R'},
		};
		m.setChessField(chessField);
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		AI ai = new AI();
		
		assertEquals(0, ai.evaluateBoard(v, 'p'));
	}
}
