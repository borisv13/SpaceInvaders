import java.awt.image.BufferedImage;


public class AlienMissile extends Missile{

	AlienMissile(BufferedImage image, int x, int y) {
		super(image, x, y);
	}
	
	public void move() {
		y += getImage().getHeight() / TunableParameters.MissileSpeed;
	}
}
