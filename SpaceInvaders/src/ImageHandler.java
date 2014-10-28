import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageHandler {
	
	private static final String RELATIVE_PATH = "./resources/images/";
	
	BufferedImage ship;
	
	ImageHandler() {
		try {
			ship = ImageIO.read(new File(RELATIVE_PATH.concat(ImageNames.SHIP)));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	BufferedImage getShip() {
		return ship;
	}
}
