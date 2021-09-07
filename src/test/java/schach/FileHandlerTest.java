package schach;

import org.junit.jupiter.api.Test;

import model.Model;
import view.ConsoleView;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The class FileHandlerTest contains the tests for the FileHandler class.
 *
 */
public class FileHandlerTest {
	/**
	 * 
	 * Test for writing the movements made in a game into a file.
	 *
	 */
	@Test
	public void testWriteMovementsToFile() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//Setup
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		List<String> moves = new ArrayList<>();
		moves.add("a2-a3");
		FileHandler f = new FileHandler();
		f.writeMovementsToFile(v, moves, "player");
		
		Field privateFieldTop = f.getClass().getDeclaredField("top");
		privateFieldTop.setAccessible(true);
		int top = (int) privateFieldTop.get(f)-1;
		
		FileReader reader = new FileReader("src/main/resources/schach/StoredMovements" + top + ".txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
		String x = bufferedReader.readLine();
		x = bufferedReader.readLine();
		x = bufferedReader.readLine();
		x = bufferedReader.readLine();
		
		assertEquals("a2-a3", x);
		reader.close();
	}
	
	/**
	 * 
	 * Test for getting the movements made in a game from a file.
	 *
	 */
	@Test
	public void testGetMovementsFromFile() {
		//Setup
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		FileHandler f = new FileHandler();
		List<String> moves = new ArrayList<>();
		moves.add("a1-h3");
		f.writeMovementsToFile(v, moves, "player");
		
		int top = 0;
		try {
			FileReader reader = new FileReader("src/main/resources/schach/top.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String input = bufferedReader.readLine();
			top = Integer.parseInt(input)-1;
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		assertEquals(moves, f.getMovementsFromFile(v,top));
	}

	/**
	 * 
	 * Test for showing all saved games and the moves made in them.
	 *
	 */
	@Test
	public void testShowStoredGames() {
	//Setup
		Model m = new Model();
		ConsoleView v = new ConsoleView();
		v.setModel(m);
		FileHandler f = new FileHandler();
		int top = 0;
		try {
			FileReader reader = new FileReader("src/main/resources/schach/top.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String input = bufferedReader.readLine();
			top = Integer.parseInt(input)-1;
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(top+1, f.showStoredGames(v).split("Game").length-1);
	}
}
