package schach;


import org.junit.jupiter.api.Test;

import controller.Bishop;
import controller.Figure;
import controller.Move;
import controller.Pawn;
import model.Model;
import view.ConsoleView;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class MoveTest contains tests for examples of valid and invalid moves.
 *
 */
public class MoveTest {
	/**
	 * 
	 * Test for moves except for moves from the wrong player.
	 *
	 */
    @Test
    public void testMoveFigure() {
    	//Setup
    	Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		Move move = new Move();
		List<String> moves = new ArrayList<>();
		char[][] chessFieldComparator = {
				{'r','n','b','q','k','b','n','r'},
				{' ','p','p','p','p','p','p','p'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'P',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'P',' ','P','P','P','P','P','P'},
				{'R','N','B','Q','K','B','N','R'},
		};
    	
		String turn1 = "b2-b4";
		String turn2 = "a7-a5";
		String turn3 = "b4-a5";
		String turn4 = "a1-b4";
		String turnError = "a2a3";

		
		//Test if the figure was moved
		move.moveFigure(turn1, v, 2, moves);
		
		//Test for figure gets beaten
		move.moveFigure(turn2, v, 3, moves);
		move.moveFigure(turn3, v, 4, moves);
		
		//Test for not allowed move
		move.moveFigure(turn4, v, 5, moves);
		move.moveFigure(turnError, v, 7, moves);
		
		assertArrayEquals(chessFieldComparator, m.getChessField());
		
    }
    
    /**
	 * 
	 * Test for incorrect move (move from incorrect player)
	 *
	 */
   @Test
    public void testCorrectFigure() {
    	Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		Move move = new Move();
		char[][] chessFieldComparator = {
				{'r','n','b','q','k','b','n','r'},
				{'p','p','p','p','p','p','p','p'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'P','P','P','P','P','P','P','P'},
				{'R','N','B','Q','K','B','N','R'},
		};
		
    	String turn1 = "b2-b4";
    	List<String> moves = new ArrayList<>();
    	move.moveFigure(turn1, v, 1, moves);
    	
    	assertArrayEquals(chessFieldComparator, m.getChessField());
    }
   /**
	 * 
	 * Test for non-queen conversions of pawns
	 *
	 */
   
   @Test
   public void testSetProspectivePawnConversion() {
	   // Setup
	   Move move = new Move();
	   Map<Character, Figure> decodeFigure = new HashMap<Character, Figure>();
	   decodeFigure.put('B', new Bishop());
	   
	   try {
		assertEquals('B', move.setProspectivePawnConversion("a1-a2B", 'P', decodeFigure));
	} catch (Exception e) {
		e.printStackTrace();
	}
	   try {
		   move.setProspectivePawnConversion("a1-a2T", 'P', decodeFigure);
	   } catch (Exception e) {
		   assertEquals(Exception.class, e.getClass());
	   }
   }
   
   /**
	 * 
	 * Test for en passant
	 *
	 */
   @Test
   public void testReactToMove() {
	 //Setup
   	Model m = new Model();
    char[][] chessField = {
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ',' ',' ',' '},
			{'p',' ',' ',' ','p',' ',' ',' '},
			{' ','P',' ',' ',' ','P',' ',' '},
	};
	m.setChessField(chessField);
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Move move = new Move();
	Pawn pawn = new Pawn();
	List<String> moves = new ArrayList<>();
	int[] decodedMoveUpper = {1,7,0,6};
	int[] decodedMoveLower = {4,6,5,7};
	move.setFigure(pawn);
	List<Character> storeBeatenFigures = new ArrayList<Character> ();
	storeBeatenFigures.add('p');
	storeBeatenFigures.add('P');
	
	move.reactToMove(v,"a1-a2" , moves, decodedMoveUpper);
	move.reactToMove(v,"a1-a2" , moves, decodedMoveLower);
	
	assertEquals(storeBeatenFigures, move.getBeatenFigures().getStoreBeatenFigures());
   }
   
}
