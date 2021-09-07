package schach;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import view.ViewContainer;


/**
 * 
 * The class FileHandler enables writing list elements to a file and reading them out. 
 *
 */
public class FileHandler {
	
	
	private static final String LINE_SEPARATOR = "line.separator";
	private static Charset UTF8 = Charset.forName("UTF-8");
	private int top; // points to position, where the next file can be included
	
	
	/**
	 * writes header information and moves out of list to a file => used for storing of game
	 * @param v currently used View
	 * @param moves list of move elements that should be written into body of file
	 * @param opponent of game that should be stored
	 */
	public void writeMovementsToFile(ViewContainer v, List<String> moves, String opponent) {
		try {
			
			// read in top 
			readInTop();
			
			Writer writerMoves = new OutputStreamWriter(new FileOutputStream("src/main/resources/schach/StoredMovements" + top + ".txt"), UTF8);
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now(); 
			
            // header
            writerMoves.write(v.getModel().getCurrent().getString("dateTime") + dtf.format(now));
            writerMoves.write(System.getProperty(LINE_SEPARATOR));   // write new line for corresponding operating system
            writerMoves.write(v.getModel().getCurrent().getString("currentOpponent") + opponent);
            writerMoves.write(System.getProperty(LINE_SEPARATOR));
            writerMoves.write("---");
            
            // body
            for(String move : moves) {
            	writerMoves.write("\r\n");
            	writerMoves.write(move);
            }
            
            // increment pointer
            Writer writerTop = new OutputStreamWriter(new FileOutputStream("src/main/resources/schach/top.txt"), UTF8);
            top++;
            writerTop.write(String.valueOf(top));
            writerTop.close();
            
            writerMoves.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		//System.exit(0);
	}
	
	/**
	 * reads out move elements, stored in a file, and adds them to a list => used for loading of the previous game
	 * @param v currently used view
	 * @param index of game that should be loaded
	 * @return List of moves in notation 'e2-e4'
	 */
	public List<String> getMovementsFromFile(ViewContainer v, int index){
		try {
			List<String> loadMoves = new ArrayList<>();
            FileReader reader = new FileReader("src/main/resources/schach/StoredMovements" + index + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
            
            bufferedReader.readLine();
            bufferedReader.readLine();
            bufferedReader.readLine();
 
            while ((line = bufferedReader.readLine()) != null) {
  
                loadMoves.add(line);
            }
            reader.close();
       
            return loadMoves;
 
        } catch (IOException e) {
        	System.out.println(v.getModel().getCurrent().getString("loadError"));
            //e.printStackTrace();
        }
		
		return null;
	}
	
	
	/**
	 * Creates a string with information about all stored games and the index, that can be used to access them.
	 * @param v currently used View
	 * @return String with information about all stored games and the index, that can be used to access them
	 */
	public String showStoredGames(ViewContainer v) {

		String storedGames = "------------------------------------------------ " + System.getProperty(LINE_SEPARATOR);
		readInTop();
		
		for(int i=0; i<top; i++) {
			String fileName = "src/main/resources/schach/StoredMovements" + i + ".txt";
			
			try {
				FileReader reader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(reader);
				
				String line;
				
				storedGames += v.getModel().getCurrent().getString("gameNumber") + i;
                storedGames += " " + System.getProperty(LINE_SEPARATOR);
				
                // update internationalization for date and time
                String dateAndTime = bufferedReader.readLine(); 
                String [] partsDT = dateAndTime.split(":");
                storedGames += v.getModel().getCurrent().getString("dateTime") + partsDT[1];
                storedGames += " " + System.getProperty(LINE_SEPARATOR);

                
                // opponent not shown for showStored
                bufferedReader.readLine(); 
                
				while ((line = bufferedReader.readLine()) != null) {
					
	                storedGames += line;
	                storedGames += " " + System.getProperty(LINE_SEPARATOR);
	            }
                storedGames += " " + System.getProperty(LINE_SEPARATOR);
				storedGames += "------------------------------------------------";
                storedGames += " " + System.getProperty(LINE_SEPARATOR);

	            reader.close();    
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return storedGames;
	}
	
	/**
	 * reads in position of top pointer from text file => storing position also if game is closed
	 */
	private void readInTop(){
		// read in top from text file
		try {
			FileReader reader = new FileReader("src/main/resources/schach/top.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String input = bufferedReader.readLine();
			top = Integer.parseInt(input);
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Exception File not found. Index error.");
		} catch (IOException e) {
			System.out.println("Exception IO. Index error.");
		}
	}
}