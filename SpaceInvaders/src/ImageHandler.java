import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageHandler {
	
	private static final String RELATIVE_PATH = "./resources/images/";
	
	private BufferedImage ship;
	private BufferedImage background;
	private BufferedImage alien;
	
	ImageHandler() {
		try {
			ship = ImageIO.read(new File(RELATIVE_PATH.concat(ImageNames.SHIP)));
			background = ImageIO.read(new File(RELATIVE_PATH.concat(ImageNames.BACKGROUND)));
			alien = ImageIO.read(new File(RELATIVE_PATH.concat(ImageNames.ALIEN)));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	BufferedImage getShip() {
		return ship;
	}
	
	BufferedImage getBackground() {
		return background;
	}

	BufferedImage getAlien() {
		return alien;
	}
}
