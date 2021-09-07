package view;

import model.Model;

/**
 * 
 * Container for View of controller version and of gui version.
 *
 */
public interface ViewContainer {
	
	/**
	 * returns current version of stored model
	 * @return Model
	 */
	Model getModel();
	/**
	 * sets the model
	 * @param model
	 */
	void setModel(Model model);
	/**
	 * copies used ViewContainer
	 * @return copy of ViewContainer
	 */
	ViewContainer getCopyOfView();
}
