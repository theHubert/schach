package view;

import model.Model;

/**
 * 
 * The class view prints out the current state of the chess field.
 *
 */
public class ConsoleView implements ViewContainer{
	
	// prints out chess-field for console-based frontend
	
	private Model model;
	
	/**
	 * Prints out the message "Please enter move (e.g. 'e2-e4'):" in selected language.
	 */
	
	public void printOutEnterMove() {
		System.out.println(model.getCurrent().getString("enterMove"));
	}
	
	/**
	 * Prints out the question "which opponent would you like to play against?" in the selected language
	 */
	public void printOutWhichOpponent() {
		System.out.println(model.getCurrent().getString("WhichOpponent"));
	}
	
	/**
	 * Prints out the current state of the chess field.
	 */

	public void printOutChessField(){
	    System.out.println(" ");
	    System.out.println("    a b c d e f g h");
	    System.out.println("   _ _ _ _ _ _ _ _ _");
	    
	    String line = "";
	    for(int i=0; i<model.getChessField().length; i++) {
	    	line = "";
	    	for(int j=0; j<model.getChessField()[i].length; j++) {
	    		line += model.getChessField()[i][j];
	    		line += " ";
	    	}
	    	int lineNumber = 8-i;
	    	System.out.println(lineNumber + " | " + line + "| " + lineNumber);
	    }
	    
	    System.out.println("   _ _ _ _ _ _ _ _ _");
	    System.out.println("    a b c d e f g h");
	    
	}
	@Override
	public Model getModel() {
		return model;
	}
	@Override
	public void setModel(Model model) {
		this.model = model;
	}
	
	/**
	 * getCopyOfView copies the current view and returns it
	 * 
	 * @return a copy of the current view
 
	 */
	@Override
	public ViewContainer getCopyOfView() {
		ViewContainer v = new ConsoleView();
		v.setModel(this.model.getCopyOfModel());
		//v.model = this.model.getCopyOfModel();
		return v;
	}
	

}
