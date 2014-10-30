import java.awt.Point;
import java.awt.image.BufferedImage;


public class Background extends DualCoordinateImage {
	
	Background(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	public void move() {
		y--;
		if(y < -image.getHeight())
			y = 0;
	}
}
