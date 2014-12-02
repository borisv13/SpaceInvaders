package spaceinvaders;
import java.awt.image.BufferedImage;


public class ShipMissile extends Missile {

	ShipMissile(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	ShipMissile(ShipMissile source) {
		super(source);
	}

	public void move() {
		y -= getImage().getHeight() / TunableParameters.MissileSpeed;
	}
}
