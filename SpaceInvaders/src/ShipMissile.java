import java.awt.image.BufferedImage;


public class ShipMissile extends Missile {

	ShipMissile(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	public void move() {
		y -= getImage().getHeight() / 3; //TODO move 3 to a tunable parameter?
	}
}
