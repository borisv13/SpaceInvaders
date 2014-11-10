import java.awt.image.BufferedImage;


public abstract class Missile extends DualCoordinateImage{

	
	Missile(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	abstract public void move();
}
