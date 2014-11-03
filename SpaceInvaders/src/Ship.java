import java.awt.image.BufferedImage;


public class Ship extends DualCoordinateImage{

	Ship(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	public void moveLeft() {
		x -= TunableParameters.ShipSpeed;
	}
	
	public void moveRight() {
		x += TunableParameters.ShipSpeed;
	}
}
