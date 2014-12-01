package spaceinvaders;
import java.awt.image.BufferedImage;


public class AlienMissile extends Missile{

	AlienMissile(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	AlienMissile(AlienMissile source) {
		super(source);
	}
	
	public void move() {
		y += getImage().getHeight() / TunableParameters.MissileSpeed;
	}
}
