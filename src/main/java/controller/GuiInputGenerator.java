package controller;

/**
 * 
 * The class GuiInputGenerator enables to generate an input string out of the clicked origin and destination positions,
 * that can be used by class Move for updating the model.
 *
 */
public class GuiInputGenerator {
	
	/**
	 * Generates the input as string, so that it can be used by method moveFigure 
	 * @param colAndRowAndI stores position and counter (for executed moves) of destination
	 * @return input as string
	 */
	public String generateInput(int[] colAndRowAndI, int[] storeLastClickCRI) {
		MoveGenerator moveGenerator = new MoveGenerator();
		String generatedInput = "";
		generatedInput += moveGenerator.encodeMoveX.get(storeLastClickCRI[0]);
		generatedInput += moveGenerator.encodeMoveY.get(storeLastClickCRI[1]);
		generatedInput += '-';
		generatedInput += moveGenerator.encodeMoveX.get(colAndRowAndI[0]);
		generatedInput += moveGenerator.encodeMoveY.get(colAndRowAndI[1]);
		
		
		return generatedInput;
	}

}
