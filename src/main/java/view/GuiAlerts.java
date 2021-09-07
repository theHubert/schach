package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Model;

/**
 * 
 * The class GuiAlerts stores the information of all alerts used. 
 *
 */
public class GuiAlerts {
	
	Model model; // model for accessing current bundle
	
	/**
	 * Initializes a new GuiAlerts object with the currently used model.
	 * @param model
	 */
	public GuiAlerts(Model model) {
		this.model = model;
	}
	
	/**
	 * Enables accessing alert for mate.
	 */
	public void showAlertMate() {
		Alert alertMate = new Alert(AlertType.INFORMATION);
		alertMate.setTitle(model.getCurrent().getString("mate"));
		alertMate.setHeaderText(null);
		alertMate.setContentText(model.getCurrent().getString("mate"));
		alertMate.showAndWait();
	}
	
	/**
	 * Enables accessing alert for draw.
	 */
	public void showAlertDraw() {
		Alert alertDraw = new Alert(AlertType.INFORMATION);
		alertDraw.setTitle(model.getCurrent().getString("draw"));
		alertDraw.setHeaderText(null);
		alertDraw.setContentText(model.getCurrent().getString("draw"));
		alertDraw.showAndWait();
	}
	
	/**
	 * Enables accessing alert for check.
	 */
	public void showAlertCheck() {
		Alert alertCheck = new Alert(AlertType.INFORMATION);
		alertCheck.setTitle(model.getCurrent().getString("schach"));
		alertCheck.setHeaderText(null);
		alertCheck.setContentText(model.getCurrent().getString("schach"));
		alertCheck.showAndWait();
	}
	
	/**
	 * Enables accessing alert for invalid input index for loading a game in main menu.
	 */
	public void showAlertIndex() {
		Alert alertIndex = new Alert(AlertType.INFORMATION);
		alertIndex.setTitle(model.getCurrent().getString("invalidInput"));
		alertIndex.setHeaderText(null);
		alertIndex.setContentText(model.getCurrent().getString("invalidInputForIndex"));
		alertIndex.showAndWait();
	}
}
