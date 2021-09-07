package view;

@SuppressWarnings("PMD.DataClass")
//this class should only contain bools for activated buttons and therefore is a data class

/**
 * stores variables, that are storing if a button is activated
 */
public class ButtonsActivated {
	
	
	private boolean checkNotificationActivated;
	private boolean highlightActivated;
	private boolean reselectFigure;
	private boolean flipButtonClicked;
	private String button= "button";
	
	/**
	 * initializes the variables
	 */
	public ButtonsActivated() {
		this.checkNotificationActivated = false;
		this.highlightActivated = false;
		this.reselectFigure = false;
		this.flipButtonClicked = false;
		
	}

	public String getButton() {
		return button;
	}

	public boolean isCheckNotificationActivated() {
		return checkNotificationActivated;
	}

	public void setCheckNotificationActivated(boolean checkNotificationActivated) {
		this.checkNotificationActivated = checkNotificationActivated;
	}

	public boolean isHighlightActivated() {
		return highlightActivated;
	}

	public void setHighlightActivated(boolean highlightActivated) {
		this.highlightActivated = highlightActivated;
	}

	public boolean isReselectFigure() {
		return reselectFigure;
	}

	public void setReselectFigure(boolean reselectFigure) {
		this.reselectFigure = reselectFigure;
	}

	public boolean isFlipButtonClicked() {
		return flipButtonClicked;
	}

	public void setFlipButtonClicked(boolean flipButtonClicked) {
		this.flipButtonClicked = flipButtonClicked;
	}
	
}
