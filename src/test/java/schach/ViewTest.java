package schach;

import org.junit.jupiter.api.Test;

import model.Model;
import view.ConsoleView;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.ResourceBundle;

@SuppressWarnings("CPD-START")
//the cpd report is supressed in the test classes, since the setup of the
//chessfields is the same in every test 

/**
 * 
 * The class ViewTest contains tests for the methods of the class View.
 *
 */
public class ViewTest {
	/**
	 * 
	 * Test for the console output of the chessfield.
	 *
	 */
	@Test
	public void testPrintOutChessField() {
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		v.printOutChessField();
		
		assertEquals(" \n" + 
				"    a b c d e f g h\n" + 
				"   _ _ _ _ _ _ _ _ _\n" + 
				"8 | r n b q k b n r | 8\n" + 
				"7 | p p p p p p p p | 7\n" + 
				"6 |                 | 6\n" + 
				"5 |                 | 5\n" + 
				"4 |                 | 4\n" + 
				"3 |                 | 3\n" + 
				"2 | P P P P P P P P | 2\n" + 
				"1 | R N B Q K B N R | 1\n" + 
				"   _ _ _ _ _ _ _ _ _\n" + 
				"    a b c d e f g h\n" , os.toString()); 
	}
	/**
	 * 
	 * Test for the console output of a move.
	 *
	 */
	@Test
	public void testPrintOutEnterMove() {
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
	    m.setCurrent(ResourceBundle.getBundle("schach/Bundle", new Locale("en", "US")));
		v.printOutEnterMove();
		
		assertEquals("Please enter move (e.g. \'e2-e4\'):\n" , os.toString()); 
	}
}
