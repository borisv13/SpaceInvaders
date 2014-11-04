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
	
	public Missile fireMissile() {
		Missile newMissile = Factory.createMissile(this.getX(), 0);
		newMissile.setY(this.getY() - newMissile.getImage().getHeight());
		return newMissile;
	}
}
