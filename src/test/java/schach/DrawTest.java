package schach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import controller.Draw;
import controller.Move;
import model.Model;
import view.ConsoleView;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class DrawTest contains the tests for all 4 possible draw scenarios.
 *
 */
public class DrawTest {
	/**
	 * 
	 * Test for the 4 possible draw scenarios.
	 *
	 */
	@Test
	public void testCheckDraw() {
	//Setup
		Model m = new Model();
		char[][] chessFieldDrawKings = {
				{' ',' ',' ',' ',' ',' ',' ','k'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'K',' ',' ',' ',' ',' ',' ',' '},
		};
		
		char[][] chessFieldDrawKnight = {
				{' ',' ',' ',' ',' ',' ',' ','k'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'K','N',' ',' ',' ',' ',' ',' '},
		};
		
		char[][] chessFieldDrawBishop = {
				{' ',' ',' ',' ',' ',' ','b','k'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'K',' ',' ',' ',' ',' ',' ',' '},
		};
		
		char[][] chessFieldDrawFail = {
				{' ',' ',' ',' ',' ',' ','b','k'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'K','N',' ',' ',' ',' ',' ',' '},
		};
		
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		Move move = new Move();
		
		// Draw with 2 kings only
		m.setChessField(chessFieldDrawKings);
		assertTrue(Draw.checkDraw(v, move));
		
		// Test for draw with kings and one knight
		m.setChessField(chessFieldDrawKnight);
		assertTrue(Draw.checkDraw(v, move));
		
		// Test for draw with kings and one bishop
		m.setChessField(chessFieldDrawBishop);
		assertTrue(Draw.checkDraw(v, move));
		
		// Test for draw fail
		m.setChessField(chessFieldDrawFail);
		assertFalse(Draw.checkDraw(v, move));

	}
}
