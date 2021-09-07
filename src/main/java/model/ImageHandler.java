package model;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * Class to get and set the Images for the specific figures.
 */
public class ImageHandler {
	
	private static ImageHandler instance;
	private Map<String, Image> imgs = new HashMap<String, Image>();
	
	/**
	 * Get method for instance of image handler.
	 */
	public static ImageHandler getInstance() {
		if (instance == null) {
			instance = new ImageHandler();
		}
		return instance;
	}
	
	/**
	 * Loads the correlating image for the figure.
	 * @param name String of the name of the figure.
	 * @param extension Format of picture.
	 */
	private void loadImage(String name, String... extensions) {
		URL url = null;
		for (String ext : extensions) {
			url = ImageHandler.class.getResource("/schach/" + name + "." + ext);
			if (url != null) {
				break;
			}
		}
		//System.out.println(url);
		Image image = new Image(url.toExternalForm(), true);
		imgs.put(name, image);
	}
	
	/**
	 * Get method for Image of figure
	 * @param key requested figure
	 */
	public Image getImage(final String key) {
		if (imgs.get(key) == null) {
			loadImage(key, "jpg", "png", "bmp", "gif");
		}
		return imgs.get(key);
	}

}
