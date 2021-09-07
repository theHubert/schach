package schach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import controller.ConsoleController;
import controller.Move;
import model.Model;
import view.ConsoleView;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test

/**
 * 
 * The class ControllerTest1 contains the first part of the tests for the Controller class.
 *
 */
public class ControllerTest{
	
	/**
	 * 
	 * Test for toggling upper and lower case.
	 *
	 */
	@Test
	public void testToggleChar() {
		ConsoleController c = new ConsoleController();
		c.toggleChar('p');
		assertEquals('p', c.toggleChar('P'));
		
	}
	
	/**
	 * 
	 * Test for the different input options.
	 * 
	 */
	@Test
	public void testCheckDifferentInput() {
		//Setup
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		ConsoleController c = new ConsoleController();
		Move move = new Move();
		c.moves.add("a2-a4");
		c.moves.add("a2-a4");
		c.moves.add("a2-a4");
		c.setMove(move);
		boolean[] bool = new boolean[8];
		Arrays.fill(bool, true);
		bool[7] = false;
		boolean[] result = new boolean[8];
		String player = "player";
		String computer = "computer";
		
		// Test for German
		result[0] = c.checkDifferentInput("german", v, player);
	
		// Test for English
		result[1] = c.checkDifferentInput("english", v, player);
		
		// Test for beaten figure output
		result[2] = c.checkDifferentInput("beaten", v, player);
		
		// Test for undo
		c.top = 1;
		result[3] = c.checkDifferentInput("undo", v, player);
		c.top = 2;
		result[4] = c.checkDifferentInput("undo", v, computer);
		
		// Test for redo
		result[5] = c.checkDifferentInput("redo", v, player);
		c.top = 1;
		result[6] = c.checkDifferentInput("redo", v, computer);
		
		// Test for fail
		result[7] = c.checkDifferentInput("FAIL!", v, player);
		
		assertArrayEquals(bool, result);
	}
	
	
	/**
	 * 
	 * Test for setting ai depth
	 * 
	 */
	@Test
	public void testSetdepth() {
		//Setup
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		ConsoleController c = new ConsoleController();
		Scanner scannerPass = new Scanner("4");
		Scanner scannerFail = new Scanner("9");
		
		c.setDepth(v, scannerPass);
		c.setDepth(v, scannerFail);
		assertEquals(v.getModel().getCurrent().getString("aiDepth") + "\n"+
				v.getModel().getCurrent().getString("aiDepth") + "\n" + 
				v.getModel().getCurrent().getString("aiException") + "\n", os.toString());
	}
	
	/**
	 * 
	 * Test for checking the final state of the game (draw)
	 * 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws NoSuchFieldException 
	 * 
	 */
	@Test
	@ExtendWith(NoSystemExitExtension.class)
	public void testCheckFinalSituationsDraw() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		//Setup
		char[][] chessFieldMate = {
				{' ',' ',' ',' ',' ','Q',' ','k'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ','Q'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'q',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'K',' ','q',' ',' ',' ',' ',' '},
		};
		char[][] chessFieldDraw = {
				{' ',' ',' ',' ',' ',' ',' ','k'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'K',' ',' ',' ',' ',' ',' ',' '},
		};
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		m.setChessField(chessFieldDraw);
		ConsoleController c = new ConsoleController();
		Move move = new Move();
		c.setMove(move);
		List<String> moves = new ArrayList<>();
		
		PrintStream sysOutBackup = System.out;
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		Method privateMethod = c.getClass().getDeclaredMethod("checkFinalSituations", ConsoleView.class, int.class, List.class);
		privateMethod.setAccessible(true);
		privateMethod.invoke(c, v, 1, moves);
		
		m.setChessField(chessFieldMate);
		privateMethod.invoke(c, v, 1, moves);
		
		assertEquals("Draw!", os.toString());
		
		System.setOut(sysOutBackup);
	}
	
	/**
	 * 
	 * Test for checking the final state of the game (mate)
	 * 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws NoSuchFieldException 
	 * 
	 */
	@Test
	@ExtendWith(NoSystemExitExtension.class)
	public void testCheckFinalSituationsMate() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		//Setup
		char[][] chessFieldCheck = {
				{' ',' ',' ',' ',' ',' ',' ','k'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ','Q'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'q',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'K',' ',' ',' ',' ',' ',' ',' '},
		};
		char[][] chessFieldMate = {
				{' ',' ',' ',' ',' ','Q',' ','k'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ','Q'},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'q',' ',' ',' ',' ',' ',' ',' '},
				{' ',' ',' ',' ',' ',' ',' ',' '},
				{'K',' ','q',' ',' ',' ',' ',' '},
		};
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		m.setChessField(chessFieldCheck);
		ConsoleController c = new ConsoleController();
		Move move = new Move();
		c.setMove(move);
		List<String> moves = new ArrayList<>();
		
		PrintStream sysOutBackup = System.out;
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		
		Method privateMethod = c.getClass().getDeclaredMethod("checkFinalSituations", ConsoleView.class, int.class, List.class);
		privateMethod.setAccessible(true);
		privateMethod.invoke(c, v, 1, moves);
		
		m.setChessField(chessFieldMate);
		privateMethod.invoke(c, v, 1, moves);
		
		assertEquals("Checkmate!", os.toString());
		
		System.setOut(sysOutBackup);
	}
	
	/**
	 * 
	 * Test for updating the AI
	 * 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * 
	 */
	@Test
	public void testUpdateAiMove() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		ConsoleController c = new ConsoleController();
		Move move = new Move();
		c.setMove(move);
		List<String> moves = new ArrayList<>();
		
		Method privateMethod = c.getClass().getDeclaredMethod("updateAiMove", ConsoleView.class);
		privateMethod.setAccessible(true);
		privateMethod.invoke(c, v);
		
		assertEquals("[]", moves.toString());
		
	}
	
	/**
	 * 
	 * Test for loading for computer and player
	 * 
	 */
	@Test
	public void testLoadGame() {
	Model m = new Model();
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Scanner scanner = new Scanner(System.in);
	ConsoleController c = new ConsoleController();
	InputStream sysInBackup = System.in;
	System.out.println(v.getModel().getCurrent().getString("yes"));
	
	// No
	ByteArrayInputStream in = new ByteArrayInputStream("N".getBytes());
	System.setIn(in);
	assertThrows(NoSuchElementException.class, () -> c.loadGame(v, scanner, "PLAYER"));
	
	assertThrows(NoSuchElementException.class, () -> c.loadGame(v, scanner, "COMPUTER"));
	
	// Yes
	in = new ByteArrayInputStream("Y".getBytes());
	System.setIn(in);
	assertThrows(NoSuchElementException.class, () -> c.loadGame(v, scanner, "COMPUTER"));
	
	// Nonsense
	in = new ByteArrayInputStream("42".getBytes());
	System.setIn(in);
	assertThrows(NoSuchElementException.class, () -> c.loadGame(v, scanner, "PLAYER"));
	
	
	//Reset
	System.setIn(sysInBackup);
	}
	
	/**
	 * 
	 * Test for setting the view for computer and player
	 * 
	 */
	@Test
	public void testSetView() {
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		ConsoleController c = new ConsoleController();
		
		// VS player
		ByteArrayInputStream in = new ByteArrayInputStream("player".getBytes());
		System.setIn(in);
		assertThrows(NoSuchElementException.class, () -> c.setView(v));
		
		// VS computer
		in = new ByteArrayInputStream("computer".getBytes());
		System.setIn(in);
		assertThrows(NoSuchElementException.class, () -> c.setView(v));
		
		// VS nonsense
		in = new ByteArrayInputStream("42".getBytes());
		System.setIn(in);
		assertThrows(NoSuchElementException.class, () -> c.setView(v));
	}
	
	
	/**
	 * 
	 * Test for setting the player view
	 * 
	 */
	@Test
	public void testSetViewPlayer() {
	Model m = new Model();
	ConsoleView v = new ConsoleView();
	v.setModel(m);
	Scanner scanner = new Scanner(System.in);
	ConsoleController c = new ConsoleController();
	InputStream sysInBackup = System.in;
	System.out.println(v.getModel().getCurrent().getString("yes"));
	
	ByteArrayInputStream in = new ByteArrayInputStream("N".getBytes());
	System.setIn(in);
	assertThrows(NullPointerException.class, () -> c.setViewPlayer(v, scanner));
	
	//Reset
	System.setIn(sysInBackup);
	}
	
	/**
	 * 
	 * Test for different languages and inputs (mensch/player)
	 * 
	 */
	/*@Test
	public void testPlayerSelected() {
		//Setup
		Model m = new Model();
		View v = new View();
		v.setModel(m);
		Controller c = new Controller();
		boolean[] bool = new boolean[2];
		bool[0] = true;
		bool[1] = false;
		boolean[] result = new boolean[2];
		
		result[0] = c.playerSelected("player", v);
		result[1] = c.playerSelected("mensch", v);
		
		assertArrayEquals(bool, result);
	}*/
	
	
	
	
	
}
