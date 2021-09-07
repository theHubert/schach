package schach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import controller.MoveGenerator;
import model.Model;
import view.ConsoleView;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class MoveGeneratorTest contains tests for listing all possible moves.
 *
 */
public class MoveGeneratorTest {
	/**
	 * 
	 * Test for the list of all possible moves in a specific scenario.
	 *
	 */
	@Test
	public void testGenerateMoves() {
		Model m = new Model();
		char[][] chessField = {
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ','p'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
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
		MoveGenerator mg = new MoveGenerator();
		List<String> possibleMovesUpper = new ArrayList<String>();
		possibleMovesUpper.add("a2-a4");
		possibleMovesUpper.add("a2-a3");
		List<String> possibleMovesLower = new ArrayList<String>();
		possibleMovesLower.add("h7-h6");
		possibleMovesLower.add("h7-h5");
		
		assertEquals(possibleMovesUpper, mg.generateMoves(v, 2, moves, 'P'));
		assertEquals(possibleMovesLower, mg.generateMoves(v, 3, moves, 'p'));
	}
	
	/**
	 * 
	 * Test for the list of all possible moves for a specific figure.
	 *
	 */
	@Test
	public void testPossibleDestinationsForClickedFigure() {
		Model m = new Model();
		char[][] chessField = {
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
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
		MoveGenerator mg = new MoveGenerator();
		int[] posXYandI = {0,6,0};
		List<Number> possibleDestinations = new ArrayList<Number>();
		possibleDestinations.add(0);
		possibleDestinations.add(4);
		possibleDestinations.add(0);
		possibleDestinations.add(5);
		
		assertEquals(possibleDestinations, mg.possibleDestinationsForClickedFigure(v, posXYandI, moves, 'P'));
	}
}